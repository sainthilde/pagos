package pe.gob.vuce.cp.sp.pagos.application.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.AnularOrdenPagoRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.OrdenPagoResponseDto;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;


class AnularOrdenPagoLocalUseCaseImplTest {

    private AnularOrdenPagoRepositoryPort repositoryPort;
    private AnularOrdenPagoLocalUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        repositoryPort = mock(AnularOrdenPagoRepositoryPort.class);
        useCase = new AnularOrdenPagoLocalUseCaseImpl(repositoryPort);
    }

    @Test
    void testAnularOrdenPagoLocal_success() {
        // Arrange
        Integer ordenPagoId = 101;
        String user = "testUser";
        OrdenPagoResponseDto response = new OrdenPagoResponseDto(
                ordenPagoId,
                10,              // entidadId
                20,              // documentoId
                30,              // escalaId
                "12345678901",   // rucAgente
                "OP-2025-0001",  // codigoOrdenPago
                1500.50,         // monto
                "2025-05-18",    // fechaGeneracion
                "CPB001",        // cpb
                "ANULADA",       // estado
                "2025-06-01",    // fechaVigencia
                null,            // fechaPagado
                "2025-05-19",    // fechaAnulacionCpb
                null,            // fechaExtornoOrdenPago
                100.0,           // gpMonto
                "2025-05-18",    // fechaCreacionOrdenPago
                null,            // ppFechaConfGeneracionCpb
                "PROCEDIMIENTO A"// gpDescProcedimiento
        );

        when(repositoryPort.anularOrdenPagoLocal(ordenPagoId,user)).thenReturn(response);

        // Act
        OrdenPagoResponseDto result = useCase.anularOrdenPagoLocal(ordenPagoId,user);

        // Assert
        assertNotNull(result);
        assertEquals(ordenPagoId, result.ordenPagoId());
        assertEquals("ANULADA", result.estado());
        assertEquals("OP-2025-0001", result.codigoOrdenPago());
        verify(repositoryPort, times(1)).anularOrdenPagoLocal(ordenPagoId,user);
    }

    @Test
    void testAnularOrdenPagoLocal_nullResponse() {
        // Arrange
        Integer ordenPagoId = 999;
        String user = "testUser";
        when(repositoryPort.anularOrdenPagoLocal(ordenPagoId,user)).thenReturn(null);

        // Act
        OrdenPagoResponseDto result = useCase.anularOrdenPagoLocal(ordenPagoId,user);

        // Assert
        assertNull(result);
        verify(repositoryPort).anularOrdenPagoLocal(ordenPagoId,user);
    }
}
