package pe.gob.vuce.cp.sp.pagos.infrastructure.config.exception;

import org.junit.jupiter.api.Test;
import pe.gob.vuce.cp.sp.pagos.domain.exception.ModelNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

 class ModelNotFoundExceptionTest {

    @Test
     void testExceptionMessage() {
        String message = "Model not found";

        // Lanzamos la excepción y verificamos el mensaje
        ModelNotFoundException exception = assertThrows(ModelNotFoundException.class, () -> {
            throw new ModelNotFoundException(message);
        });

        assertEquals(message, exception.getMessage());
    }
}
