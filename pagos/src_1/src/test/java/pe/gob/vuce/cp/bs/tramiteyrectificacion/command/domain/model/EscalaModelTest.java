package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EscalaModelTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        EscalaModel escala = new EscalaModel();
        Integer escalaId = 10;
        Integer estadoDueId = 20;

        // Act
        escala.setEscalaId(escalaId);
        escala.setEstadoDueId(estadoDueId);

        // Assert
        assertEquals(escalaId, escala.getEscalaId());
        assertEquals(estadoDueId, escala.getEstadoDueId());
    }
}
