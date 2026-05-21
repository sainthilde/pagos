package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class TramiteUpdateRequestDtoTest {

    private Validator validator;
    private TramiteUpdateRequestDto tramiteUpdateRequestDto;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        tramiteUpdateRequestDto = new TramiteUpdateRequestDto();
    }

    @Test
    public void testTramiteIdNotNull() {
        // Preparación
        tramiteUpdateRequestDto.setTramiteId(null);

        // Ejecución
        Set<ConstraintViolation<TramiteUpdateRequestDto>> violations = validator.validate(tramiteUpdateRequestDto);

        // Verificación
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("tramiteId") &&
                v.getMessage().equals("no puede ser nulo.")));
    }

    @Test
    public void testNumeroTramiteEntidadSize() {
        // Preparación
        tramiteUpdateRequestDto.setNumeroTramiteEntidad("123456789012345678901"); // 21 caracteres, fuera del límite

        // Ejecución
        Set<ConstraintViolation<TramiteUpdateRequestDto>> violations = validator.validate(tramiteUpdateRequestDto);

        // Verificación
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("numeroTramiteEntidad") &&
                v.getMessage().contains("debe tener entre 1 y 20 un dígitos.")));
    }

    @Test
    public void testRucUsuarioSize() {
        // Preparación
        tramiteUpdateRequestDto.setRucUsuario("1234567890"); // Solo 10 caracteres, debería ser 11

        // Ejecución
        Set<ConstraintViolation<TramiteUpdateRequestDto>> violations = validator.validate(tramiteUpdateRequestDto);

        // Verificación
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("rucUsuario") &&
                v.getMessage().contains("debe tener 11 dígitos.")));
    }

    @Test
    public void testOperacionNotNull() {
        // Preparación
        tramiteUpdateRequestDto.setOperacion(null);

        // Ejecución
        Set<ConstraintViolation<TramiteUpdateRequestDto>> violations = validator.validate(tramiteUpdateRequestDto);

        // Verificación
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("operacion") &&
                v.getMessage().equals("no puede ser nulo.")));
    }

    @Test
    public void testValidDto() {
        // Preparación
        tramiteUpdateRequestDto.setTramiteId(123);
        tramiteUpdateRequestDto.setNumeroTramiteEntidad("123456");
        tramiteUpdateRequestDto.setRucUsuario("12345678901");
        tramiteUpdateRequestDto.setOperacion("ACTUALIZAR");

        // Ejecución
        Set<ConstraintViolation<TramiteUpdateRequestDto>> violations = validator.validate(tramiteUpdateRequestDto);

        // Verificación
        assertTrue(violations.isEmpty()); // No debería haber violaciones ya que todo es válido
    }

}
