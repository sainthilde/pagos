package pe.gob.vuce.cp.sp.pagos.domain.constants;
/**
 * @author Mateo Huancho
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
public class ConstantsHelpers {
    /**
     * Constructor privado para evitar la creación de instancias de la clase.
     * Lanza una excepción si se intenta instanciar, ya que esta clase solo contiene constantes.
     */
    private ConstantsHelpers() {
        throw new IllegalStateException("Constants Pagos class");
    }

    public static final String ORDEN_PAGO_ANULADA_CPD = "Orden de Pago anulada cpb = cp";
    public static final String ORDEN_PAGO_CREADA_CPD = "Orden de Pago creada cpb = cp";
    public static final String ORDEN_PAGO_NOT= "Orden de pago no encontrada";
    public static final String ORDEN_PAGO_NOT_FOUND = "No existe la orden de pago.";
    public static final String ORDEN_ENTIDAD_NOT_FOUND = "No se encontró la entidad.";
    public static final String PROCEDURE_NOT_FOUND = "No se encontraron procedimientos para el componente y entidad especificados.";
    public static final String TASA_NOT_FOUND = "No se encontraron tasas para el procedimiento especificado.";
    public static final String PARAM_INVALID = "Parámetro inválido o mal formado";
    public static final String ERROR_SERVER = "Error inesperado en el servidor";
    public static final String ERROR_INTERNAL = "Internal Server Error: ";
    public static final String SERVER_UNAVALILABLE = "Service Unavailable1: ";

}
