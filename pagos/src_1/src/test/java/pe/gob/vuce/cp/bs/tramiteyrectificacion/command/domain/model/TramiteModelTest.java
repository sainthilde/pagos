package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TramiteModelTest {

    @Test
    void testGettersAndSetters() {
        TramiteModel tramite = new TramiteModel();

        // Testing tramiteId
        tramite.setTramiteId(1);
        assertEquals(1, tramite.getTramiteId());

        // Testing numeroSuce
        tramite.setNumeroSuce("SUCE123");
        assertEquals("SUCE123", tramite.getNumeroSuce());

        // Testing fechaTramite
        LocalDateTime now = LocalDateTime.now();
        tramite.setFechaTramite(now);
        assertEquals(now, tramite.getFechaTramite());

        // Testing escalaId
        tramite.setEscalaId(100);
        assertEquals(100, tramite.getEscalaId());

        // Testing documentoId
        tramite.setDocumentoId(200);
        assertEquals(200, tramite.getDocumentoId());

        // Testing actividadEntidadPuertoId
        tramite.setActividadEntidadPuertoId(300);
        assertEquals(300, tramite.getActividadEntidadPuertoId());

        // Testing indicadorEs
        tramite.setIndicadorEs("E");
        assertEquals("E", tramite.getIndicadorEs());

        // Testing numeroTramiteEntidad
        tramite.setNumeroTramiteEntidad("ENT123");
        assertEquals("ENT123", tramite.getNumeroTramiteEntidad());

        // Testing rucAgente
        tramite.setRucAgente("12345678901");
        assertEquals("12345678901", tramite.getRucAgente());

        // Testing estadoTramite
        tramite.setEstadoTramite("Activo");
        assertEquals("Activo", tramite.getEstadoTramite());

        // Testing indNoRequierePago
        tramite.setIndNoRequierePago(true);
        assertTrue(tramite.getIndNoRequierePago());

        // Testing tipoTramite
        tramite.setTipoTramite("S");
        assertEquals("S", tramite.getTipoTramite());

        // Testing indAsignacionTramiteManual
        tramite.setIndAsignacionTramiteManual(false);
        assertFalse(tramite.getIndAsignacionTramiteManual());

        // Testing fechaActNumTramiteManual
        LocalDateTime fechaManual = LocalDateTime.now().minusDays(1);
        tramite.setFechaActNumTramiteManual(fechaManual);
        assertEquals(fechaManual, tramite.getFechaActNumTramiteManual());

        // Testing sustentoActNumTramiteManual
        tramite.setSustentoActNumTramiteManual("guid123");
        assertEquals("guid123", tramite.getSustentoActNumTramiteManual());

        // Testing tupa
        tramite.setTupa("TUPA123");
        assertEquals("TUPA123", tramite.getTupa());
    }

}
