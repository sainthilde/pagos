package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.enums;

/**
 * Enumeración que representa los tipos de mensajes.
 *
 * @project cp-api-bs-tramiteyrectificacion-query
 * @autor Luis Francisco Huertas Mostacero
 * @date 24/06/2024
 */
public enum MessageTypes {
    ERROR("E"),
    INFO("I");

    private final String code;

    /**
     * Constructor para establecer el código del tipo de mensaje.
     * 
     * @param code el código del tipo de mensaje
     * @project cp-api-bs-tramiteyrectificacion-query
     * @autor Luis Francisco Huertas Mostacero
     * @date 24/06/2024
     */
    MessageTypes(String code) {
        this.code = code;
    }

    /**
     * Obtiene el código asociado al tipo de mensaje.
     * 
     * @return el código del tipo de mensaje
     * @project cp-api-bs-tramiteyrectificacion-query
     * @autor Luis Francisco Huertas Mostacero
     * @date 24/06/2024
     */
    public String getCode() {
        return code;
    }
}
