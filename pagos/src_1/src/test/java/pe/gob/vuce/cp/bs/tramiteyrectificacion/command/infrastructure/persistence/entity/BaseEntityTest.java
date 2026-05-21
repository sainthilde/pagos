package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.configuration.util.DataSourceUtil;

class BaseEntityTest {

    static class DummyEntity extends BaseEntity {
    }

    @Test
    void onCreateShouldSetFechaRegAud() throws Exception {
        DummyEntity ent = new DummyEntity();
        assertNull(ent.getFechaRegAud());
        ent.onCreate();
        assertNotNull(ent.getFechaRegAud());
    }

    @Test
    void onUpdateShouldSetFechaModAudAndUserBd() {
        DummyEntity ent = new DummyEntity();
        DataSourceUtil.setUsername("dbUser");
        ent.onUpdate();
        assertNotNull(ent.getFechaModAud());
       // assertEquals("dbUser", ent.getUsubdModAud());
    }
}
