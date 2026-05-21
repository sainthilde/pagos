package pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

 class OrdenPagoRequestDtoTest {

    @Test
     void shouldCreateOrdenPagoRequestDtoWithAllFields() {
        OrdenPagoRequestDto dto = new OrdenPagoRequestDto(1, 2, 3, "1234567890", "2023-12-31", "component1", "search", 4,1);
        assertNotNull(dto);
        assertEquals(1, dto.getEntidadId());
        assertEquals(2, dto.getDocumentoId());
        assertEquals(3, dto.getEscalaId());
        assertEquals("1234567890", dto.getRucAgente());
        assertEquals("2023-12-31", dto.getFechaVigencia());
        assertEquals("component1", dto.getIdComponente());
        assertEquals("search", dto.getTextSearch());
        assertEquals(4, dto.getActividadEntidadPuertoId());
    }

    @Test
     void shouldCreateOrdenPagoRequestDtoWithMandatoryFieldsOnly() {
        OrdenPagoRequestDto dto = new OrdenPagoRequestDto(1, 2, 3, "1234567890", "2023-12-31", "component1", null, null,0);
        assertNotNull(dto);
        assertEquals(1, dto.getEntidadId());
        assertEquals(2, dto.getDocumentoId());
        assertEquals(3, dto.getEscalaId());
        assertEquals("1234567890", dto.getRucAgente());
        assertEquals("2023-12-31", dto.getFechaVigencia());
        assertEquals("component1", dto.getIdComponente());
        assertNull(dto.getTextSearch());
        assertNull(dto.getActividadEntidadPuertoId());
    }
}