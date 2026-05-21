package pe.gob.vuce.cp.sp.pagos.infrastructure.config.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

 class PdfGenerationExceptionTest {

    @Test
    void testMessageOnlyConstructor() {
        String message = "PDF generation failed";
        PdfGenerationException exception = new PdfGenerationException(message);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }
}
