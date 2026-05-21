package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.enums;

/**
 * Enumeración que representa los resultados de meta.
 *
 * @project cp-api-bs-tramiteyrectificacion-query
 * @autor Luis Francisco Huertas Mostacero
 * @date 24/06/2024
 */
public enum MetaResults {
    SUCCESS("SUCCESS"),
    ERROR("ERROR");

    private final String value;

    /**
     * Constructor para establecer el valor del resultado de meta.
     * 
     * @param value el valor del resultado de meta
     * @project cp-api-bs-tramiteyrectificacion-query
     * @autor Luis Francisco Huertas Mostacero
     * @date 24/06/2024
     */
    MetaResults(String value) {
        this.value = value;
    }

    /**
     * Obtiene el valor asociado al resultado de meta.
     * 
     * @return el valor del resultado de meta
     * @project cp-api-bs-tramiteyrectificacion-query
     * @autor Luis Francisco Huertas Mostacero
     * @date 24/06/2024
     */
    public String getValue() {
        return value;
    }
}
