package pe.gob.vuce.cp.sp.pagos.infrastructure.config.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

 class JsonParseExceptionTest {

    @Test
    void testMessageAndCauseConstructor() {
        String message = "Error parsing JSON";
        Throwable cause = new NullPointerException("Null value");
        JsonParseException exception = new JsonParseException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}
