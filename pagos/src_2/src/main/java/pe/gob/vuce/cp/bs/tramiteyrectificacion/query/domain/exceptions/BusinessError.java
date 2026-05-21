package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.exceptions;

import java.util.List;

import org.springframework.http.HttpStatus;

/**
 * @project cp-api-bs-tramiteyrectificacion-query
 * @autor Luis Francisco Huertas Mostacero
 * @date 22/05/2024
 * 
 *       Clase de excepción para errores de negocio.
 */
public class BusinessError extends RuntimeException {

    private final String errorCode;
    private final List<String> messageParams;
    private final HttpStatus httpStatus;

    /**
     * @project cp-api-bs-tramiteyrectificacion-query
     * @autor Luis Francisco Huertas Mostacero
     * @date 02/07/2024
     * 
     *       Constructor para crear una instancia de BusinessError.
     * 
     * @param httpStatus    el estado HTTP asociado con el error
     * @param errorCode     el código de error
     * @param messageParams los parámetros del mensaje de error
     */
    public BusinessError(HttpStatus httpStatus, String errorCode, List<String> messageParams, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.messageParams = messageParams;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public List<String> getMessageParams() {
        return messageParams;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
