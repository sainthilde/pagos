package pe.gob.vuce.cp.sp.pagos.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pe.gob.vuce.cp.sp.pagos.domain.model.ActividadEntidad;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.ActividadEntidadUseCase;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

 class ActividadEntidadServiceTest {

    @Mock
    private ActividadEntidadUseCase actividadEntidadUseCase;

    @InjectMocks
    private ActividadEntidadService actividadEntidadService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByEntidadIdAndDocumentoIdAndPuertoDue() {
        Integer entidadId = 1;
        Integer actividadId = 2;
        String codPuertoNacional = "PUERTO123";
        ActividadEntidad actividadEntidad = new ActividadEntidad();
        Optional<ActividadEntidad> expectedResult = Optional.of(actividadEntidad);

        // Simulamos el comportamiento del mock
        when(actividadEntidadUseCase.findByEntidadIdAndDocumentoIdAndPuertoDue(entidadId, actividadId, codPuertoNacional))
                .thenReturn(expectedResult);

        // Llamamos al método del servicio que estamos probando
        Optional<ActividadEntidad> result = actividadEntidadService.findByEntidadIdAndDocumentoIdAndPuertoDue(entidadId, actividadId, codPuertoNacional);

        // Validamos que el resultado sea correcto
        assertTrue(result.isPresent());
        assertEquals(actividadEntidad, result.get());

        // Verificamos que el mock fue llamado correctamente
        verify(actividadEntidadUseCase, times(1))
                .findByEntidadIdAndDocumentoIdAndPuertoDue(entidadId, actividadId, codPuertoNacional);
    }

    @Test
    void testFindByEntidadIdAndDocumentoIdAndPuertoDue_NotFound() {
        Integer entidadId = 1;
        Integer actividadId = 2;
        String codPuertoNacional = "PUERTO123";

        // Simulamos un caso donde no se encuentra el resultado
        when(actividadEntidadUseCase.findByEntidadIdAndDocumentoIdAndPuertoDue(entidadId, actividadId, codPuertoNacional))
                .thenReturn(Optional.empty());

        // Llamamos al método del servicio que estamos probando
        Optional<ActividadEntidad> result = actividadEntidadService.findByEntidadIdAndDocumentoIdAndPuertoDue(entidadId, actividadId, codPuertoNacional);

        // Validamos que el resultado sea vacío
        assertFalse(result.isPresent());

        // Verificamos que el mock fue llamado correctamente
        verify(actividadEntidadUseCase, times(1))
                .findByEntidadIdAndDocumentoIdAndPuertoDue(entidadId, actividadId, codPuertoNacional);
    }
}