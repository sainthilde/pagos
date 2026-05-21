package pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pe.gob.vuce.cp.sp.pagos.infrastructure.config.ConstantsMetaData.NOT_FOUND;

class ResponseMetadataFactoryTest {

    @Test
    void testPrivateConstructorShouldThrowException() throws Exception {
        var constructor = ResponseMetadataFactory.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        Exception exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
        assertInstanceOf(UnsupportedOperationException.class, exception.getCause());
    }

    @Test
    void testNotFoundListarMetadata() {
        var metadata = ResponseMetadataFactory.notFoundListarMetadata();

        assertNotNull(metadata);
        assertEquals(NOT_FOUND, metadata.getCodeInfo());
        assertEquals("LISTAR", metadata.getTipoOperacion());
        assertEquals("No se encontraron datos", metadata.getMensajeOperacion());
        assertFalse(metadata.isEsExitoso());
        assertEquals(HttpStatus.NOT_FOUND, metadata.getHttpStatus());
    }

    @Test
    void testOkListarMetadata() {
        String mensaje = "Consulta exitosa";
        var metadata = ResponseMetadataFactory.okListarMetadata(mensaje);

        assertNotNull(metadata);
        assertEquals("200", metadata.getCodeInfo());
        assertEquals("LISTAR", metadata.getTipoOperacion());
        assertEquals(mensaje, metadata.getMensajeOperacion());
        assertTrue(metadata.isEsExitoso());
        assertEquals(HttpStatus.OK, metadata.getHttpStatus());
    }
}

