package pe.gob.vuce.cp.sp.pagos.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.AnularOrdenPagoUseCase;
import pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.response.OrdenPagoResponseDTO;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

class AnularOrdenPagoServiceTest {

    private AnularOrdenPagoUseCase anularOrdenPagoUseCase;
    private AnularOrdenPagoService service;

    @BeforeEach
    void setUp() {
        anularOrdenPagoUseCase = Mockito.mock(AnularOrdenPagoUseCase.class);
        service = new AnularOrdenPagoService(anularOrdenPagoUseCase);
    }

    @Test
    void testAnularOrdenPago_success() {
        Integer ordenPagoId = 123;
        String user = "USER";
        OrdenPagoResponseDTO mockResponse = new OrdenPagoResponseDTO();
        mockResponse.setOrdenPagoId(ordenPagoId);
        mockResponse.setCodigoOrdenPago("OP-2025-001");
        mockResponse.setMonto(1500.75);
        mockResponse.setEstado("ANULADA");

        when(anularOrdenPagoUseCase.anularOrdenPago(ordenPagoId,user)).thenReturn(mockResponse);

        OrdenPagoResponseDTO result = service.anularOrdenPago(ordenPagoId,user);

        assertNotNull(result);
        assertEquals(ordenPagoId, result.getOrdenPagoId());
        assertEquals("OP-2025-001", result.getCodigoOrdenPago());
        assertEquals(1500.75, result.getMonto());
        assertEquals("ANULADA", result.getEstado());

        verify(anularOrdenPagoUseCase, times(1)).anularOrdenPago(ordenPagoId,user);
    }
}
