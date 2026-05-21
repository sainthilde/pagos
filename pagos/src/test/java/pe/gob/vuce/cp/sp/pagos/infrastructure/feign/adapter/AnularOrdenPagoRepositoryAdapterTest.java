package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.gob.vuce.cp.sp.pagos.domain.exception.OrdenPagoNotFoundException;
import pe.gob.vuce.cp.sp.pagos.domain.model.OrdenPago;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.CancelarOrdenPagoUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.CreateSeguimientoUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.OrdenPagoRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.OrdenPagoResponseDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.SeguimientoRequestDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.mapper.OrdenPagoMapper;
import pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.response.OrdenPagoResponseDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;

@ExtendWith(MockitoExtension.class)
class AnularOrdenPagoRepositoryAdapterTest {

    @Mock
    OrdenPagoRepositoryPort ordenPagoRepositoryPort;

    @Mock
    CancelarOrdenPagoUseCase cancelarOrdenPagoUseCase;

    @Mock
    CreateSeguimientoUseCase createSeguimientoUseCase;

    @Mock
    OrdenPagoMapper ordenPagoMapper;

    @InjectMocks
    AnularOrdenPagoRepositoryAdapter adapter;

    private final Integer ordenPagoId = 1;
    private final OrdenPago ordenPago = new OrdenPago();
    private final OrdenPagoResponseDTO responseDTO = new OrdenPagoResponseDTO();
    private final OrdenPagoResponseDto responseDtoLocal = new OrdenPagoResponseDto(
            1, 1, 1, 1, "123456789", "OP001", 100.0, "2025-01-01",
            "CPB001", "AN", null, null, null, null, 100.0, "2025-01-01",
            "2025-01-02", "Descripción procedimiento");

    @BeforeEach
    void setup() {
        ordenPago.setOrdenPagoId(ordenPagoId);
        ordenPago.setDocumentoId(1);
        ordenPago.setEscalaId(100);
        ordenPago.setRucAgente("123456789");
        ordenPago.setUsuidModAud("admin");
        ordenPago.setCpb("CPB001");
        ordenPago.setOrdenPagoInternaId(999);
        ordenPago.setEstado("PE");
    }

    @Test
    void testAnularOrdenPago_ok() {

        String user = "testUser";
        responseDTO.setCpb("CPB001");

        when(ordenPagoRepositoryPort.findById(ordenPagoId)).thenReturn(ordenPago);
        when(cancelarOrdenPagoUseCase.cancelarOrdenPago(ordenPago.getOrdenPagoInternaId())).thenReturn(responseDTO);

        OrdenPagoResponseDTO result = adapter.anularOrdenPago(ordenPagoId,user);

        assertNotNull(result);
        assertEquals("CPB001", result.getCpb());
        verify(createSeguimientoUseCase).create(any(SeguimientoRequestDto.class), eq(ordenPago.getUsuidModAud()));
    }

    @Test
    void testAnularOrdenPago_notFound() {
        String user = "testUser";
        when(ordenPagoRepositoryPort.findById(ordenPagoId)).thenReturn(null);

        OrdenPagoNotFoundException ex = assertThrows(OrdenPagoNotFoundException.class, () -> {
            adapter.anularOrdenPago(ordenPagoId,user);
        });

        assertEquals("No existe la orden de pago.", ex.getMessage());
    }

    @Test
    void testAnularOrdenPagoLocal_ok() {
        String user = "testUser";
        when(ordenPagoRepositoryPort.findById(ordenPagoId)).thenReturn(ordenPago); // first call
        when(ordenPagoRepositoryPort.findById(ordenPagoId)).thenReturn(ordenPago); // second call after update
        when(ordenPagoMapper.modelToDto(any())).thenReturn(responseDtoLocal);

        OrdenPagoResponseDto result = adapter.anularOrdenPagoLocal(ordenPagoId,user);

        assertNotNull(result);
        assertEquals("AN", ordenPago.getEstado());
        verify(ordenPagoRepositoryPort).update(ordenPago);
        verify(createSeguimientoUseCase).create(any(SeguimientoRequestDto.class), eq(ordenPago.getUsuidModAud()));
    }

    @Test
    void testAnularOrdenPagoLocal_notFound() {
        String user = "testUser";
        when(ordenPagoRepositoryPort.findById(ordenPagoId)).thenReturn(null);

        assertThrows(OrdenPagoNotFoundException.class, () -> {
            adapter.anularOrdenPagoLocal(ordenPagoId,user);
        });
    }
}

