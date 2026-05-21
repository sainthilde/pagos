package pe.gob.vuce.cp.sp.pagos.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import pe.gob.vuce.cp.sp.pagos.domain.model.OrdenPago;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.FeignPasarelaPort;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.OrdenPagoRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.PasarelaEstatusResponse;

class SelectOrdenPagoUseCaseImplTest {

    @Mock
    private OrdenPagoRepositoryPort ordenPagoRepositoryPort;

    @InjectMocks
    private SelectOrdenPagoUseCaseImpl selectOrdenPagoUseCase;

    @Mock
    private FeignPasarelaPort feignPasarelaPort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        // Datos de prueba
        Integer ordenPagoId = 1;
        OrdenPago ordenPago = new OrdenPago();
        ordenPago.setOrdenPagoId(ordenPagoId);

        // Simulación del comportamiento del repositorio
        when(ordenPagoRepositoryPort.findById(ordenPagoId)).thenReturn(ordenPago);

        // Llamar al método y verificar resultados
        OrdenPago resultado = selectOrdenPagoUseCase.findById(ordenPagoId);

        assertNotNull(resultado);
        assertEquals(ordenPagoId, resultado.getOrdenPagoId());
        verify(ordenPagoRepositoryPort, times(1)).findById(ordenPagoId);
    }

    @Test
    void testFindByEscalaIdAndDocumentoId() {
        // Datos de prueba
        Integer escalaId = 1;
        Integer documentoId = 2;
        List<OrdenPago> ordenesPago = List.of(new OrdenPago(), new OrdenPago());

        // Simulación del comportamiento del repositorio
        when(ordenPagoRepositoryPort.findByEscalaIdAndDocumentoId(escalaId, documentoId)).thenReturn(ordenesPago);

        // Llamar al método y verificar resultados
        List<OrdenPago> resultado = selectOrdenPagoUseCase.findByEscalaIdAndDocumentoId(escalaId, documentoId);

        assertNotNull(resultado);
        assertEquals(ordenesPago.size(), resultado.size());
        verify(ordenPagoRepositoryPort, times(1)).findByEscalaIdAndDocumentoId(escalaId, documentoId);
    }

    @Test
    void testFindByPpIdOrdenPagoInterna() {
        // Datos de prueba
        Integer ordenPagoInterna = 123;
        OrdenPago ordenPago = new OrdenPago();
        ordenPago.setOrdenPagoInternaId(ordenPagoInterna);

        // Simulación del comportamiento del repositorio
        when(ordenPagoRepositoryPort.findByPpIdOrdenPagoInterna(ordenPagoInterna)).thenReturn(ordenPago);

        // Llamar al método y verificar resultados
        OrdenPago resultado = selectOrdenPagoUseCase.findByPpIdOrdenPagoInterna(ordenPagoInterna);

        assertNotNull(resultado);
        assertEquals(ordenPagoInterna, resultado.getOrdenPagoInternaId());
        verify(ordenPagoRepositoryPort, times(1)).findByPpIdOrdenPagoInterna(ordenPagoInterna);
    }

    @Test
    void testFindByEscalaIdAndDocumentoId_NoProcesaSiNoEsPP() {
        OrdenPago op = new OrdenPago();
        op.setEstado("OTRO");

        when(ordenPagoRepositoryPort.findByEscalaIdAndDocumentoId(1, 2))
                .thenReturn(List.of(op));

        List<OrdenPago> result =
                selectOrdenPagoUseCase.findByEscalaIdAndDocumentoId(1, 2);

        assertEquals(1, result.size());
        verify(ordenPagoRepositoryPort, times(0)).update(any());
    }

    @Test
    void testFindByEscalaIdAndDocumentoId_ResponseNull() {
        OrdenPago op = new OrdenPago();
        op.setEstado("PP");
        op.setOrdenPagoInternaId(10);

        when(ordenPagoRepositoryPort.findByEscalaIdAndDocumentoId(1, 2))
                .thenReturn(List.of(op));

        when(feignPasarelaPort.obtenerEstatusOrdenPago(10))
                .thenReturn(null);

        selectOrdenPagoUseCase.findByEscalaIdAndDocumentoId(1, 2);

        verify(ordenPagoRepositoryPort, times(0)).update(any());
    }

    @Test
    void testFindByEscalaIdAndDocumentoId_Pagado() {
        OrdenPago op = new OrdenPago();
        op.setEstado("PP");
        op.setOrdenPagoInternaId(10);

        PasarelaEstatusResponse response = new PasarelaEstatusResponse();
        response.setEstado("PAGADO");
        response.setFechaOperacion("20240101 10:00:00");

        when(ordenPagoRepositoryPort.findByEscalaIdAndDocumentoId(1, 2))
                .thenReturn(List.of(op));

        when(feignPasarelaPort.obtenerEstatusOrdenPago(10))
                .thenReturn(response);

        selectOrdenPagoUseCase.findByEscalaIdAndDocumentoId(1, 2);

        assertEquals("PG", op.getEstado());
        assertNotNull(op.getFechaPagado());
        verify(ordenPagoRepositoryPort, times(1)).update(op);
    }

    @Test
    void testFindByEscalaIdAndDocumentoId_Anulado() {
        OrdenPago op = new OrdenPago();
        op.setEstado("PP");
        op.setOrdenPagoInternaId(10);

        PasarelaEstatusResponse response = new PasarelaEstatusResponse();
        response.setEstado("ANULADO");
        response.setFechaOperacion("20240101 10:00:00");

        when(ordenPagoRepositoryPort.findByEscalaIdAndDocumentoId(1, 2))
                .thenReturn(List.of(op));

        when(feignPasarelaPort.obtenerEstatusOrdenPago(10))
                .thenReturn(response);

        selectOrdenPagoUseCase.findByEscalaIdAndDocumentoId(1, 2);

        assertEquals("AN", op.getEstado());
        verify(ordenPagoRepositoryPort, times(1)).update(op);
    }

    @Test
    void testFindByEscalaIdAndDocumentoId_Extornado() {
        OrdenPago op = new OrdenPago();
        op.setEstado("PP");
        op.setOrdenPagoInternaId(10);

        PasarelaEstatusResponse response = new PasarelaEstatusResponse();
        response.setEstado("EXTORNADO");
        response.setFechaOperacion("20240101 10:00:00");

        when(ordenPagoRepositoryPort.findByEscalaIdAndDocumentoId(1, 2))
                .thenReturn(List.of(op));

        when(feignPasarelaPort.obtenerEstatusOrdenPago(10))
                .thenReturn(response);

        selectOrdenPagoUseCase.findByEscalaIdAndDocumentoId(1, 2);

        assertEquals("EX", op.getEstado());
        verify(ordenPagoRepositoryPort, times(1)).update(op);
    }


}
