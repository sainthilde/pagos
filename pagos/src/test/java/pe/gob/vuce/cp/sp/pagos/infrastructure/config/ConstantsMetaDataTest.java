package pe.gob.vuce.cp.sp.pagos.infrastructure.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class ConstantsMetaDataTest {

    @Test
    void testConstantsValues() {
        assertEquals("SUCCESS", ConstantsMetaData.SUCCESS);
        assertEquals("200", ConstantsMetaData.SUCCESS_200);
        assertEquals("ERROR", ConstantsMetaData.ERROR);
        assertEquals("NOT_FOUND", ConstantsMetaData.NOT_FOUND);
        assertEquals("LISTAR", ConstantsMetaData.LISTAR);
        assertEquals("PAGO", ConstantsMetaData.PAGO);
        assertEquals("No se encontraron datos", ConstantsMetaData.LIST_NOT_FOUND);
        assertEquals("Esta clase no puede ser instanciada.", ConstantsMetaData.CLASS_CANNOT);
        assertEquals("Formas de pago listadas exitosamente", ConstantsMetaData.FORMA_PAGO_LISTAR);
        assertEquals("No se encontraron formas de pago", ConstantsMetaData.FORMA_PAGO_NOT_FOUND);
        assertEquals("No es posible generar PDF para esa orden en el estado actual.", ConstantsMetaData.PDF_NOT_GENERATED);
        assertEquals("attachment; filename=\"voucher-", ConstantsMetaData.ATTACHMENT_FILENAME);
        assertEquals(".pdf\"", ConstantsMetaData.ATTACHMENT_PDF);
        assertEquals("Operación exitosa", ConstantsMetaData.OK);
    }
}
