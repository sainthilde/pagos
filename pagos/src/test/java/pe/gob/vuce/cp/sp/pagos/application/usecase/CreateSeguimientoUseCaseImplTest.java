package pe.gob.vuce.cp.sp.pagos.application.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.SeguimientoRequestDto;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.FeignComunesCommandClientPort;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

class CreateSeguimientoUseCaseImplTest {

    @Mock
    private FeignComunesCommandClientPort feignComunesCommandClientPort;

    @InjectMocks
    private CreateSeguimientoUseCaseImpl createSeguimientoUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        // Datos de prueba para el SeguimientoRequestDto
        SeguimientoRequestDto seguimientoRequestDto = new SeguimientoRequestDto();
        seguimientoRequestDto.setTipoSegId(41);
        seguimientoRequestDto.setRucUsuario("1234567890");
        seguimientoRequestDto.setEscalaId(30);
        seguimientoRequestDto.setAcronimoDocumento("DMS");
        seguimientoRequestDto.setIndicadorEs("E");
        seguimientoRequestDto.setComentario("");
        seguimientoRequestDto.setEstado("S");

        String user = "testUser";

        // Ejecutar el método a probar
        createSeguimientoUseCase.create(seguimientoRequestDto, user);

        // Verificaciones
        verify(feignComunesCommandClientPort, times(1)).saveEscalaSeguimiento(seguimientoRequestDto, user);
    }
}
