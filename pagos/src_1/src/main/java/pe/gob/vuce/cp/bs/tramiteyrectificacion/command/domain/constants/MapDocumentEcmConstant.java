package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants;

/**
 * MapDocumentEcmConstant defines properties for mapping and calling the ECM
 * API.
 * This class cannot be instantiated.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 21/08/2024
 */
public final class MapDocumentEcmConstant {

    /**
     * Constructor privado para evitar la instanciación de esta clase de constantes.
     */
    private MapDocumentEcmConstant() {
        throw new UnsupportedOperationException("Esta es una clase de constantes y no puede ser instanciada");
    }

    /**
     * Clave para el componente en la llamada al ECM.
     */
    public static final String KEY_COMPONENTE = "componente";

    /**
     * Valor por defecto para el componente en la llamada al ECM.
     */
    public static final String VALUE_COMPONENTE = "CP2";

    /**
     * Clave para la opción en la llamada al ECM.
     */
    public static final String KEY_OPCION = "opcion";

    /**
     * Valor por defecto para la opción en la llamada al ECM.
     */
    public static final String VALUE_OPCION = "ficha_tecnica";

    /**
     * Clave para los folders extras en la llamada al ECM.
     */
    public static final String KEY_FOLDER_EXTRAS = "foldersExtras";

    /**
     * Clave para el ID del adjunto en la llamada al ECM.
     */
    public static final String KEY_ADJUNTO_ID = "adjunto_id";

    /**
     * Valor por defecto para el ID del adjunto en la llamada al ECM.
     */
    public static final String VALUE_ADJUNTO_ID = "123";

    /**
     * Clave para el tipo de adjunto en la llamada al ECM.
     */
    public static final String KEY_ADJUNTO_TIPO = "adjunto_tipo";

    /**
     * Clave para las propiedades adicionales en la llamada al ECM.
     */
    public static final String KEY_PROPIEDADES = "propiedades";

    /**
     * Clave para el ID del documento en el ECM.
     */
    public static final String KEY_ECM_DOCUMENTO_ID = "ecmDocumentoId";

    /**
     * Ruta por defecto para la ficha sanitaria en el ECM.
     */
    public static final String PATH_TRAMITE = "TRAMITE";
}
