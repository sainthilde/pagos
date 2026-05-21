package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.escala.Escala;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.mae.ActividadEntidadPuerto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.mae.Agencia;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.pago.DeclaracionJurada;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.pago.OrdenDePago;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.pago.Tramite;

import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

class TramiteTest {

    @InjectMocks
    private Tramite tramite;

    @Mock
    private Escala escala;

    @Mock
    private ActividadEntidadPuerto actividadEntidadPuerto;

    @Mock
    private Agencia agencia;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tramite = new Tramite();
        tramite.setId(1);
        tramite.setNumeroSuce("123456789012");
        tramite.setFechaTramite(LocalDateTime.now());
        tramite.setEscala(escala);
        tramite.setActividadEntidadPuerto(actividadEntidadPuerto);
        tramite.setIndicadorEs("E");
        tramite.setNumeroTramiteEntidad("000123");
        tramite.setAgencia(agencia);
        tramite.setEstadoTramite("En proceso");
        tramite.setEstado("S");
        tramite.setIndNoRequierePago(false);
        tramite.setTupa("TUPA123");
        tramite.setFueTramiteManual(false);
        tramite.setDeclaracionesJuradas(List.of(new DeclaracionJurada()));
        tramite.setOrdenesDePago(List.of(new OrdenDePago()));
        tramite.setFechaTramiteManual(LocalDateTime.now());
    }

    @Test
    void testTramiteFields() {
        assertNotNull(tramite);
        assertEquals(1, tramite.getId());
        assertEquals("123456789012", tramite.getNumeroSuce());
        assertNotNull(tramite.getFechaTramite());
        assertEquals(escala, tramite.getEscala());
        assertEquals(actividadEntidadPuerto, tramite.getActividadEntidadPuerto());
        assertEquals("E", tramite.getIndicadorEs());
        assertEquals("000123", tramite.getNumeroTramiteEntidad());
        assertEquals(agencia, tramite.getAgencia());
        assertEquals("En proceso", tramite.getEstadoTramite());
        assertFalse(tramite.getIndNoRequierePago());
        assertFalse(tramite.getFueTramiteManual());
        assertEquals("TUPA123", tramite.getTupa());
        assertNotNull(tramite.getDeclaracionesJuradas());
        assertNotNull(tramite.getOrdenesDePago());
        assertNotNull(tramite.getFechaTramiteManual());
        assertEquals("S", tramite.getEstado());
    }

}
