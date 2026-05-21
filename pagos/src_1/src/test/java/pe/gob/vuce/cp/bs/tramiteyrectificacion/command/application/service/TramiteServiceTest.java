package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.TramiteModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.CreateTramiteUseCase;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.DesistTramiteUseCase;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.UpdateNumeroTramiteEntidadUseCase;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.UpdateTramiteUseCase;

public class TramiteServiceTest {

    @Mock
    private CreateTramiteUseCase createTramiteUseCase;

    @Mock
    private UpdateTramiteUseCase updateTramiteUseCase;

    @Mock
    private DesistTramiteUseCase desistTramiteUseCase;

    @Mock
    private UpdateNumeroTramiteEntidadUseCase updateNumeroTramiteEntidadUseCase;

    @InjectMocks
    private TramiteService tramiteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        // Datos de prueba
        TramiteModel tramiteModel = new TramiteModel();
        String ruc = "ruc";
        String user = "user";

        // Simular comportamiento del caso de uso
        when(createTramiteUseCase.create(tramiteModel, ruc, user)).thenReturn(tramiteModel);

        // Llamar al método a probar
        TramiteModel result = tramiteService.create(tramiteModel, ruc, user);

        // Verificar resultados
        assertEquals(tramiteModel, result);
        verify(createTramiteUseCase, times(1)).create(tramiteModel, ruc, user);
    }

    @Test
    void testUpdate() {
        // Datos de prueba
        TramiteModel tramiteModel = new TramiteModel();
        String ruc = "123456789";
        String operacion = "UPDATE";
        String user = "USER";
        // Simular comportamiento del caso de uso
        when(updateTramiteUseCase.update(tramiteModel, ruc, user, operacion)).thenReturn(tramiteModel);

        // Llamar al método a probar
        TramiteModel result = tramiteService.update(tramiteModel, ruc, user, operacion);

        // Verificar resultados
        assertEquals(tramiteModel, result);
        verify(updateTramiteUseCase, times(1)).update(tramiteModel, ruc, user, operacion);
    }

    @Test
    void testDesist() {
        // Datos de prueba
        String user = "testUser";
        Integer escalaId = 1;
        Integer tramiteId = 123;
        List<TramiteModel> expectedList = List.of(new TramiteModel());

        // Simular comportamiento del caso de uso
        when(desistTramiteUseCase.desist(escalaId, tramiteId, user)).thenReturn(expectedList);

        // Llamar al método a probar
        List<TramiteModel> result = tramiteService.desist(escalaId, tramiteId, user);

        // Verificar resultados
        assertEquals(expectedList, result);
        verify(desistTramiteUseCase, times(1)).desist(escalaId, tramiteId, user);
    }

}
