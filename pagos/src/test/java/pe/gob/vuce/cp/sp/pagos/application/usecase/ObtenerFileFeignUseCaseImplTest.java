package pe.gob.vuce.cp.sp.pagos.application.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.FeignRepositoryPort;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;

class ObtenerFileFeignUseCaseImplTest {

    private FeignRepositoryPort feignRepositoryPort;
    private ObtenerFileFeignUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        feignRepositoryPort = mock(FeignRepositoryPort.class);
        useCase = new ObtenerFileFeignUseCaseImpl(feignRepositoryPort);
    }

    @Test
    void testObtenerFile_returnsResource() {
        // Arrange
        String documentoId = "doc123";
        Resource mockResource = mock(Resource.class);
        when(feignRepositoryPort.obtenerFile(documentoId)).thenReturn(mockResource);

        // Act
        Resource result = useCase.obtenerFile(documentoId);

        // Assert
        assertNotNull(result);
        assertEquals(mockResource, result);
        verify(feignRepositoryPort, times(1)).obtenerFile(documentoId);
    }

    @Test
    void testObtenerFile_returnsNull() {
        // Arrange
        String documentoId = "docNotFound";
        when(feignRepositoryPort.obtenerFile(documentoId)).thenReturn(null);

        // Act
        Resource result = useCase.obtenerFile(documentoId);

        // Assert
        assertNull(result);
        verify(feignRepositoryPort, times(1)).obtenerFile(documentoId);
    }
}
