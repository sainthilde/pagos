package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.OrdenDePagoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.OrdenDePagoDto;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

 class OrdenDePagoDtoMapperTest {

    private OrdenDePagoDtoMapper mapper;

    @BeforeEach
     void setup() {
        mapper = Mappers.getMapper(OrdenDePagoDtoMapper.class);
    }

    @Test
    @DisplayName("doToDto returns list of OrdenDePagoDto when list of OrdenDePagoModel is passed")
     void doToDtoReturnsListWhenListOfOrdenDePagoModelIsPassed() {
        List<OrdenDePagoModel> ordenDePagoModels = Arrays.asList(new OrdenDePagoModel(), new OrdenDePagoModel());
        List<OrdenDePagoDto> ordenDePagoDtos = mapper.doToDto(ordenDePagoModels);
        assertEquals(ordenDePagoModels.size(), ordenDePagoDtos.size());
    }

    @Test
    @DisplayName("doToDto returns empty list when empty list of OrdenDePagoModel is passed")
     void doToDtoReturnsEmptyListWhenEmptyListOfOrdenDePagoModelIsPassed() {
        List<OrdenDePagoModel> ordenDePagoModels = Arrays.asList();
        List<OrdenDePagoDto> ordenDePagoDtos = mapper.doToDto(ordenDePagoModels);
        assertTrue(ordenDePagoDtos.isEmpty());
    }
}