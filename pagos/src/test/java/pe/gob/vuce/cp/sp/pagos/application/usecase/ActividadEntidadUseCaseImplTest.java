package pe.gob.vuce.cp.sp.pagos.application.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pe.gob.vuce.cp.sp.pagos.domain.model.ActividadEntidad;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.ActividadEntidadRepositoryPort;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

 class ActividadEntidadUseCaseImplTest {

    @Mock
    private ActividadEntidadRepositoryPort actividadEntidadRepositoryPort;

    @InjectMocks
    private ActividadEntidadUseCaseImpl actividadEntidadUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByEntidadIdAndDocumentoIdAndPuertoDue_Success() {
        Integer actividadEntidadId = 1;
        Integer actividadId = 2;
        String codPuertoNacional = "PUERTO123";
        ActividadEntidad actividadEntidad = new ActividadEntidad();
        Optional<ActividadEntidad> expectedResult = Optional.of(actividadEntidad);

        // Simulación del comportamiento del repositorio
        when(actividadEntidadRepositoryPort.findByEntidadIdAndDocumentoIdAndPuertoDue(actividadEntidadId, actividadId, codPuertoNacional))
                .thenReturn(expectedResult);

        // Llamada al método bajo prueba
        Optional<ActividadEntidad> result = actividadEntidadUseCase.findByEntidadIdAndDocumentoIdAndPuertoDue(actividadEntidadId, actividadId, codPuertoNacional);

        // Verificaciones
        assertTrue(result.isPresent());
        assertEquals(actividadEntidad, result.get());

        // Verificación de la interacción con el mock
        verify(actividadEntidadRepositoryPort, times(1))
                .findByEntidadIdAndDocumentoIdAndPuertoDue(actividadEntidadId, actividadId, codPuertoNacional);
    }

    @Test
    void testFindByEntidadIdAndDocumentoIdAndPuertoDue_NotFound() {
        Integer actividadEntidadId = 1;
        Integer actividadId = 2;
        String codPuertoNacional = "PUERTO123";

        // Simulamos un caso donde no se encuentran resultados
        when(actividadEntidadRepositoryPort.findByEntidadIdAndDocumentoIdAndPuertoDue(actividadEntidadId, actividadId, codPuertoNacional))
                .thenReturn(Optional.empty());

        // Llamada al método bajo prueba
        Optional<ActividadEntidad> result = actividadEntidadUseCase.findByEntidadIdAndDocumentoIdAndPuertoDue(actividadEntidadId, actividadId, codPuertoNacional);

        // Verificaciones
        assertFalse(result.isPresent());

        // Verificación de la interacción con el mock
        verify(actividadEntidadRepositoryPort, times(1))
                .findByEntidadIdAndDocumentoIdAndPuertoDue(actividadEntidadId, actividadId, codPuertoNacional);
    }
}
