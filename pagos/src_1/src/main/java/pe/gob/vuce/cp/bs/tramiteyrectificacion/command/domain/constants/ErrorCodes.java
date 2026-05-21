package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants;

/**
 * Clase que contiene los códigos de error utilizados en la aplicación.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 19/08/2024
 */
public final class ErrorCodes {

    /**
     * Constructor privado para evitar la instanciación de esta clase de constantes.
     */
    private ErrorCodes() {
        throw new UnsupportedOperationException("Esta es una clase de constantes y no puede ser instanciada");
    }

    /**
     * Código de error para cuando un campo ya existe en el sistema.
     */
    public static final String FIELD_ALREADY_EXIST = "E0069";

    /**
     * Código de error para cuando un campo es inválido.
     */
    public static final String FIELD_INVALID = "E0070";

    /**
     * Código de error genérico para problemas en el servidor.
     */
    public static final String SERVER_ERROR = "E0071";

    /**
     * Código de error para argumentos ilegales en las llamadas a métodos.
     */
    public static final String ILLEGAL_ARGUMENT = "E0072";

    /**
     * Código de error para cuando un recurso no se encuentra.
     */
    public static final String NOT_FOUND = "E0073";

    /**
     * Código de error para errores de validación.
     */
    public static final String VALIDATION_ERROR = "E0074";

    /**
     * Código de error para violaciones de restricciones.
     */
    public static final String CONSTRAINT_VIOLATION = "E0076";

    /**
     * Código de error para errores internos del servidor.
     */
    public static final String INTERNAL_SERVER_ERROR = "E0077";

    /**
     * Código de error para operaciones inválidas.
     */
    public static final String INVALID_OPERATION = "E0077";

    /**
     * Código de error para desajustes de tipo.
     */
    public static final String TYPE_MISMATCH = "E0075";
}
