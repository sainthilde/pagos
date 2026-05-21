package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity.mae;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class EntidadTest {

    @Test
    void testEntidadSettersAndGetters() {
        // Create a new Entidad
        Entidad entidad = new Entidad();
        entidad.setRuc("12345678901");
        entidad.setNombre("Entidad Test");
        entidad.setObservacion("Observación de prueba");
        entidad.setEstado("A");
        entidad.setUsuidRegAud("user001");
        entidad.setUsuidModAud("user001");
        entidad.setFechaRegAud(LocalDateTime.now());
        entidad.setFechaModAud(LocalDateTime.now());
        entidad.setUsubdRegAud("subdb001");
        entidad.setUsubdModAud("subdb001");
        entidad.setGrupoEntidadId(1);

        // Validate fields using getters
        assertEquals("12345678901", entidad.getRuc(), "RUC should match");
        assertEquals("Entidad Test", entidad.getNombre(), "Nombre should match");
        assertEquals("Observación de prueba", entidad.getObservacion(), "Observación should match");
        assertEquals("A", entidad.getEstado(), "Estado should match");
        assertEquals("user001", entidad.getUsuidRegAud(), "Usuario Reg Aud should match");
        assertEquals("user001", entidad.getUsuidModAud(), "Usuario Mod Aud should match");
        assertEquals("subdb001", entidad.getUsubdRegAud(), "Subdb Reg Aud should match");
        assertEquals("subdb001", entidad.getUsubdModAud(), "Subdb Mod Aud should match");
        assertEquals(1, entidad.getGrupoEntidadId(), "Grupo Entidad ID should match");
    }
}
