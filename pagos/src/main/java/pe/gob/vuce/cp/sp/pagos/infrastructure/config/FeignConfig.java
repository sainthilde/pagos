package pe.gob.vuce.cp.sp.pagos.infrastructure.config;

import feign.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.BASIC;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.HEADERS;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.FULL;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.ORDEN_PAGO_SUNAT;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.DOCUMENT_CLIENT;
/**
 * Clase de configuración para personalizar el comportamiento del cliente Feign.
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Configuration
@SuppressWarnings("all")
public class FeignConfig {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(FeignConfig.class);
    private Logger.Level level;

    /**
     * Configura el nivel de registro para el cliente Feign basado en la propiedad de configuración.
     *
     * @param loggerLevel El nivel de registro especificado en la configuración (por defecto es FULL).
     * @return El nivel de registro configurado para Feign.
     */
    @Bean
    public Logger.Level feignLoggerLevel(@Value("${feign.client.config.default.loggerLevel:FULL}") String loggerLevel) {
        switch (loggerLevel.toUpperCase()) {
            case BASIC:
                level = Logger.Level.BASIC;
                break;
            case HEADERS:
                level = Logger.Level.HEADERS;
                break;
            case FULL:
            default:
                level = Logger.Level.FULL;
                break;
        }

        return level;
    }

    /**
     * Crea un logger personalizado para el cliente Feign que omite ciertos métodos de registro.
     *
     * @return Un objeto Logger que se utilizará para registrar las solicitudes y respuestas de Feign.
     */
    @Bean
    public Logger feignLogger() {
        return new Logger() {
            @Override
            protected void log(String configKey, String format, Object... args) {
                if (!configKey.contains(ORDEN_PAGO_SUNAT) && !configKey.contains(DOCUMENT_CLIENT)) {
                    if (level == Logger.Level.FULL) {
                        logger.info("[Feign Logger] {} - {}", configKey, String.format(format, args));
                    }
                }
            }
        };
    }
}