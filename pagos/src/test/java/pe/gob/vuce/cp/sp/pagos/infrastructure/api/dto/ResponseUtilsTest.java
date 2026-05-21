package pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.response.ApiResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.response.ApiResponseMeta;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.response.Mensaje;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.response.ResponseMetadata;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

class ResponseUtilsTest {

    @Test
    void testPrivateConstructorShouldThrowUnsupportedOperationException() throws Exception {
        Constructor<ResponseUtils> constructor = ResponseUtils.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        Exception exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
        Throwable cause = exception.getCause();
        assertInstanceOf(UnsupportedOperationException.class, cause);
    }


    @Test
    void testBuildResponse_withListData_success() {
        List<String> data = List.of("a", "b", "c");

        ResponseMetadata metadata = ResponseMetadata.builder()
                .codeInfo("200")
                .tipoOperacion("LIST")
                .mensajeOperacion("Consulta exitosa")
                .esExitoso(true)
                .httpStatus(HttpStatus.OK)
                .build();

        ResponseEntity<ApiResponse> response = ResponseUtils.buildResponse(data, metadata);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ApiResponse body = response.getBody();
        assertNotNull(body);
        assertEquals(data, body.getData());

        ApiResponseMeta meta = body.getMeta();
        assertEquals("SUCCESS", meta.getResult());
        assertEquals(3, meta.getCantidadRegistros());
        assertEquals(3, meta.getCantidadRegistrosTotal());

        assertEquals(1, meta.getMensajes().size());
        Mensaje mensaje = meta.getMensajes().get(0);
        assertEquals("200", mensaje.getCodigo());
        assertEquals("LIST", mensaje.getTipo());
        assertEquals("Consulta exitosa", mensaje.getMessage());
    }

    @Test
    void testBuildResponse_withObjectDataAndErrors() {
        String data = "algo";

        ResponseMetadata metadata = ResponseMetadata.builder()
                .codeInfo("500")
                .tipoOperacion("ERROR")
                .mensajeOperacion("Fallo")
                .esExitoso(false)
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .erroresAdicionales(List.of("Error 1", "Error 2"))
                .atributos(Map.of("debug", true))
                .build();

        ResponseEntity<ApiResponse> response = ResponseUtils.buildResponse(data, metadata);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        ApiResponse body = response.getBody();
        assertNotNull(body);
        assertEquals(data, body.getData());

        ApiResponseMeta meta = body.getMeta();
        assertEquals("ERROR", meta.getResult());
        assertEquals(1, meta.getCantidadRegistros());
        assertEquals(1, meta.getCantidadRegistrosTotal());

        assertEquals(2, meta.getMensajes().size());
        assertEquals("Error 1", meta.getMensajes().get(0).getMessage());
        assertTrue((Boolean) meta.getAtributos().get("debug"));
    }

    @Test
    void testBuildResponse_withNullData() {
        ResponseMetadata metadata = ResponseMetadata.builder()
                .codeInfo("204")
                .tipoOperacion("NONE")
                .mensajeOperacion("Sin contenido")
                .esExitoso(true)
                .httpStatus(HttpStatus.NO_CONTENT)
                .build();

        ResponseEntity<ApiResponse> response = ResponseUtils.buildResponse(null, metadata);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        ApiResponse body = response.getBody();
        assertNull(body.getData());

        ApiResponseMeta meta = body.getMeta();
        assertEquals(0, meta.getCantidadRegistros());
        assertEquals(0, meta.getCantidadRegistrosTotal());
    }
    @Test
    void testBuildResponse_withoutAtributos() {
        ResponseMetadata metadata = ResponseMetadata.builder()
                .codeInfo("200")
                .tipoOperacion("SIN_ATRIBUTOS")
                .mensajeOperacion("Sin atributos")
                .esExitoso(true)
                .httpStatus(HttpStatus.OK)
                .erroresAdicionales(List.of("Error X")) // Para que genere mensaje
                .build();

        ResponseEntity<ApiResponse> response = ResponseUtils.buildResponse("data", metadata);
        ApiResponseMeta meta = response.getBody().getMeta();

        assertNotNull(meta.getAtributos()); // Ya debe devolver Collections.emptyMap()
        assertTrue(meta.getAtributos().isEmpty());
    }
    @Test
    void testConstruirAtributos_nullInput_returnsEmptyMap() throws Exception {
        Method method = ResponseUtils.class.getDeclaredMethod("construirAtributos", Map.class);
        method.setAccessible(true);

        @SuppressWarnings("unchecked")
        Map<String, Object> result = (Map<String, Object>) method.invoke(null, (Map<String, Object>) null);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    @Test
    void testBuildResponse_withNoErroresField() {
        ResponseMetadata metadata = ResponseMetadata.builder()
                .codeInfo("200")
                .tipoOperacion("INFO")
                .mensajeOperacion("Sin errores")
                .esExitoso(true)
                .httpStatus(HttpStatus.OK)
                .erroresAdicionales(null) // clave aquí
                .build();

        ResponseEntity<ApiResponse> response = ResponseUtils.buildResponse("data", metadata);

        ApiResponseMeta meta = response.getBody().getMeta();
        assertEquals(1, meta.getMensajes().size()); // Solo el mensaje principal
        assertEquals("Sin errores", meta.getMensajes().get(0).getMessage());
    }
    @Test
    void testBuildResponse_withEmptyErroresList() {
        ResponseMetadata metadata = ResponseMetadata.builder()
                .codeInfo("200")
                .tipoOperacion("INFO")
                .mensajeOperacion("Sin errores adicionales")
                .esExitoso(true)
                .httpStatus(HttpStatus.OK)
                .erroresAdicionales(Collections.emptyList())
                .build();

        ResponseEntity<ApiResponse> response = ResponseUtils.buildResponse("data", metadata);

        ApiResponseMeta meta = response.getBody().getMeta();
        assertEquals(1, meta.getMensajes().size()); // Solo el mensaje principal
        assertEquals("Sin errores adicionales", meta.getMensajes().get(0).getMessage());
    }

}
