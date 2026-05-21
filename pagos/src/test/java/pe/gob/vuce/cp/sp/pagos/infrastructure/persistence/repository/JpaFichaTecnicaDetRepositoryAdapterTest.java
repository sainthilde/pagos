package pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.gob.vuce.cp.sp.pagos.domain.model.FichaTecnicaDetModel;
import pe.gob.vuce.cp.sp.pagos.infrastructure.mapper.FichaTecnicaDetMapper;
import pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.entity.FichaTecnicaDet;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class JpaFichaTecnicaDetRepositoryAdapterTest {

    @Mock
    private FichaTecnicaDetRepository fichaTecnicaDetRepository;

    @Mock
    private FichaTecnicaDetMapper fichaTecnicaDetMapper;

    @InjectMocks
    private JpaFichaTecnicaDetRepositoryAdapter repositoryAdapter;

    private final Integer fichaId = 1;
    private FichaTecnicaDet fichaEntity;
    private FichaTecnicaDetModel fichaModel;

    @BeforeEach
    void setUp() {
        fichaEntity = new FichaTecnicaDet();
        fichaEntity.setFichaTecnicaId(fichaId);
        fichaEntity.setNombreNave("NAVE_TEST");


        fichaModel = new FichaTecnicaDetModel();
        fichaModel.setFichaTecnicaDetId(fichaId);
        fichaModel.setNombreNave("NAVE_TEST");

    }

    @Test
    void findById_WhenEntityExists_ShouldReturnMappedModel() {
        // Arrange
        when(fichaTecnicaDetRepository.findById(fichaId)).thenReturn(Optional.of(fichaEntity));
        when(fichaTecnicaDetMapper.entityToModel(fichaEntity)).thenReturn(fichaModel);

        // Act
        FichaTecnicaDetModel result = repositoryAdapter.findById(fichaId);

        // Assert
        assertNotNull(result);
        assertEquals(fichaId, result.getFichaTecnicaDetId());
        assertEquals("NAVE_TEST", result.getNombreNave());

        verify(fichaTecnicaDetRepository).findById(fichaId);
        verify(fichaTecnicaDetMapper).entityToModel(fichaEntity);
    }

    @Test
    void findById_WhenEntityNotExists_ShouldReturnNull() {
        // Arrange
        when(fichaTecnicaDetRepository.findById(fichaId)).thenReturn(Optional.empty());

        // Act
        FichaTecnicaDetModel result = repositoryAdapter.findById(fichaId);

        // Assert
        assertNull(result);
        verify(fichaTecnicaDetRepository).findById(fichaId);
        verify(fichaTecnicaDetMapper, never()).entityToModel(any());
    }

    @Test
    void findById_WhenMapperThrowsException_ShouldPropagateException() {
        // Arrange
        when(fichaTecnicaDetRepository.findById(fichaId)).thenReturn(Optional.of(fichaEntity));
        when(fichaTecnicaDetMapper.entityToModel(fichaEntity)).thenThrow(new RuntimeException("Mapping error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            repositoryAdapter.findById(fichaId);
        });

        verify(fichaTecnicaDetRepository).findById(fichaId);
        verify(fichaTecnicaDetMapper).entityToModel(fichaEntity);
    }

    @Test
    void findById_WithDifferentId_ShouldCallRepositoryWithCorrectId() {
        // Arrange
        Integer differentId = 2;
        when(fichaTecnicaDetRepository.findById(differentId)).thenReturn(Optional.of(fichaEntity));
        when(fichaTecnicaDetMapper.entityToModel(fichaEntity)).thenReturn(fichaModel);

        // Act
        FichaTecnicaDetModel result = repositoryAdapter.findById(differentId);

        // Assert
        assertNotNull(result);
        verify(fichaTecnicaDetRepository).findById(differentId);
        verify(fichaTecnicaDetRepository, never()).findById(fichaId);
    }

    @Test
    void findById_ShouldVerifyCompleteModelMapping() {
        // Arrange
        FichaTecnicaDet testEntity = new FichaTecnicaDet();
        testEntity.setFichaTecnicaId(3);
        testEntity.setNombreNave("NAVE_COMPLETA");

        FichaTecnicaDetModel expectedModel = new FichaTecnicaDetModel();
        expectedModel.setFichaTecnicaDetId(3);
        expectedModel.setNombreNave("NAVE_COMPLETA");

        when(fichaTecnicaDetRepository.findById(3)).thenReturn(Optional.of(testEntity));
        when(fichaTecnicaDetMapper.entityToModel(testEntity)).thenReturn(expectedModel);

        // Act
        FichaTecnicaDetModel result = repositoryAdapter.findById(3);

        // Assert
        assertNotNull(result);
        assertEquals(expectedModel.getFichaTecnicaDetId(), result.getFichaTecnicaDetId());
        assertEquals(expectedModel.getNombreNave(), result.getNombreNave());
    }

    @Test
    void findById_WhenRepositoryThrowsException_ShouldPropagateException() {
        // Arrange
        when(fichaTecnicaDetRepository.findById(fichaId)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            repositoryAdapter.findById(fichaId);
        });

        verify(fichaTecnicaDetRepository).findById(fichaId);
        verify(fichaTecnicaDetMapper, never()).entityToModel(any());
    }
}