package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.api.advices.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.constants.ErrorCodes;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.exceptions.BusinessError;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.ApiResponseCpGeneric;

class ExceptionControllerAdviceTest {

    private ExceptionControllerAdvice exceptionControllerAdvice;
    private HttpServletRequest httpServletRequest;
    private WebRequest webRequest;

    @BeforeEach
    void setUp() {
        exceptionControllerAdvice = new ExceptionControllerAdvice();
        httpServletRequest = mock(HttpServletRequest.class);
        webRequest = mock(WebRequest.class);
    }

    @Test
    void handleNotFoundException() {
        BusinessError businessError = new BusinessError(HttpStatus.NOT_FOUND, "ERROR_CODE", List.of("param"),
                "Not found");
        ResponseEntity<ApiResponseCpGeneric> responseEntity = exceptionControllerAdvice
                .handleNotFoundException(businessError, webRequest);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("ERROR_CODE", responseEntity.getBody().getMeta().getMensajes().get(0).getCodigo());
    }

    @Test
    void handleValidationExceptions() {
        // Arrange
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("objectName", "fieldName", null); // null rejected value
        
        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));
    
        // Act
        ResponseEntity<ApiResponseCpGeneric> responseEntity = exceptionControllerAdvice
                .handleValidationExceptions(ex, httpServletRequest);
    
        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(ErrorCodes.VALIDATION_ERROR, responseEntity.getBody().getMeta().getMensajes().get(0).getCodigo());
        assertEquals("fieldName null", responseEntity.getBody().getMeta().getMensajes().get(0).getMensaje());
    }
    

    @Test
    void handleTypeMismatchExceptions() {
        // Arrange
        MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
        when(ex.getName()).thenReturn("testParam");
        when(ex.getLocalizedMessage()).thenReturn("Localized error message");
        ResponseEntity<ApiResponseCpGeneric> responseEntity = exceptionControllerAdvice
                .handleTypeMismatchExceptions(ex, httpServletRequest);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("testParam",
                responseEntity.getBody().getMeta().getMensajes().get(0).getParametrosDeMensaje().get(0));
    }

    @Test
    void handleIllegalArgumentException() {
        IllegalArgumentException ex = new IllegalArgumentException("Invalid argument");
        ResponseEntity<ApiResponseCpGeneric> responseEntity = exceptionControllerAdvice
                .handleIllegalArgumentException(ex, httpServletRequest);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void handleConstraintViolationException() {
        ConstraintViolationException ex = new ConstraintViolationException("Constraint violation", null);
        ResponseEntity<ApiResponseCpGeneric> responseEntity = exceptionControllerAdvice
                .handleConstraintViolationException(ex, httpServletRequest);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void handleNoHandlerFoundException() {
        NoHandlerFoundException ex = new NoHandlerFoundException("GET", "/notfound", null);
        ResponseEntity<ApiResponseCpGeneric> responseEntity = exceptionControllerAdvice
                .handleNoHandlerFoundException(ex, httpServletRequest);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().getMeta().getMensajes().get(0).getMensaje().contains("/notfound"));
    }

    @Test
    void handleAllExceptions() {
        Exception ex = new Exception("General error");
        ResponseEntity<ApiResponseCpGeneric> responseEntity = exceptionControllerAdvice.handleAllExceptions(ex,
                httpServletRequest);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
}
