package pe.gob.vuce.cp.sp.pagos.domain.constants;
/**
 * La clase ConstantsPagosSunat es una clase de utilidad que define constantes
 * específicas para la integración y procesamiento de pagos relacionados con SUNAT.
 * Estas constantes incluyen nombres de campos, formatos de datos, configuraciones
 * de zona horaria, y otros valores que son reutilizados en la aplicación.
 *
 * <p>Características de la clase:
 * <ul>
 *   <li>Es una clase de solo constantes, diseñada para no ser instanciada.</li>
 *   <li>El constructor privado lanza una excepción para prevenir la creación de
 *       instancias de la clase.</li>
 * </ul>
 *
 * <p>Constantes principales:
 * <ul>
 *   <li>{@code ALIAS_FIELD}, {@code DESCRIPTION_FIELD}: Nombres de campos específicos
 *       utilizados en las respuestas de SUNAT o en la estructura de datos.</li>
 *   <li>{@code FORMAT_DATE}: Formato de fecha utilizado para representar datos
 *       temporales en el formato "yyyyMMdd HH:mm:ss".</li>
 *   <li>{@code ATZONE}: Zona horaria "America/Lima" utilizada para operaciones
 *       con fechas y horas.</li>
 *   <li>{@code H1}, {@code P_ERROR}: Selectores de HTML específicos que podrían
 *       usarse para buscar elementos de error o títulos en documentos de respuesta.</li>
 *   <li>{@code ECM_DOC}, {@code RUC}, {@code COMPONENT}, {@code VOUCHER}: Identificadores
 *       de campos específicos y constantes de formato utilizadas en el proceso de
 *       validación y generación de documentos PDF.</li>
 *   <li>{@code APP_PDF}: Tipo MIME para archivos PDF.</li>
 *   <li>{@code PDF}: Extensión de archivo para archivos PDF generados o procesados.</li>
 * </ul>
 * @author Mateo Huancho
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
public class ConstantsPagosSunat {
    /**
     * Constructor privado para evitar la creación de instancias de la clase.
     * Lanza una excepción si se intenta instanciar, ya que esta clase solo contiene constantes.
     */
    private ConstantsPagosSunat() {
        throw new IllegalStateException("Constants Pagos Sunat class");
    }

    public static final String ALIAS_FIELD = "alias";
    public static final String DESCRIPTION_FIELD = "description";
    public static final String FORMAT_DATE = "yyyyMMdd HH:mm:ss";
    public static final String ATZONE = "America/Lima";
    public static final String H1 = "h1";
    public static final String P_ERROR = "p.error";
    public static final String DATA = "data";
    public static final String ECM_DOC = "ecmDocumentoId";
    public static final String RUC = "RUC";
    public static final String NAVE = "NAVE";
    public static final String TEST = "Test";
    public static final String COMPONENT = "componente";
    public static final String CP2 = "CP2";
    public static final String OPTTION = "opcion";
    public static final String ESCALA = "escala";
    public static final String FOLDER_EXTRAS = "foldersExtras";
    public static final String ENTRADA2 = "ENTRADA/2/2";
    public static final String ADJUNTO_ID = "adjunto_id";
    public static final String ADJUNTO_TIPO = "adjunto_tipo";
    public static final String APP_PDF = "application/pdf";
    public static final String PROPIEDADES = "propiedades";
    public static final String VOUCHER = "voucher-";
    public static final String PDF = ".pdf";


}
