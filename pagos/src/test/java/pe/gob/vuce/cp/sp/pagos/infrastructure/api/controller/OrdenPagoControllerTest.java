package pe.gob.vuce.cp.sp.pagos.infrastructure.api.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import pe.gob.vuce.cp.sp.pagos.application.service.AnularOrdenPagoLocalService;
import pe.gob.vuce.cp.sp.pagos.application.service.AnularOrdenPagoService;
import pe.gob.vuce.cp.sp.pagos.application.service.FormaPagoService;
import pe.gob.vuce.cp.sp.pagos.application.service.ObtenerFileService;
import pe.gob.vuce.cp.sp.pagos.application.service.OrdenPagoService;
import pe.gob.vuce.cp.sp.pagos.application.service.PagoSunatService;
import pe.gob.vuce.cp.sp.pagos.application.service.TasaService;
import pe.gob.vuce.cp.sp.pagos.domain.exception.FeignExceptionHandler;
import pe.gob.vuce.cp.sp.pagos.domain.model.OrdenPago;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.GenericResponseDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.OrdenPagoRequestDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.OrdenPagoResponseDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.Tupa0ResponseDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.response.ApiResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.mapper.OrdenPagoMapper;
import pe.gob.vuce.cp.sp.pagos.infrastructure.config.exception.PdfGenerationException;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.PaymentMethodResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.TasaResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.response.OrdenPagoResponseDTO;

 class OrdenPagoControllerTest {

    @Mock
    private OrdenPagoService ordenPagoService;

    @Mock
    private PagoSunatService pagoSunatService;

    @Mock
    private TasaService tasaService;

    @Mock
    private AnularOrdenPagoService anularOrdenPagoService;

    @Mock
    private AnularOrdenPagoLocalService anularOrdenPagoLocalService;

    @Mock
    private ObtenerFileService obtenerFileService;

    @Mock
    private FormaPagoService formaPagoService;

    @InjectMocks
    private OrdenPagoController ordenPagoController;

    @Mock
    private OrdenPagoMapper ordenPagoMapper;

    @Mock
    private FeignExceptionHandler feignExceptionHandler;

    @Mock
    private HttpServletRequest request;
    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testCreateOrdenPago() {
        // Arrange
        String user = "testUser";
        OrdenPagoRequestDto requestDto = new OrdenPagoRequestDto();
        OrdenPagoResponseDto responseDto = new OrdenPagoResponseDto(1, 1, 1, 1, "RUC123", "CODE123", 100.0, "2023-10-01", "CPB123", "PENDING", "2023-10-01", null, null, null, 100.0, "2023-10-01", null, "DESCRIPTION");

        when(pagoSunatService.ejecutar(requestDto, user)).thenReturn(responseDto);

        // Act
        ResponseEntity<GenericResponseDto<Object>> response = ordenPagoController.createOrdenPago(user, requestDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        // Add more assertions to verify the response body
    }

    @Test
     void testGetTasas() {
        // Arrange
        Integer entidadId = 1;
        String idComponente = "COMP123";
        String textSearch = "searchText";
        TasaResponse.Tasa tasa = new TasaResponse.Tasa();
        tasa.setProcedimientoId(1);
        tasa.setMonto(100.0);

        when(tasaService.obtenerTasa(entidadId, idComponente, textSearch)).thenReturn(tasa);

        // Act
        ResponseEntity<GenericResponseDto<Object>> response = ordenPagoController.getTasas(entidadId, idComponente, textSearch);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Add more assertions to verify the response body
    }

    @Test
     void testFindByEscalaIdAndDocumentoId() {
        // Arrange
        Integer escalaId = 1;
        Integer documentoId = 1;
        OrdenPago ordenPago = new OrdenPago();
        ordenPago.setOrdenPagoId(1);

        // Mock the behavior of ordenPagoService
        when(ordenPagoService.findByEscalaIdAndDocumentoId(escalaId, documentoId)).thenReturn(Collections.singletonList(ordenPago));


        // Act
        ResponseEntity<GenericResponseDto<Object>> response = ordenPagoController.findByEscalaIdAndDocumentoId(escalaId, documentoId);

        // Assert
        assertNotNull(response);
       assertEquals(OK, response.getStatusCode());
    }

    @Test
     void testAnular() {
        // Arrange
        String user = "testUser";
        Integer ordenPagoId = 1;
        OrdenPagoResponseDTO responseDTO = new OrdenPagoResponseDTO();
        responseDTO.setOrdenPagoId(1);

        when(anularOrdenPagoService.anularOrdenPago(ordenPagoId,user)).thenReturn(responseDTO);

        // Act
        ResponseEntity<GenericResponseDto<Object>> response = ordenPagoController.anular(user,ordenPagoId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Add more assertions to verify the response body
    }

    @Test
     void testAnularCreado() {
        // Arrange
        String user = "testUser";
        Integer ordenPagoId = 1;
        OrdenPagoResponseDto responseDto = new OrdenPagoResponseDto(1, 1, 1, 1, "RUC123", "CODE123", 100.0, "2023-10-01", "CPB123", "PENDING", "2023-10-01", null, null, null, 100.0, "2023-10-01", null, "DESCRIPTION");

        when(anularOrdenPagoLocalService.anularOrdenPagoLocal(ordenPagoId,user)).thenReturn(responseDto);

        // Act
        ResponseEntity<GenericResponseDto<Object>> response = ordenPagoController.anularCreado(user,ordenPagoId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Add more assertions to verify the response body
    }

    @Test
     void testGetPaymentMethods() {
        // Arrange
        Integer canalId = 1;
        Integer entidadId = 1;
        PaymentMethodResponse paymentMethod = new PaymentMethodResponse();
        paymentMethod.setCanalId(1);
        paymentMethod.setEntidadId(1);

        when(formaPagoService.getPaymentMethods(canalId, entidadId)).thenReturn(Collections.singletonList(paymentMethod));

        // Act
        ResponseEntity<ApiResponse> response = ordenPagoController.getPaymentMethods(canalId, entidadId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Add more assertions to verify the response body
    }

     @Test
      void testGetPdf() {
         // Arrange
         Integer ordenPagoId = 1;
         OrdenPago ordenPago = new OrdenPago();
         ordenPago.setOrdenPagoId(ordenPagoId);
         ordenPago.setFilenetGuid("some-guid");

         Resource mockResource = mock(Resource.class);

         when(ordenPagoService.findById(ordenPagoId)).thenReturn(ordenPago);
         when(obtenerFileService.getDocument(ordenPago.getFilenetGuid())).thenReturn(mockResource);

         // Act
         ResponseEntity<Resource> response = ordenPagoController.getPdf(ordenPagoId);

         // Assert
         assertNotNull(response);
         assertEquals(HttpStatus.OK, response.getStatusCode());

         HttpHeaders headers = response.getHeaders();
         assertEquals("application/pdf", headers.getFirst(HttpHeaders.CONTENT_TYPE));
         assertEquals("attachment; filename=\"voucher-1.pdf\"", headers.getFirst(HttpHeaders.CONTENT_DISPOSITION));

         assertEquals(mockResource, response.getBody());
     }

     @Test
     void testGetPdf_WhenFilenetGuidIsNull() {
         // Arrange
         Integer ordenPagoId = 1;
         OrdenPago ordenPago = new OrdenPago();
         ordenPago.setOrdenPagoId(ordenPagoId);
         ordenPago.setFilenetGuid(null);

         when(ordenPagoService.findById(ordenPagoId)).thenReturn(ordenPago);

         // Act & Assert
         assertThrows(PdfGenerationException.class, () -> {
             ordenPagoController.getPdf(ordenPagoId);
         });
     }


    @Test
    void testCreateOrdenPago_FeignException() {
       // Arrange
       String user = "testUser";
       OrdenPagoRequestDto requestDto = new OrdenPagoRequestDto();
       FeignException feignException = mock(FeignException.class);

       when(feignException.status()).thenReturn(503);
       when(feignException.getLocalizedMessage()).thenReturn("Service Unavailable");
       when(feignException.getMessage()).thenReturn("Service Unavailable");

       // 👇 Solución al NPE
       when(feignExceptionHandler.getErrorSource(feignException)).thenReturn("FUENTE_DE_ERROR");

       when(pagoSunatService.ejecutar(requestDto, user)).thenThrow(feignException);

       // Act
       ResponseEntity<GenericResponseDto<Object>> response = ordenPagoController.createOrdenPago(user, requestDto);

       // Assert
       assertNotNull(response);
       assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
       assertNotNull(response.getBody());
       assertEquals("ERROR", response.getBody().getMeta().getResult());
    }

    @Test
    void testFindByEscalaIdAndDocumentoId_FeignException() {
       // Arrange
       Integer escalaId = 1;
       Integer documentoId = 1;
       FeignException feignException = mock(FeignException.class);

       when(feignException.status()).thenReturn(503);
       when(feignException.getLocalizedMessage()).thenReturn("Service Unavailable");
       when(feignException.getMessage()).thenReturn("Service Unavailable");

       when(ordenPagoService.findByEscalaIdAndDocumentoId(escalaId, documentoId)).thenThrow(feignException);

       // Act
       ResponseEntity<GenericResponseDto<Object>> response = ordenPagoController.findByEscalaIdAndDocumentoId(escalaId, documentoId);

       // Assert
       assertNotNull(response);
       assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
       // Add more assertions to verify the response body
    }

    @Test
    void testCreateOrdenPago_GenericException() {
       // Arrange
       String user = "testUser";
       OrdenPagoRequestDto requestDto = new OrdenPagoRequestDto();
       Exception genericException = new RuntimeException("Unexpected error");

       when(pagoSunatService.ejecutar(requestDto, user)).thenThrow(genericException);

       // Act
       ResponseEntity<GenericResponseDto<Object>> response = ordenPagoController.createOrdenPago(user, requestDto);

       // Assert
       assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
       assertNotNull(response.getBody());
       assertEquals("ERROR", response.getBody().getMeta().getResult());
    }

    @Test
    void testGetTasas_TasaNull() {
       // Arrange
       Integer entidadId = 1;
       String idComponente = "COMP123";
       String textSearch = "searchText";

       when(tasaService.obtenerTasa(entidadId, idComponente, textSearch)).thenReturn(null);

       // Act
       ResponseEntity<GenericResponseDto<Object>> response = ordenPagoController.getTasas(entidadId, idComponente, textSearch);

       // Assert
       assertEquals(HttpStatus.OK, response.getStatusCode());
       assertNotNull(response.getBody());
       assertTrue(((List<?>) response.getBody().getData()).isEmpty());
    }

    @Test
    void testGetPaymentMethods_WithData() {
       // Arrange
       Integer canalId = 1;
       Integer entidadId = 1;
       List<PaymentMethodResponse> methods = List.of(new PaymentMethodResponse());

       when(formaPagoService.getPaymentMethods(canalId, entidadId)).thenReturn(methods);

       // Act
       ResponseEntity<ApiResponse> response = ordenPagoController.getPaymentMethods(canalId, entidadId);

       // Assert
       assertEquals(HttpStatus.OK, response.getStatusCode());
       assertNotNull(response.getBody());
       assertEquals("SUCCESS", response.getBody().getMeta().getResult());
    }

    @Test
    void testGetPaymentMethods_NoData() {
       // Arrange
       Integer canalId = 1;
       Integer entidadId = 1;

       when(formaPagoService.getPaymentMethods(canalId, entidadId)).thenReturn(Collections.emptyList());

       // Act
       ResponseEntity<ApiResponse> response = ordenPagoController.getPaymentMethods(canalId, entidadId);

       // Assert
       assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
       assertNotNull(response.getBody());
       assertEquals("ERROR", response.getBody().getMeta().getResult());
    }

    @Test
    void testGetPaymentMethodsConDatos() {
       // Arrange
       Integer canalId = 1;
       Integer entidadId = 2;
       PaymentMethodResponse method = new PaymentMethodResponse();
       List<PaymentMethodResponse> paymentMethods = Collections.singletonList(method);

       when(formaPagoService.getPaymentMethods(canalId, entidadId)).thenReturn(paymentMethods);

       // Act
       ResponseEntity<ApiResponse> response = ordenPagoController.getPaymentMethods(canalId, entidadId);

       // Assert
       assertNotNull(response);
       assertEquals(HttpStatus.OK, response.getStatusCode());
       ApiResponse body = response.getBody();
       assertNotNull(body);
        assertEquals("SUCCESS", body.getMeta().getResult());
       assertEquals("SUCCESS", response.getBody().getMeta().getResult());
    }
    @Test
    void testGetPaymentMethodsSinDatos() {
       // Arrange
       Integer canalId = 1;
       Integer entidadId = 2;

       when(formaPagoService.getPaymentMethods(canalId, entidadId)).thenReturn(Collections.emptyList());
       ResponseEntity<ApiResponse> response = ordenPagoController.getPaymentMethods(canalId, entidadId);
       assertNotNull(response);
       assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
       ApiResponse body = response.getBody();
       assertNotNull(body);
       assertEquals("ERROR", body.getMeta().getResult());
       assertEquals("ERROR", response.getBody().getMeta().getResult());
    }

    @Test
    void testCreateOrdenPago_FeignException503() {
       // Arrange
       String user = "testUser";
       OrdenPagoRequestDto requestDto = new OrdenPagoRequestDto();
       String errorMessage = "Service Unavailable";

       FeignException feignException = mock(FeignException.class);
       when(feignException.status()).thenReturn(503);
       when(feignException.getLocalizedMessage()).thenReturn(errorMessage);
       when(feignException.getMessage()).thenReturn("FeignException: " + errorMessage);

       // Mock necesario para evitar NullPointerException
       when(feignExceptionHandler.getErrorSource(any())).thenReturn("FEIGN_ERROR_SOURCE");

       when(pagoSunatService.ejecutar(any(), any())).thenThrow(feignException);

       // Act
       ResponseEntity<GenericResponseDto<Object>> response = ordenPagoController.createOrdenPago(user, requestDto);

       // Assert
       assertNotNull(response);
       assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
       assertNotNull(response.getBody());
       assertEquals("ERROR", response.getBody().getMeta().getResult());
    }


    @Test
    void testGetPaymentMethods_SinDatos() {
       // Arrange
       Integer canalId = 1;
       Integer entidadId = 1;

       when(formaPagoService.getPaymentMethods(canalId, entidadId)).thenReturn(Collections.emptyList());

       // Act
       ResponseEntity<ApiResponse> response = ordenPagoController.getPaymentMethods(canalId, entidadId);

       // Assert
       assertNotNull(response);
       assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

       ApiResponse responseBody = response.getBody();
       assertNotNull(responseBody);

       assertFalse(responseBody.getMeta().getResult().isEmpty());
       assertEquals("ERROR", response.getBody().getMeta().getResult());
    }


    @Test
void testValidarTupa0_Success() {
    // Arrange
    String user = "testUser";
    String indicador = "IND";
    String token = "Bearer token123";
    String tramite = "tramiteTest";

    OrdenPagoRequestDto requestDto = new OrdenPagoRequestDto();

    Tupa0ResponseDto responseDto = new Tupa0ResponseDto(
            true, "La tasa es 0, no se requiere pago"
    );

    // 👇 Mock de HttpServletRequest
    when(request.getHeader("Authorization")).thenReturn(token);
    when(request.getHeader("X-Tramite")).thenReturn(tramite);

    when(pagoSunatService.validarTupa0(requestDto, user, token, tramite, indicador))
            .thenReturn(responseDto);

    // Act
    ResponseEntity<GenericResponseDto<Object>> response =
            ordenPagoController.validarTupa0(user, indicador, requestDto);

    // Assert
    assertNotNull(response);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("SUCCESS", response.getBody().getMeta().getResult());

    List<?> data = (List<?>) response.getBody().getData();
    assertFalse(data.isEmpty());
}
 }
