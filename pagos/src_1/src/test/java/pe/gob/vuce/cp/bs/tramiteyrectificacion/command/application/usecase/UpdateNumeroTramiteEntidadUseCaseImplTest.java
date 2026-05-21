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
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.TramiteRepositoryPort;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UpdateNumeroTramiteEntidadUseCaseImplTest {

    @InjectMocks
    private UpdateNumeroTramiteEntidadUseCaseImpl useCase;

    @Mock
    private TramiteRepositoryPort tramiteRepositoryPort;

    private TramiteModel tramite;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tramite = new TramiteModel();
        tramite.setTramiteId(10);
        tramite.setEscalaId(100);
        tramite.setUsuidRegAud("USR-REG");
    }

    @Test
    void updateNumeroTramiteEntidad_success() {
        when(tramiteRepositoryPort.findByIdAndEscalaId(10,100)).thenReturn(Optional.of(tramite));
        when(tramiteRepositoryPort.update(any(TramiteModel.class))).thenAnswer(inv -> inv.getArgument(0));

        TramiteModel result = useCase.updateNumeroTramiteEntidad(10, 100, "EXP-123", "TUPA-1", Boolean.TRUE);

        assertNotNull(result);
        assertEquals("EXP-123", result.getNumeroTramiteEntidad());
        assertEquals("TUPA-1", result.getTupa());
        assertTrue(result.getIndAsignacionTramiteManual());
        assertEquals("USR-REG", result.getUsuidModAud(), "usuidModAud debe copiarse de usuidRegAud");
        verify(tramiteRepositoryPort).update(any(TramiteModel.class));
    }

    @Test
    void updateNumeroTramiteEntidad_notFound() {
        when(tramiteRepositoryPort.findByIdAndEscalaId(99,1)).thenReturn(Optional.empty());
        BusinessError ex = assertThrows(BusinessError.class, () -> useCase.updateNumeroTramiteEntidad(99, 1, "X", "T", false));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getHttpStatus());
        assertEquals(ErrorCodes.NOT_FOUND, ex.getErrorCode());
    }
}
