package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.application.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants.ErrorCodes;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.exceptions.BusinessError;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.TramiteModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.UpdateTramiteUseCase;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.any;

public class UpdateTramitesUseCaseImplTest {


    @InjectMocks
    private UpdateTramitesUseCaseImpl updateTramitesUseCaseImpl;

    @Mock
    private UpdateTramiteUseCase updateTramiteUseCase;

    private TramiteModel tramiteModel;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        tramiteModel = new TramiteModel();
    }

    @Test
    public void testUpdateTramites_Success() {
        // Configuración del mock
        when(updateTramiteUseCase.update(any(TramiteModel.class), anyString(),anyString(), anyString())).thenReturn(tramiteModel);

        // Ejecución del método
        List<TramiteModel> tramites = List.of(tramiteModel);
        List<TramiteModel> result = updateTramitesUseCaseImpl.update(tramites, "12345678901", "user","OPERACION");

        // Verificación
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(updateTramiteUseCase, times(1)).update(any(TramiteModel.class), anyString(),anyString(), anyString());
    }

    @Test
    public void testUpdateTramites_ThrowsBusinessError() {
        // Configuración del mock para que lance una excepción
        when(updateTramiteUseCase.update(any(TramiteModel.class), anyString(),anyString(), anyString()))
                .thenThrow(new RuntimeException("Error al actualizar"));

        // Ejecución y verificación de la excepción
        BusinessError exception = assertThrows(BusinessError.class, () ->
                updateTramitesUseCaseImpl.update(List.of(tramiteModel), "12345678901", "User","OPERACION"));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getHttpStatus());
        assertEquals(ErrorCodes.INTERNAL_SERVER_ERROR, exception.getErrorCode());
        verify(updateTramiteUseCase, times(1)).update(any(TramiteModel.class), anyString(),anyString(), anyString());
    }

    @Test
    public void testUpdateTramites_MultipleTramites() {
        // Configuración del mock
        TramiteModel tramiteModel2 = new TramiteModel();
        when(updateTramiteUseCase.update(any(TramiteModel.class), anyString(),anyString(), anyString()))
                .thenReturn(tramiteModel, tramiteModel2);

        // Ejecución del método
        List<TramiteModel> tramites = List.of(tramiteModel, tramiteModel2);
        List<TramiteModel> result = updateTramitesUseCaseImpl.update(tramites, "12345678901","User", "OPERACION");

        // Verificación
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(updateTramiteUseCase, times(2)).update(any(TramiteModel.class), anyString(),anyString(), anyString());
    }


}
