package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants;

/**
 * Clase que define constantes utilizadas en el proyecto.
 * 
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 19/08/2024
 */
public final class Constants {

    /**
     * Constructor privado para evitar la instanciación de esta clase de constantes.
     */
    private Constants() {
        throw new AssertionError("Cannot instantiate Constants class");
    }

    /**
     * Valor para tramites con pagos realizados.
     */
    public static final String TRAMITE_PAGO = "S";

    /**
     * Valor para tramites con declaracion jurada.
     */
    public static final String TRAMITE_DJ = "D";

    /**
     * Valor que representa al componente portuario en el numero suce.
     */
    public static final String COMPONENTE_PORTUARIO = "CP";

    public static final String DECLARACION_JURADA = "DJP";

    /**
     * Valor que representa al tamnio de secuencia por anio en el numero suce.
     */
    public static final Integer TAMANIO_SECUENCIA_SUCE = 6;
    public static final Integer TAMANIO_SECUENCIA_DJ= 8;

    /**
     * Valor de la zona horaria de Perú
     */
    public static final String ZONA_HORARIA_PERU = "America/Lima";

    public static final String VALOR_POR_DEFECTO_ESTADO = "S";

    public static final boolean ES_REGISTRO_EXPEDIENTE_MANUAL = true;

    public static final String ENTRADA_NAVE = "E";
    public static final String SALIDA_NAVE = "S";

    public static final String ACRONIMO_MERP = "MERP";
    public static final String ACRONIMO_DGA = "MERP";
    public static final String EN_TRAMITE = "ET";

    public static final String NO_VALUE = "NO VALUE";
    public static final String DOCUMENT_VALUE = " Documento: ";
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
        MERP(54),
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

    // Uso:
    public static String tipoDocumento(Integer tipo) {
        return tipo != null ? TipoDocumento.getDescripcion(tipo) : "";
    }

    public static String separador(String texto,Integer num){
        return (texto.split("\\|")[num - 1]).trim(); // Elimina espacios alrededor del |

    }
}
