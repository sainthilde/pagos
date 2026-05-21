package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.OrdenDePagoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.mapper.OrdenPagoMapper;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity.OrdenDePago;

public class JpaOrdenPagoRepositoryAdapterTest {

    @InjectMocks
    private JpaOrdenPagoRepositoryAdapter jpaOrdenPagoRepositoryAdapter;

    @Mock
    private OrderPagoRepository orderPagoRepository;

    @Mock
    private OrdenPagoMapper ordenPagoMapper;

    private OrdenDePago ordenDePagoEntity;
    private OrdenDePagoModel ordenDePagoModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Initialize a dummy entity and model with an ID, etc.
        ordenDePagoEntity = new OrdenDePago();
        ordenDePagoEntity.setId(1);
        // (Optionally, set additional properties on the entity here)

        ordenDePagoModel = new OrdenDePagoModel();
        ordenDePagoModel.setId(1);
        // (Optionally, set additional properties on the model here)
    }

    @Test
    void testFindById() {
        Integer id = 1;
        when(orderPagoRepository.findById(id)).thenReturn(Optional.of(ordenDePagoEntity));
        when(ordenPagoMapper.entityToModel(ordenDePagoEntity)).thenReturn(ordenDePagoModel);

        Optional<OrdenDePagoModel> result = jpaOrdenPagoRepositoryAdapter.findById(id);

        assertTrue(result.isPresent());
        assertEquals(ordenDePagoModel, result.get());
    }

    @Test
    void testSave() {
        // Stub conversion from model to entity and back
        when(ordenPagoMapper.modelToEntity(ordenDePagoModel)).thenReturn(ordenDePagoEntity);
        when(orderPagoRepository.save(ordenDePagoEntity)).thenReturn(ordenDePagoEntity);
        when(ordenPagoMapper.entityToModel(ordenDePagoEntity)).thenReturn(ordenDePagoModel);

        OrdenDePagoModel result = jpaOrdenPagoRepositoryAdapter.save(ordenDePagoModel);

        assertEquals(ordenDePagoModel, result);
    }

    @Test
    void testUpdateV2_whenEntityIsPresent() {
        // Set a value that will be updated on the entity
        ordenDePagoModel.setCancelarDestinoDelPago("someValue");

        // Simulate that an entity with this ID already exists
        when(orderPagoRepository.findById(anyInt())).thenReturn(Optional.of(ordenDePagoEntity));
        // When updating, the entity gets its cancelarDestinoDelPago updated
        ordenDePagoEntity.setCancelarDestinoDelPago("someValue");
        when(orderPagoRepository.save(ordenDePagoEntity)).thenReturn(ordenDePagoEntity);
        when(ordenPagoMapper.entityToModel(ordenDePagoEntity)).thenReturn(ordenDePagoModel);

        OrdenDePagoModel result = jpaOrdenPagoRepositoryAdapter.updateV2(ordenDePagoModel);

        assertEquals(ordenDePagoModel.getId(), result.getId());
        assertEquals("someValue", result.getCancelarDestinoDelPago());
    }

    @Test
    void testUpdateV2_whenEntityIsNotPresent() {
        // Simulate that no entity exists with the given ID
        when(orderPagoRepository.findById(anyInt())).thenReturn(Optional.empty());
        when(ordenPagoMapper.modelToEntity(ordenDePagoModel)).thenReturn(ordenDePagoEntity);
        when(orderPagoRepository.save(ordenDePagoEntity)).thenReturn(ordenDePagoEntity);
        when(ordenPagoMapper.entityToModel(ordenDePagoEntity)).thenReturn(ordenDePagoModel);

        OrdenDePagoModel result = jpaOrdenPagoRepositoryAdapter.updateV2(ordenDePagoModel);

        assertEquals(ordenDePagoModel.getId(), result.getId());
    }

    @Test
    void testFindByTramiteTramiteId() {
        Integer tramiteId = 1;
        List<OrdenDePago> entityList = new ArrayList<>();
        entityList.add(ordenDePagoEntity);
        List<OrdenDePagoModel> modelList = new ArrayList<>();
        modelList.add(ordenDePagoModel);

        when(orderPagoRepository.findByTramiteTramiteId(tramiteId)).thenReturn(entityList);
        when(ordenPagoMapper.entityListToModelList(entityList)).thenReturn(modelList);

        List<OrdenDePagoModel> result = jpaOrdenPagoRepositoryAdapter.findByTramiteTramiteId(tramiteId);
        assertEquals(modelList, result);
    }

    @Test
    void testFindAllByTramiteTramiteIdAndEstadoOrdenPagoIn() {
        Integer tramiteId = 1;
        List<String> estadoOrdenPagos = List.of("CR");
        List<OrdenDePago> entityList = new ArrayList<>();
        entityList.add(ordenDePagoEntity);
        List<OrdenDePagoModel> modelList = new ArrayList<>();
        modelList.add(ordenDePagoModel);

        when(orderPagoRepository.findAllByTramiteTramiteIdAndEstadoOrdenPagoIn(tramiteId, estadoOrdenPagos))
                .thenReturn(entityList);
        when(ordenPagoMapper.entityListToModelList(entityList)).thenReturn(modelList);

        List<OrdenDePagoModel> result = jpaOrdenPagoRepositoryAdapter
                .findAllByTramiteTramiteIdAndEstadoOrdenPagoIn(tramiteId, estadoOrdenPagos);
        assertEquals(modelList, result);
    }

    @Test
    void testFindAllByEscalaIdAndEstadoOrdenPagoIn() {
        Integer escalaId = 1;
        List<String> estadoOrdenPagos = List.of("CR");
        List<OrdenDePago> entityList = new ArrayList<>();
        entityList.add(ordenDePagoEntity);
        List<OrdenDePagoModel> modelList = new ArrayList<>();
        modelList.add(ordenDePagoModel);

        when(orderPagoRepository.findAllByEscalaIdAndEstadoOrdenPagoIn(escalaId, estadoOrdenPagos))
                .thenReturn(entityList);
        when(ordenPagoMapper.entityListToModelList(entityList)).thenReturn(modelList);

        List<OrdenDePagoModel> result = jpaOrdenPagoRepositoryAdapter
                .findAllByEscalaIdAndEstadoOrdenPagoIn(escalaId, estadoOrdenPagos);
        assertEquals(modelList, result);
    }

    @Test
    void testFindByEscalaId() {
        Integer escalaId = 1;
        List<OrdenDePago> entityList = new ArrayList<>();
        entityList.add(ordenDePagoEntity);
        List<OrdenDePagoModel> modelList = new ArrayList<>();
        modelList.add(ordenDePagoModel);

        when(orderPagoRepository.findByEscalaId(escalaId)).thenReturn(entityList);
        when(ordenPagoMapper.entityListToModelList(entityList)).thenReturn(modelList);

        List<OrdenDePagoModel> result = jpaOrdenPagoRepositoryAdapter.findByEscalaId(escalaId);
        assertEquals(modelList, result);
    }

    @Test
    void testFindByDocumentoIdAndEscalaIdAndRucAgente() {
        Integer documentoId = 1;
        Integer escalaId = 1;
        String rucAgente = "12345678901";
        String estado = "S";
        List<OrdenDePago> entityList = new ArrayList<>();
        entityList.add(ordenDePagoEntity);
        List<OrdenDePagoModel> modelList = new ArrayList<>();
        modelList.add(ordenDePagoModel);

        when(orderPagoRepository.findByDocumentoIdAndEscalaIdAndRucAgenteAndEstado(documentoId, escalaId, rucAgente,estado))
                .thenReturn(entityList);
        when(ordenPagoMapper.entityListToModelList(entityList)).thenReturn(modelList);

        List<OrdenDePagoModel> result = jpaOrdenPagoRepositoryAdapter
                .findByDocumentoIdAndEscalaIdAndRucAgente(documentoId, escalaId, rucAgente);
        assertEquals(modelList, result);
    }
}
