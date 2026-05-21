package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.api.advices.exception;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants.Constants;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants.ErrorCodes;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.enums.MessageTypes;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.enums.MetaResults;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.exceptions.BusinessError;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.exception.ApiResponseMeta;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.exception.ApiResponseVuceCP2Exception;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.exception.VuceCP2Exception;
import pe.gob.vuce.cp.framework.globallogger.util.ErrorLoggingUtil;


/**
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Luis Francisco Huertas Mostacero
 * @date 22/05/2024
 **/
@ControllerAdvice
public class ExceptionControllerAdvice {

        /**
         * Maneja las excepciones de tipo BusinessError.
         *
         * @param ex      la excepción BusinessError que se lanzó
         * @param request la solicitud web actual
         * @return una respuesta con ApiResponseVuceCP2Exception y el estado HTTP
         *         NOT_FOUND
         * @project cp-api-bs-tramiteyrectificacion-command
         * @autor Luis Francisco Huertas Mostacero
         * @date 22/05/2024
         **/
        @ExceptionHandler(BusinessError.class)
        public ResponseEntity<ApiResponseVuceCP2Exception> handleNotFoundException(BusinessError ex,
                                                                                   WebRequest request) {
                String errorCode = ex.getErrorCode() != null ? ex.getErrorCode()
                                : String.valueOf(HttpStatus.NOT_FOUND.value());
                ApiResponseVuceCP2Exception response = createApiResponseVuceCP2Exception(errorCode,
                                ex.getLocalizedMessage(), ex.getMessageParams());

                logException(ex, response, ex.getHttpStatus());
                return new ResponseEntity<>(response, ex.getHttpStatus());
        }

        /**
         * Maneja las excepciones de tipo MethodArgumentNotValidException.
         *
         * @param ex      la excepción MethodArgumentNotValidException que se lanzó
         * @param request la solicitud HTTP actual
         * @return una respuesta con ApiResponseVuceCP2Exception y el estado HTTP
         *         BAD_REQUEST
         * @project cp-api-bs-tramiteyrectificacion-command
         * @autor Luis Francisco Huertas Mostacero
         * @date 22/05/2024
         **/
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiResponseVuceCP2Exception> handleValidationExceptions(
                        MethodArgumentNotValidException ex, HttpServletRequest request) {

                List<VuceCP2Exception> errorMessages = ex.getBindingResult().getFieldErrors().stream()
                                .map(fieldError -> {
                                        VuceCP2Exception exception = new VuceCP2Exception();
                                        String rejectedValue = fieldError.getRejectedValue() != null
                                                        ? fieldError.getRejectedValue().toString()
                                                        : Constants.NO_VALUE;
                                        exception.setCodigo(ErrorCodes.VALIDATION_ERROR);
                                        exception.setMensaje(
                                                        fieldError.getField() + " " + fieldError.getDefaultMessage());
                                        exception.setTipo(MessageTypes.ERROR.getCode());
                                        exception.setParametrosDeMensaje(List.of(fieldError.getField(), rejectedValue));
                                        return exception;
                                })
                                .collect(Collectors.toList());

                ApiResponseVuceCP2Exception response = createApiResponseVuceCP2Exception(errorMessages);

                logException(ex, response, HttpStatus.BAD_REQUEST);

                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        /**
         * Maneja las excepciones de tipo MethodArgumentTypeMismatchException.
         *
         * @param ex      la excepción MethodArgumentTypeMismatchException que se lanzó
         * @param request la solicitud HTTP actual
         * @return una respuesta con ApiResponseVuceCP2Exception y el estado HTTP
         *         BAD_REQUEST
         * @project cp-api-bs-tramiteyrectificacion-command
         * @autor Luis Francisco Huertas Mostacero
         * @date 22/05/2024
         **/
        @ExceptionHandler(MethodArgumentTypeMismatchException.class)
        public ResponseEntity<ApiResponseVuceCP2Exception> handleTypeMismatchExceptions(
                        MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
                ApiResponseVuceCP2Exception response = createApiResponseVuceCP2Exception(ErrorCodes.TYPE_MISMATCH,
                                ex.getLocalizedMessage(), List.of(ex.getName()));
                logException(ex, response, HttpStatus.BAD_REQUEST);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        /**
         * Maneja las excepciones de tipo IllegalArgumentException.
         *
         * @param ex      la excepción IllegalArgumentException que se lanzó
         * @param request la solicitud HTTP actual
         * @return una respuesta con ApiResponseVuceCP2Exception y el estado HTTP
         *         BAD_REQUEST
         * @project cp-api-bs-tramiteyrectificacion-command
         * @autor Luis Francisco Huertas Mostacero
         * @date 22/05/2024
         **/
        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ApiResponseVuceCP2Exception> handleIllegalArgumentException(IllegalArgumentException ex,
                        HttpServletRequest request) {
                ApiResponseVuceCP2Exception response = createApiResponseVuceCP2Exception(
                                ErrorCodes.CONSTRAINT_VIOLATION,
                                ex.getLocalizedMessage(), List.of());
                logException(ex, response, HttpStatus.BAD_REQUEST);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        /**
         * Maneja las excepciones de tipo ConstraintViolationException.
         *
         * @param ex      la excepción ConstraintViolationException que se lanzó
         * @param request la solicitud HTTP actual
         * @return una respuesta con ApiResponseVuceCP2Exception y el estado HTTP
         *         BAD_REQUEST
         * @project cp-api-bs-tramiteyrectificacion-command
         * @autor Luis Francisco Huertas Mostacero
         * @date 22/05/2024
         **/
        @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity<ApiResponseVuceCP2Exception> handleConstraintViolationException(
                        ConstraintViolationException ex, HttpServletRequest request) {
                ApiResponseVuceCP2Exception response = createApiResponseVuceCP2Exception(
                                ErrorCodes.CONSTRAINT_VIOLATION, ex.getLocalizedMessage(), List.of());

                logException(ex, response, HttpStatus.BAD_REQUEST);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        /**
         * Maneja las excepciones de tipo NoHandlerFoundException.
         *
         * @param ex      la excepción NoHandlerFoundException que se lanzó
         * @param request la solicitud HTTP actual
         * @return una respuesta con ApiResponseVuceCP2Exception y el estado HTTP
         *         NOT_FOUND
         * @project cp-api-bs-tramiteyrectificacion-command
         * @autor Luis Francisco Huertas Mostacero
         * @date 22/05/2024
         **/
        @ExceptionHandler(NoHandlerFoundException.class)
        public ResponseEntity<ApiResponseVuceCP2Exception> handleNoHandlerFoundException(NoHandlerFoundException ex,
                        HttpServletRequest request) {
                ApiResponseVuceCP2Exception response = createApiResponseVuceCP2Exception(ErrorCodes.NOT_FOUND,
                                ex.getLocalizedMessage(), List.of(ex.getRequestURL()));
                logException(ex, response, HttpStatus.NOT_FOUND);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        /**
         * Maneja todas las demás excepciones.
         *
         * @param ex      la excepción que se lanzó
         * @param request la solicitud HTTP actual
         * @return una respuesta con ApiResponseVuceCP2Exception y el estado HTTP
         *         INTERNAL_SERVER_ERROR
         * @project cp-api-bs-tramiteyrectificacion-command
         * @autor Luis Francisco Huertas Mostacero
         * @date 22/05/2024
         **/
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiResponseVuceCP2Exception> handleAllExceptions(Exception ex,
                        HttpServletRequest request) {
                ApiResponseVuceCP2Exception response = createApiResponseVuceCP2Exception(
                                ErrorCodes.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), List.of());
                logException(ex, response, HttpStatus.INTERNAL_SERVER_ERROR);
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        /**
         * Registra los detalles de la excepción.
         *
         * @param ex                          la excepción que se lanzó
         * @param apiResponseVuceCP2Exception la respuesta de la API con la excepción
         * @param httpStatus                  el estado HTTP correspondiente
         * @project cp-api-bs-tramiteyrectificacion-command
         * @autor Luis Francisco Huertas Mostacero
         * @date 22/05/2024
         **/
        private void logException(Exception ex, ApiResponseVuceCP2Exception apiResponseVuceCP2Exception,
                        HttpStatus httpStatus) {
                ErrorLoggingUtil.logException(ex, this.getClass().getName(), "handleException",
                                apiResponseVuceCP2Exception, httpStatus);
        }

        /**
         * Crea una instancia de ApiResponseVuceCP2Exception.
         *
         * @param errorCode           el código de error
         * @param description         la descripción del error
         * @param parametrosDeMensaje los parámetros del mensaje de error
         * @return una instancia de ApiResponseVuceCP2Exception
         * @project cp-api-bs-tramiteyrectificacion-command
         * @autor Luis Francisco Huertas Mostacero
         * @date 22/05/2024
         **/
        private ApiResponseVuceCP2Exception createApiResponseVuceCP2Exception(String errorCode, String description,
                        List<String> parametrosDeMensaje) {
                VuceCP2Exception exception = new VuceCP2Exception();
                exception.setCodigo(errorCode);
                exception.setMensaje(description);
                exception.setParametrosDeMensaje(parametrosDeMensaje);
                exception.setTipo(MessageTypes.ERROR.getCode());

                ApiResponseMeta meta = new ApiResponseMeta();
                meta.setResult(MetaResults.ERROR.getValue());
                meta.setMensajes(List.of(exception));
                meta.setCantidadRegistros(0);
                meta.setAtributos(Map.of());

                ApiResponseVuceCP2Exception response = new ApiResponseVuceCP2Exception();
                response.setMeta(meta);
                response.setData(List.of());
                return response;
        }

        /**
         * Crea una instancia de ApiResponseVuceCP2Exception con una lista de
         * excepciones.
         *
         * @param exceptions la lista de excepciones VuceCP2Exception
         * @return una instancia de ApiResponseVuceCP2Exception
         * @project cp-api-bs-tramiteyrectificacion-command
         * @autor Luis Francisco Huertas Mostacero
         * @date 22/05/2024
         **/
        private ApiResponseVuceCP2Exception createApiResponseVuceCP2Exception(List<VuceCP2Exception> exceptions) {
                ApiResponseMeta meta = new ApiResponseMeta();
                meta.setResult(MetaResults.ERROR.getValue());
                meta.setMensajes(exceptions);
                meta.setCantidadRegistros(0);
                meta.setAtributos(Map.of());

                ApiResponseVuceCP2Exception response = new ApiResponseVuceCP2Exception();
                response.setMeta(meta);
                response.setData(List.of());
                return response;
        }
}
