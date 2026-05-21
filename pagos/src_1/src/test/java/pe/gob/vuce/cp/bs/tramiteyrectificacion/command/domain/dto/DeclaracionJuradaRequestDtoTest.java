package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class DeclaracionJuradaRequestDtoTest {

    @Test
    void testSettersAndGetters() {
        DocumentoDDJJRequestDto documento = new DocumentoDDJJRequestDto();

        DeclaracionJuradaRequestDto dto = new DeclaracionJuradaRequestDto();
        dto.setId(1);
        dto.setEstadoDdjjPago("Pagado");
        dto.setNumeroDdjj("DDJJ123");
        dto.setFechaSolicitudDdjj(LocalDateTime.now());
        dto.setDocumento(documento);
        dto.setTramiteId(10);
        dto.setEscalaId(20);
        dto.setMotivoDeclaracion("Motivo de prueba");
        dto.setMensajeError("Sin error");
        dto.setEstado("Activo");
        dto.setRucAgente("12345678901");

        // Verificar los valores establecidos
        assertEquals(1, dto.getId());
        assertEquals("Pagado", dto.getEstadoDdjjPago());
        assertEquals("DDJJ123", dto.getNumeroDdjj());
        assertNotNull(dto.getFechaSolicitudDdjj());
        assertEquals(documento, dto.getDocumento());
        assertEquals(10, dto.getTramiteId());
        assertEquals(20, dto.getEscalaId());
        assertEquals("Motivo de prueba", dto.getMotivoDeclaracion());
        assertEquals("Sin error", dto.getMensajeError());
        assertEquals("Activo", dto.getEstado());
        assertEquals("12345678901", dto.getRucAgente());
    }
}
