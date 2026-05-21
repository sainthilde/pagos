package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SeguimientoRequestDtoTest {

    @Test
    void testConstructorNoArgs() {
        // Prueba del constructor sin argumentos (constructor por defecto)
        SeguimientoRequestDto dto = new SeguimientoRequestDto();
        assertNull(dto.getTipoSegId());
        assertNull(dto.getRucUsuario());
        assertNull(dto.getIndNil());
        assertNull(dto.getEscalaId());
        assertNull(dto.getAcronimoDocumento());
        assertNull(dto.getIndicadorEs());
        assertNull(dto.getComentario());
        assertNull(dto.getEstado());
    }

    @Test
    void testSettersAndGetters() {
        SeguimientoRequestDto dto = new SeguimientoRequestDto();
        dto.setTipoSegId(1);
        dto.setRucUsuario("12345678901");
        dto.setIndNil(true);
        dto.setEscalaId(2);
        dto.setAcronimoDocumento("DOC");
        dto.setIndicadorEs("E");
        dto.setComentario("Test comment");
        dto.setEstado("Active");

        // Verificación de los getters
        assertEquals(1, dto.getTipoSegId());
        assertEquals("12345678901", dto.getRucUsuario());
        assertTrue(dto.getIndNil());
        assertEquals(2, dto.getEscalaId());
        assertEquals("DOC", dto.getAcronimoDocumento());
        assertEquals("E", dto.getIndicadorEs());
        assertEquals("Test comment", dto.getComentario());
        assertEquals("Active", dto.getEstado());
    }

}
