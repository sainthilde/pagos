package pe.gob.vuce.cp.sp.pagos.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

 class ActividadEntidadTest {

    private ActividadEntidad actividadEntidad;

    @BeforeEach
     void setUp() {
        actividadEntidad = new ActividadEntidad();
    }

    @Test
     void testActividadEntidadId() {
        actividadEntidad.setActividadEntidadId(1);
        assertEquals(1, actividadEntidad.getActividadEntidadId());
    }

    @Test
     void testEntidadId() {
        actividadEntidad.setEntidadId(2);
        assertEquals(2, actividadEntidad.getEntidadId());
    }

    @Test
     void testActividadId() {
        actividadEntidad.setActividadId(3);
        assertEquals(3, actividadEntidad.getActividadId());
    }

    @Test
     void testCodPuertoNacional() {
        actividadEntidad.setCodPuertoNacional("PUERTO001");
        assertEquals("PUERTO001", actividadEntidad.getCodPuertoNacional());
    }

    @Test
     void testCodReglaNegocio() {
        actividadEntidad.setCodReglaNegocio("REGLA001");
        assertEquals("REGLA001", actividadEntidad.getCodReglaNegocio());
    }

    @Test
     void testEstado() {
        actividadEntidad.setEstado("A");
        assertEquals("A", actividadEntidad.getEstado());
    }

    @Test
     void testConstructorConParametros() {
        ActividadEntidad actividadEntidadParametrizada = new ActividadEntidad(1, 2, 3, "PUERTO001", "REGLA001", "A");
        assertEquals(1, actividadEntidadParametrizada.getActividadEntidadId());
        assertEquals(2, actividadEntidadParametrizada.getEntidadId());
        assertEquals(3, actividadEntidadParametrizada.getActividadId());
        assertEquals("PUERTO001", actividadEntidadParametrizada.getCodPuertoNacional());
        assertEquals("REGLA001", actividadEntidadParametrizada.getCodReglaNegocio());
        assertEquals("A", actividadEntidadParametrizada.getEstado());
    }
}