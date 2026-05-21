package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model;

import org.junit.jupiter.api.Test;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BaseModelTest {

    @Test
    void testGettersAndSetters() {
        BaseModel baseModel = new BaseModel();

        // Testing estado
        baseModel.setEstado("Active");
        assertEquals("Active", baseModel.getEstado());

        // Testing usuidRegAud
        baseModel.setUsuidRegAud("user123");
        assertEquals("user123", baseModel.getUsuidRegAud());

        // Testing usuidModAud
        baseModel.setUsuidModAud("user456");
        assertEquals("user456", baseModel.getUsuidModAud());

        // Testing usubdRegAud
        baseModel.setUsuidModAud("dbUser123");
        assertEquals("dbUser123", baseModel.getUsuidModAud());

        // Testing usubdModAud
        baseModel.setUsuidModAud("dbUser456");
        assertEquals("dbUser456", baseModel.getUsuidModAud());

        // Testing fechaRegAud
        Instant now = Instant.now();
        baseModel.setFechaRegAud(now);
        assertEquals(now, baseModel.getFechaRegAud());

        // Testing fechaModAud
        Instant later = now.plusSeconds(3600);
        baseModel.setFechaModAud(later);
        assertEquals(later, baseModel.getFechaModAud());
    }

}
