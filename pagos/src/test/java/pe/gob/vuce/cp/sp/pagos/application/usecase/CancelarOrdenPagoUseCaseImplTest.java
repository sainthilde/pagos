package pe.gob.vuce.cp.sp.pagos.application.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.CancelarOrdenPagoRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.response.OrdenPagoResponseDTO;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;

class CancelarOrdenPagoUseCaseImplTest {

    private CancelarOrdenPagoRepositoryPort cancelarOrdenPagoRepositoryPort;
    private CancelarOrdenPagoUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        cancelarOrdenPagoRepositoryPort = mock(CancelarOrdenPagoRepositoryPort.class);
        useCase = new CancelarOrdenPagoUseCaseImpl(cancelarOrdenPagoRepositoryPort);
    }

    @Test
    void testCancelarOrdenPago_success() {
        // Arrange
        Integer ordenPagoId = 123;
        OrdenPagoResponseDTO response = new OrdenPagoResponseDTO();
        response.setOrdenPagoId(ordenPagoId);
        response.setEstado("CANCELADA");

        when(cancelarOrdenPagoRepositoryPort.cancelarOrdenPago(ordenPagoId)).thenReturn(response);

        // Act
        OrdenPagoResponseDTO result = useCase.cancelarOrdenPago(ordenPagoId);

        // Assert
        assertNotNull(result);
        assertEquals(ordenPagoId, result.getOrdenPagoId());
        assertEquals("CANCELADA", result.getEstado());
        verify(cancelarOrdenPagoRepositoryPort, times(1)).cancelarOrdenPago(ordenPagoId);
    }

    @Test
    void testCancelarOrdenPago_nullResponse() {
        // Arrange
        Integer ordenPagoId = 999;
        when(cancelarOrdenPagoRepositoryPort.cancelarOrdenPago(ordenPagoId)).thenReturn(null);

        // Act
        OrdenPagoResponseDTO result = useCase.cancelarOrdenPago(ordenPagoId);

        // Assert
        assertNull(result);
        verify(cancelarOrdenPagoRepositoryPort).cancelarOrdenPago(ordenPagoId);
    }
}
