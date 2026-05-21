package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants.Constants;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.TramiteModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.mapper.TramiteMapper;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.mapper.TramiteUpdateMapper;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity.Tramite;

public class JpaTramiteRepositoryAdapterTest {

    @InjectMocks
    private JpaTramiteRepositoryAdapter jpaTramiteRepositoryAdapter;

    @Mock
    private TramiteRepository tramiteRepository;

    @Mock
    private TramiteUpdateMapper tramiteUpdateMapper;

    @Mock
    private TramiteMapper tramiteMapper;

    private Tramite tramiteEntity;
    private TramiteModel tramiteModel;
    private TramiteModel updatedTramiteModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Initialize a dummy persistence entity
        tramiteEntity = new Tramite();
        // (Optionally, set properties on the entity here)

        // Initialize a dummy domain model with an ID (used for both save and update)
        tramiteModel = new TramiteModel();
        tramiteModel.setTramiteId(1);
        // (Set additional properties on the model as needed)

        // The expected result after updating
        updatedTramiteModel = new TramiteModel();
        updatedTramiteModel.setTramiteId(1);
    }

    @Test
    void testSave() {
        // When saving, the model is converted to an entity, saved, then converted back
        // to a model.
        when(tramiteMapper.modelToEntity(tramiteModel)).thenReturn(tramiteEntity);
        when(tramiteRepository.save(tramiteEntity)).thenReturn(tramiteEntity);
        when(tramiteMapper.entityToModel(tramiteEntity)).thenReturn(tramiteModel);

        TramiteModel result = jpaTramiteRepositoryAdapter.save(tramiteModel);

        assertEquals(tramiteModel, result);
    }

    @Test
    void testUpdateExistingTramite() {
        // Simulate an existing entity is found by ID.
        when(tramiteRepository.findById(tramiteModel.getTramiteId())).thenReturn(Optional.of(tramiteEntity));
        // Convert the model to an entity that will be used to update.
        Tramite entityToUpdate = new Tramite();
        when(tramiteMapper.modelToEntity(tramiteModel)).thenReturn(entityToUpdate);
        // The update mapper copies properties from the new entity to the existing one.
        doNothing().when(tramiteUpdateMapper).updateTramiteFromDto(entityToUpdate, tramiteEntity);
        when(tramiteRepository.save(tramiteEntity)).thenReturn(tramiteEntity);
        when(tramiteMapper.entityToModel(tramiteEntity)).thenReturn(updatedTramiteModel);

        TramiteModel result = jpaTramiteRepositoryAdapter.update(tramiteModel);

        assertEquals(updatedTramiteModel, result);
    }

    @Test
    void testUpdateNewTramite() {
        // Simulate that no existing entity is found.
        when(tramiteRepository.findById(tramiteModel.getTramiteId())).thenReturn(Optional.empty());
        when(tramiteMapper.modelToEntity(tramiteModel)).thenReturn(tramiteEntity);
        when(tramiteRepository.save(tramiteEntity)).thenReturn(tramiteEntity);
        when(tramiteMapper.entityToModel(tramiteEntity)).thenReturn(updatedTramiteModel);

        TramiteModel result = jpaTramiteRepositoryAdapter.update(tramiteModel);

        assertEquals(updatedTramiteModel, result);
    }

    @Test
    void testFindById() {
        Integer id = 1;
        when(tramiteRepository.findById(id)).thenReturn(Optional.of(tramiteEntity));
        when(tramiteMapper.entityToModel(tramiteEntity)).thenReturn(tramiteModel);

        Optional<TramiteModel> result = jpaTramiteRepositoryAdapter.findById(id);

        assertTrue(result.isPresent());
        assertEquals(tramiteModel, result.get());
    }

    @Test
    void testGetNumeroTramitePorAnio() {
        LocalDateTime inicio = LocalDateTime.now().minusYears(1);
        LocalDateTime fin = LocalDateTime.now();
        int countValue = 9; // Example repository count value
        // The adapter adds one to the count.
        when(tramiteRepository.countByFechaTramiteBetween(inicio, fin)).thenReturn(countValue);

        Integer result = jpaTramiteRepositoryAdapter.getNumeroTramitePorAnio(inicio, fin);

        assertEquals(countValue + 1, result);
    }

    @Test
    void testFindByEscalaId() {
        Integer escalaId = 1;
        List<Tramite> entityList = List.of(tramiteEntity);
        List<TramiteModel> modelList = List.of(tramiteModel);
        // The repository method returns entities filtered by escalaId and a specific
        // state.
        when(tramiteRepository.findAllByEscalaIdAndEstadoTramite(escalaId, Constants.EN_TRAMITE))
                .thenReturn(entityList);
        when(tramiteMapper.entityListToModelList(entityList)).thenReturn(modelList);

        List<TramiteModel> result = jpaTramiteRepositoryAdapter.findByEscalaId(escalaId);

        assertEquals(modelList, result);
    }

    @Test
    void testFindAllByEscalaId() {
        Integer escalaId = 1;
        List<Tramite> entityList = List.of(tramiteEntity);
        List<TramiteModel> modelList = List.of(tramiteModel);
        when(tramiteRepository.findAllByEscalaId(escalaId)).thenReturn(entityList);
        when(tramiteMapper.entityListToModelList(entityList)).thenReturn(modelList);

        List<TramiteModel> result = jpaTramiteRepositoryAdapter.findAllByEscalaId(escalaId);

        assertEquals(modelList, result);
    }

    @Test
    void testFindAllByEscalaIdAndDocumentoIdIn() {
        Integer escalaId = 1;
        List<Integer> documentoIds = List.of(1, 2, 3);
        List<Tramite> entityList = List.of(tramiteEntity);
        List<TramiteModel> modelList = List.of(tramiteModel);
        when(tramiteRepository.findAllByEscalaIdAndDocumentoIdIn(escalaId, documentoIds)).thenReturn(entityList);
        when(tramiteMapper.entityListToModelList(entityList)).thenReturn(modelList);

        List<TramiteModel> result = jpaTramiteRepositoryAdapter.findAllByEscalaIdAndDocumentoIdIn(escalaId,
                documentoIds);

        assertEquals(modelList, result);
    }
}
