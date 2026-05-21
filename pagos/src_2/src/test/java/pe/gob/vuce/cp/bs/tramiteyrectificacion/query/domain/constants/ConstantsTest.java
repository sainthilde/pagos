package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.constants;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

 class ConstantsTest {

    @Test
    void testConstants() {
        // Testing TRAMITE_PAGO
        Assertions.assertEquals(1, Constants.ONE, "TRAMITE_PAGO should be 'S'");

        // Testing TRAMITE_DJ
        Assertions.assertEquals(0, Constants.ZERO, "TRAMITE_DJ should be 'D'");

        // Testing COMPONENTE_PORTUARIO
        Assertions.assertEquals("NO VALUE", Constants.NO_VALUE, "COMPONENTE_PORTUARIO should be 'CP'");

        // Testing DECLARACION_JURADA
        Assertions.assertEquals( "null", Constants.NULL_VALUE, "DECLARACION_JURADA should be 'DJP'");
 }

    @Test
    void testPrivateConstructor() {
        Constructor<Constants> constructor = null;
        try {
            constructor = Constants.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
            fail("Expected AssertionError to be thrown");
        } catch (InvocationTargetException e) {
            assertTrue(e.getCause() instanceof AssertionError, "Expected cause to be AssertionError");
        } catch (Exception e) {
            fail("Unexpected exception thrown: " + e);
        } finally {
            if (constructor != null) {
                constructor.setAccessible(false);
            }
        }
    }

}
