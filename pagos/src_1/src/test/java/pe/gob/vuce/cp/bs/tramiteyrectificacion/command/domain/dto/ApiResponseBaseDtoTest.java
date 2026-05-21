package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.exception.ApiResponseMeta;

public class ApiResponseBaseDtoTest {
    private ApiResponseBaseDto<String> apiResponseBaseDto;
    private ApiResponseMeta meta;

    @BeforeEach
    void setUp() {
        apiResponseBaseDto = new ApiResponseBaseDto<>();
        meta = new ApiResponseMeta().result("success").cantidadRegistros(1).cantidadRegistrosTotal(10);
    }

    @Test
    void testSetAndGetMeta() {
        // Verifica que la meta esté inicialmente nula
        assertNull(apiResponseBaseDto.getMeta());

        // Establecer meta y verificar
        apiResponseBaseDto.setMeta(meta);
        assertEquals(meta, apiResponseBaseDto.getMeta());
    }

    @Test
    void testSetAndGetData() {
        // Verifica que data esté inicialmente nula
        assertNull(apiResponseBaseDto.getData());

        // Crear lista de datos y asignarla
        List<String> data = Arrays.asList("data1", "data2");
        apiResponseBaseDto.setData(data);

        // Verificar que se haya asignado correctamente
        assertEquals(data, apiResponseBaseDto.getData());
    }

    @Test
    void testAddDataItem() {
        // Verifica que data esté inicialmente nula
        assertNull(apiResponseBaseDto.getData());

        // Agregar un item y verificar que se crea la lista y contiene el item
        apiResponseBaseDto.addDataItem("data1");
        assertNotNull(apiResponseBaseDto.getData());
        assertEquals(1, apiResponseBaseDto.getData().size());
        assertEquals("data1", apiResponseBaseDto.getData().get(0));
    }

    @Test
    void testToString() {
        // Establecer meta y data
        apiResponseBaseDto.setMeta(meta);
        apiResponseBaseDto.addDataItem("data1");

        // El resultado esperado
        String expectedString = "class ApiResponseBaseDto {\n" +
                "    meta: class ApiResponseMeta {\n" +
                "    result: success\n" +
                "    mensajes: []\n" +
                "    cantidadRegistros: 1\n" +
                "    cantidadRegistrosTotal: 10\n" +
                "    atributos: {}\n" +
                "}\n" +
                "    data: [data1]\n" +
                "}";

        // Eliminar espacios en blanco y saltos de línea
        String actualString = apiResponseBaseDto.toString().replaceAll("\\s+", "");
        String expectedFormattedString = expectedString.replaceAll("\\s+", "");

        // Comparar las cadenas formateadas
        assertEquals(expectedFormattedString, actualString);
    }

    @Test
    void testEqualsAndHashCode() {
        // Crear el objeto bajo prueba con meta y data
        ApiResponseMeta meta = new ApiResponseMeta();
        meta.setResult("success");
        meta.setCantidadRegistros(1);
        meta.setCantidadRegistrosTotal(10);

        ApiResponseBaseDto<String> response1 = new ApiResponseBaseDto<>();
        response1.setMeta(meta);
        response1.addDataItem("data1");

        // Crear el objeto esperado con los mismos valores
        ApiResponseBaseDto<String> response2 = new ApiResponseBaseDto<>();
        response2.setMeta(meta);
        response2.addDataItem("data1");

        // Comparar los dos objetos
        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    void testSetMeta() {
        ApiResponseBaseDto<String> responseDto = new ApiResponseBaseDto<>();
        ApiResponseMeta meta = new ApiResponseMeta();
        meta.setResult("Success");

        responseDto.meta(meta);

        assertNotNull(responseDto.getMeta());
        assertEquals("Success", responseDto.getMeta().getResult());
    }

    @Test
    void testEquals() {
        ApiResponseBaseDto<String> responseDto1 = new ApiResponseBaseDto<>();
        ApiResponseMeta meta = new ApiResponseMeta();
        meta.setResult("Success");
        responseDto1.meta(meta).data(List.of("item1"));

        ApiResponseBaseDto<String> responseDto2 = new ApiResponseBaseDto<>();
        responseDto2.meta(meta).data(List.of("item1"));

        assertTrue(responseDto1.equals(responseDto2));

        // Cambiar algún valor para que no sean iguales
        responseDto2.data(List.of("item2"));
        assertFalse(responseDto1.equals(responseDto2));
    }

    @Test
    void testToIndentedString() {
        ApiResponseBaseDto<String> responseDto = new ApiResponseBaseDto<>();
        String result = responseDto.toString();

        assertTrue(result.contains("meta: null"));
        assertTrue(result.contains("data: null"));
    }

}
