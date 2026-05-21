package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.fictec.FichaTecnica;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FichaTecnicaTest {

    @InjectMocks
    private FichaTecnica fichaTecnica;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fichaTecnica = new FichaTecnica();
        fichaTecnica.setFichaTecnicaId(1);
        fichaTecnica.setImo("IMO1234567");
        fichaTecnica.setMatricula("MAT1234567");
    }

    @Test
    void testFichaTecnicaFields() {
        assertNotNull(fichaTecnica);
        assertEquals(1, fichaTecnica.getFichaTecnicaId());
        assertEquals("IMO1234567", fichaTecnica.getImo());
        assertEquals("MAT1234567", fichaTecnica.getMatricula());
    }
}
