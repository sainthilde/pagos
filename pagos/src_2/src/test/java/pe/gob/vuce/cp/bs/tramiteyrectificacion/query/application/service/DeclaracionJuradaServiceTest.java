package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.DeclaracionJuradaModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.port.in.ObtenerDeclaracionJuradaUseCase;

class DeclaracionJuradaServiceTest {

    @Mock
    private ObtenerDeclaracionJuradaUseCase obtenerDeclaracionJurada;

    @InjectMocks
    private DeclaracionJuradaService declaracionJuradaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarDeclaracionesJuradasPorId() {
        // Arrange
        Integer id = 1;
        DeclaracionJuradaModel declaracionJuradaModel = new DeclaracionJuradaModel();
        List<DeclaracionJuradaModel> expectedDeclaraciones = Collections.singletonList(declaracionJuradaModel);

        when(obtenerDeclaracionJurada.buscarDeclaracionesJuradas(id)).thenReturn(expectedDeclaraciones);

        // Act
        List<DeclaracionJuradaModel> actualDeclaraciones = declaracionJuradaService.buscarDeclaracionesJuradas(id);

        // Assert
        assertEquals(expectedDeclaraciones, actualDeclaraciones);
        verify(obtenerDeclaracionJurada).buscarDeclaracionesJuradas(id);
    }

    @Test
    void testBuscarDeclaracionesJuradasConParametros() {
        // Arrange
        Integer id = 1;
        String estado = "A";
        Integer documentoId = 2;
        String estadoDdjjPago = "P";
        String rucAgente = "123456789";
        DeclaracionJuradaModel declaracionJuradaModel = new DeclaracionJuradaModel();
        List<DeclaracionJuradaModel> expectedDeclaraciones = Collections.singletonList(declaracionJuradaModel);

        when(obtenerDeclaracionJurada.buscarDeclaracionesJuradas(id, estado, documentoId, estadoDdjjPago, rucAgente))
                .thenReturn(expectedDeclaraciones);

        // Act
        List<DeclaracionJuradaModel> actualDeclaraciones = declaracionJuradaService.buscarDeclaracionesJuradas(id, estado, documentoId, estadoDdjjPago, rucAgente);

        // Assert
        assertEquals(expectedDeclaraciones, actualDeclaraciones);
        verify(obtenerDeclaracionJurada).buscarDeclaracionesJuradas(id, estado, documentoId, estadoDdjjPago, rucAgente);
    }
}