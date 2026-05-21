package pe.gob.vuce.cp.sp.pagos.application.usecase;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.FeignRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.PaymentMethodResponse;

import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;

class ObtenerFormasPagoUseCaseImplTest {

    private FeignRepositoryPort feignRepositoryPort;
    private ObtenerFormasPagoUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        feignRepositoryPort = mock(FeignRepositoryPort.class);
        useCase = new ObtenerFormasPagoUseCaseImpl(feignRepositoryPort);
    }

    @Test
    void testGetPaymentMethods_returnsList() {
        // Arrange
        Integer canalId = 1;
        Integer entidadId = 10;

        PaymentMethodResponse.Banco banco = new PaymentMethodResponse.Banco();
        banco.setNombre("Banco XYZ");
        banco.setTooltip("Banco XYZ Tooltip");
        banco.setUrlImg("http://image.url/banco.png");
        banco.setOrden(1);

        PaymentMethodResponse paymentMethod = getPaymentMethodResponse(canalId, entidadId, banco);

        List<PaymentMethodResponse> mockedList = new ArrayList<>();
        mockedList.add(paymentMethod);

        when(feignRepositoryPort.getPaymentMethods(canalId, entidadId)).thenReturn(mockedList);

        // Act
        List<PaymentMethodResponse> result = useCase.getPaymentMethods(canalId, entidadId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Pago en línea", result.get(0).getTitulo());
        assertEquals("Banco XYZ", result.get(0).getListaBanco().get(0).getNombre());
        verify(feignRepositoryPort, times(1)).getPaymentMethods(canalId, entidadId);
    }

    @NotNull
    private static PaymentMethodResponse getPaymentMethodResponse(Integer canalId, Integer entidadId, PaymentMethodResponse.Banco banco) {
        PaymentMethodResponse.Cuenta cuenta = new PaymentMethodResponse.Cuenta();
        cuenta.setBanco("Banco XYZ");
        cuenta.setCuenta("123456789");

        PaymentMethodResponse.Instruccion instruccion = new PaymentMethodResponse.Instruccion();
        instruccion.setDescripcion("Instrucción 1");
        instruccion.setOrden(1);

        PaymentMethodResponse.Nota nota = new PaymentMethodResponse.Nota();
        nota.setDescripcion("Nota 1");
        nota.setOrden(1);

        PaymentMethodResponse paymentMethod = new PaymentMethodResponse();
        paymentMethod.setCanalId(canalId);
        paymentMethod.setEntidadId(entidadId);
        paymentMethod.setTitulo("Pago en línea");
        paymentMethod.setIconoTitulo("icono.png");
        paymentMethod.setOrden(1);
        paymentMethod.setListaBanco(List.of(banco));
        paymentMethod.setListaCuenta(List.of(cuenta));
        paymentMethod.setListaInstruccion(List.of(instruccion));
        paymentMethod.setListaNota(List.of(nota));
        return paymentMethod;
    }

    @Test
    void testGetPaymentMethods_returnsEmptyList() {
        // Arrange
        Integer canalId = 99;
        Integer entidadId = 99;

        when(feignRepositoryPort.getPaymentMethods(canalId, entidadId)).thenReturn(List.of());

        // Act
        List<PaymentMethodResponse> result = useCase.getPaymentMethods(canalId, entidadId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(feignRepositoryPort, times(1)).getPaymentMethods(canalId, entidadId);
    }
}
