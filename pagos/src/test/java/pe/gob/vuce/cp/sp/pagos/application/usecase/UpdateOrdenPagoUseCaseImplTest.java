package pe.gob.vuce.cp.sp.pagos.application.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pe.gob.vuce.cp.sp.pagos.domain.model.OrdenPago;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.OrdenPagoRepositoryPort;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;

class UpdateOrdenPagoUseCaseImplTest {

    @Mock
    private OrdenPagoRepositoryPort ordenPagoRepositoryPort;

    @InjectMocks
    private UpdateOrdenPagoUseCaseImpl updateOrdenPagoUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateOrdenPago() {
        // Datos de prueba
        OrdenPago ordenPago = new OrdenPago();
        ordenPago.setOrdenPagoId(1);
        ordenPago.setEntidadId(123);
        ordenPago.setDocumentoId(456);

        // Simulación del comportamiento del repositorio
        when(ordenPagoRepositoryPort.update(any(OrdenPago.class))).thenReturn(ordenPago);

        // Llamar al método y verificar resultados
        OrdenPago resultado = updateOrdenPagoUseCase.updateOrdenPago(ordenPago);

        // Validar que el resultado no es nulo y contiene los mismos datos
        assertNotNull(resultado);
        assertEquals(ordenPago.getOrdenPagoId(), resultado.getOrdenPagoId());
        assertEquals(ordenPago.getEntidadId(), resultado.getEntidadId());
        assertEquals(ordenPago.getDocumentoId(), resultado.getDocumentoId());

        // Verificar que el repositorio fue invocado una vez con el modelo correcto
        verify(ordenPagoRepositoryPort, times(1)).update(any(OrdenPago.class));
    }
}

