package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.junit.jupiter.api.Test;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.OrdenPagoErrorResponse;

class OrdenPagoAnulacionExceptionTest {

    @Test
    void shouldStoreErrorsAndMessage() {
        OrdenPagoErrorResponse err = new OrdenPagoErrorResponse(400, "{\"error\":true}", 10);
        OrdenPagoAnulacionException ex = new OrdenPagoAnulacionException("msg", List.of(err));
        assertEquals("msg", ex.getMessage());
        assertNotNull(ex.getErrores());
        assertEquals(1, ex.getErrores().size());
    }

    @Test
    void transientErrorsShouldNotSerialize() throws Exception {
        OrdenPagoErrorResponse err = new OrdenPagoErrorResponse(400, "{\"error\":true}", 10);
        OrdenPagoAnulacionException ex = new OrdenPagoAnulacionException("msg", List.of(err));
        byte[] bytes;
        try (ByteArrayOutputStream bout = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bout)) {
            oos.writeObject(ex);
            bytes = bout.toByteArray();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            OrdenPagoAnulacionException read = (OrdenPagoAnulacionException) ois.readObject();
            assertNull(read.getErrores(), "Errores list should be null after deserialization because it's transient");
        }
    }
}
