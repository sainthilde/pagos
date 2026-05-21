package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MensajeTest {

    @Test
    void testGettersAndSetters() {
        Mensaje mensaje = new Mensaje();

        // Testing codigo
        mensaje.setCodigo("E001");
        assertEquals("E001", mensaje.getCodigo());

        // Testing tipo
        mensaje.setTipo("error");
        assertEquals("error", mensaje.getTipo());

        // Testing message
        mensaje.setMessage("This is an error message");
        assertEquals("This is an error message", mensaje.getMessage());
    }

}
