package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class TramiteResponseDtoTest {

    @Test
    void testConstructorAndGetters() {
        Integer idSuce = 123;
        String numeroSuce = "SUCE-001";

        // Prueba del constructor y getters
        TramiteResponseDto tramite = new TramiteResponseDto(idSuce, numeroSuce);
        assertEquals(idSuce, tramite.getIdSuce());
        assertEquals(numeroSuce, tramite.getNumeroSuce());
    }

    @Test
    void testSetters() {
        TramiteResponseDto tramite = new TramiteResponseDto(123, "SUCE-001");

        // Cambiar valores con setters
        tramite.setIdSuce(456);
        tramite.setNumeroSuce("SUCE-002");

        // Verificar que los setters funcionen correctamente
        assertEquals(456, tramite.getIdSuce());
        assertEquals("SUCE-002", tramite.getNumeroSuce());
    }

    @Test
    void testNullValues() {
        TramiteResponseDto tramite = new TramiteResponseDto(null, null);

        // Verificar que el objeto maneje valores nulos
        assertNull(tramite.getIdSuce());
        assertNull(tramite.getNumeroSuce());
    }
}