package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.DocumentoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.TramiteModel;

public class DeclaracionJuradaResponseDtoTest {

    @Test
    void testSettersAndGetters() {
        // Crear instancias de Documento y Tramite para probar los getters y setters
        DocumentoModel documento = new DocumentoModel(); // Asume que Documento tiene un constructor por defecto
        TramiteModel tramite = new TramiteModel(); // Asume que Tramite tiene un constructor por defecto

        DeclaracionJuradaResponseDto dto = new DeclaracionJuradaResponseDto(
                1,
                "Pagado",
                "DDJJ123",
                LocalDateTime.now(),
                documento,
                10,
                "Motivo de prueba",
                "Sin error",
                tramite,
                "Activo",
                "12345678901");

        // Verificar los valores establecidos
        assertEquals(1, dto.getId());
        assertEquals("Pagado", dto.getEstadoDdjjPago());
        assertEquals("DDJJ123", dto.getNumeroDdjj());
        assertNotNull(dto.getFechaSolicitudDdjj());
        assertEquals(documento, dto.getDocumento());
        assertEquals(10, dto.getEscalaId());
        assertEquals("Motivo de prueba", dto.getMotivoDeclaracion());
        assertEquals("Sin error", dto.getMensajeError());
        assertEquals(tramite, dto.getTramite());
        assertEquals("Activo", dto.getEstado());
        assertEquals("12345678901", dto.getRucAgente());
    }

}
