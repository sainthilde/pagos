package pe.gob.vuce.cp.sp.pagos.application.service;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.ObtenerFormasPagoUseCase;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.PaymentMethodResponse;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;

class FormaPagoServiceTest {

    private ObtenerFormasPagoUseCase obtenerFormasPagoUseCase;
    private FormaPagoService formaPagoService;

    @BeforeEach
    void setUp() {
        obtenerFormasPagoUseCase = mock(ObtenerFormasPagoUseCase.class);
        formaPagoService = new FormaPagoService(obtenerFormasPagoUseCase);
    }

    @Test
    void testGetPaymentMethods() {
        // Arrange
        Integer canalId = 1;
        Integer entidadId = 10;

        PaymentMethodResponse response = getPaymentMethodResponse(canalId, entidadId);

        when(obtenerFormasPagoUseCase.getPaymentMethods(canalId, entidadId)).thenReturn(List.of(response));

        // Act
        List<PaymentMethodResponse> result = formaPagoService.getPaymentMethods(canalId, entidadId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Transferencia Bancaria", result.get(0).getTitulo());
        assertEquals(1, result.get(0).getListaInstruccion().size());
        assertEquals("Ir al banco", result.get(0).getListaInstruccion().get(0).getDescripcion());

        verify(obtenerFormasPagoUseCase, times(1)).getPaymentMethods(canalId, entidadId);
    }

    @NotNull
    private static PaymentMethodResponse getPaymentMethodResponse(Integer canalId, Integer entidadId) {
        PaymentMethodResponse response = new PaymentMethodResponse();
        response.setCanalId(canalId);
        response.setEntidadId(entidadId);
        response.setTitulo("Transferencia Bancaria");
        response.setIconoTitulo("icon.png");
        response.setOrden(1);

        PaymentMethodResponse.Instruccion instruccion = new PaymentMethodResponse.Instruccion();
        instruccion.setDescripcion("Ir al banco");
        instruccion.setOrden(1);

        response.setListaInstruccion(List.of(instruccion));
        response.setListaNota(List.of());
        response.setListaCuenta(List.of());
        response.setListaBanco(List.of());
        return response;
    }
}
