package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.OrdenDePagoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.pago.OrdenDePago;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

 class OrdenDePagoModelMapperTest {

    private OrdenDePagoModelMapper mapper;

    @BeforeEach
     void setup() {
        mapper = Mappers.getMapper(OrdenDePagoModelMapper.class);
    }

    @Test
    @DisplayName("toOrdenDePagoModel returns list of OrdenDePagoModel when list of OrdenDePago is passed")
     void toOrdenDePagoModelReturnsListWhenListOfOrdenDePagoIsPassed() {
        List<OrdenDePago> ordenDePagoList = Arrays.asList(new OrdenDePago(), new OrdenDePago());
        List<OrdenDePagoModel> ordenDePagoModels = mapper.toOrdenDePagoModel(ordenDePagoList);
        assertEquals(ordenDePagoList.size(), ordenDePagoModels.size());
    }

    @Test
    @DisplayName("toOrdenDePagoModel returns empty list when empty list of OrdenDePago is passed")
     void toOrdenDePagoModelReturnsEmptyListWhenEmptyListOfOrdenDePagoIsPassed() {
        List<OrdenDePago> ordenDePagoList = Arrays.asList();
        List<OrdenDePagoModel> ordenDePagoModels = mapper.toOrdenDePagoModel(ordenDePagoList);
        assertTrue(ordenDePagoModels.isEmpty());
    }
}