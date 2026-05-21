package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class DocumentRequestDTOTest {

    @Test
    void testBuilder() {
        // Crear un mapa para el campo 'data'
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("key1", "value1");
        dataMap.put("key2", 123);

        // Crear una instancia de DocumentRequestDTO usando el builder
        DocumentRequestDTO dto = DocumentRequestDTO.builder()
                .nombre("example.txt")
                .file("base64encodedstring")
                .data(dataMap)
                .build();

        // Verificación de los valores establecidos
        assertEquals("example.txt", dto.getNombre());
        assertEquals("base64encodedstring", dto.getFile());
        assertEquals(dataMap, dto.getData());
    }

    @Test
    void testConstructor() {
        // Crear un mapa para el campo 'data'
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("key1", "value1");
        dataMap.put("key2", 123);

        // Crear una instancia de DocumentRequestDTO usando el constructor
        DocumentRequestDTO dto = new DocumentRequestDTO("example.txt", "base64encodedstring", dataMap);

        // Verificación de los valores establecidos
        assertEquals("example.txt", dto.getNombre());
        assertEquals("base64encodedstring", dto.getFile());
        assertEquals(dataMap, dto.getData());
    }

}
