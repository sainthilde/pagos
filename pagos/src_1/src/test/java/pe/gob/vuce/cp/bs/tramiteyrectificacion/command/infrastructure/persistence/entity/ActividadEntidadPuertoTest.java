package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity.mae.ActividadEntidadPuerto;

public class ActividadEntidadPuertoTest {
    @Test
    void testActividadEntidadPuertoGettersAndSetters() {
        ActividadEntidadPuerto actividadEntidadPuerto = new ActividadEntidadPuerto();

        actividadEntidadPuerto.setId(1);
        actividadEntidadPuerto.setEntidadId(100);
        actividadEntidadPuerto.setActividadId(200);
        actividadEntidadPuerto.setCodPuertoNacional("PEN001");

        assertEquals(1, actividadEntidadPuerto.getId());
        assertEquals(100, actividadEntidadPuerto.getEntidadId());
        assertEquals(200, actividadEntidadPuerto.getActividadId());
        assertEquals("PEN001", actividadEntidadPuerto.getCodPuertoNacional());

        ActividadEntidadPuerto another = new ActividadEntidadPuerto();
        assertNotNull(another);
    }
}
