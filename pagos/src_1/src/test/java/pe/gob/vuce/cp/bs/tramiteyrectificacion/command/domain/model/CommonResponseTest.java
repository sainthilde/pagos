package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonResponseTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testGettersAndSetters() {
        CommonResponse response = new CommonResponse();

        // Create and set Meta object
        Meta meta = new Meta();
        meta.setResult("Success");
        meta.setCantidadRegistros(10);
        meta.setMensajes(new ArrayList<>());
        Map<String, Object> atributos = new HashMap<>();
        atributos.put("key", "value");
        meta.setAtributos(atributos);

        response.setMeta(meta);
        response.setData("Sample data");

        // Test getter and setter for 'meta'
        assertEquals(meta, response.getMeta(), "El objeto meta debe ser igual al proporcionado");

        // Test getter and setter for 'data'
        assertEquals("Sample data", response.getData(), "El dato debe ser 'Sample data'");
    }

    @Test
    void testJsonSerialization() throws Exception {
        // Create an instance of CommonResponse
        CommonResponse response = new CommonResponse();

        // Create and set Meta
        Meta meta = new Meta();
        meta.setResult("Success");
        meta.setCantidadRegistros(5);

        // Create a list of Mensaje and populate it
        List<Mensaje> mensajes = new ArrayList<>();
        Mensaje mensaje = new Mensaje();
        mensaje.setCodigo("E001");
        mensaje.setTipo("Error");
        mensaje.setMessage("Sample error message");
        mensajes.add(mensaje);
        meta.setMensajes(mensajes);

        // Set attributes map
        Map<String, Object> atributos = new HashMap<>();
        atributos.put("attribute1", "value1");
        meta.setAtributos(atributos);

        // Assign meta and data to CommonResponse
        response.setMeta(meta);
        response.setData("Some data");

        // Serialize the response to JSON
        String json = objectMapper.writeValueAsString(response);

        // Deserialize the JSON back to CommonResponse
        CommonResponse deserializedResponse = objectMapper.readValue(json, CommonResponse.class);

        // Validate the deserialized response
        assertNotNull(deserializedResponse);
        assertNotNull(deserializedResponse.getMeta());

        // Convert Meta objects to JSON strings for comparison
        String expectedMetaJson = objectMapper.writeValueAsString(response.getMeta());
        String actualMetaJson = objectMapper.writeValueAsString(deserializedResponse.getMeta());

        // Compare the JSON strings
        assertEquals(expectedMetaJson, actualMetaJson);
    }

    @Test
    void testMetaGettersAndSetters() {
        Meta meta = new Meta();

        // Test setter and getter for 'result'
        meta.setResult("Success");
        assertEquals("Success", meta.getResult(), "El resultado debe ser 'Success'");

        // Test setter and getter for 'cantidadRegistros'
        meta.setCantidadRegistros(10);
        assertEquals(10, meta.getCantidadRegistros(), "La cantidad de registros debe ser 10");

        // Test setter and getter for 'mensajes'
        List<Mensaje> mensajes = new ArrayList<>();
        Mensaje mensaje = new Mensaje();
        mensaje.setCodigo("E001");
        mensaje.setTipo("Error");
        mensaje.setMessage("Sample error message");
        meta.setMensajes(mensajes);
        assertEquals(mensajes, meta.getMensajes(), "La lista de mensajes debe ser igual a la proporcionada");

        // Test setter and getter for 'atributos'
        Map<String, Object> atributos = new HashMap<>();
        atributos.put("key", "value");
        meta.setAtributos(atributos);
        assertEquals(atributos, meta.getAtributos(), "El mapa de atributos debe ser igual al proporcionado");
    }

    @Test
    void testMetaJsonSerialization() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        // Create Meta object
        Meta meta = new Meta();
        meta.setResult("Success");
        meta.setCantidadRegistros(10);
        meta.setMensajes(new ArrayList<>());
        Map<String, Object> atributos = new HashMap<>();
        atributos.put("key", "value");
        meta.setAtributos(atributos);

        // Serialize to JSON
        String json = objectMapper.writeValueAsString(meta);
        assertNotNull(json, "La serialización a JSON no debe ser nula");

        // Deserialize from JSON
        Meta deserializedMeta = objectMapper.readValue(json, Meta.class);
        assertEquals(meta.getResult(), deserializedMeta.getResult(),
                "El resultado deserializado debe ser igual al original");
        assertEquals(meta.getCantidadRegistros(), deserializedMeta.getCantidadRegistros(),
                "La cantidad de registros deserializada debe ser igual a la original");
        assertEquals(meta.getMensajes(), deserializedMeta.getMensajes(),
                "La lista de mensajes deserializada debe ser igual a la original");
        assertEquals(meta.getAtributos(), deserializedMeta.getAtributos(),
                "El mapa de atributos deserializado debe ser igual al original");
    }

}
