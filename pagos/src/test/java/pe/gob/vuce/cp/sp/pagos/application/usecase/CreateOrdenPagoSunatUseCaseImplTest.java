package pe.gob.vuce.cp.sp.pagos.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pe.gob.vuce.cp.sp.pagos.domain.port.out.OrdenPagoSunatRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.OrdenPagoRequestDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.OrdenPagoResponseDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.Tupa0ResponseDto;

class CreateOrdenPagoSunatUseCaseImplTest {

    private OrdenPagoSunatRepositoryPort ordenPagoSunatRepositoryPort;
    private CreateOrdenPagoSunatUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        ordenPagoSunatRepositoryPort = mock(OrdenPagoSunatRepositoryPort.class);
        useCase = new CreateOrdenPagoSunatUseCaseImpl(ordenPagoSunatRepositoryPort);
    }

    @Test
    void testEjecutar_returnsResponseDto() {
        // Arrange
        OrdenPagoRequestDto requestDto = new OrdenPagoRequestDto();
        String user = "usuarioTest";

        OrdenPagoResponseDto expectedResponse = new OrdenPagoResponseDto(
                1, 10, 20, 30, "12345678901", "CODIGO-001", 1500.0,
                "2025-05-18", "CPB123", "PENDIENTE", "2025-06-01",
                "2025-06-02", null, null, 100.0, "2025-05-18T10:00:00", null, "Procedimiento X"
        );

        when(ordenPagoSunatRepositoryPort.ejecutar(requestDto, user)).thenReturn(expectedResponse);

        // Act
        OrdenPagoResponseDto actualResponse = useCase.ejecutar(requestDto, user);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.ordenPagoId(), actualResponse.ordenPagoId());
        assertEquals(expectedResponse.codigoOrdenPago(), actualResponse.codigoOrdenPago());
        assertEquals(expectedResponse.monto(), actualResponse.monto());
        assertEquals(expectedResponse.estado(), actualResponse.estado());
        verify(ordenPagoSunatRepositoryPort, times(1)).ejecutar(requestDto, user);
    }


    @Test
    void testValidarTupa0_returnsResponseDto() {
    // Arrange
    OrdenPagoRequestDto requestDto = new OrdenPagoRequestDto();
    String user = "usuarioTest";
    String token = "token123";
    String tramite = "tramiteTest";
    String indicador = "IND";

    Tupa0ResponseDto expectedResponse = new Tupa0ResponseDto(
            true, "La tasa es 0, no se requiere pago"
    );

    when(ordenPagoSunatRepositoryPort.validarTupa0(
            requestDto, user, token, tramite, indicador))
            .thenReturn(expectedResponse);

    // Act
    Tupa0ResponseDto actualResponse = useCase.validarTupa0(
            requestDto, user, token, tramite, indicador);

    // Assert
    assertNotNull(actualResponse);
    assertEquals(expectedResponse, actualResponse); 
    // o valida campo por campo si no tiene equals()

    verify(ordenPagoSunatRepositoryPort, times(1))
            .validarTupa0(requestDto, user, token, tramite, indicador);
}
}
