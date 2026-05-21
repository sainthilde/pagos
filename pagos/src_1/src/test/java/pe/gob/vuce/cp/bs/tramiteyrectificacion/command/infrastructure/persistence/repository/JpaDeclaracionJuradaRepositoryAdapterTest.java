package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants.Constants;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.DeclaracionJuradaModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.mapper.DeclaracionJuradaMapper;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity.DeclaracionJurada;

public class JpaDeclaracionJuradaRepositoryAdapterTest {

        @InjectMocks
        private JpaDeclaracionJuradaRepositoryAdapter jpaDeclaracionJuradaRepositoryAdapter;

        @Mock
        private DeclaracionJuradaRepository declaracionJuradaRepository;

        @Mock
        private DeclaracionJuradaMapper declaracionJuradaMapper;

        private DeclaracionJurada declaracionJuradaEntity;
        private DeclaracionJuradaModel declaracionJuradaModel;

        @BeforeEach
        void setUp() {
                MockitoAnnotations.openMocks(this);
                // Initialize dummy entity and model objects
                declaracionJuradaEntity = new DeclaracionJurada();
                // (Optionally, set properties on the entity here)

                declaracionJuradaModel = new DeclaracionJuradaModel();
                // (Optionally, set properties on the model here)
        }

        @Test
        void testFindById() {
                Integer id = 1;
                when(declaracionJuradaRepository.findById(id))
                                .thenReturn(Optional.of(declaracionJuradaEntity));
                when(declaracionJuradaMapper.entityToModel(declaracionJuradaEntity))
                                .thenReturn(declaracionJuradaModel);

                Optional<DeclaracionJuradaModel> result = jpaDeclaracionJuradaRepositoryAdapter.findById(id);

                assertTrue(result.isPresent());
                assertEquals(declaracionJuradaModel, result.get());
        }

        @Test
        void testSave() {
                when(declaracionJuradaMapper.modelToEntity(declaracionJuradaModel))
                                .thenReturn(declaracionJuradaEntity);
                when(declaracionJuradaRepository.save(declaracionJuradaEntity))
                                .thenReturn(declaracionJuradaEntity);
                when(declaracionJuradaMapper.entityToModel(declaracionJuradaEntity))
                                .thenReturn(declaracionJuradaModel);

                DeclaracionJuradaModel result = jpaDeclaracionJuradaRepositoryAdapter.save(declaracionJuradaModel);

                assertEquals(declaracionJuradaModel, result);
        }

        @Test
        void testFindByTramiteTramiteId() {
                Integer tramiteId = 1;
                List<DeclaracionJurada> entityList = new ArrayList<>();
                List<DeclaracionJuradaModel> modelList = new ArrayList<>();
                entityList.add(declaracionJuradaEntity);
                modelList.add(declaracionJuradaModel);

                when(declaracionJuradaRepository.findByTramiteTramiteId(tramiteId))
                                .thenReturn(entityList);
                when(declaracionJuradaMapper.entityListToModelList(entityList))
                                .thenReturn(modelList);

                List<DeclaracionJuradaModel> result = jpaDeclaracionJuradaRepositoryAdapter
                                .findByTramiteTramiteId(tramiteId);

                assertEquals(modelList, result);
        }

        @Test
        void testFindByEscalaId() {
                Integer escalaId = 1;
                List<DeclaracionJurada> entityList = new ArrayList<>();
                List<DeclaracionJuradaModel> modelList = new ArrayList<>();
                entityList.add(declaracionJuradaEntity);
                modelList.add(declaracionJuradaModel);

                when(declaracionJuradaRepository.findByEscalaId(escalaId))
                                .thenReturn(entityList);
                when(declaracionJuradaMapper.entityListToModelList(entityList))
                                .thenReturn(modelList);

                List<DeclaracionJuradaModel> result = jpaDeclaracionJuradaRepositoryAdapter.findByEscalaId(escalaId);

                assertEquals(modelList, result);
        }

        @Test
        void testFindByDocumentoIdAndEscalaIdAndRucAgente() {
                Integer documentoId = 1;
                Integer escalaId = 1;
                String rucAgente = "12345678901";
                String estado="S";
                List<DeclaracionJurada> entityList = new ArrayList<>();
                List<DeclaracionJuradaModel> modelList = new ArrayList<>();
                entityList.add(declaracionJuradaEntity);
                modelList.add(declaracionJuradaModel);

                when(declaracionJuradaRepository.findByDocumentoDocumentoIdAndEscalaIdAndRucAgenteAndEstado(documentoId,
                                escalaId,
                                rucAgente,
                                estado))
                                .thenReturn(entityList);
                when(declaracionJuradaMapper.entityListToModelList(entityList))
                                .thenReturn(modelList);

                List<DeclaracionJuradaModel> result = jpaDeclaracionJuradaRepositoryAdapter
                                .findByDocumentoIdAndEscalaIdAndRucAgente(documentoId, escalaId, rucAgente);

                assertEquals(modelList, result);
        }

        @Test
        void testCountByFechaSolicitudDdjjBetween() {
                LocalDateTime startDate = LocalDateTime.now().minusDays(1);
                LocalDateTime endDate = LocalDateTime.now();
                int count = 5;
                when(declaracionJuradaRepository.countByFechaSolicitudDdjjBetween(startDate, endDate))
                                .thenReturn(count);

                int result = jpaDeclaracionJuradaRepositoryAdapter.countByFechaSolicitudDdjjBetween(startDate, endDate);

                assertEquals(count, result);
        }
}
