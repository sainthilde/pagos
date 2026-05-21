package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class TramiteDesistRequestDtoTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void testConstructorAllArgs() {
        Integer escalaId = 5;
        Integer tramiteId = 123;

        // Prueba del constructor con todos los argumentos
        TramiteDesistRequestDto dto = new TramiteDesistRequestDto(escalaId, tramiteId);
        assertEquals(escalaId, dto.getEscalaId());
        assertEquals(tramiteId, dto.getTramiteId());
    }

    @Test
    void testConstructorNoArgs() {
        // Prueba del constructor sin argumentos (constructor por defecto)
        TramiteDesistRequestDto dto = new TramiteDesistRequestDto();
        assertNull(dto.getEscalaId());
        assertNull(dto.getTramiteId());
    }

    @Test
    void testSettersAndGetters() {
        TramiteDesistRequestDto dto = new TramiteDesistRequestDto();
        dto.setEscalaId(7);
        dto.setTramiteId(456);

        // Verificación de los getters
        assertEquals(7, dto.getEscalaId());
        assertEquals(456, dto.getTramiteId());
    }

    @Test
    void testNotNullValidation() {
        // Crear un DTO con escalaId nulo
        TramiteDesistRequestDto dto = new TramiteDesistRequestDto(null, 123);

        // Validar el DTO
        Set<ConstraintViolation<TramiteDesistRequestDto>> violations = validator.validate(dto);

        // Verificar que hay una violación de la restricción @NotNull
        assertFalse(violations.isEmpty());

        // Verificar que el mensaje de error corresponde al campo escalaId
        ConstraintViolation<TramiteDesistRequestDto> violation = violations.iterator().next();
        assertEquals("no puede ser nulo.", violation.getMessage());
        assertEquals("escalaId", violation.getPropertyPath().toString());
    }

    @Test
    void testValidDto() {
        // Crear un DTO válido
        TramiteDesistRequestDto dto = new TramiteDesistRequestDto(5, 123);

        // Validar el DTO
        Set<ConstraintViolation<TramiteDesistRequestDto>> violations = validator.validate(dto);

        // No debería haber violaciones de restricciones
        assertTrue(violations.isEmpty());
    }

}
