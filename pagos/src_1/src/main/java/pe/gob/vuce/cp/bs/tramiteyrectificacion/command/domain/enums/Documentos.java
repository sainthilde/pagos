package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.enums;

/**
 * Enum que representa los tipos de documentos manejados en la aplicación.
 *
 * @project cp-api-bs-fichasanitaria-command
 * @autor Luis Francisco Huertas Mostacero
 * @date 15/08/2024
 */
public enum Documentos {

    /**
     * Documento correspondiente a la Ficha Sanitaria.
     */
    TRAMITE("DGE"),
    ACUERDO_MERP("MERP"),
    DECLARACION_GENERAL_ZARPE("DGZ"),
    DECLARACION_GENERAL_ARRIBO("DGA"),
    DECLARACION_MARITIMA_SANIDAD("DMS"),
    PATENTE_SANITARIA("SPS");

    private final String value;

    /**
     * Constructor del enum.
     *
     * @param value Valor asociado al tipo de documento.
     */
    Documentos(String value) {
        this.value = value;
    }

    /**
     * Obtiene el valor asociado al tipo de documento.
     *
     * @return El valor del tipo de documento.
     */
    public String getValue() {
        return value;
    }
}
