package pe.gob.vuce.cp.sp.pagos.domain.exception;

import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pe.gob.vuce.cp.sp.pagos.domain.constants.ResponseUtil;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.GenericResponseDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsHelpers.PARAM_INVALID;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsHelpers.ERROR_SERVER;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsHelpers.ORDEN_PAGO_NOT;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsHelpers.ERROR_INTERNAL;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsHelpers.SERVER_UNAVALILABLE;

/**
 * Controlador de excepciones global para manejar excepciones en las respuestas de los servicios REST.
 * <p>
 * Esta clase utiliza anotaciones de @RestControllerAdvice para capturar y gestionar excepciones específicas,
 * devolviendo respuestas personalizadas en formato JSON con detalles del error.
 * </p>
 * @author MATEO HUANCHO
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-29
 */
@RestControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * Maneja la excepción {@code HttpServerErrorException.InternalServerError} y devuelve una respuesta personalizada.
     *
     * @param ex      Excepción de error interno del servidor.
     * @param request Información de la solicitud web en la que ocurrió el error.
     * @return Una entidad de respuesta con detalles del error y el estado HTTP 500 (Internal Server Error).
     */
    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public ResponseEntity<GenericResponseDto<Object>> handleInternalServerError(HttpServerErrorException.InternalServerError ex, WebRequest request) {
        CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(), ERROR_INTERNAL + ex.getMessage(), request.getDescription(false));
        GenericResponseDto<Object> response = ResponseUtil.createApiResponseVuceCP2Exception(
                String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                err.getMessage(),
                List.of(err.getDetails())
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    /**
     * Maneja la excepción {@code HttpServerErrorException.ServiceUnavailable} y devuelve una respuesta personalizada.
     *
     * @param ex      Excepción de servicio no disponible.
     * @param request Información de la solicitud web en la que ocurrió el error.
     * @return Una entidad de respuesta con detalles del error y el estado HTTP 503 (Service Unavailable).
     */
    @ExceptionHandler(HttpServerErrorException.ServiceUnavailable.class)
    public ResponseEntity<GenericResponseDto<Object>> handleServiceUnavailableException(HttpServerErrorException.ServiceUnavailable ex, WebRequest request) {
        CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(), SERVER_UNAVALILABLE + ex.getMessage(), request.getDescription(false));
        GenericResponseDto<Object> response = ResponseUtil.createApiResponseVuceCP2Exception(
                String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()),
                err.getMessage(),
                List.of(err.getDetails())
        );
        return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
    }
    /**
     * Maneja la excepción {@code ModelNotFoundException} y devuelve una respuesta personalizada.
     *
     * @param ex      Excepción que indica que un modelo no fue encontrado.
     * @param request Información de la solicitud web en la que ocurrió el error.
     * @return Una entidad de respuesta con detalles del error y el estado HTTP 404 (Not Found).
     */
    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<GenericResponseDto<Object>> handleModelNotFoundException(ModelNotFoundException ex, WebRequest request) {
        CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        GenericResponseDto<Object> response = ResponseUtil.createApiResponseVuceCP2Exception(
                String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                err.getMessage(),
                List.of(err.getDetails())
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    /**
     * Maneja la excepción {@code ArithmeticException} y devuelve una respuesta personalizada.
     *
     * @param ex      Excepción aritmética lanzada durante la ejecución.
     * @param request Información de la solicitud web en la que ocurrió el error.
     * @return Una entidad de respuesta con detalles del error y el estado HTTP 400 (Bad Request).
     */
    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<GenericResponseDto<Object>> handleArithmeticException(ArithmeticException ex, WebRequest request) {
        CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        GenericResponseDto<Object> response = ResponseUtil.createApiResponseVuceCP2Exception(
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                err.getMessage(),
                List.of(err.getDetails())
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    /**
     * Maneja la excepción {@code MethodArgumentNotValidException} y devuelve una respuesta personalizada
     * cuando un argumento de método no es válido.
     *
     * @param ex      Excepción de argumentos no válidos.
     * @param headers Cabeceras de la solicitud.
     * @param status  Estado HTTP de la respuesta.
     * @param request Información de la solicitud web en la que ocurrió el error.
     * @return Una entidad de respuesta con detalles del error y el estado HTTP 400 (Bad Request).
     */
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField().concat(":").concat(Objects.requireNonNull(err.getDefaultMessage())))
                .collect(Collectors.joining(","));

        CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(), msg, request.getDescription(false));
        GenericResponseDto<Object> response = ResponseUtil.createApiResponseVuceCP2Exception(
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                err.getMessage(),
                List.of(err.getDetails())
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(OrdenPagoNotFoundException.class)
    public ResponseEntity<GenericResponseDto<Object>> handleOrdenPagoNotFound(OrdenPagoNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), ORDEN_PAGO_NOT);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<GenericResponseDto<Object>> handleFeignException(FeignException ex) {
        HttpStatus status;

        switch (ex.status()) {
            case 503 -> status = HttpStatus.SERVICE_UNAVAILABLE;
            case 500 -> status = HttpStatus.INTERNAL_SERVER_ERROR;
            case 404 -> status = HttpStatus.NOT_FOUND;
            case 412 -> status = HttpStatus.PRECONDITION_FAILED;
            default -> status = HttpStatus.BAD_GATEWAY;
        }
        return buildResponse(status, ex.getLocalizedMessage(), ex.getMessage());
}

    @ExceptionHandler({IllegalArgumentException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<GenericResponseDto<Object>> handleBadRequest(Exception ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), PARAM_INVALID);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponseDto<Object>> handleGenericException(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), ERROR_SERVER);
    }

    private ResponseEntity<GenericResponseDto<Object>> buildResponse(HttpStatus status, String devMessage, String userMessage) {
        GenericResponseDto<Object> response = ResponseUtil.createApiResponseVuceCP2Exception(
                String.valueOf(status.value()),
                devMessage,
                List.of(userMessage)
        );
        return new ResponseEntity<>(response, status);
    }
}
