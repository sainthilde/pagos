package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class OrdenPagoResponseDtoTest {

    @Test
    void testNoArgsConstructor() {
        OrdenPagoResponseDto dto = new OrdenPagoResponseDto();
        assertNotNull(dto);
    }

    @Test
    void testAllArgsConstructor() {
        OrdenPagoResponseDto dto = new OrdenPagoResponseDto(
                1, 2, 3, 4, "12345678901", "OP123456", 100.5,
                "2024-01-30", "CPB001", "PENDIENTE", "2024-02-15",
                "2024-03-01", "2024-03-15", "2024-04-01", 200.0,
                "2024-01-30", "2024-01-30", "Procedimiento X"
        );
        assertNotNull(dto);
        assertEquals(1, dto.getOrdenPagoId());
        assertEquals(2, dto.getEntidadId());
        assertEquals("12345678901", dto.getRucAgente());
    }

    @Test
    void testSettersAndGetters() {
        OrdenPagoResponseDto dto = new OrdenPagoResponseDto();

        dto.setOrdenPagoId(10);
        dto.setEntidadId(20);
        dto.setDocumentoId(30);
        dto.setCodigoOrdenPago("OP654321");
        dto.setMonto(500.75);
        dto.setEstado("PAGADO");

        assertEquals(10, dto.getOrdenPagoId());
        assertEquals(20, dto.getEntidadId());
        assertEquals(30, dto.getDocumentoId());
        assertEquals("OP654321", dto.getCodigoOrdenPago());
        assertEquals(500.75, dto.getMonto());
        assertEquals("PAGADO", dto.getEstado());
    }
}
