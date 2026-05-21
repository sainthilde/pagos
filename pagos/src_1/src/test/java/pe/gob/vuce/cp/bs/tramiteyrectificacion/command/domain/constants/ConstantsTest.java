package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class ConstantsTest {

    @Test
    void testConstants() {
        // Testing TRAMITE_PAGO
        assertEquals("S", Constants.TRAMITE_PAGO, "TRAMITE_PAGO should be 'S'");

        // Testing TRAMITE_DJ
        assertEquals("D", Constants.TRAMITE_DJ, "TRAMITE_DJ should be 'D'");

        // Testing COMPONENTE_PORTUARIO
        assertEquals("CP", Constants.COMPONENTE_PORTUARIO, "COMPONENTE_PORTUARIO should be 'CP'");

        // Testing DECLARACION_JURADA
        assertEquals("DJP", Constants.DECLARACION_JURADA, "DECLARACION_JURADA should be 'DJP'");

        // Testing TAMANIO_SECUENCIA_SUCE
        assertEquals(6, Constants.TAMANIO_SECUENCIA_SUCE, "TAMANIO_SECUENCIA_SUCE should be 6");

        // Testing TAMANIO_SECUENCIA_DJ
        assertEquals(8, Constants.TAMANIO_SECUENCIA_DJ, "TAMANIO_SECUENCIA_DJ should be 8");

        // Testing ZONA_HORARIA_PERU
        assertEquals("America/Lima", Constants.ZONA_HORARIA_PERU, "ZONA_HORARIA_PERU should be 'America/Lima'");

        // Testing VALOR_POR_DEFECTO_ESTADO
        assertEquals("S", Constants.VALOR_POR_DEFECTO_ESTADO, "VALOR_POR_DEFECTO_ESTADO should be 'S'");

        // Testing ES_REGISTRO_EXPEDIENTE_MANUAL
        assertEquals(true, Constants.ES_REGISTRO_EXPEDIENTE_MANUAL, "ES_REGISTRO_EXPEDIENTE_MANUAL should be true");

        // Testing ENTRADA_NAVE
        assertEquals("E", Constants.ENTRADA_NAVE, "ENTRADA_NAVE should be 'E'");

        // Testing ACRONIMO_MERP
        assertEquals("MERP", Constants.ACRONIMO_MERP, "ACRONIMO_MERP should be 'MERP'");

        // Testing ACRONIMO_DGA
        assertEquals("MERP", Constants.ACRONIMO_DGA, "ACRONIMO_DGA should be 'MERP'");

        // tipoDocumento mapping
        assertEquals("DMS", Constants.tipoDocumento(81));
        assertEquals("", Constants.tipoDocumento(999));
        assertEquals("", Constants.tipoDocumento(null));

        // separador utility
        assertEquals("user", Constants.separador("user|test", 1));
        assertEquals("test", Constants.separador("user|test", 2));

        // constructor guard (unwrap reflection exception)
        try {
            var ctor = Constants.class.getDeclaredConstructor();
            ctor.setAccessible(true);
            ctor.newInstance();
            fail("Should have thrown");
        } catch (Exception e) {
            Throwable cause = e.getCause();
            assertTrue(cause instanceof AssertionError, "Cause should be AssertionError");
        }
    }

}
