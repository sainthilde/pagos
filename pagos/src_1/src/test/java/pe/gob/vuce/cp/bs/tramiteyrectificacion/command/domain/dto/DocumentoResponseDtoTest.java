package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
public class DocumentoResponseDtoTest {


    @Test
    void testSettersAndGetters() {
        DocumentoResponseDto dto = new DocumentoResponseDto();

        // Establecer y verificar el valor usando setters
        dto.setFilenetGuid("sample-guid");
        assertEquals("sample-guid", dto.getFilenetGuid());

        // Verificar que el método `filenetGuid` también funciona correctamente
        dto.filenetGuid("another-guid");
        assertEquals("another-guid", dto.getFilenetGuid());
    }


    @Test
    void testEqualsAndHashCode() {
        DocumentoResponseDto dto1 = new DocumentoResponseDto();
        dto1.setFilenetGuid("sample-guid");

        DocumentoResponseDto dto2 = new DocumentoResponseDto();
        dto2.setFilenetGuid("sample-guid");

        DocumentoResponseDto dto3 = new DocumentoResponseDto();
        dto3.setFilenetGuid("different-guid");

        // Los dos primeros DTOs deberían ser iguales
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        // El tercer DTO debería ser diferente
        assertNotEquals(dto1, dto3);
    }

    @Test
    void testToString() {
        DocumentoResponseDto dto = new DocumentoResponseDto();
        dto.setFilenetGuid("sample-guid");

        String expectedString = "class DocumentoResponseDto {\n    filenetGuid: sample-guid\n}";
        assertEquals(expectedString, dto.toString());
    }


}
