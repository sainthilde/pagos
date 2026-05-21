package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.List;

 class TramiteResponseTest {

    @Test
    void testTramiteResponseSettersAndGetters() {
        TramiteResponse.Tramites tramite = new TramiteResponse.Tramites();

        tramite.setId(100);
        tramite.setDue("DUE123");
        tramite.setNombreNave("Nave Uno");
        tramite.setNumeroSuce("SUCE001");
        tramite.setNumeroTramite("TRM123");
        tramite.setEntidadNombre("Entidad X");
        tramite.setTupa("TUPA2025");
        tramite.setEstadoTramite("EN_PROCESO");
        tramite.setCpb("CPB123");
        tramite.setMonto(1500.75);

        assertEquals(100, tramite.getId());
        assertEquals("DUE123", tramite.getDue());
        assertEquals("Nave Uno", tramite.getNombreNave());
        assertEquals("SUCE001", tramite.getNumeroSuce());
        assertEquals("TRM123", tramite.getNumeroTramite());
        assertEquals("Entidad X", tramite.getEntidadNombre());
        assertEquals("TUPA2025", tramite.getTupa());
        assertEquals("EN_PROCESO", tramite.getEstadoTramite());
        assertEquals("CPB123", tramite.getCpb());
        assertEquals(1500.75, tramite.getMonto());

        TramiteResponse response = new TramiteResponse();
        response.setMeta(new Meta());
        response.setData(List.of(tramite));

        assertNotNull(response.getMeta());
        assertNotNull(response.getData());
        assertEquals(1, response.getData().size());
        assertEquals("Nave Uno", response.getData().get(0).getNombreNave());
    }

    @Test
    void testEmptyTramiteResponse() {
        TramiteResponse response = new TramiteResponse();

        assertNull(response.getMeta());
        assertNull(response.getData());
    }
}
