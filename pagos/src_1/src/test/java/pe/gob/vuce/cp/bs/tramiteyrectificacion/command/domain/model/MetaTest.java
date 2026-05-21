package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class MetaTest {

    @Test
    void testGettersAndSetters() {
        Meta meta = new Meta();

        // Testing result
        meta.setResult("success");
        assertEquals("success", meta.getResult());

        // Testing cantidadRegistros
        meta.setCantidadRegistros(10);
        assertEquals(10, meta.getCantidadRegistros());

        // Testing mensajes
        Mensaje mensaje = new Mensaje();
        mensaje.setCodigo("E001");
        mensaje.setTipo("error");
        mensaje.setMessage("Error message");
        meta.setMensajes(Collections.singletonList(mensaje));
        assertEquals(1, meta.getMensajes().size());
        assertEquals("E001", meta.getMensajes().get(0).getCodigo());

        // Testing atributos
        Map<String, Object> atributos = new HashMap<>();
        atributos.put("key1", "value1");
        meta.setAtributos(atributos);
        assertEquals("value1", meta.getAtributos().get("key1"));
    }

}
