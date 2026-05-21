package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.enums;

/**
 * Enum que representa los posibles resultados de una operación en la aplicación.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 19/08/2024
 */
public enum MetaResults {

    /**
     * Indica que la operación fue exitosa.
     */
    SUCCESS("SUCCESS"),

    /**
     * Indica que la operación encontró un error.
     */
    ERROR("ERROR");

    private final String value;

    /**
     * Constructor del enum.
     *
     * @param value Valor asociado al resultado de la operación.
     */
    MetaResults(String value) {
        this.value = value;
    }

    /**
     * Obtiene el valor asociado al resultado de la operación.
     *
     * @return El valor del resultado de la operación.
     */
    public String getValue() {
        return value;
    }
}
