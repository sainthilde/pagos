package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BaseEntityTest {

    @InjectMocks
    private BaseEntity baseEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        baseEntity = new BaseEntity();
        baseEntity.setEstado("A");
        baseEntity.setUsuidRegAud("user1");
        baseEntity.setUsuidModAud("user2");
        baseEntity.setFechaRegAud(LocalDateTime.now());
        baseEntity.setFechaModAud(LocalDateTime.now().plusHours(1));
    }

    @Test
    void testBaseEntityFields() {
        assertNotNull(baseEntity);
        assertEquals("A", baseEntity.getEstado());
        assertEquals("user1", baseEntity.getUsuidRegAud());
        assertEquals("user2", baseEntity.getUsuidModAud());
        assertNotNull(baseEntity.getFechaRegAud());
        assertNotNull(baseEntity.getFechaModAud());
    }
}
