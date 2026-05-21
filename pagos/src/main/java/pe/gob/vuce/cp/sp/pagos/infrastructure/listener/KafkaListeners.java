package pe.gob.vuce.cp.sp.pagos.infrastructure.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import pe.gob.vuce.cp.framework.globallogger.annotation.Loggable;
import pe.gob.vuce.cp.framework.globallogger.constants.LogTypes;
import pe.gob.vuce.cp.sp.pagos.domain.constants.Constants;
import pe.gob.vuce.cp.sp.pagos.domain.constants.SeguimientoUtils;
import pe.gob.vuce.cp.sp.pagos.domain.exception.KafkaListenerException;
import pe.gob.vuce.cp.sp.pagos.domain.model.OrdenPago;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.CreateSeguimientoUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.UpdateArchivoPDFUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.SelectOrdenPagoUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.UpdateOrdenPagoUseCase;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.SeguimientoRequestDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.request.NotificarExpiracionDTO;
import pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.request.NotificarExtornoDTO;
import pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.response.AcuseReciboDTO;
import pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.request.NotificarAnulacionDTO;
import pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.request.NotificarPagoDTO;
import pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.response.OrdenPagoResponseDTO;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.PENDIENTE_PAGO;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.PP;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.PG;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.DOCUMENT_SEGUI;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.PAGADO;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.ANULADO;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.AN;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.EXTORNADO;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.EX;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.NOTIFICATION_TOPIC_STATUS_EXPIRED;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.PAYMENT_ORDER_STATUS_EXPIRED;

/**
 * La clase KafkaListeners escucha eventos Kafka en diversos tópicos
 * relacionados con el
 * estado de las órdenes de pago, tales como notificación de pago pendiente,
 * pago,
 * anulación, y extorno. Cada método en esta clase está suscrito a un tópico
 * específico,
 * y actualiza el estado de la orden de pago correspondiente en el sistema según
 * la información recibida, además de enviar un mensaje de confirmación al
 * tópico de respuesta.
 *
 * <p>
 * Dependencias:
 * <ul>
 * <li>{@code ordenPagoService}: Servicio que gestiona las operaciones sobre la
 * entidad
 * {@code OrdenPago}, permitiendo actualizar el estado de la orden de pago.</li>
 * <li>{@code kafkaTemplate}: Plantilla de Kafka para enviar mensajes a tópicos
 * específicos.</li>
 * <li>{@code objectMapper}: Mapea JSON a objetos Java para facilitar el
 * procesamiento de mensajes.</li>
 * <li>{@code ordenPagoSunatService}: Servicio para realizar actualizaciones
 * adicionales
 * sobre la orden de pago, como la creación de archivos PDF en ciertos
 * casos.</li>
 * </ul>
 *
 * <p>
 * Configuración de Tópicos:
 * <ul>
 * <li>{@code notificacionPendienteResponseTopic}: Tópico para enviar
 * confirmaciones
 * de recepción de mensajes de notificación pendiente.</li>
 * <li>{@code notificacionPagoResponseTopic}: Tópico para enviar confirmaciones
 * de recepción de mensajes de notificación de pago.</li>
 * <li>{@code notificacionAnulacionResponseTopic}: Tópico para enviar
 * confirmaciones
 * de recepción de mensajes de notificación de anulación.</li>
 * </ul>
 *
 * <p>
 * Métodos principales:
 * <ul>
 * <li>{@code listenTopicNotificacionPendiente}: Escucha mensajes del tópico
 * de notificación de pago pendiente y actualiza la orden de pago con el estado
 * "PENDIENTE_PAGO". También genera un archivo PDF si es necesario.</li>
 * <li>{@code listenTopicNotificacionPago}: Escucha mensajes del tópico de
 * notificación de pago y actualiza la orden de pago con el estado
 * "PAGADO".</li>
 * <li>{@code listenTopicNotificacionAnulacion}: Escucha mensajes del tópico de
 * notificación de anulación y actualiza la orden de pago con el estado
 * "ANULADO".</li>
 * <li>{@code listenTopicNotificacionExtorno}: Escucha mensajes del tópico de
 * notificación de extorno y actualiza la orden de pago con el estado
 * "EXTORNADO".</li>
 * </ul>
 * 
 * @author Mateo Huancho
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Service
public class KafkaListeners {

    private static final Logger logger = LoggerFactory.getLogger(KafkaListeners.class);
    private static final String DATE_TIME_FORMAT = "yyyyMMdd HH:mm:ss";
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final CreateSeguimientoUseCase createSeguimientoUseCase;

    private String cpb = "";
    private final String notificacionPendienteResponseTopic;
    private final String notificacionPagoResponseTopic;
    private final String notificacionAnulacionResponseTopic;
    private final String notificacionExpiracionResponseTopic;

    private final UpdateArchivoPDFUseCase updateArchivoPDFUseCase;
    private final SelectOrdenPagoUseCase selectOrdenPagoUseCase;
    private final UpdateOrdenPagoUseCase updateOrdenPagoUseCase;

    private String userSegui = "PASARELADEPAGOS | PASARELADEPAGOS";
    private String userPago = "PASARELADEPAGOS";

    public KafkaListeners(
            KafkaTemplate<String, Object> kafkaTemplate,
            ObjectMapper objectMapper,
            CreateSeguimientoUseCase createSeguimientoUseCase,
            @Value("${spring.kafka.topics.notificacion-pendiente-response}") String notificacionPendienteResponseTopic,
            @Value("${spring.kafka.topics.notificacion-pago-response}") String notificacionPagoResponseTopic,
            @Value("${spring.kafka.topics.notificacion-anulacion-response}") String notificacionAnulacionResponseTopic,
            UpdateArchivoPDFUseCase updateArchivoPDFUseCase, SelectOrdenPagoUseCase selectOrdenPagoUseCase,
            UpdateOrdenPagoUseCase updateOrdenPagoUseCase,
            @Value("${spring.kafka.topics.notificacion-expiracion-response}") String notificacionExpiracionResponseTopic) {
        this.updateOrdenPagoUseCase = updateOrdenPagoUseCase;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.createSeguimientoUseCase = createSeguimientoUseCase;
        this.notificacionPendienteResponseTopic = notificacionPendienteResponseTopic;
        this.notificacionPagoResponseTopic = notificacionPagoResponseTopic;
        this.notificacionAnulacionResponseTopic = notificacionAnulacionResponseTopic;
        this.updateArchivoPDFUseCase = updateArchivoPDFUseCase;
        this.selectOrdenPagoUseCase = selectOrdenPagoUseCase;
        this.notificacionExpiracionResponseTopic = notificacionExpiracionResponseTopic;
    }

    /**
     * Escucha los mensajes del tópico notificacion-pendiente y actualiza el estado
     * de la orden de pago
     * correspondiente en función de la información recibida.
     *
     * @param message El mensaje recibido del tópico notificacion-pendiente
     *                (required).
     */
    @Loggable(category = LogTypes.MESSAGE)
    @KafkaListener(topics = "${spring.kafka.topics.notificacion-pendiente}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenTopicNotificacionPendiente(String message) {
        OrdenPagoResponseDTO ordenPagoResponseDTO = new OrdenPagoResponseDTO();

        try {
            ordenPagoResponseDTO = objectMapper.readValue(message, OrdenPagoResponseDTO.class);
            logger.info("Received message from topic notificacion-pendiente: {}", message);

            OrdenPago ordenPago = selectOrdenPagoUseCase
                    .findByPpIdOrdenPagoInterna(ordenPagoResponseDTO.getOrdenPagoId());
            if (ordenPagoResponseDTO.getEstado().equals(PENDIENTE_PAGO)) {
                logger.debug("Updating order status to 'PP' for ordenPagoId: {}",
                        ordenPagoResponseDTO.getOrdenPagoId());
                ordenPago.setEstado(PP);
                ordenPago.setMonto(ordenPagoResponseDTO.getMonto());

                if (ordenPago.getFilenetGuid() == null) {
                    ordenPago = updateArchivoPDFUseCase.updateArchivoPDF(ordenPagoResponseDTO.getOrdenPagoId(),
                            ordenPago);
                }

                if (ordenPagoResponseDTO.getFechaGeneracion() != null) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
                    LocalDateTime localDateTime = LocalDateTime.parse(ordenPagoResponseDTO.getFechaGeneracion(),
                            formatter);
                    ordenPago.setPpFechaRespuestaCreacionCpb(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
                }
                ordenPago.setUsuidModAud(userPago);
                OrdenPago ordenPagoCreated = updateOrdenPagoUseCase.updateOrdenPago(ordenPago);
                cpb = "Orden de Pago generada desde kafka cpb = cp";
                if (ordenPagoCreated.getCpb() != null && !ordenPagoCreated.getCpb().trim().isEmpty()
                        && !"null".equals(ordenPagoCreated.getCpb())) {
                    cpb = cpb + ordenPagoCreated.getCpb();
                }
                cpb = cpb + DOCUMENT_SEGUI + Constants.tipoDocumento(ordenPago.documentoId);
                SeguimientoRequestDto ordenPagoSeguimiento = SeguimientoUtils.generarRequestSeguimiento(
                        ordenPago.getEscalaId(),
                        SeguimientoUtils.GENERADO,
                        Constants.indicador(ordenPago.documentoId),
                        ordenPago.getRucAgente(),
                        Constants.tipoDocumento(ordenPago.documentoId),
                        cpb);
                createSeguimientoUseCase.create(ordenPagoSeguimiento, userSegui);
                AcuseReciboDTO acuseReciboDTO = AcuseReciboDTO.builder()
                        .ordenPagoId(ordenPagoResponseDTO.getOrdenPagoId())
                        .fechaProcesamiento(ordenPagoResponseDTO.getFechaGeneracion())
                        .build();

                kafkaTemplate.send(notificacionPendienteResponseTopic, acuseReciboDTO);
                logger.info("Sent response message to topic notificacion-pendiente-response: {}", acuseReciboDTO);
            }
        } catch (Exception e) {
            logger.info("Error processing message from topic notificacion-pendiente. OrdenPagoId:{}", ordenPagoResponseDTO.getOrdenPagoId());
            throw new KafkaListenerException("Error processing message from topic notificacion-pendiente", e);
        }
    }

    /**
     * Escucha los mensajes del tópico notificacion-pago y actualiza el estado de la
     * orden de pago
     * correspondiente en función de la información recibida.
     *
     * @param message El mensaje recibido del tópico notificacion-pago (required).
     */
    @Loggable(category = LogTypes.MESSAGE)
    @KafkaListener(topics = "${spring.kafka.topics.notificacion-pago}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenTopicNotificacionPago(String message){
        NotificarPagoDTO notificarPagoDTO = new NotificarPagoDTO();

        try {
            notificarPagoDTO = objectMapper.readValue(message, NotificarPagoDTO.class);
            logger.info("Received message from topic notificacion-pago: {}", message);

            OrdenPago ordenPago = selectOrdenPagoUseCase.findByPpIdOrdenPagoInterna(notificarPagoDTO.getOrdenPagoId());
            if (notificarPagoDTO.getEstado().equals(PAGADO)) {
                logger.debug("Updating order status to 'PG' for ordenPagoId: {}", notificarPagoDTO.getOrdenPagoId());
                ordenPago.setEstado(PG);
                ordenPago.setPpEstadoCpbTexto(notificarPagoDTO.getEstado());

                if (notificarPagoDTO.getFechaPago() != null) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
                    LocalDateTime localDateTime = LocalDateTime.parse(notificarPagoDTO.getFechaPago(), formatter);
                    ordenPago.setFechaPagado(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
                }
                ordenPago.setUsuidModAud(userPago);
                updateOrdenPagoUseCase.updateOrdenPago(ordenPago);
                cpb = "Orden de Pago pagado desde kafka cbp = cp";
                if (ordenPago.getCpb() != null && !ordenPago.getCpb().trim().isEmpty()
                        && !"null".equals(ordenPago.getCpb())) {
                    cpb = cpb + ordenPago.getCpb();
                }
                cpb = cpb + DOCUMENT_SEGUI + Constants.tipoDocumento(ordenPago.documentoId);
                SeguimientoRequestDto ordenPagoSeguimiento = SeguimientoUtils.generarRequestSeguimiento(
                        ordenPago.getEscalaId(),
                        SeguimientoUtils.PAGADO,
                        Constants.indicador(ordenPago.documentoId),
                        ordenPago.getRucAgente(),
                        Constants.tipoDocumento(ordenPago.documentoId),
                        cpb);
                createSeguimientoUseCase.create(ordenPagoSeguimiento, userSegui);
                AcuseReciboDTO acuseReciboDTO = AcuseReciboDTO.builder()
                        .ordenPagoId(notificarPagoDTO.getOrdenPagoId())
                        .fechaProcesamiento(notificarPagoDTO.getFechaProcesamiento())
                        .build();

                kafkaTemplate.send(notificacionPagoResponseTopic, acuseReciboDTO);
                logger.info("Sent response message to topic notificacion-pago-response: {}", acuseReciboDTO);
            }
        } catch (Exception e) {
            logger.info("Error processing message from topic notificacion-pago. OrdenPagoId:{}", notificarPagoDTO.getOrdenPagoId());
            throw new KafkaListenerException("Error processing message from topic notificacion-pago", e);
        }
    }

    /**
     * Escucha los mensajes del tópico notificacion-anulacion y actualiza el estado
     * de la orden de pago
     * correspondiente en función de la información recibida.
     *
     * @param message El mensaje recibido del tópico notificacion-anulacion
     *                (required).
     */
    @Loggable(category = LogTypes.MESSAGE)
    @KafkaListener(topics = "${spring.kafka.topics.notificacion-anulacion}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenTopicNotificacionAnulacion(String message) {
        NotificarAnulacionDTO notificarAnulacionDTO = new NotificarAnulacionDTO();

        try {
            notificarAnulacionDTO = objectMapper.readValue(message, NotificarAnulacionDTO.class);
            logger.info("Received message from topic notificacion-anulacion: {}", message);

            OrdenPago ordenPago = selectOrdenPagoUseCase
                    .findByPpIdOrdenPagoInterna(notificarAnulacionDTO.getOrdenPagoId());
            if (notificarAnulacionDTO.getEstado().equals(ANULADO)) {
                logger.debug("Updating order status to 'AN' for ordenPagoId: {}",
                        notificarAnulacionDTO.getOrdenPagoId());
                ordenPago.setEstado(AN);
                ordenPago.setPpEstadoCpbTexto(notificarAnulacionDTO.getEstado());

                if (notificarAnulacionDTO.getFechaAnulacion() != null) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
                    LocalDateTime localDateTime = LocalDateTime.parse(notificarAnulacionDTO.getFechaAnulacion(),
                            formatter);
                    ordenPago.setFechaAnulacionCpb(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
                }
                ordenPago.setUsuidModAud(userPago);
                updateOrdenPagoUseCase.updateOrdenPago(ordenPago);
                cpb = "Orden de Pago anulada desde kafka cpb = cp";
                if (ordenPago.getCpb() != null && !ordenPago.getCpb().trim().isEmpty()
                        && !"null".equals(ordenPago.getCpb())) {
                    cpb = cpb + ordenPago.getCpb();
                }
                cpb = cpb + DOCUMENT_SEGUI + Constants.tipoDocumento(ordenPago.documentoId);
                SeguimientoRequestDto ordenPagoSeguimiento = SeguimientoUtils.generarRequestSeguimiento(
                        ordenPago.getEscalaId(),
                        SeguimientoUtils.ANULADO,
                        Constants.indicador(ordenPago.documentoId),
                        ordenPago.getRucAgente(),
                        Constants.tipoDocumento(ordenPago.documentoId),
                        cpb);
                createSeguimientoUseCase.create(ordenPagoSeguimiento, userSegui);
                AcuseReciboDTO acuseReciboDTO = AcuseReciboDTO.builder()
                        .ordenPagoId(notificarAnulacionDTO.getOrdenPagoId())
                        .fechaProcesamiento(notificarAnulacionDTO.getFechaProcesamiento())
                        .build();

                kafkaTemplate.send(notificacionAnulacionResponseTopic, acuseReciboDTO);
                logger.info("Sent response message to topic notificacion-anulacion-response: {}", acuseReciboDTO);
            }
        } catch (Exception e) {
            logger.info("Error processing message from topic notificacion-anulacion. OrdenPagoId:{}", notificarAnulacionDTO.getOrdenPagoId());
            throw new KafkaListenerException("Error processing message from topic notificacion-anulacion", e);
        }
    }

    /**
     * Escucha los mensajes del tópico notificacion-extorno y actualiza el estado de
     * la orden de pago
     * correspondiente en función de la información recibida.
     *
     * @param message El mensaje recibido del tópico notificacion-extorno
     *                (required).
     */
    @Loggable(category = LogTypes.MESSAGE)
    @KafkaListener(topics = "${spring.kafka.topics.notificacion-extorno}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenTopicNotificacionExtorno(String message) {
        NotificarExtornoDTO notificarExtornoDTO = new NotificarExtornoDTO();

        try {
            notificarExtornoDTO = objectMapper.readValue(message, NotificarExtornoDTO.class);
            logger.info("Received message from topic notificacion-extorno: {}", message);

            OrdenPago ordenPago = selectOrdenPagoUseCase
                    .findByPpIdOrdenPagoInterna(notificarExtornoDTO.getOrdenPagoId());
            if (notificarExtornoDTO.getEstado().equals(EXTORNADO)) {
                logger.debug("Updating order status to 'EX' for ordenPagoId: {}", notificarExtornoDTO.getOrdenPagoId());
                ordenPago.setEstado(EX);
                ordenPago.setPpEstadoCpbTexto(notificarExtornoDTO.getEstado());

                if (notificarExtornoDTO.getFechaProcesamiento() != null) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
                    LocalDateTime localDateTime = LocalDateTime.parse(notificarExtornoDTO.getFechaProcesamiento(),
                            formatter);
                    ordenPago.setFechaExtornoOrdenPago(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
                }
                ordenPago.setUsuidModAud(userPago);
                updateOrdenPagoUseCase.updateOrdenPago(ordenPago);
                cpb = "Orden de Pago extornada desde kafka cpb = cp";
                if (ordenPago.getCpb() != null && !ordenPago.getCpb().trim().isEmpty()
                        && !"null".equals(ordenPago.getCpb())) {
                    cpb = cpb + ordenPago.getCpb();
                }
                cpb = cpb + DOCUMENT_SEGUI + Constants.tipoDocumento(ordenPago.documentoId);
                SeguimientoRequestDto ordenPagoSeguimiento = SeguimientoUtils.generarRequestSeguimiento(
                        ordenPago.getEscalaId(),
                        SeguimientoUtils.EXTORNADO,
                        Constants.indicador(ordenPago.documentoId),
                        ordenPago.getRucAgente(),
                        Constants.tipoDocumento(ordenPago.documentoId),
                        cpb);
                createSeguimientoUseCase.create(ordenPagoSeguimiento, userSegui);
                AcuseReciboDTO acuseReciboDTO = AcuseReciboDTO.builder()
                        .ordenPagoId(notificarExtornoDTO.getOrdenPagoId())
                        .fechaProcesamiento(notificarExtornoDTO.getFechaProcesamiento())
                        .build();

                kafkaTemplate.send(notificacionAnulacionResponseTopic, acuseReciboDTO);
                logger.info("Sent response message to topic notificacion-extorno-response: {}", acuseReciboDTO);
            }
        } catch (Exception e) {
            logger.info("Error processing message from topic notificacion-extorno. OrdenPagoId:{}", notificarExtornoDTO.getOrdenPagoId());
            throw new KafkaListenerException("Error processing message from topic notificacion-extorno", e);
        }
    }

    /**
     * Escucha los mensajes del tópico notificacion-expiracion y actualiza el estado
     * de la orden de pago
     * correspondiente en función de la información recibida.
     *
     * @param message El mensaje recibido del tópico notificacion-expiracion
     *                (required).
     */
    @Loggable(category = LogTypes.MESSAGE)
    @KafkaListener(topics = "${spring.kafka.topics.notificacion-expiracion}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenTopicNotificacionExpiracion(String message) {
        NotificarExpiracionDTO notificarExpiracionDTO = new NotificarExpiracionDTO();

        try {
            notificarExpiracionDTO = objectMapper.readValue(message,
                    NotificarExpiracionDTO.class);
            logger.info("Received message from topic notificacion-expiracion: {}", message);

            OrdenPago ordenPago = selectOrdenPagoUseCase
                    .findByPpIdOrdenPagoInterna(notificarExpiracionDTO.getOrdenPagoId());
            if (notificarExpiracionDTO.getEstado().equals(NOTIFICATION_TOPIC_STATUS_EXPIRED)) {
                logger.info("Updating order status to EXPIRED for ordenPagoId: {}",
                        notificarExpiracionDTO.getOrdenPagoId());
                ordenPago.setEstado(PAYMENT_ORDER_STATUS_EXPIRED);
                ordenPago.setPpEstadoCpbTexto(notificarExpiracionDTO.getEstado());

                if (notificarExpiracionDTO.getFechaProcesamiento() != null) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
                    LocalDateTime localDateTime = LocalDateTime.parse(notificarExpiracionDTO.getFechaProcesamiento(),
                            formatter);
                    ordenPago.setFechaExtornoOrdenPago(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
                }
                ordenPago.setUsuidModAud(userPago);
                updateOrdenPagoUseCase.updateOrdenPago(ordenPago);
                cpb = "Orden de Pago expirada desde kafka cpb = cp";
                if (ordenPago.getCpb() != null && !ordenPago.getCpb().trim().isEmpty()
                        && !"null".equals(ordenPago.getCpb())) {
                    cpb = cpb + ordenPago.getCpb();
                }
                cpb = cpb + DOCUMENT_SEGUI + Constants.tipoDocumento(ordenPago.documentoId);
                SeguimientoRequestDto ordenPagoSeguimiento = SeguimientoUtils.generarRequestSeguimiento(
                        ordenPago.getEscalaId(),
                        SeguimientoUtils.EXPIRADO,
                        Constants.indicador(ordenPago.documentoId),
                        ordenPago.getRucAgente(),
                        Constants.tipoDocumento(ordenPago.documentoId),
                        cpb);
                createSeguimientoUseCase.create(ordenPagoSeguimiento, userSegui);
                AcuseReciboDTO acuseReciboDTO = AcuseReciboDTO.builder()
                        .ordenPagoId(notificarExpiracionDTO.getOrdenPagoId())
                        .fechaProcesamiento(notificarExpiracionDTO.getFechaProcesamiento())
                        .build();

                kafkaTemplate.send(notificacionExpiracionResponseTopic, acuseReciboDTO);
                logger.info("Sent response message to topic notificacion-expiracion-response: {}", acuseReciboDTO);
            }
        } catch (Exception e) {
            logger.info("Error processing message from topic notificacion-expiracion. OrdenPagoId:{}", notificarExpiracionDTO.getOrdenPagoId());
            throw new KafkaListenerException("Error processing message from topic notificacion-expiracion", e);
        }
    }

}
