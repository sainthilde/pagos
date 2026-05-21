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

public class TramiteCrearRequestDtoTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void testConstructorAllArgs() {
        Integer escalaId = 1;
        Integer documentoId = 2;
        String indicadorEs = "E";
        String rucAgente = "12345678901";
        Integer actividadEntidadPuertoId = 3;
        Boolean indNoRequierePago = true;
        String tupa = "123456789012";
        String reglaPagoExencionAplicada = "reglaPagoExencionAplicada";
        String descripcionTramite = "descripcionTramite";

        // Prueba del constructor con todos los argumentos
        TramiteCrearRequestDto dto = new TramiteCrearRequestDto(
                escalaId,
                documentoId,
                indicadorEs,
                rucAgente,
                actividadEntidadPuertoId,
                indNoRequierePago,
                tupa,
                reglaPagoExencionAplicada,
                descripcionTramite);

        assertEquals(escalaId, dto.getEscalaId());
        assertEquals(documentoId, dto.getDocumentoId());
        assertEquals(indicadorEs, dto.getIndicadorEs());
        assertEquals(rucAgente, dto.getRucAgente());
        assertEquals(actividadEntidadPuertoId, dto.getActividadEntidadPuertoId());
        assertEquals(indNoRequierePago, dto.getIndNoRequierePago());
        assertEquals(tupa, dto.getTupa());
    }

    @Test
    void testConstructorNoArgs() {
        // Prueba del constructor sin argumentos (constructor por defecto)
        TramiteCrearRequestDto dto = new TramiteCrearRequestDto();
        assertNull(dto.getEscalaId());
        assertNull(dto.getDocumentoId());
        assertNull(dto.getIndicadorEs());
        assertNull(dto.getRucAgente());
        assertNull(dto.getActividadEntidadPuertoId());
        assertNull(dto.getIndNoRequierePago());
        assertNull(dto.getTupa());
    }

    @Test
    void testSettersAndGetters() {
        TramiteCrearRequestDto dto = new TramiteCrearRequestDto();
        dto.setEscalaId(1);
        dto.setDocumentoId(2);
        dto.setIndicadorEs("E");
        dto.setRucAgente("12345678901");
        dto.setActividadEntidadPuertoId(3);
        dto.setIndNoRequierePago(true);
        dto.setTupa("123456789012");
        dto.setReglaPagoExencionAplicada("reglaPagoExencionAplicada");
        dto.setDescripcionTramite("descripcionTramite");

        // Verificación de los getters
        assertEquals(1, dto.getEscalaId());
        assertEquals(2, dto.getDocumentoId());
        assertEquals("E", dto.getIndicadorEs());
        assertEquals("12345678901", dto.getRucAgente());
        assertEquals(3, dto.getActividadEntidadPuertoId());
        assertTrue(dto.getIndNoRequierePago());
        assertEquals("123456789012", dto.getTupa());
        assertEquals("reglaPagoExencionAplicada", dto.getReglaPagoExencionAplicada());
        assertEquals("descripcionTramite", dto.getDescripcionTramite());
    }

    @Test
    void testValidDto() {
        // Crear un DTO válido
        TramiteCrearRequestDto dto = new TramiteCrearRequestDto(
                1, 2, "E", "12345678901", 3, true, "123456789012", "a", "b");

        // Validar el DTO
        Set<ConstraintViolation<TramiteCrearRequestDto>> violations = validator.validate(dto);

        // No debería haber violaciones de restricciones
        assertTrue(violations.isEmpty());
    }

}
