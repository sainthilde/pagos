package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.escala.Escala;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.fictec.FichaTecnicaDet;

import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EscalaTest {

    @InjectMocks
    private Escala escala;

    @Mock
    private FichaTecnicaDet fichaTecnicaDetIn;

    @Mock
    private FichaTecnicaDet fichaTecnicaDetSa;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        escala = new Escala();
        escala.setEscalaId(1);
        escala.setFichaTecnicaDetIn(fichaTecnicaDetIn);
        escala.setPuertoEscalaId("PE1");
        escala.setAnnoEscala(2024);
        escala.setNumeroEscala(123);
        escala.setNumeroViaje("VIAJE123");
        escala.setEta(LocalDateTime.now());
        escala.setEtd(LocalDateTime.now().plusDays(1));
        escala.setTipoTraficoDueId(1);
        escala.setAta(LocalDateTime.now().plusDays(2));
        escala.setAtd(LocalDateTime.now().plusDays(3));
        escala.setFechaLibrePlatica(LocalDateTime.now().plusDays(4));
        escala.setEstado("A");
    }

    @Test
    void testEscalaFields() {
        assertNotNull(escala);
        assertEquals(1, escala.getEscalaId());
        assertEquals(fichaTecnicaDetIn, escala.getFichaTecnicaDetIn());
        assertEquals("PE1", escala.getPuertoEscalaId());
        assertEquals(2024, escala.getAnnoEscala());
        assertEquals(123, escala.getNumeroEscala());
        assertEquals("VIAJE123", escala.getNumeroViaje());
        assertNotNull(escala.getEta());
        assertNotNull(escala.getEtd());
        assertEquals(1, escala.getTipoTraficoDueId());
        assertNotNull(escala.getAta());
        assertNotNull(escala.getAtd());
        assertNotNull(escala.getFechaLibrePlatica());
        assertEquals("A", escala.getEstado());
    }
}