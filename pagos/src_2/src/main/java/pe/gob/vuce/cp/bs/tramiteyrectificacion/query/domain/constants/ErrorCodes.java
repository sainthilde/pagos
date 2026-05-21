package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.constants;

/**
 * Clase que contiene los códigos de error utilizados en la aplicación.
 *
 * @project cp-api-bs-tramiteyrectificacion-query
 * @autor Luis Francisco Huertas Mostacero
 * @date 22/05/2024
 */
public final class ErrorCodes {
    private ErrorCodes() {
        throw new UnsupportedOperationException("Esta es una clase de constantes y no puede ser instanciada");
    }

    /**
     * Código de error para cuando un recurso no se encuentra.
     */
    public static final String NOT_FOUND = "E0069";

    /**
     * Código de error para errores de validación.
     */
    public static final String VALIDATION_ERROR = "E0070";

    /**
     * Código de error para desajustes de tipo.
     */
    public static final String TYPE_MISMATCH = "E0071";

    /**
     * Código de error para violaciones de restricciones.
     */
    public static final String CONSTRAINT_VIOLATION = "E0072";

    /**
     * Código de error para errores internos del servidor.
     */
    public static final String INTERNAL_SERVER_ERROR = "E0073";

}
