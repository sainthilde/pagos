package pe.gob.vuce.cp.sp.pagos.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pe.gob.vuce.cp.sp.pagos.domain.model.OrdenPago;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.CreateOrdenPagoUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.SelectOrdenPagoUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.UpdateOrdenPagoUseCase;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

 class OrdenPagoServiceTest {

    @Mock
    private CreateOrdenPagoUseCase createOrdenPagoUseCase;

    @Mock
    private SelectOrdenPagoUseCase selectOrdenPagoUseCase;

    @Mock
    private UpdateOrdenPagoUseCase updateOrdenPagoUseCase;

    @InjectMocks
    private OrdenPagoService ordenPagoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrdenPago() {
        OrdenPago ordenPago = new OrdenPago();
        when(createOrdenPagoUseCase.createOrdenPago(ordenPago)).thenReturn(ordenPago);

        OrdenPago result = ordenPagoService.createOrdenPago(ordenPago);

        assertNotNull(result);
        verify(createOrdenPagoUseCase, times(1)).createOrdenPago(ordenPago);
    }

    @Test
    void testFindById() {
        Integer ordenPagoId = 1;
        OrdenPago ordenPago = new OrdenPago();
        when(selectOrdenPagoUseCase.findById(ordenPagoId)).thenReturn(ordenPago);

        OrdenPago result = ordenPagoService.findById(ordenPagoId);

        assertNotNull(result);
        verify(selectOrdenPagoUseCase, times(1)).findById(ordenPagoId);
    }

    @Test
    void testFindByEscalaIdAndDocumentoId() {
        Integer escalaId = 1;
        Integer documentId = 2;
        List<OrdenPago> ordenesPago = List.of(new OrdenPago());
        when(selectOrdenPagoUseCase.findByEscalaIdAndDocumentoId(escalaId, documentId)).thenReturn(ordenesPago);

        List<OrdenPago> result = ordenPagoService.findByEscalaIdAndDocumentoId(escalaId, documentId);

        assertEquals(ordenesPago, result);
        verify(selectOrdenPagoUseCase, times(1)).findByEscalaIdAndDocumentoId(escalaId, documentId);
    }

    @Test
    void testFindByPpIdOrdenPagoInterna() {
        Integer ordenPagoInterna = 123;
        OrdenPago ordenPago = new OrdenPago();
        when(selectOrdenPagoUseCase.findByPpIdOrdenPagoInterna(ordenPagoInterna)).thenReturn(ordenPago);

        OrdenPago result = ordenPagoService.findByPpIdOrdenPagoInterna(ordenPagoInterna);

        assertNotNull(result);
        verify(selectOrdenPagoUseCase, times(1)).findByPpIdOrdenPagoInterna(ordenPagoInterna);
    }

    @Test
    void testUpdateOrdenPago() {
        OrdenPago ordenPago = new OrdenPago();
        when(updateOrdenPagoUseCase.updateOrdenPago(ordenPago)).thenReturn(ordenPago);

        OrdenPago result = ordenPagoService.updateOrdenPago(ordenPago);

        assertNotNull(result);
        verify(updateOrdenPagoUseCase, times(1)).updateOrdenPago(ordenPago);
    }
}
