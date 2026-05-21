package pe.gob.vuce.cp.sp.pagos.domain.constants;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

 class ResponseUtilTest {

    @Test
    void constructorShouldThrowException() throws Exception {
        Constructor<ResponseUtil> constructor = ResponseUtil.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        Exception exception = assertThrows(Exception.class, constructor::newInstance);
        Throwable cause = exception.getCause();

        assertNotNull(cause);
        assertInstanceOf(UnsupportedOperationException.class, cause);
        assertEquals("This is a utility class and cannot be instantiated", cause.getMessage());
    }

    @Test
    void createResponseMetaDataDtoShouldReturnValidObject() {
        var result = ResponseUtil.createResponseMetaDataDto();

        assertNotNull(result);
        assertEquals("SUCCESS", result.getResult());
        assertEquals(1, result.getCantidadRegistros());
        assertEquals(1, result.getCantidadRegistrosTotal());
        assertNotNull(result.getMensajes());
        assertTrue(result.getMensajes().isEmpty());
        assertNull(result.getAtributos());
    }

    @Test
    void createApiResponseVuceCP2ExceptionShouldReturnValidErrorResponse() {
        int errorCode = 400;
        String description = "Bad Request";
        String paramMensaje = "Falta un campo";

        var response = ResponseUtil.createApiResponseVuceCP2Exception(
                String.valueOf(errorCode),
                description,
                List.of(paramMensaje)
        );

        assertNotNull(response);
        assertNotNull(response.getMeta());
        assertEquals("ERROR", response.getMeta().getResult());
        assertEquals(0, response.getMeta().getCantidadRegistros());

        var mensajes = response.getMeta().getMensajes();
        assertNotNull(mensajes);
        assertEquals(1, mensajes.size());

        var mensaje = mensajes.get(0);
        assertEquals(String.valueOf(errorCode), mensaje.getCodigo());
        assertEquals("E", mensaje.getTipo());
        assertEquals(description, mensaje.getMensaje());
        assertEquals(List.of(paramMensaje), mensaje.getParametrosDeMensaje());

        assertNotNull(response.getData());
        assertTrue(response.getData().isEmpty());
        assertEquals(Map.of(), response.getMeta().getAtributos());
    }
}
