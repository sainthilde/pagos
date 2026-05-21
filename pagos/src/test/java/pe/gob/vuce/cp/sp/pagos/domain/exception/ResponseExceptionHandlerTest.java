package pe.gob.vuce.cp.sp.pagos.domain.exception;

import feign.FeignException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.GenericResponseDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.ResponseMessageDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResponseExceptionHandlerTest {

    private ResponseExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new ResponseExceptionHandler();
    }

    @Test
    void testHandleInternalServerError() {
        // given
        HttpServerErrorException.InternalServerError ex = mock(HttpServerErrorException.InternalServerError.class);
        when(ex.getMessage()).thenReturn("Internal error");

        WebRequest mockRequest = mock(WebRequest.class);
        when(mockRequest.getDescription(false)).thenReturn("uri=/api/test");

        // when
        ResponseEntity<GenericResponseDto<Object>> response = handler.handleInternalServerError(ex, mockRequest);

        // then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("ERROR", response.getBody().getMeta().getResult());

        ResponseMessageDto mensaje = response.getBody().getMeta().getMensajes().get(0);
        assertEquals("500", mensaje.getCodigo()); // <-- validación de código
        assertTrue(mensaje.getMensaje().contains("Internal error")); // <-- validación del mensaje
        assertEquals(List.of("uri=/api/test"), mensaje.getParametrosDeMensaje()); // <-- validación de detalles
    }


    @Test
    void testHandleModelNotFoundException() {
        // given
        ModelNotFoundException ex = new ModelNotFoundException("Modelo no encontrado");
        WebRequest request = mock(WebRequest.class);
        when(request.getDescription(false)).thenReturn("uri=/api/model");

        // when
        ResponseEntity<GenericResponseDto<Object>> response = handler.handleModelNotFoundException(ex, request);

        // then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());

        ResponseMessageDto mensaje = response.getBody().getMeta().getMensajes().get(0);
        assertEquals("500", mensaje.getCodigo()); // <- el handler está usando 500 como código lógico
        assertEquals("Modelo no encontrado", mensaje.getMensaje());
        assertEquals(List.of("uri=/api/model"), mensaje.getParametrosDeMensaje());
    }


    @Test
    void testHandleFeignException503() {
        // given
        FeignException feignEx = mock(FeignException.class);
        when(feignEx.status()).thenReturn(503);
        when(feignEx.getMessage()).thenReturn("Service Unavailable");
        when(feignEx.getLocalizedMessage()).thenReturn("Service Unavailable Localized");

        // when
        ResponseEntity<GenericResponseDto<Object>> response = handler.handleFeignException(feignEx);

        // then
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getMeta().getMensajes().get(0).getMensaje().contains("Service Unavailable"));
    }

    @Test
    void testHandleArithmeticException() {
        // given
        ArithmeticException ex = new ArithmeticException("Division by zero");
        WebRequest mockRequest = mock(WebRequest.class);
        when(mockRequest.getDescription(false)).thenReturn("uri=/api/test");

        ResponseExceptionHandler responseExceptionHandler = new ResponseExceptionHandler();

        // when
        ResponseEntity<GenericResponseDto<Object>> response = responseExceptionHandler.handleArithmeticException(ex, mockRequest);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("ERROR", response.getBody().getMeta().getResult());
        assertEquals("Division by zero", response.getBody().getMeta().getMensajes().get(0).getMensaje());
        assertEquals("400", response.getBody().getMeta().getMensajes().get(0).getCodigo());
    }

    @Test
    void testHandleServiceUnavailableException() {
        // given
        String errorMessage = "Service Temporarily Unavailable";
        String requestDescription = "503";

        // Mock de la excepción específica
        HttpServerErrorException.ServiceUnavailable ex = mock(HttpServerErrorException.ServiceUnavailable.class);
        when(ex.getMessage()).thenReturn(errorMessage);

        // Mock del WebRequest
        WebRequest mockRequest = mock(WebRequest.class);
        when(mockRequest.getDescription(false)).thenReturn(requestDescription);

        // Instancia del handler
        ResponseExceptionHandler exceptionHandler = new ResponseExceptionHandler();

        // when
        ResponseEntity<GenericResponseDto<Object>> response = exceptionHandler.handleServiceUnavailableException(ex, mockRequest);

        // then
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("ERROR", response.getBody().getMeta().getResult());

        // Validar mensaje de error
        String actualMessage = response.getBody().getMeta().getMensajes().get(0).getMensaje();
        assertTrue(actualMessage.contains(errorMessage)); // "Service Temporarily Unavailable"

        // Validar código (request description)
        assertEquals(requestDescription, response.getBody().getMeta().getMensajes().get(0).getCodigo());
    }

    @Test
    void testHandleOrdenPagoNotFound() {
        // given
        OrdenPagoNotFoundException ex = new OrdenPagoNotFoundException("Orden de pago no encontrada");

        ResponseExceptionHandler responseExceptionHandler = new ResponseExceptionHandler();

        // when
        ResponseEntity<GenericResponseDto<Object>> response = responseExceptionHandler.handleOrdenPagoNotFound(ex);

        // then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("ERROR", response.getBody().getMeta().getResult());
        assertEquals("Orden de pago no encontrada", response.getBody().getMeta().getMensajes().get(0).getMensaje());
    }

    @Test
    void testHandleFeignException() {
        // given
        FeignException ex = mock(FeignException.class);
        when(ex.status()).thenReturn(503);
        when(ex.getLocalizedMessage()).thenReturn("Servicio no disponible");
        when(ex.getMessage()).thenReturn("Error 503");

        ResponseExceptionHandler responseExceptionHandler = new ResponseExceptionHandler();

        // when
        ResponseEntity<GenericResponseDto<Object>> response = responseExceptionHandler.handleFeignException(ex);

        // then
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("ERROR", response.getBody().getMeta().getResult());
        assertEquals("Servicio no disponible", response.getBody().getMeta().getMensajes().get(0).getMensaje());
    }
    @Test
    void testHandleBadRequestWithIllegalArgument() {
        // given
        IllegalArgumentException ex = new IllegalArgumentException("Parámetro inválido");

        ResponseExceptionHandler exceptionHandler = new ResponseExceptionHandler();

        // when
        ResponseEntity<GenericResponseDto<Object>> response = exceptionHandler.handleBadRequest(ex);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("ERROR", response.getBody().getMeta().getResult());
        assertEquals("Parámetro inválido", response.getBody().getMeta().getMensajes().get(0).getMensaje());
    }
    @Test
    void testHandleGenericException() {
        // given
        Exception ex = new Exception("Excepción inesperada");

        ResponseExceptionHandler exceptionHandler = new ResponseExceptionHandler();

        // when
        ResponseEntity<GenericResponseDto<Object>> response = exceptionHandler.handleGenericException(ex);

        // then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("ERROR", response.getBody().getMeta().getResult());
        assertEquals("Excepción inesperada", response.getBody().getMeta().getMensajes().get(0).getMensaje());
    }

    @Test
    void testHandleMethodArgumentNotValid() {
        BindingResult bindingResult = getBindingResult();

        MethodParameter methodParameter = mock(MethodParameter.class);
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(methodParameter, bindingResult);

        WebRequest webRequest = mock(WebRequest.class);
        when(webRequest.getDescription(false)).thenReturn("uri=/api/endpoint");

        ResponseExceptionHandler exceptionHandler = new ResponseExceptionHandler();

        ResponseEntity<Object> response = exceptionHandler.handleMethodArgumentNotValid(
                ex,
                HttpHeaders.EMPTY,
                HttpStatus.BAD_REQUEST,
                webRequest
        );

        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertInstanceOf(GenericResponseDto.class, response.getBody());

        GenericResponseDto<?> genericResponse = (GenericResponseDto<?>) response.getBody();

        assertEquals("ERROR", genericResponse.getMeta().getResult());

        String mensaje = genericResponse.getMeta().getMensajes().get(0).getMensaje();
        assertTrue(mensaje.contains("nombre:no debe estar vacío"));
        assertTrue(mensaje.contains("edad:debe ser mayor que 0"));

        String codigo = genericResponse.getMeta().getMensajes().get(0).getCodigo();
        assertEquals("400", codigo);
    }

    @NotNull
    private BindingResult getBindingResult() {
        class DummyTarget {
            private String nombre;
            private Integer edad;

            public String getNombre() { return nombre; }
            public void setNombre(String nombre) { this.nombre = nombre; }

            public Integer getEdad() { return edad; }
            public void setEdad(Integer edad) { this.edad = edad; }
        }

        DummyTarget target = new DummyTarget();

        BindingResult bindingResult = new BeanPropertyBindingResult(target, "dummyTarget");
        bindingResult.rejectValue("nombre", "NotEmpty", "no debe estar vacío");
        bindingResult.rejectValue("edad", "Min", "debe ser mayor que 0");
        return bindingResult;
    }

    @Test
    void testHandleFeignException500() {
        FeignException ex = mock(FeignException.class);
        when(ex.status()).thenReturn(500);
        when(ex.getLocalizedMessage()).thenReturn("Internal Server Error");
        when(ex.getMessage()).thenReturn("500 Internal Server Error");

        ResponseEntity<GenericResponseDto<Object>> response = handler.handleFeignException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testHandleFeignException404() {
        FeignException ex = mock(FeignException.class);
        when(ex.status()).thenReturn(404);
        when(ex.getLocalizedMessage()).thenReturn("Not Found");
        when(ex.getMessage()).thenReturn("404 Not Found");

        ResponseEntity<GenericResponseDto<Object>> response = handler.handleFeignException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testHandleFeignException412() {
        FeignException ex = mock(FeignException.class);
        when(ex.status()).thenReturn(412);
        when(ex.getLocalizedMessage()).thenReturn("Precondition Failed");
        when(ex.getMessage()).thenReturn("412 Precondition Failed");

        ResponseEntity<GenericResponseDto<Object>> response = handler.handleFeignException(ex);

        assertEquals(HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testHandleFeignExceptionDefault() {
        FeignException ex = mock(FeignException.class);
        when(ex.status()).thenReturn(999);
        when(ex.getLocalizedMessage()).thenReturn("Unknown Error");
        when(ex.getMessage()).thenReturn("999 Unknown Error");

        ResponseEntity<GenericResponseDto<Object>> response = handler.handleFeignException(ex);

        assertEquals(HttpStatus.BAD_GATEWAY, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}
