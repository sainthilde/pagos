package pe.gob.vuce.cp.sp.pagos.application.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pe.gob.vuce.cp.sp.pagos.domain.model.OrdenPago;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.CreateSeguimientoUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.OrdenPagoRepositoryPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;

class CreateOrdenPagoUseCaseImplTest {

    @Mock
    private OrdenPagoRepositoryPort ordenPagoRepositoryPort;

    @InjectMocks
    private CreateOrdenPagoUseCaseImpl createOrdenPagoUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    CreateSeguimientoUseCase createSeguimientoUseCase;

    @Test
    void testCreateOrdenPago() {
        // Datos de prueba para el OrdenPago
        OrdenPago ordenPago = new OrdenPago();
        ordenPago.setOrdenPagoId(1);
        ordenPago.setEntidadId(10);
        ordenPago.setDocumentoId(20);
        ordenPago.setEscalaId(30);
        ordenPago.setRucAgente("1234567890");
        ordenPago.setCodigoOrdenPago("ORD123");
        ordenPago.setMonto(1000.0);
        // (Otros atributos pueden ser configurados de manera similar)

        OrdenPago ordenPagoGuardado = new OrdenPago();
        ordenPagoGuardado.setOrdenPagoId(1);

        // Simulación del comportamiento del repositorio
        when(ordenPagoRepositoryPort.save(any(OrdenPago.class))).thenReturn(ordenPagoGuardado);

        // Ejecutar el método a probar
        OrdenPago resultado = createOrdenPagoUseCase.createOrdenPago(ordenPago);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals(ordenPagoGuardado.getOrdenPagoId(), resultado.getOrdenPagoId());
        verify(ordenPagoRepositoryPort, times(1)).save(any(OrdenPago.class));
    }
}

