package pe.gob.vuce.cp.sp.pagos.infrastructure.config.exception;

import org.junit.jupiter.api.Test;
import pe.gob.vuce.cp.sp.pagos.domain.exception.CustomErrorResponse;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

 class CustomErrorResponseTest {

    @Test
     void testAllArgsConstructor() {
        LocalDateTime datetime = LocalDateTime.now();
        String message = "Test message";
        String details = "Test details";

        CustomErrorResponse errorResponse = new CustomErrorResponse(datetime, message, details);

        assertNotNull(errorResponse);
        assertEquals(datetime, errorResponse.getDatetime());
        assertEquals(message, errorResponse.getMessage());
        assertEquals(details, errorResponse.getDetails());
    }

    @Test
     void testNoArgsConstructorAndSetters() {
        CustomErrorResponse errorResponse = new CustomErrorResponse();

        LocalDateTime datetime = LocalDateTime.now();
        String message = "Another test message";
        String details = "Another test details";

        errorResponse.setDatetime(datetime);
        errorResponse.setMessage(message);
        errorResponse.setDetails(details);

        assertNotNull(errorResponse);
        assertEquals(datetime, errorResponse.getDatetime());
        assertEquals(message, errorResponse.getMessage());
        assertEquals(details, errorResponse.getDetails());
    }
}
