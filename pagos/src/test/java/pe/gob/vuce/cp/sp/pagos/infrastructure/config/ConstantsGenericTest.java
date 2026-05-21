package pe.gob.vuce.cp.sp.pagos.infrastructure.config;

import org.junit.jupiter.api.Test;
import pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsHelpers;
import java.lang.reflect.Constructor;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

 class ConstantsGenericTest {

    @Test
    void testConstantsMetaDataThrowsException() throws Exception {
        Constructor<ConstantsMetaData> constructor = ConstantsMetaData.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        Exception exception = assertThrows(Exception.class, constructor::newInstance);

        // Ahora verificamos que la causa sea UnsupportedOperationException
        Throwable cause = exception.getCause();
        assertNotNull(cause);
        assertInstanceOf(UnsupportedOperationException.class, cause);
        assertEquals("Utility class cannot be instantiated", cause.getMessage());
    }

    @Test
    void testConstantsHelpersThrowsException() throws Exception {
        Constructor<ConstantsHelpers> constructor = ConstantsHelpers.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        Exception exception = assertThrows(Exception.class, constructor::newInstance);

        // Ahora verificamos que la causa sea IllegalStateException
        Throwable cause = exception.getCause();
        assertNotNull(cause);
        assertInstanceOf(IllegalStateException.class, cause);
        assertEquals("Constants Pagos class", cause.getMessage());
    }
}
