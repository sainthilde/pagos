package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.exception;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class VuceCP2ExceptionTest {

    @Test
    void testGettersAndSetters() {
        VuceCP2Exception exception = new VuceCP2Exception();

        // Test setter and getter for 'codigo'
        exception.setCodigo("E001");
        assertEquals("E001", exception.getCodigo(), "El código debe ser 'E001'");

        // Test setter and getter for 'tipo'
        exception.setTipo("ErrorTipo");
        assertEquals("ErrorTipo", exception.getTipo(), "El tipo debe ser 'ErrorTipo'");

        // Test setter and getter for 'mensaje'
        exception.setMensaje("Error description");
        assertEquals("Error description", exception.getMensaje(), "El mensaje debe ser 'Error description'");

        // Test setter and getter for 'parametrosDeMensaje'
        List<String> parametros = new ArrayList<>();
        parametros.add("param1");
        parametros.add("param2");
        exception.setParametrosDeMensaje(parametros);
        assertEquals(parametros, exception.getParametrosDeMensaje(), "Los parámetros de mensaje deben ser igual a los proporcionados");
    }

    @Test
    void testAddParametrosDeMensajeItem() {
        VuceCP2Exception exception = new VuceCP2Exception();

        exception.addParametrosDeMensajeItem("param1");
        assertEquals(1, exception.getParametrosDeMensaje().size(), "La lista de parámetros debe contener 1 elemento");
        assertTrue(exception.getParametrosDeMensaje().contains("param1"), "La lista de parámetros debe contener 'param1'");

        exception.addParametrosDeMensajeItem("param2");
        assertEquals(2, exception.getParametrosDeMensaje().size(), "La lista de parámetros debe contener 2 elementos");
        assertTrue(exception.getParametrosDeMensaje().contains("param2"), "La lista de parámetros debe contener 'param2'");
    }

    @Test
    void testEqualsAndHashCode() {
        VuceCP2Exception exception1 = new VuceCP2Exception();
        exception1.setCodigo("E001");
        exception1.setTipo("ErrorTipo");
        exception1.setMensaje("Error description");

        VuceCP2Exception exception2 = new VuceCP2Exception();
        exception2.setCodigo("E001");
        exception2.setTipo("ErrorTipo");
        exception2.setMensaje("Error description");

        assertEquals(exception1, exception2, "Las excepciones deben ser iguales");
        assertEquals(exception1.hashCode(), exception2.hashCode(), "Los códigos hash de las excepciones deben ser iguales");

        exception2.setMensaje("Otro mensaje");
        assertNotEquals(exception1, exception2, "Las excepciones no deben ser iguales si el mensaje cambia");
    }

    @Test
    void testToString() {
        VuceCP2Exception exception = new VuceCP2Exception();
        exception.setCodigo("E001");
        exception.setTipo("ErrorTipo");
        exception.setMensaje("Error description");
        exception.setParametrosDeMensaje(List.of("param1", "param2"));

        String expectedToString = "class VuceCP2Exception {\n" +
                "    codigo: E001\n" +
                "    tipo: ErrorTipo\n" +
                "    mensaje: Error description\n" +
                "    parametrosDeMensaje: [param1, param2]\n" +
                "}";

        assertEquals(expectedToString, exception.toString(), "El método toString() no devuelve el formato esperado");
    }

}
