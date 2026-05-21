package pe.gob.vuce.cp.sp.pagos.infrastructure.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConsumerAwareRecordRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;
import pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.response.ErrorMessageDLQDTO;

@Configuration
public class KafkaConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConfiguration.class);

    private final String dlqTopic;

    private final String intervalAttempts;

    private final String maxAttempts;

    public KafkaConfiguration(@Value("${spring.kafka.topics.dlq-topic}") String dlqTopic,
                       @Value("${spring.kafka.consumer.interval-attempts}") String intervalAttempts,
                       @Value("${spring.kafka.consumer.max-attempts}") String maxAttempts){
        this.dlqTopic = dlqTopic;
        this.intervalAttempts = intervalAttempts;
        this.maxAttempts = maxAttempts;
    }

    @Bean
    public DefaultErrorHandler errorHandler(KafkaTemplate<String, Object> kafkaTemplate) {

        /**
         * Definir el recoverer personalizado para enviar mensajes fallidos a la DLQ
         */
        ConsumerAwareRecordRecoverer recoverer = (consumerRecord, consumer, exception) -> {

            /**
             * 1. Crear el payload personalizado para la DLQ
             */
            ErrorMessageDLQDTO payloadPersonalizado = new ErrorMessageDLQDTO(consumerRecord.value(), exception,
                    consumerRecord.topic(),
                    consumerRecord.partition(),
                    consumerRecord.offset()
            );

            /**
             * 2. Definir el topic de destino (DLQ)
             */
            String targetTopic = dlqTopic;

            /**
             * 3. Enviar el mensaje al topic DLQ
             */
            kafkaTemplate.send(
                    targetTopic,
                    consumerRecord.partition(),
                    consumerRecord.key() != null ? consumerRecord.key().toString() : null,
                    payloadPersonalizado
            );

            /**
             * 4. Loguear el envío a la DLQ
             */
            logger.info("Sent message to DLQ topic: " + targetTopic);
        };

        FixedBackOff fixedBackOff = new FixedBackOff(Long.valueOf(intervalAttempts), Long.valueOf(maxAttempts));

        return new DefaultErrorHandler(recoverer, fixedBackOff);
    }

}
