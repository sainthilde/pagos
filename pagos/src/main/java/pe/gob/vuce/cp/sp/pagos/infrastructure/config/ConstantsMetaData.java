package pe.gob.vuce.cp.sp.pagos.infrastructure.config;

public class ConstantsMetaData {

    ConstantsMetaData() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
    public static final String SUCCESS = "SUCCESS";
    public static final String SUCCESS_200 = "200";
    public static final String ERROR = "ERROR";
    public static final String NOT_FOUND = "NOT_FOUND";
    public static final String LISTAR = "LISTAR";
    public static final String PAGO = "PAGO";
    public static final String LIST_NOT_FOUND = "No se encontraron datos";
    public static final String CLASS_CANNOT = "Esta clase no puede ser instanciada.";
    public static final String FORMA_PAGO_LISTAR = "Formas de pago listadas exitosamente";
    public static final String FORMA_PAGO_NOT_FOUND = "No se encontraron formas de pago";
    public static final String PDF_NOT_GENERATED = "No es posible generar PDF para esa orden en el estado actual.";
    public static final String ATTACHMENT_FILENAME = "attachment; filename=\"voucher-";
    public static final String ATTACHMENT_PDF = ".pdf\"";
    public static final String OK = "Operación exitosa";

}
