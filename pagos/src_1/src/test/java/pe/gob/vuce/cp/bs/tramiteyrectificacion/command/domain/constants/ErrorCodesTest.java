package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ErrorCodesTest {

    @Test
    void testErrorCodesValues() {
        assertEquals("E0069", ErrorCodes.FIELD_ALREADY_EXIST);
        assertEquals("E0070", ErrorCodes.FIELD_INVALID);
        assertEquals("E0071", ErrorCodes.SERVER_ERROR);
        assertEquals("E0072", ErrorCodes.ILLEGAL_ARGUMENT);
        assertEquals("E0073", ErrorCodes.NOT_FOUND);
        assertEquals("E0074", ErrorCodes.VALIDATION_ERROR);
        assertEquals("E0076", ErrorCodes.CONSTRAINT_VIOLATION);
        assertEquals("E0077", ErrorCodes.INTERNAL_SERVER_ERROR);
        assertEquals("E0077", ErrorCodes.INVALID_OPERATION);
        assertEquals("E0075", ErrorCodes.TYPE_MISMATCH);
    }

    @Test
    void testConstructor() {
        // Verifica que no se pueda instanciar la clase
        assertThrows(InvocationTargetException.class, () -> {
            // Llamamos al constructor privado a través de reflexión
            java.lang.reflect.Constructor<ErrorCodes> constructor = ErrorCodes.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }

}
