package pe.gob.vuce.cp.sp.pagos.domain.constants;

/**
 * La clase Constants es una clase de utilidad que define constantes globales
 * utilizadas en la aplicación. Las constantes incluyen códigos, nombres de
 * métodos,
 * estados y mensajes comunes, que facilitan la reutilización de estos valores
 * en
 * múltiples partes del sistema, asegurando consistencia y reduciendo errores
 * de codificación repetitiva.
 *
 * <p>
 * Características de la clase:
 * <ul>
 * <li>Es una clase final de solo constantes, por lo que no está diseñada para
 * ser instanciada.</li>
 * <li>El constructor privado lanza una excepción para evitar la creación de
 * instancias de la clase.</li>
 * </ul>
 *
 * <p>
 * Constantes principales:
 * <ul>
 * <li>{@code ORDEN_PAGO}: Prefijo utilizado para identificar órdenes de pago
 * en SUNAT.</li>
 * <li>{@code BASIC}, {@code HEADERS}, {@code FULL}: Indicadores para
 * configuraciones
 * de autenticación o detalles de encabezado en solicitudes.</li>
 * <li>{@code ORDEN_PAGO_SUNAT} y {@code DOCUMENT_CLIENT}: Identificadores para
 * operaciones en clientes de integración con servicios externos (SUNAT y
 * documentos).</li>
 * <li>Estados de orden: {@code PENDIENTE_PAGO}, {@code EN_PROCESO},
 * {@code PAGADO},
 * {@code ANULADO}, {@code EXTORNADO}, y otros códigos abreviados para estatus
 * de orden.</li>
 * <li>{@code MESSAGE} y {@code STATUS}: Claves comunes utilizadas en respuestas
 * JSON
 * para enviar mensajes y estatus.</li>
 * <li>{@code MESSAGE_RESP}: Mensaje de error estándar cuando no se encuentra
 * información
 * con los parámetros dados.</li>
 * </ul>
 * 
 * @author Mateo Huancho
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
public class Constants {

    /**
     * Constructor privado para evitar la creación de instancias de la clase.
     * Lanza una excepción si se intenta instanciar, ya que esta clase solo contiene
     * constantes.
     */
    private Constants() {
        throw new IllegalStateException("Constantes class");
    }

    public static final String ORDEN_PAGO = "OrdenPagoSUNAT-";
    public static final String BASIC = "BASIC";
    public static final String HEADERS = "HEADERS";
    public static final String FULL = "FULL";
    public static final String ORDEN_PAGO_SUNAT = "OrdenPagoSunatClient#getArchivo";
    public static final String DOCUMENT_CLIENT = "DocumentClient#postFile";
    public static final String PENDIENTE_PAGO = "PENDIENTE DE PAGO";
    public static final String EN_PROCESO = "EN PROCESO";
    public static final String PP = "PP";
    public static final String PAGADO = "PAGADO";
    public static final String PG = "PG";
    public static final String AN = "AN";
    public static final String EX = "EX";
    public static final String CR = "CR";
    public static final String EP = "EP";
    public static final String MT = "MT";
    public static final String DUE = "DUE";
    public static final String ANULADO = "ANULADO";
    public static final String EXTORNADO = "EXTORNADO";
    public static final String MESSAGE = "message";
    public static final String STATUS = "status";
    public static final String MESSAGE_RESP = "No se encontró la información para los parámetros proporcionados.";
    public static final String DOCUMENT_SEGUI = " Documento: ";
    public static final String GESTOR_PATH = "procedimiento";
    public static final String GESTOR_DESC = "GESTORDEPROCEDIMIENTOS";
    public static final String PASERELA_DESC = "PASARELADEPAGOS";
    public static final String SUNAT_PATH = "sunat";
    public static final String ARCHIVO_PATH = "archivo";
    public static final String DOCUMENTOS_PATH = "documentos";
    public static final String FL_PREFIX = "FL";
    public static final String GP_PREFIX = "GP ";
    public static final String SUNAT_PREFIX = "PP ";
    public static final String SALIDA_NAVE = "S";
    public static final String ENTRADA_NAVE = "E";
    public static final String RESULTADO_OK = "SUCCESS";
    public static final String RESULTADO_ERROR = "ERROR";
    public static final String NOTIFICATION_TOPIC_STATUS_EXPIRED = "EXPIRADO";
    public static final String PAYMENT_ORDER_STATUS_EXPIRED = "XP";
    public static final String UNKNOWN = "UNKNOWN";

    public static final String ERROR_E0047 = "E0047";
    public static final String ERROR_E0048 = "E0048";
    public static final String ERROR_E0073 = "E0073";

    public static final String RESULTADO_OK_CODE = "I";
    public static final String RESULTADO_ERROR_CODE = "E";
    public static final String NOT_FOUND_FICTEC = "FichaTecnicaDet no encontrada para el ID especificado";
    public static final Integer ESTADO_VIGENTE = 2;

    public enum TipoDocumento {
        DMS(81),
        LT(65),
        CP(66),
        PBIP(67),
        LN(68),
        LP(76),
        DCAR(84),
        PR(83),
        AE(90),
        DGA(63),
        DGZ(64),
        SPS(93);

        private final int codigo;

        TipoDocumento(int codigo) {
            this.codigo = codigo;
        }

        public static String getDescripcion(int codigo) {
            for (TipoDocumento tipo : values()) {
                if (tipo.codigo == codigo) {
                    return tipo.name();
                }
            }
            return "";
        }
    }

    public static String indicador(Integer tipo) {
        if (tipo == 93 || tipo == 64) {
            return Constants.SALIDA_NAVE;
        } else {
            return Constants.ENTRADA_NAVE;
        }
    }

    // Uso:
    public static String tipoDocumento(Integer tipo) {
        return tipo != null ? TipoDocumento.getDescripcion(tipo) : "";
    }

    public static String separador(String texto,Integer num){
        return (texto.split("\\|")[num - 1]).trim(); // Elimina espacios alrededor del |

    }
}
