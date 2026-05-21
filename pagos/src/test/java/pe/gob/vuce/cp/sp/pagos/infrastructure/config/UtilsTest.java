package pe.gob.vuce.cp.sp.pagos.infrastructure.config;

import org.junit.jupiter.api.Test;
import pe.gob.vuce.cp.sp.pagos.domain.constants.OrdenPagoUtils;
import pe.gob.vuce.cp.sp.pagos.domain.constants.Constants;
import pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos;
import pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagosSunat;
import pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsError;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class UtilsTest {

    private <T> void assertPrivateConstructorThrows(Class<T> clazz, String expectedMessage) throws Exception {
        Constructor<T> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);

        InvocationTargetException exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
        IllegalStateException thrown = (IllegalStateException) exception.getCause();

        assertEquals(expectedMessage, thrown.getMessage());
    }

    @Test
    void testPrivateConstructorOrdenPagoUtils() throws Exception {
        assertPrivateConstructorThrows(OrdenPagoUtils.class, "OrdenPagoUtils class");
    }

    @Test
    void testPrivateConstructorConstants() throws Exception {
        assertPrivateConstructorThrows(Constants.class, "Constantes class");
    }

    @Test
    void testPrivateConstructorConstantsPagos() throws Exception {
        assertPrivateConstructorThrows(ConstantsPagos.class, "Constants Pagos class");
    }

    @Test
    void testPrivateConstructorConstantsPagosSunat() throws Exception {
        assertPrivateConstructorThrows(ConstantsPagosSunat.class, "Constants Pagos Sunat class");
    }

    @Test
    void testPrivateConstructorConstantsError() throws Exception {
        assertPrivateConstructorThrows(ConstantsError.class, "Constants Error class");
    }

}
