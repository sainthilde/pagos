package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.api.advices.exception;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ResponseBuildExceptionTest {

    @Test
    void shouldCreateExceptionWithMessage() {
        String message = "Error message";
        ResponseBuildException exception = new ResponseBuildException(message);

        assertThat(exception)
                .isInstanceOf(RuntimeException.class)
                .hasMessage(message)
                .hasNoCause();
    }

    @Test
    void shouldCreateExceptionWithMessageAndCause() {
        String message = "Error with cause";
        Throwable cause = new IllegalArgumentException("Invalid argument");

        ResponseBuildException exception = new ResponseBuildException(message, cause);

        assertThat(exception)
                .isInstanceOf(RuntimeException.class)
                .hasMessage(message)
                .hasCause(cause);
    }
}
