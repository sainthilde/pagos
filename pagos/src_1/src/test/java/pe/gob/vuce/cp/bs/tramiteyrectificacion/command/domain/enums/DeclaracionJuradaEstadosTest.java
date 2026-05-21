package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class DeclaracionJuradaEstadosTest {

    @Test
    void testGetCodigo() {
        // Verificar el código de cada estado
        assertEquals("P", DeclaracionJuradaEstados.PEDIENTE.getCodigo(), "El código de PEDIENTE debe ser 'P'");
        assertEquals("A", DeclaracionJuradaEstados.ACEPTADA.getCodigo(), "El código de ACEPTADA debe ser 'A'");
        assertEquals("D", DeclaracionJuradaEstados.DENEGADA.getCodigo(), "El código de DENEGADA debe ser 'D'");
    }

}
