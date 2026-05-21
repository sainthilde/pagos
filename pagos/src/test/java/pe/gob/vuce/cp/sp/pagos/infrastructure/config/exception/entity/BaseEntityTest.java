package pe.gob.vuce.cp.sp.pagos.infrastructure.config.exception.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.gob.vuce.cp.sp.pagos.domain.entity.BaseEntity;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

 class BaseEntityTest {

    private BaseEntity baseEntity;

    @BeforeEach
     void setUp() {
        baseEntity = new BaseEntity();
    }

    @Test
     void testEstado() {
        baseEntity.setEstado("A");
        assertEquals("A", baseEntity.getEstado());
    }

    @Test
     void testUsuidRegAud() {
        baseEntity.setUsuidRegAud("user1");
        assertEquals("user1", baseEntity.getUsuidRegAud());
    }

    @Test
     void testUsubdRegAud() {
        baseEntity.setUsubdRegAud("current_user");
        assertEquals("current_user", baseEntity.getUsubdRegAud());
    }

    @Test
     void testFechaRegAud() {
        Instant now = Instant.now();
        baseEntity.setFechaRegAud(now);
        assertEquals(now, baseEntity.getFechaRegAud());
    }

    @Test
     void testUsuidModAud() {
        baseEntity.setUsuidModAud("user2");
        assertEquals("user2", baseEntity.getUsuidModAud());
    }

    @Test
     void testUsubdModAud() {
        baseEntity.setUsubdModAud("current_user");
        assertEquals("current_user", baseEntity.getUsubdModAud());
    }

    @Test
     void testFechaModAud() {
        Instant now = Instant.now();
        baseEntity.setFechaModAud(now);
        assertEquals(now, baseEntity.getFechaModAud());
    }
}