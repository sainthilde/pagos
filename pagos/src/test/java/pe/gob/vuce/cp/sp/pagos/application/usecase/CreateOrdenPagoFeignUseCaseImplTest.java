package pe.gob.vuce.cp.sp.pagos.application.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.FeignRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.OrdenPagoRequestDTO;
import pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.response.OrdenPagoResponseDTO;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;

class CreateOrdenPagoFeignUseCaseImplTest {

    private FeignRepositoryPort feignRepositoryPort;
    private CreateOrdenPagoFeignUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        feignRepositoryPort = mock(FeignRepositoryPort.class);
        useCase = new CreateOrdenPagoFeignUseCaseImpl(feignRepositoryPort);
    }

    @Test
    void testCreateOrdenPago_success() {
        // Arrange
        OrdenPagoRequestDTO request = new OrdenPagoRequestDTO();

        OrdenPagoResponseDTO expectedResponse = new OrdenPagoResponseDTO();
        expectedResponse.setOrdenPagoId(1);
        expectedResponse.setMonto(100.0);
        expectedResponse.setEstado("CREADA");

        when(feignRepositoryPort.createOrdenPago(request)).thenReturn(expectedResponse);

        // Act
        OrdenPagoResponseDTO result = useCase.createOrdenPago(request);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getOrdenPagoId());
        assertEquals(100.0, result.getMonto());
        assertEquals("CREADA", result.getEstado());
        verify(feignRepositoryPort, times(1)).createOrdenPago(request);
    }

    @Test
    void testCreateOrdenPago_nullResponse() {
        // Arrange
        OrdenPagoRequestDTO request = new OrdenPagoRequestDTO();
        when(feignRepositoryPort.createOrdenPago(request)).thenReturn(null);

        // Act
        OrdenPagoResponseDTO result = useCase.createOrdenPago(request);

        // Assert
        assertNull(result);
        verify(feignRepositoryPort).createOrdenPago(request);
    }
}
