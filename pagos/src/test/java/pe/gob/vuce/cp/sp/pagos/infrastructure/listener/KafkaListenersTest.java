package pe.gob.vuce.cp.sp.pagos.infrastructure.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import pe.gob.vuce.cp.sp.pagos.domain.model.OrdenPago;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.CreateSeguimientoUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.SelectOrdenPagoUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.UpdateArchivoPDFUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.UpdateOrdenPagoUseCase;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.SeguimientoRequestDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.request.NotificarAnulacionDTO;
import pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.request.NotificarExpiracionDTO;
import pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.request.NotificarExtornoDTO;
import pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.request.NotificarPagoDTO;
import pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.response.AcuseReciboDTO;
import pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.response.OrdenPagoResponseDTO;
import org.mockito.MockitoAnnotations;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.PENDIENTE_PAGO;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.PAGADO;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.ANULADO;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.NOTIFICATION_TOPIC_STATUS_EXPIRED;

class KafkaListenersTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private CreateSeguimientoUseCase createSeguimientoUseCase;

    @Mock
    private UpdateArchivoPDFUseCase updateArchivoPDFUseCase;

    @Mock
    private SelectOrdenPagoUseCase selectOrdenPagoUseCase;

    @Mock
    private UpdateOrdenPagoUseCase updateOrdenPagoUseCase;

    @InjectMocks
    private KafkaListeners kafkaListeners;

    @Mock
    private KafkaTemplate<String, AcuseReciboDTO> kafkaTemplate;

    private OrdenPago ordenPago;
    private final String DATE_TIME_FORMAT = "yyyyMMdd HH:mm:ss";
    private String userSegui = "PASARELADEPAGOS | PASARELADEPAGOS";

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);
        ordenPago = new OrdenPago();
        ordenPago.setOrdenPagoId(1);
        ordenPago.setEstado("PENDIENTE DE PAGO");
        ordenPago.setDocumentoId(1);
        ordenPago.setRucAgente("12345678901");
        ordenPago.setEscalaId(1);
        ordenPago.setUsuidModAud("1");

    }

    @Test
    void listenTopicNotificacionPendiente_WithNullFilenetGuid() throws Exception {
        // Arrange
        String message = "{\"ordenPagoId\":1,\"estado\":\"PENDIENTE_PAGO\"}";
        OrdenPagoResponseDTO responseDTO = new OrdenPagoResponseDTO();
        responseDTO.setOrdenPagoId(1);
        responseDTO.setEstado(PENDIENTE_PAGO);

        ordenPago.setFilenetGuid(null);
        ordenPago.setCpb("CPB4582");

        when(objectMapper.readValue(message, OrdenPagoResponseDTO.class)).thenReturn(responseDTO);
        when(selectOrdenPagoUseCase.findByPpIdOrdenPagoInterna(1)).thenReturn(ordenPago);
        when(updateArchivoPDFUseCase.updateArchivoPDF(anyInt(), any())).thenReturn(ordenPago);
        when(updateOrdenPagoUseCase.updateOrdenPago(ordenPago)).thenReturn(ordenPago);

        // Act
        kafkaListeners.listenTopicNotificacionPendiente(message);

        // Assert
        verify(updateArchivoPDFUseCase).updateArchivoPDF(eq(1), any(OrdenPago.class));
    }

    @Test
    void listenTopicNotificacionPendiente_shouldHandleJsonParseException() throws Exception {
       when(objectMapper.readValue(anyString(), eq(OrdenPagoResponseDTO.class))).thenThrow(new JsonProcessingException("invalid") {});

        assertThrows(RuntimeException.class, () ->
            kafkaListeners.listenTopicNotificacionPendiente("invalid-json")
        );

       verifyNoInteractions(selectOrdenPagoUseCase);
    }

    @Test
    void listenTopicNotificacionPendiente_WithNonNullFilenetGuid() throws Exception {
        // Arrange
        String message = "{\"ordenPagoId\":1,\"estado\":\"PENDIENTE_PAGO\"}";
        OrdenPagoResponseDTO responseDTO = new OrdenPagoResponseDTO();
        responseDTO.setOrdenPagoId(1);
        responseDTO.setEstado(PENDIENTE_PAGO);
        responseDTO.setCpb("CPB452");

        ordenPago.setFilenetGuid("some-guid");
        ordenPago.setCpb("CPB452");

        when(objectMapper.readValue(message, OrdenPagoResponseDTO.class)).thenReturn(responseDTO);
        when(selectOrdenPagoUseCase.findByPpIdOrdenPagoInterna(1)).thenReturn(ordenPago);
        when(updateOrdenPagoUseCase.updateOrdenPago(ordenPago)).thenReturn(ordenPago);

        // Act
        kafkaListeners.listenTopicNotificacionPendiente(message);

        // Assert
        verify(updateArchivoPDFUseCase, never()).updateArchivoPDF(anyInt(), any());
    }
    @Test
    void listenTopicNotificacionPendiente_WithNullFechaGeneracion() throws Exception {
        // Arrange
        String message = "{\"ordenPagoId\":1,\"estado\":\"PENDIENTE_PAGO\"}";
        OrdenPagoResponseDTO responseDTO = new OrdenPagoResponseDTO();
        responseDTO.setOrdenPagoId(1);
        responseDTO.setEstado(PENDIENTE_PAGO);
        responseDTO.setFechaGeneracion(null);

        ordenPago.setUsuidModAud("PASARELADEPAGOS");

        when(objectMapper.readValue(message, OrdenPagoResponseDTO.class)).thenReturn(responseDTO);
        when(selectOrdenPagoUseCase.findByPpIdOrdenPagoInterna(1)).thenReturn(ordenPago);
        when(updateArchivoPDFUseCase.updateArchivoPDF(1, ordenPago)).thenReturn(ordenPago);
        when(updateOrdenPagoUseCase.updateOrdenPago(ordenPago)).thenReturn(ordenPago);

        // Act
        kafkaListeners.listenTopicNotificacionPendiente(message);

        // Assert
        assertNull(ordenPago.getPpFechaRespuestaCreacionCpb());
    }
    @Test
    void listenTopicNotificacionPendiente_WithInvalidEstado() throws Exception {
        // Arrange
        String message = "{\"ordenPagoId\":1,\"estado\":\"INVALID\"}";
        OrdenPagoResponseDTO responseDTO = new OrdenPagoResponseDTO();
        responseDTO.setOrdenPagoId(1);
        responseDTO.setEstado("INVALID");

        when(objectMapper.readValue(message, OrdenPagoResponseDTO.class)).thenReturn(responseDTO);
        when(selectOrdenPagoUseCase.findByPpIdOrdenPagoInterna(anyInt())).thenReturn(ordenPago);

        // Act
        kafkaListeners.listenTopicNotificacionPendiente(message);

        // Assert
        verify(updateOrdenPagoUseCase, never()).updateOrdenPago(any());
    }
    @Test
    void listenTopicNotificacionPendiente_JsonProcessingException() throws Exception {
        // Arrange
        String message = "invalid-json";
        when(objectMapper.readValue(message, OrdenPagoResponseDTO.class)).thenThrow(new JsonProcessingException("Error") {});

        assertThrows(RuntimeException.class, () ->
            kafkaListeners.listenTopicNotificacionPendiente(message)
        );

        // Assert
        verify(selectOrdenPagoUseCase, never()).findByPpIdOrdenPagoInterna(anyInt());
    }

    @Test
    void listenTopicNotificacionPendiente_OrdenPagoNotFound() throws Exception {
        // Arrange
        String message = "{\"ordenPagoId\":1,\"estado\":\"PENDIENTE_PAGO\"}";
        OrdenPagoResponseDTO responseDTO = new OrdenPagoResponseDTO();
        responseDTO.setOrdenPagoId(1);
        responseDTO.setEstado(PENDIENTE_PAGO);

        when(objectMapper.readValue(message, OrdenPagoResponseDTO.class)).thenReturn(responseDTO);
        when(selectOrdenPagoUseCase.findByPpIdOrdenPagoInterna(1)).thenReturn(null);

        assertThrows(RuntimeException.class, () ->
            kafkaListeners.listenTopicNotificacionPendiente(message)
        );

        // Assert
        verify(updateOrdenPagoUseCase, never()).updateOrdenPago(any());
    }
    @Test
    void testListenTopicNotificacionPendiente() throws Exception {
        String message = "{\"ordenPagoId\":1,\"estado\":\"PENDIENTE DE PAGO\",\"monto\":1000,\"fechaGeneracion\":\"20240101 12:00:00\"}";
        OrdenPagoResponseDTO responseDTO = new OrdenPagoResponseDTO();
        responseDTO.setOrdenPagoId(1);
        responseDTO.setEstado("PENDIENTE DE PAGO");
        responseDTO.setMonto(1000.00);
        responseDTO.setFechaGeneracion("20240101 12:00:00");
        responseDTO.setCpb("CPB452");

        ordenPago.setCpb("CPB452");

        when(objectMapper.readValue(message, OrdenPagoResponseDTO.class)).thenReturn(responseDTO);
        when(selectOrdenPagoUseCase.findByPpIdOrdenPagoInterna(1)).thenReturn(ordenPago);
        when(updateArchivoPDFUseCase.updateArchivoPDF(anyInt(), any())).thenReturn(ordenPago);
        when(updateOrdenPagoUseCase.updateOrdenPago(ordenPago)).thenReturn(ordenPago);


        kafkaListeners.listenTopicNotificacionPendiente(message);

        verify(updateOrdenPagoUseCase).updateOrdenPago(ordenPago);
    }

    @Test
    void listenTopicNotificacionPago_WithNullEstadoCpbTexto() throws Exception {
        String message = "{\"ordenPagoId\":1,\"estado\":\"PAGADO\",\"fechaProcesamiento\":\"20240101 12:00:00\"}";
        NotificarPagoDTO pagoDTO = new NotificarPagoDTO();
        pagoDTO.setOrdenPagoId(1);
        pagoDTO.setFechaProcesamiento("20240101 12:00:00");
        pagoDTO.setEstado("PAGADO");

        when(objectMapper.readValue(message, NotificarPagoDTO.class)).thenReturn(pagoDTO);
        when(selectOrdenPagoUseCase.findByPpIdOrdenPagoInterna(1)).thenReturn(ordenPago);

        kafkaListeners.listenTopicNotificacionPago(message);

        verify(updateOrdenPagoUseCase).updateOrdenPago(any());
    }

    @Test
    void listenTopicNotificacionAnulacion_WithDifferentEstado() throws Exception {
        String message = "{\"ordenPagoId\":1,\"estado\":\"PAGADO\",\"fechaProcesamiento\":\"20240101 12:00:00\"}";
        NotificarAnulacionDTO anulacionDTO = new NotificarAnulacionDTO();
        anulacionDTO.setOrdenPagoId(1);
        anulacionDTO.setEstado("PAGADO"); // Estado diferente a ANULADO
        anulacionDTO.setFechaProcesamiento("20240101 12:00:00");

        when(objectMapper.readValue(message, NotificarAnulacionDTO.class)).thenReturn(anulacionDTO);
        when(selectOrdenPagoUseCase.findByPpIdOrdenPagoInterna(1)).thenReturn(ordenPago);

        kafkaListeners.listenTopicNotificacionAnulacion(message);

        verify(updateOrdenPagoUseCase, never()).updateOrdenPago(any());
    }

    @Test
    void listenTopicNotificacionAnulacion_VerifySeguimientoParams() throws Exception {
        String message = "{\"ordenPagoId\":1,\"estado\":\"ANULADO\",\"fechaProcesamiento\":\"20240101 12:00:00\"}";
        NotificarAnulacionDTO anulacionDTO = new NotificarAnulacionDTO();
        anulacionDTO.setOrdenPagoId(1);
        anulacionDTO.setEstado(ANULADO);
        anulacionDTO.setFechaProcesamiento("20240101 12:00:00");

        when(objectMapper.readValue(message, NotificarAnulacionDTO.class)).thenReturn(anulacionDTO);
        when(selectOrdenPagoUseCase.findByPpIdOrdenPagoInterna(1)).thenReturn(ordenPago);
        ArgumentCaptor<SeguimientoRequestDto> seguimientoCaptor = ArgumentCaptor.forClass(SeguimientoRequestDto.class);

        kafkaListeners.listenTopicNotificacionAnulacion(message);

        verify(createSeguimientoUseCase).create(seguimientoCaptor.capture(), eq(userSegui));
        SeguimientoRequestDto seguimiento = seguimientoCaptor.getValue();
        assertEquals(43, seguimiento.getTipoSegId());
        assertEquals("12345678901", seguimiento.getRucUsuario());
    }

    @Test
    void testListenTopicNotificacionExtorno() throws Exception {
        String message = "{\"ordenPagoId\":1," +
                "\"montoExtornado\":150.75," + // Agregando montoExtornado
                "\"fechaExtorno\":\"20240101 12:00:00\"," + // Agregando fechaExtorno
                "\"fechaProcesamiento\":\"20240101 12:00:00\"," +
                "\"canalId\":100," + // Incluyendo un valor para canalId
                "\"canalDescripcion\":\"Canal de Prueba\"," + // Incluyendo un valor para canalDescripcion
                "\"estado\":\"EXTORNADO\"}"; // Asegurando que el estado también está presente

        NotificarExtornoDTO extornoDTO = getNotificarExtornoDTO();

        when(objectMapper.readValue(message, NotificarExtornoDTO.class)).thenReturn(extornoDTO);
        when(selectOrdenPagoUseCase.findByPpIdOrdenPagoInterna(1)).thenReturn(ordenPago);

        kafkaListeners.listenTopicNotificacionExtorno(message);

        verify(updateOrdenPagoUseCase).updateOrdenPago(ordenPago);
    }

    @NotNull
    private static NotificarExtornoDTO getNotificarExtornoDTO() {
        NotificarExtornoDTO extornoDTO = new NotificarExtornoDTO();
        extornoDTO.setOrdenPagoId(1);
        extornoDTO.setMontoExtornado(150.75); // Estableciendo el monto extornado
        extornoDTO.setFechaExtorno("20240101 12:00:00"); // Agregando fechaExtorno
        extornoDTO.setFechaProcesamiento("20240101 12:00:00");
        extornoDTO.setCanalId(100); // Estableciendo valor de canalId
        extornoDTO.setCanalDescripcion("Canal de Prueba"); // Estableciendo valor de canalDescripcion
        extornoDTO.setEstado("EXTORNADO");
        return extornoDTO;
    }

    @Test
    void listenTopicNotificacionExtorno_WithNullEstado() throws Exception {
        String message = "{\"ordenPagoId\":1,\"estado\":null,\"fechaProcesamiento\":\"20240101 12:00:00\"}";
        NotificarExtornoDTO extornoDTO = new NotificarExtornoDTO();
        extornoDTO.setOrdenPagoId(1);
        extornoDTO.setEstado(PAGADO);
        extornoDTO.setFechaProcesamiento("20240101 12:00:00");

        when(objectMapper.readValue(message, NotificarExtornoDTO.class)).thenReturn(extornoDTO);
        when(selectOrdenPagoUseCase.findByPpIdOrdenPagoInterna(1)).thenReturn(ordenPago);

        kafkaListeners.listenTopicNotificacionExtorno(message);

        verify(updateOrdenPagoUseCase, never()).updateOrdenPago(any());
    }

    @Test
    void listenTopicNotificacionPago_VerifySeguimientoCreation() throws Exception {
        String message = "{\"ordenPagoId\":1,\"estado\":\"PAGADO\",\"fechaProcesamiento\":\"20240101 12:00:00\"}";
        NotificarPagoDTO pagoDTO = new NotificarPagoDTO();
        pagoDTO.setOrdenPagoId(1);
        pagoDTO.setEstado(PAGADO);
        pagoDTO.setFechaProcesamiento("20240101 12:00:00");

        when(objectMapper.readValue(message, NotificarPagoDTO.class)).thenReturn(pagoDTO);
        when(selectOrdenPagoUseCase.findByPpIdOrdenPagoInterna(1)).thenReturn(ordenPago);
        ArgumentCaptor<SeguimientoRequestDto> seguimientoCaptor = ArgumentCaptor.forClass(SeguimientoRequestDto.class);

        kafkaListeners.listenTopicNotificacionPago(message);

        verify(createSeguimientoUseCase).create(seguimientoCaptor.capture(), eq(userSegui));
        SeguimientoRequestDto seguimiento = seguimientoCaptor.getValue();
        assertNotNull(seguimiento);
        assertEquals(42, seguimiento.getTipoSegId());
        assertTrue(seguimiento.getComentario().contains("Orden de Pago pagado"));
    }

    @Test
    void listenTopicNotificacionAnulacion_WithValidDateFormat() throws Exception {
        String message = "{\"ordenPagoId\":1,\"estado\":\"ANULADO\",\"fechaAnulacion\":\"20240101 12:00:00\",\"fechaProcesamiento\":\"20240101 12:00:00\"}";
        NotificarAnulacionDTO anulacionDTO = new NotificarAnulacionDTO();
        anulacionDTO.setOrdenPagoId(1);
        anulacionDTO.setEstado(ANULADO);
        anulacionDTO.setFechaAnulacion("20240101 12:00:00");
        anulacionDTO.setFechaProcesamiento("20240101 12:00:00");

        when(objectMapper.readValue(message, NotificarAnulacionDTO.class)).thenReturn(anulacionDTO);
        when(selectOrdenPagoUseCase.findByPpIdOrdenPagoInterna(1)).thenReturn(ordenPago);

        kafkaListeners.listenTopicNotificacionAnulacion(message);

        assertNotNull(ordenPago.getFechaAnulacionCpb());
        assertEquals(Instant.from(LocalDateTime.parse("20240101 12:00:00",
                        DateTimeFormatter.ofPattern(DATE_TIME_FORMAT))
                .atZone(ZoneId.systemDefault())), ordenPago.getFechaAnulacionCpb());
    }

    @Test
    void listenTopicNotificacionExtorno_VerifyErrorLogging() throws Exception {
        String message = "invalid-message";

        when(objectMapper.readValue(message, NotificarExtornoDTO.class)).thenThrow(new JsonProcessingException("Error") {});

        assertThrows(RuntimeException.class, () ->
            kafkaListeners.listenTopicNotificacionExtorno(message)
        );

        // Verificar que se registró el error (necesitarías un captor para el logger)
        verify(selectOrdenPagoUseCase, never()).findByPpIdOrdenPagoInterna(anyInt());
    }

    @Test
    void testListenTopicNotificacionExpiracion_estadoExpirado() throws Exception {
        // Arrange
        String message = "{\"ordenPagoId\":1,\"estado\":\"EXPIRADO\",\"fechaProcesamiento\":\"20240609 12:00:00\"}";
        NotificarExpiracionDTO dto = new NotificarExpiracionDTO();
        dto.setOrdenPagoId(1);
        dto.setEstado(NOTIFICATION_TOPIC_STATUS_EXPIRED);
        dto.setFechaProcesamiento("20240609 12:00:00");

        OrdenPago ordenPago = new OrdenPago();
        ordenPago.setCpb("CP123");
        ordenPago.setDocumentoId(123);
        ordenPago.setEscalaId(999);
        ordenPago.setRucAgente("20123456789");
        ordenPago.setUsuidModAud("user123");

        when(objectMapper.readValue(message, NotificarExpiracionDTO.class)).thenReturn(dto);
        when(selectOrdenPagoUseCase.findByPpIdOrdenPagoInterna(1)).thenReturn(ordenPago);

        CompletableFuture<SendResult<String, AcuseReciboDTO>> future = CompletableFuture.completedFuture(null);
        when(kafkaTemplate.send(anyString(), any())).thenReturn(future);

        kafkaListeners.listenTopicNotificacionExpiracion(message);

        assertNotNull(message);
    }

    @Test
    void testListenTopicNotificacionExpiracion_estadoExpirado_valOtros() throws Exception {
        // Arrange
        String message = "{\"ordenPagoId\":1,\"estado\":\"EXPIRADO\"}";
        NotificarExpiracionDTO dto = new NotificarExpiracionDTO();
        dto.setOrdenPagoId(1);
        dto.setEstado(NOTIFICATION_TOPIC_STATUS_EXPIRED);
        dto.setCanalId(45);

        ordenPago.setDocumentoId(1253698574);


        when(objectMapper.readValue(message, NotificarExpiracionDTO.class)).thenReturn(dto);
        when(selectOrdenPagoUseCase.findByPpIdOrdenPagoInterna(1)).thenReturn(ordenPago);

        CompletableFuture<SendResult<String, AcuseReciboDTO>> future = CompletableFuture.completedFuture(null);
        when(kafkaTemplate.send(anyString(), any())).thenReturn(future);

        kafkaListeners.listenTopicNotificacionExpiracion(message);

        assertNotNull(message);
    }

    @Test
    void testListenTopicNotificacionExpiracion_estadoOtros() throws Exception {
        // Arrange
        String message = "{\"ordenPagoId\":1,\"estado\":\"Otros\",\"fechaProcesamiento\":\"2024-06-09 12:00:00\"}";
        NotificarExpiracionDTO dto = new NotificarExpiracionDTO();
        dto.setOrdenPagoId(1);
        dto.setEstado("Otros");
        dto.setFechaProcesamiento("2024-06-09 12:00:00");

        when(objectMapper.readValue(message, NotificarExpiracionDTO.class)).thenReturn(dto);
        when(selectOrdenPagoUseCase.findByPpIdOrdenPagoInterna(1)).thenReturn(ordenPago);

        kafkaListeners.listenTopicNotificacionExpiracion(message);

        assertNotNull(message);
    }

    @Test
    void testListenTopicNotificacionExpiracion_excepcion() throws Exception {
        // Arrange
        String message = "{\"ordenPagoId\":1,\"estado\":\"EXPIRED\"}";

        when(objectMapper.readValue(anyString(), eq(NotificarExpiracionDTO.class)))
                .thenThrow(new RuntimeException("JSON parse error"));

        assertThrows(RuntimeException.class, () ->
            kafkaListeners.listenTopicNotificacionExpiracion(message)
        );

        assertNotNull(message);
    }

}
