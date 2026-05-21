package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.application.usecase;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.DeclaracionJuradaModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.port.out.DeclaracionJuradaRepositoryPort;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.DeclaracionJuradaListaDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.GetDjjQueryParamsDto;

@SpringBootTest
class ObtenerDeclaracionJuradaUseCaseImplTest {

    @Mock
    private DeclaracionJuradaRepositoryPort declaracionJuradaRepositoryPort;

    @InjectMocks
    private ObtenerDeclaracionJuradaUseCaseImpl obtenerDeclaracionJuradaUseCaseImpl;

    @Test
    void returnDeclaracionJuradaListWhenFilterEscalaId() {
        // Arrange
        Integer escalaId = 1;

        when(declaracionJuradaRepositoryPort.findByEscala(anyInt())).thenReturn(mockDeclaracionJuradaModelList());

        // Act
        List<DeclaracionJuradaModel> result = obtenerDeclaracionJuradaUseCaseImpl.buscarDeclaracionesJuradas(escalaId);

        assertNotNull(result);
    }

    @Test
    void returnDeclaracionJuradaListWhenFilterEscalaIdAndDocumentiD() {
        // Arrange
        Integer escalaId = 1;
        String estado = "S";
        Integer documentId = 1;
        String rucAgente = "123456789";
        String estadoordenDePago = "PG";

    when(declaracionJuradaRepositoryPort.findDeclaracionJurada(
        anyInt(), anyString(), anyInt(), anyString(), anyString()))
                .thenReturn(mockDeclaracionJuradaModelList());

        // Act
        List<DeclaracionJuradaModel> result = obtenerDeclaracionJuradaUseCaseImpl.buscarDeclaracionesJuradas(
                escalaId, estado, documentId, estadoordenDePago, rucAgente);

        assertNotNull(result);
    }

    @Test
    void returnPageOfDeclaracionJuradaListaDtoWhenGetDjjs() {
        // Arrange
        GetDjjQueryParamsDto params = new GetDjjQueryParamsDto();
        Pageable pageable = PageRequest.of(0, 10);
        List<DeclaracionJuradaListaDto> lista = new ArrayList<>();
        lista.add(new DeclaracionJuradaListaDto());
        Page<DeclaracionJuradaListaDto> page = new PageImpl<>(lista, pageable, lista.size());

        when(declaracionJuradaRepositoryPort.getDjjs(any(GetDjjQueryParamsDto.class))).thenReturn(page);

        // Act
        Page<DeclaracionJuradaListaDto> result = obtenerDeclaracionJuradaUseCaseImpl.getDjjs(params);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    public DeclaracionJuradaModel mockDeclaracionJuradaModel() {
        DeclaracionJuradaModel declaracionJuradaModel = new DeclaracionJuradaModel();
        declaracionJuradaModel.setId(1);
        declaracionJuradaModel.setEstadoDeclaracionJurada("DE");
        declaracionJuradaModel.setNumeroDeclaracionJurada("45464554");

        return declaracionJuradaModel;

    }

    public List<DeclaracionJuradaModel> mockDeclaracionJuradaModelList() {
        List<DeclaracionJuradaModel> ordenPagoList = new ArrayList<>();
        ordenPagoList.add(mockDeclaracionJuradaModel());
        return ordenPagoList;

    }

}
