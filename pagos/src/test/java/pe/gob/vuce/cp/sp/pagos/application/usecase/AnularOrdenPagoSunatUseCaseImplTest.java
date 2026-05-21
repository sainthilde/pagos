package pe.gob.vuce.cp.sp.pagos.application.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.AnularOrdenPagoRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.response.OrdenPagoResponseDTO;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;


class AnularOrdenPagoSunatUseCaseImplTest {

    private AnularOrdenPagoRepositoryPort repositoryPort;
    private AnularOrdenPagoSunatUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        repositoryPort = mock(AnularOrdenPagoRepositoryPort.class);
        useCase = new AnularOrdenPagoSunatUseCaseImpl(repositoryPort);
    }

    @Test
    void testAnularOrdenPago_success() {
        // Arrange
        Integer ordenPagoId = 123;
        String user = "USER";
        OrdenPagoResponseDTO expectedResponse = new OrdenPagoResponseDTO();
        expectedResponse.setOrdenPagoId(ordenPagoId);
        expectedResponse.setEstado("ANULADA");

        when(repositoryPort.anularOrdenPago(ordenPagoId,user)).thenReturn(expectedResponse);

        // Act
        OrdenPagoResponseDTO result = useCase.anularOrdenPago(ordenPagoId,user);

        // Assert
        assertNotNull(result);
        assertEquals("ANULADA", result.getEstado());
        assertEquals(ordenPagoId, result.getOrdenPagoId());
        verify(repositoryPort, times(1)).anularOrdenPago(ordenPagoId,user);
    }

    @Test
    void testAnularOrdenPago_nullResponse() {
        // Arrange
        Integer ordenPagoId = 999;
        String user = "USER";
        when(repositoryPort.anularOrdenPago(ordenPagoId,user)).thenReturn(null);

        // Act
        OrdenPagoResponseDTO result = useCase.anularOrdenPago(ordenPagoId,user);

        // Assert
        assertNull(result);
        verify(repositoryPort).anularOrdenPago(ordenPagoId,user);
    }
}
