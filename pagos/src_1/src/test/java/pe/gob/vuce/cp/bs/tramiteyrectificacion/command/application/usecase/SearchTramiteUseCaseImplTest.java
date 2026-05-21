package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.application.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.TramiteModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.TramiteRepositoryPort;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

public class SearchTramiteUseCaseImplTest {

    private TramiteRepositoryPort tramiteRepositoryPort;
    private SearchTramiteUseCaseImpl searchTramiteUseCase;

    @BeforeEach
    void setUp() {
        tramiteRepositoryPort = mock(TramiteRepositoryPort.class);
        searchTramiteUseCase = new SearchTramiteUseCaseImpl(tramiteRepositoryPort);
    }

    @Test
    void testFindByEscalaId_ReturnsListOfTramites() {
        // Arrange
        Integer escalaId = 1;

        TramiteModel tramite1 = new TramiteModel();
        tramite1.setTramiteId(100);
        tramite1.setEscalaId(escalaId);
        tramite1.setNumeroSuce("SUCE-001");
        tramite1.setFechaTramite(LocalDateTime.now());

        TramiteModel tramite2 = new TramiteModel();
        tramite2.setTramiteId(101);
        tramite2.setEscalaId(escalaId);
        tramite2.setNumeroSuce("SUCE-002");
        tramite2.setFechaTramite(LocalDateTime.now());

        List<TramiteModel> expectedList = List.of(tramite1, tramite2);

        when(tramiteRepositoryPort.findByEscalaId(escalaId)).thenReturn(expectedList);

        // Act
        List<TramiteModel> result = searchTramiteUseCase.findByEscalaId(escalaId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("SUCE-001", result.get(0).getNumeroSuce());
        assertEquals("SUCE-002", result.get(1).getNumeroSuce());

        verify(tramiteRepositoryPort, times(1)).findByEscalaId(escalaId);
    }
}

