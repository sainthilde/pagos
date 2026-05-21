package pe.gob.vuce.cp.sp.pagos;

import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;

class CpSpPagosApplicationMainTest {

    @Test
    void main_shouldRunSpringApplication() {
        try (var mocked = mockStatic(SpringApplication.class)) {
            CpSpPagosApplication.main(new String[]{});
            mocked.verify(() -> SpringApplication.run(CpSpPagosApplication.class, new String[]{}));
        }
    }
}