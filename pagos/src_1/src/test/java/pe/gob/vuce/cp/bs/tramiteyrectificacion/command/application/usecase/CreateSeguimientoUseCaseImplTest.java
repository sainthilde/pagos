package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.application.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.SeguimientoRequestDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.FeignComunesCommandClientPort;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CreateSeguimientoUseCaseImplTest {

    @Mock
    private FeignComunesCommandClientPort feignComunesCommandClientPort;

    @InjectMocks
    private CreateSeguimientoUseCaseImpl createSeguimientoUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSeguimiento() {
        // Datos de prueba
        SeguimientoRequestDto seguimientoRequestDto = new SeguimientoRequestDto();
        String user = "test_user";

        // Llamar al método a probar
        createSeguimientoUseCase.create(seguimientoRequestDto, user);

        // Verificar que se llamó a saveEscalaSeguimiento con los parámetros correctos
        verify(feignComunesCommandClientPort, times(1)).saveEscalaSeguimiento(eq(seguimientoRequestDto), eq(user));
    }

}
