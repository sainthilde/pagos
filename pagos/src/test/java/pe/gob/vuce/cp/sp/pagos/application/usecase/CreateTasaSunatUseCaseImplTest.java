package pe.gob.vuce.cp.sp.pagos.application.usecase;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.TasaSunatRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.TasaResponse;

class CreateTasaSunatUseCaseImplTest {

    private TasaSunatRepositoryPort tasaSunatRepositoryPort;
    private CreateTasaSunatUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        tasaSunatRepositoryPort = mock(TasaSunatRepositoryPort.class);
        useCase = new CreateTasaSunatUseCaseImpl(tasaSunatRepositoryPort);
    }

    @Test
    void testObtenerTasa_returnsTasa() {
        // Arrange
        Integer entidadId = 123;
        String idComponente = "comp1";
        String textSearch = "searchText";

        TasaResponse.Tasa tasa = new TasaResponse.Tasa();
        tasa.setProcedimientoId(1);
        tasa.setProcedimientoVersion(2);
        tasa.setProcedimientoTasaVersion(3);
        tasa.setSecuencia(1);
        tasa.setMonto(150.50);
        tasa.setEtiqueta("Etiqueta");
        tasa.setDescripcion("Descripcion");
        tasa.setCodigoMoneda("PEN");
        tasa.setMonedaDescripcion("Soles");
        tasa.setMonedaSigno("S/");

        when(tasaSunatRepositoryPort.obtenerTasa(entidadId, idComponente, textSearch)).thenReturn(tasa);

        // Act
        TasaResponse.Tasa result = useCase.obtenerTasa(entidadId, idComponente, textSearch);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getProcedimientoId());
        assertEquals(150.50, result.getMonto());
        assertEquals("Soles", result.getMonedaDescripcion());
        verify(tasaSunatRepositoryPort, times(1)).obtenerTasa(entidadId, idComponente, textSearch);
    }
}
