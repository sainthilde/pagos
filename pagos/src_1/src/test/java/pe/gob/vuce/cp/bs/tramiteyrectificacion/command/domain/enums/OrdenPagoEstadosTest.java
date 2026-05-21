package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrdenPagoEstadosTest {


    @Test
    void testGetCodigo() {
        // Verificar el código de cada estado
        assertEquals("PP", OrdenPagoEstados.PENDIENTEPAGO.getCodigo(), "El código de PENDIENTEPAGO debe ser 'PP'");
        assertEquals("PG", OrdenPagoEstados.PAGADO.getCodigo(), "El código de PAGADO debe ser 'PG'");
        assertEquals("AN", OrdenPagoEstados.ANULADO.getCodigo(), "El código de ANULADO debe ser 'AN'");
        assertEquals("XP", OrdenPagoEstados.EXPIRADO.getCodigo(), "El código de EXPIRADO debe ser 'XP'");
        assertEquals("EX", OrdenPagoEstados.EXTORNADO.getCodigo(), "El código de EXTORNADO debe ser 'EX'");
        assertEquals("PR", OrdenPagoEstados.PORREASIGNAR.getCodigo(), "El código de PORREASIGNAR debe ser 'PR'");
        assertEquals("RE", OrdenPagoEstados.REASIGNADA.getCodigo(), "El código de REASIGNADA debe ser 'RE'");
    }

}
