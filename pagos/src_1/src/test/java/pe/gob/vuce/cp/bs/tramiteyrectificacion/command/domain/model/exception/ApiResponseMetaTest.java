package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.exception;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ApiResponseMetaTest {

    @Test
    void testGettersAndSetters() {
        ApiResponseMeta meta = new ApiResponseMeta();

        // Test setter and getter for 'result'
        meta.setResult("Success");
        assertEquals("Success", meta.getResult(), "El resultado debe ser 'Success'");

        // Test setter and getter for 'mensajes'
        List<VuceCP2Exception> mensajes = new ArrayList<>();
        mensajes.add(new VuceCP2Exception().codigo("E001").mensaje("Error 1"));
        meta.setMensajes(mensajes);
        assertEquals(mensajes, meta.getMensajes(), "La lista de mensajes debe ser igual a la proporcionada");

        // Test setter and getter for 'cantidadRegistros'
        meta.setCantidadRegistros(10);
        assertEquals(10, meta.getCantidadRegistros(), "La cantidad de registros debe ser 10");

        // Test setter and getter for 'cantidadRegistrosTotal'
        meta.setCantidadRegistrosTotal(100);
        assertEquals(100, meta.getCantidadRegistrosTotal(), "La cantidad total de registros debe ser 100");

        // Test setter and getter for 'atributos'
        Map<String, String> atributos = new HashMap<>();
        atributos.put("key1", "value1");
        meta.setAtributos(atributos);
        assertEquals(atributos, meta.getAtributos(), "El mapa de atributos debe ser igual al proporcionado");
    }

    @Test
    void testAddMensajesItem() {
        ApiResponseMeta meta = new ApiResponseMeta();

        VuceCP2Exception exception = new VuceCP2Exception().codigo("E001").mensaje("Error 1");
        meta.addMensajesItem(exception);
        assertEquals(1, meta.getMensajes().size(), "La lista de mensajes debe contener 1 elemento");
        assertTrue(meta.getMensajes().contains(exception), "La lista de mensajes debe contener la excepción añadida");

        meta.addMensajesItem(new VuceCP2Exception().codigo("E002").mensaje("Error 2"));
        assertEquals(2, meta.getMensajes().size(), "La lista de mensajes debe contener 2 elementos");
    }

    @Test
    void testPutAtributosItem() {
        ApiResponseMeta meta = new ApiResponseMeta();

        meta.putAtributosItem("key1", "value1");
        assertEquals(1, meta.getAtributos().size(), "El mapa de atributos debe contener 1 entrada");
        assertEquals("value1", meta.getAtributos().get("key1"), "El valor para 'key1' debe ser 'value1'");

        meta.putAtributosItem("key2", "value2");
        assertEquals(2, meta.getAtributos().size(), "El mapa de atributos debe contener 2 entradas");
        assertEquals("value2", meta.getAtributos().get("key2"), "El valor para 'key2' debe ser 'value2'");
    }

    @Test
    void testEqualsAndHashCode() {
        ApiResponseMeta meta1 = new ApiResponseMeta();
        meta1.setResult("Success");
        meta1.setCantidadRegistros(10);
        meta1.setCantidadRegistrosTotal(100);

        ApiResponseMeta meta2 = new ApiResponseMeta();
        meta2.setResult("Success");
        meta2.setCantidadRegistros(10);
        meta2.setCantidadRegistrosTotal(100);

        assertEquals(meta1, meta2, "Los objetos ApiResponseMeta deben ser iguales");
        assertEquals(meta1.hashCode(), meta2.hashCode(), "Los códigos hash de los objetos ApiResponseMeta deben ser iguales");

        meta2.setCantidadRegistrosTotal(200);
        assertNotEquals(meta1, meta2, "Los objetos ApiResponseMeta no deben ser iguales si la cantidadRegistrosTotal cambia");
    }

    @Test
    void testToString() {
        ApiResponseMeta meta = new ApiResponseMeta();
        meta.setResult("Success");
        meta.setCantidadRegistros(10);
        meta.setCantidadRegistrosTotal(100);
        meta.putAtributosItem("key1", "value1");

        String expectedToString = "class ApiResponseMeta {\n" +
                "    result: Success\n" +
                "    mensajes: []\n" +
                "    cantidadRegistros: 10\n" +
                "    cantidadRegistrosTotal: 100\n" +
                "    atributos: {key1=value1}\n" +
                "}";

        assertEquals(expectedToString, meta.toString(), "El método toString() no devuelve el formato esperado");
    }



}
