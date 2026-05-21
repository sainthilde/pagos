package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DocumentoDDJJRequestDtoTest {

    @Test
    void testSettersAndGetters() {
        DocumentoDDJJRequestDto dto = new DocumentoDDJJRequestDto();

        dto.setDocumentoId(123);
        assertEquals(123, dto.getDocumentoId());

        dto.setDescAcronimo("Test Acronym");
        assertEquals("Test Acronym", dto.getDescAcronimo());
    }

}
