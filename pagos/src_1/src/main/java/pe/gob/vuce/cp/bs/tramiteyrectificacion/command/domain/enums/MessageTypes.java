package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.enums;

/**
 * Enum que representa los tipos de mensajes utilizados en la aplicación.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 19/08/2024
 */
public enum MessageTypes {

    /**
     * Tipo de mensaje de error.
     */
    ERROR("E"),

    /**
     * Tipo de mensaje informativo.
     */
    INFO("I");

    private final String code;

    /**
     * Constructor del enum.
     *
     * @param code Código asociado al tipo de mensaje.
     */
    MessageTypes(String code) {
        this.code = code;
    }

    /**
     * Obtiene el código asociado al tipo de mensaje.
     *
     * @return El código del tipo de mensaje.
     */
    public String getCode() {
        return code;
    }
}
