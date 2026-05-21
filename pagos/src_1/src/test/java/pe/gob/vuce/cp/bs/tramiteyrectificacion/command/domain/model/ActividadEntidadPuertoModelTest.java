package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class ActividadEntidadPuertoModelTest {
    @Test
    public void testActividadEntidadPuertoModel() {
        ActividadEntidadPuertoModel model = new ActividadEntidadPuertoModel();

        model.setId(1);
        model.setEntidadId(100);
        model.setActividadId(200);
        model.setCodPuertoNacional("PEN001");

        assertEquals(Integer.valueOf(1), model.getId());
        assertEquals(Integer.valueOf(100), model.getEntidadId());
        assertEquals(Integer.valueOf(200), model.getActividadId());
        assertEquals("PEN001", model.getCodPuertoNacional());
    }

    @Test
    public void testNoArgsConstructor() {
        ActividadEntidadPuertoModel model = new ActividadEntidadPuertoModel();

        assertNull(model.getId());
        assertNull(model.getEntidadId());
        assertNull(model.getActividadId());
        assertNull(model.getCodPuertoNacional());
    }
}
