package pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrdenPagoResponseDtoTest {

    private final ObjectMapper mapper = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @Test
    void testRecordGetters() {
        OrdenPagoResponseDto dto = new OrdenPagoResponseDto(
                1, 10, 20, 30, "12345678901", "OP-001",
                1500.0, "2025-05-18", "CPB-001", "GENERADO",
                "2025-06-01", null, null,
                "2025-05-17", 1600.0, null, "PAGO SERVICIO",null
        );

        assertEquals(1, dto.ordenPagoId());
        assertEquals("OP-001", dto.codigoOrdenPago());
        assertNull(dto.fechaAnulacionCpb());
    }

    @Test
    void testJsonSerializationExcludesNulls() throws Exception {
        OrdenPagoResponseDto dto = new OrdenPagoResponseDto(
                1, 10, 20, 30, "12345678901", "OP-001",
                1500.0, "2025-05-18", "CPB-001", "GENERADO",
                "2025-06-01", null, null,
                "2025-05-17", 1600.0, null, "PAGO SERVICIO",null
        );

        String json = mapper.writeValueAsString(dto);

        assertTrue(json.contains("\"ordenPagoId\":1"));
        assertTrue(json.contains("entidadId"));
        assertTrue(json.contains("documentoId"));
    }
}
