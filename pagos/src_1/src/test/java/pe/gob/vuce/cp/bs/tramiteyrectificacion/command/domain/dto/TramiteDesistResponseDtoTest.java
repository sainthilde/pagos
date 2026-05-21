package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

public class TramiteDesistResponseDtoTest {

    @Test
    void testConstructorAndGetters() {
        Integer tramiteId = 123;
        String fechaTramite = "2024-08-19";
        String numeroSuce = "SUCE-001";
        Integer escalaId = 5;
        Integer documentoId = 7;
        Integer actividadEntidadPuertoId = 10;
        String indicadorEs = "S";
        String estadoTramite = "Activo";

        TramiteDesistResponseDto tramite = new TramiteDesistResponseDto(tramiteId, fechaTramite, numeroSuce, escalaId, documentoId, actividadEntidadPuertoId, indicadorEs, estadoTramite, List.of("1","2"),true,"",List.of("1","2"));
        assertEquals(tramiteId, tramite.getTramiteId());
        assertEquals(fechaTramite, tramite.getFechaTramite());
        assertEquals(numeroSuce, tramite.getNumeroSuce());
        assertEquals(escalaId, tramite.getEscalaId());
        assertEquals(documentoId, tramite.getDocumentoId());
        assertEquals(actividadEntidadPuertoId, tramite.getActividadEntidadPuertoId());
        assertEquals(indicadorEs, tramite.getIndicadorEs());
        assertEquals(estadoTramite, tramite.getEstadoTramite());
    }

    @Test
    void testSetters() {
        TramiteDesistResponseDto tramite = new TramiteDesistResponseDto(123, "2024-08-19", "SUCE-001", 5, 7, 10, "S", "Activo",List.of("1","2"),true,"",List.of("1","2"));


        tramite.setTramiteId(456);
        tramite.setFechaTramite("2024-08-20");
        tramite.setNumeroSuce("SUCE-002");
        tramite.setEscalaId(6);
        tramite.setDocumentoId(8);
        tramite.setActividadEntidadPuertoId(11);
        tramite.setIndicadorEs("N");
        tramite.setEstadoTramite("Inactivo");


        assertEquals(456, tramite.getTramiteId());
        assertEquals("2024-08-20", tramite.getFechaTramite());
        assertEquals("SUCE-002", tramite.getNumeroSuce());
        assertEquals(6, tramite.getEscalaId());
        assertEquals(8, tramite.getDocumentoId());
        assertEquals(11, tramite.getActividadEntidadPuertoId());
        assertEquals("N", tramite.getIndicadorEs());
        assertEquals("Inactivo", tramite.getEstadoTramite());
    }

    @Test
    void testNullValues() {

        TramiteDesistResponseDto tramite = new TramiteDesistResponseDto(null, null, null, null, null, null, null, null,null,true,"",List.of("1","2"));


        assertNull(tramite.getTramiteId());
        assertNull(tramite.getFechaTramite());
        assertNull(tramite.getNumeroSuce());
        assertNull(tramite.getEscalaId());
        assertNull(tramite.getDocumentoId());
        assertNull(tramite.getActividadEntidadPuertoId());
        assertNull(tramite.getIndicadorEs());
        assertNull(tramite.getEstadoTramite());
    }

}
