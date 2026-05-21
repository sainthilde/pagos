package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class OrdenPagoUpdateRequestDtoTest {

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
                "2024-01-30", "2024-01-30", "Procedimiento X");
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

    @Test
    void testOrdenPagoUpdateRequestDtoValidation() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        OrdenPagoUpdateRequestDto dto = new OrdenPagoUpdateRequestDto();
        Set<ConstraintViolation<OrdenPagoUpdateRequestDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    void testJsonSerialization() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        OrdenPagoUpdateRequestDto dto = new OrdenPagoUpdateRequestDto(1, 2, 3, "APROBADO", "DESTINO1");
        String json = objectMapper.writeValueAsString(dto);

        assertNotNull(json);
        assertTrue(json.contains("documentoId"));
        assertTrue(json.contains("escalaId"));
        assertTrue(json.contains("estadoOrdenPago"));
        assertTrue(json.contains("cancelarDestinoDelPago"));
    }

}
