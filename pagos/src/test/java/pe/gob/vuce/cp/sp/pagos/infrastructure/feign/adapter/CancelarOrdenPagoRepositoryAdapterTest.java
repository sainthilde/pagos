package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.adapter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.OrdenPagoSunatClient;
import pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.response.OrdenPagoResponseDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CancelarOrdenPagoRepositoryAdapterTest {

    @Mock
    private OrdenPagoSunatClient ordenPagoSunatClient;

    @InjectMocks
    private CancelarOrdenPagoRepositoryAdapter repositoryAdapter;

    @Test
    void testCancelarOrdenPago() {
        // Arrange
        Integer ordenPagoId = 123;
        OrdenPagoResponseDTO mockResponse = new OrdenPagoResponseDTO();
        mockResponse.setOrdenPagoId(ordenPagoId);
        mockResponse.setCodigoOrdenPago("OP-123");
        mockResponse.setMonto(100.50);
        mockResponse.setEstado("Cancelada");

        when(ordenPagoSunatClient.cancelarOrdenPago(ordenPagoId)).thenReturn(mockResponse);

        // Act
        OrdenPagoResponseDTO result = repositoryAdapter.cancelarOrdenPago(ordenPagoId);

        // Assert
        assertNotNull(result);
        assertEquals(ordenPagoId, result.getOrdenPagoId());
        assertEquals("OP-123", result.getCodigoOrdenPago());
        assertEquals(100.50, result.getMonto());
        assertEquals("Cancelada", result.getEstado());

        verify(ordenPagoSunatClient).cancelarOrdenPago(ordenPagoId);
    }
}
