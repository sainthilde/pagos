package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.OrdenDePagoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.mae.Entidad;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.pago.OrdenDePago;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.mapper.OrdenDePagoModelMapper;

@SpringBootTest
class JPAOrdenPagoRepositoryAdapterTest {

    @Mock
    private JPAOrdenDePagoRepository jpaOrdenDePagoRepository;

    @Mock
    private OrdenDePagoModelMapper ordenDePagoModelMapper;

    @InjectMocks
    private JPAOrdenDePagoRepositoryAdapter jpaOrdenDePagoRepositoryAdapter;

    @Test
    void returnOrdenPagoListWhenEstadoOrdenPagoIsNotNull() {
        // Arrange
        Integer escalaId = 1;
        Integer documentId = 1;
        String rucAgente = "123456789";
        String estadoordenDePago = "PG";

        when(jpaOrdenDePagoRepository.findAllByEscalaIdAndEstadoOrdenPago(anyInt(), anyString()))
                .thenReturn(mockOrdenPagoList());

        when(ordenDePagoModelMapper.toOrdenDePagoModel(anyList())).thenReturn(mockOrdenPagoModelList());

        // Act
        List<OrdenDePagoModel> result = jpaOrdenDePagoRepositoryAdapter.findOrdenesDePago(escalaId, documentId,
                rucAgente, estadoordenDePago);

        assertNotNull(result);
    }

    @Test
    void returnOrdenPagoListWhenEstadoOrdenPagoIsNull() {
        // Arrange
        Integer escalaId = 1;
        Integer documentId = 1;
        String rucAgente = "123456789";
        String estadoordenDePago = null;

        when(jpaOrdenDePagoRepository.findByEscalaIdAndDocumentoIdAndRucAgente(anyInt(), anyInt(), anyString()))
                .thenReturn(mockOrdenPagoList());

        when(ordenDePagoModelMapper.toOrdenDePagoModel(anyList())).thenReturn(mockOrdenPagoModelList());

        // Act
        List<OrdenDePagoModel> result = jpaOrdenDePagoRepositoryAdapter.findOrdenesDePago(escalaId, documentId,
                rucAgente, estadoordenDePago);

        assertNotNull(result);
    }

    @Test
    void returnOrdenPagoListWhenEscalaIdIsNull() {
        // Arrange
        Integer escalaId = null;
        Integer documentId = 1;
        String rucAgente = "123456789";
        String estadoordenDePago = "active";

        when(jpaOrdenDePagoRepository.findByEscalaIdAndDocumentoIdAndRucAgente(anyInt(), anyInt(), anyString()))
                .thenReturn(mockOrdenPagoList());

        when(ordenDePagoModelMapper.toOrdenDePagoModel(anyList())).thenReturn(mockOrdenPagoModelList());

        // Act
        List<OrdenDePagoModel> result = jpaOrdenDePagoRepositoryAdapter.findOrdenesDePago(escalaId, documentId,
                rucAgente, estadoordenDePago);

        assertNotNull(result);
    }

    public OrdenDePago mockOrdenPago() {

        OrdenDePago ordenDePagoEn = new OrdenDePago();
        ordenDePagoEn.setId(1);
        Entidad entidad = new Entidad();
        entidad.setId(1);
        ordenDePagoEn.setEntidad(entidad);
        ordenDePagoEn.setEstadoOrdenPago("PG");
        return ordenDePagoEn;
    }

    public OrdenDePagoModel mockOrdenPagoModel() {

        OrdenDePagoModel ordenDePagoEn = new OrdenDePagoModel();
        ordenDePagoEn.setId(1);
        ordenDePagoEn.setDocumentoId(1);
        ordenDePagoEn.setEscalaId(1);
        ordenDePagoEn.setRucAgente("10457726606");
        ordenDePagoEn.setEstadoOrdenPago("PG");
        return ordenDePagoEn;
    }

    public List<OrdenDePago> mockOrdenPagoList() {
        List<OrdenDePago> ordenPagoList = new ArrayList<>();
        ordenPagoList.add(mockOrdenPago());
        return ordenPagoList;

    }

    public List<OrdenDePagoModel> mockOrdenPagoModelList() {
        List<OrdenDePagoModel> ordenPagoList = new ArrayList<>();
        ordenPagoList.add(mockOrdenPagoModel());
        return ordenPagoList;

    }

}
