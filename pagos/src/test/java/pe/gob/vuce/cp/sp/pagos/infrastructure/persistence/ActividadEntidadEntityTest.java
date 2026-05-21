package pe.gob.vuce.cp.sp.pagos.infrastructure.persistence;

import org.junit.jupiter.api.Test;
import pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.entity.ActividadEntidadEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

 class ActividadEntidadEntityTest {

    @Test
     void testGettersAndSetters() {
        // Crear una instancia de la entidad
        ActividadEntidadEntity actividadEntidad = new ActividadEntidadEntity();

        // Configurar valores
        Integer actividadEntidadId = 1;
        Integer entidadId = 2;
        Integer actividadId = 3;
        String codPuertoNacional = "PE-123";
        String codReglaNegocio = "RG-456";
        String estado = "Activo";

        // Usar los setters
        actividadEntidad.setActividadEntidadId(actividadEntidadId);
        actividadEntidad.setEntidadId(entidadId);
        actividadEntidad.setActividadId(actividadId);
        actividadEntidad.setCodPuertoNacional(codPuertoNacional);
        actividadEntidad.setCodReglaNegocio(codReglaNegocio);
        actividadEntidad.setEstado(estado);

        // Validar los valores usando los getters
        assertEquals(actividadEntidadId, actividadEntidad.getActividadEntidadId());
        assertEquals(entidadId, actividadEntidad.getEntidadId());
        assertEquals(actividadId, actividadEntidad.getActividadId());
        assertEquals(codPuertoNacional, actividadEntidad.getCodPuertoNacional());
        assertEquals(codReglaNegocio, actividadEntidad.getCodReglaNegocio());
        assertEquals(estado, actividadEntidad.getEstado());
    }
}