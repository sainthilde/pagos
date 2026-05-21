package pe.gob.vuce.cp.sp.pagos.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.CreateOrdenPagoSunatUseCase;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.OrdenPagoRequestDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.OrdenPagoResponseDto;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;

class PagoSunatServiceTest {

    private CreateOrdenPagoSunatUseCase createOrdenPagoSunatUseCase;
    private PagoSunatService pagoSunatService;

    @BeforeEach
    void setUp() {
        createOrdenPagoSunatUseCase = mock(CreateOrdenPagoSunatUseCase.class);
        pagoSunatService = new PagoSunatService(createOrdenPagoSunatUseCase);
    }

    @Test
    void testEjecutarSuccess() {
        OrdenPagoRequestDto requestDto = new OrdenPagoRequestDto();
        String user = "usuario.test";

        OrdenPagoResponseDto expectedResponse = new OrdenPagoResponseDto(
                1, 101, 202, 303, "12345678901", "OP-001",
                1000.0, "2025-05-18", "CPB123", "GENERADO",
                null, null, null, null, 1000.0, "2025-05-18", null, "Descripción procedimiento"
        );

        when(createOrdenPagoSunatUseCase.ejecutar(requestDto, user)).thenReturn(expectedResponse);

        OrdenPagoResponseDto actualResponse = pagoSunatService.ejecutar(requestDto, user);

        assertNotNull(actualResponse);
        assertEquals("OP-001", actualResponse.codigoOrdenPago());
        assertEquals(1000.0, actualResponse.monto());
        assertEquals("GENERADO", actualResponse.estado());
        assertEquals("12345678901", actualResponse.rucAgente());

        verify(createOrdenPagoSunatUseCase, times(1)).ejecutar(requestDto, user);
    }
}
