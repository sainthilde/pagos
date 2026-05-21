package pe.gob.vuce.cp.sp.pagos.application.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.AnularOrdenPagoLocalUseCase;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.OrdenPagoResponseDto;

class AnularOrdenPagoLocalServiceTest {

    private AnularOrdenPagoLocalUseCase anularOrdenPagoLocalUseCase;
    private AnularOrdenPagoLocalService service;

    @BeforeEach
    void setUp() {
        anularOrdenPagoLocalUseCase = Mockito.mock(AnularOrdenPagoLocalUseCase.class);
        service = new AnularOrdenPagoLocalService(anularOrdenPagoLocalUseCase);
    }

    @Test
    void testAnularOrdenPagoLocal_returnsExpectedDto() {
        Integer ordenPagoId = 123;
        String user = "testuser";
        OrdenPagoResponseDto expectedDto = new OrdenPagoResponseDto(
                ordenPagoId,
                1,
                2,
                3,
                "20123456789",
                "COD123",
                1500.0,
                "2025-05-18",
                "CPB-001",
                "ANULADO",
                "2025-05-18",
                "2025-05-19",
                "2025-05-20",
                null,
                1600.0,
                "2025-05-17",
                null,
                "Procedimiento X"
        );

        // Mock behavior
        when(anularOrdenPagoLocalUseCase.anularOrdenPagoLocal(ordenPagoId,user)).thenReturn(expectedDto);

        // Call service
        OrdenPagoResponseDto actualDto = service.anularOrdenPagoLocal(ordenPagoId,user);

        // Verify
        assertNotNull(actualDto);
        assertEquals(expectedDto, actualDto);

        // Verify the use case was called once with correct parameter
        verify(anularOrdenPagoLocalUseCase, times(1)).anularOrdenPagoLocal(ordenPagoId,user);
    }
}
