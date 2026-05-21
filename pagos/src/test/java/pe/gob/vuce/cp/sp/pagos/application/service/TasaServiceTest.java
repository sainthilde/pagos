package pe.gob.vuce.cp.sp.pagos.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.CreateTasaSunatUseCase;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.TasaResponse;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;

class TasaServiceTest {

    private CreateTasaSunatUseCase createTasaSunatUseCase;
    private TasaService tasaService;

    @BeforeEach
    void setUp() {
        createTasaSunatUseCase = mock(CreateTasaSunatUseCase.class);
        tasaService = new TasaService(createTasaSunatUseCase);
    }

    @Test
    void testObtenerTasa() {
        // Arrange
        Integer entidadId = 1001;
        String idComponente = "COMP-001";
        String textSearch = "TASA-X";

        TasaResponse.Tasa tasaMock = new TasaResponse.Tasa();
        tasaMock.setProcedimientoId(1);
        tasaMock.setProcedimientoVersion(2);
        tasaMock.setProcedimientoTasaVersion(3);
        tasaMock.setSecuencia(1);
        tasaMock.setMonto(120.50);
        tasaMock.setEtiqueta("TASA GENERAL");
        tasaMock.setDescripcion("Tasa por trámite general");
        tasaMock.setCodigoMoneda("PEN");
        tasaMock.setMonedaDescripcion("Soles");
        tasaMock.setMonedaSigno("S/");

        when(createTasaSunatUseCase.obtenerTasa(entidadId, idComponente, textSearch))
                .thenReturn(tasaMock);

        // Act
        TasaResponse.Tasa result = tasaService.obtenerTasa(entidadId, idComponente, textSearch);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getProcedimientoId());
        assertEquals(120.50, result.getMonto());
        assertEquals("S/", result.getMonedaSigno());
        assertEquals("TASA GENERAL", result.getEtiqueta());

        verify(createTasaSunatUseCase, times(1)).obtenerTasa(entidadId, idComponente, textSearch);
    }
}
