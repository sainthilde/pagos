package pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.gob.vuce.cp.sp.pagos.domain.model.EscalaModel;
import pe.gob.vuce.cp.sp.pagos.infrastructure.mapper.EscalaMapper;
import pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.entity.Escala;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class JpaEscalaRepositoryAdapterTest {

    @Mock
    private EscalaRepository escalaRepository;

    @Mock
    private EscalaMapper escalaMapper;

    @InjectMocks
    private JpaEscalaRepositoryAdapter jpaEscalaRepositoryAdapter;

    private final Integer escalaId = 1;
    private Escala escalaEntity;
    private EscalaModel escalaModel;

    @BeforeEach
    void setUp() {
        escalaEntity = new Escala();
        escalaEntity.setEscalaId(escalaId);
        escalaEntity.setNumeroEscala(1234);

        escalaModel = new EscalaModel();
        escalaModel.setEscalaId(escalaId);
        escalaModel.setNumeroEscala(1234);
    }


    @Test
    void findById_WhenEntityNotExists_ShouldReturnNull() {
        // Arrange
        when(escalaRepository.findById(escalaId)).thenReturn(Optional.empty());

        // Act
        EscalaModel result = jpaEscalaRepositoryAdapter.findById(escalaId);

        // Assert
        assertNull(result);
        verify(escalaRepository).findById(escalaId);
        verify(escalaMapper, never()).entityToModel(any());
    }


    @Test
    void findById_WhenMapperThrowsException_ShouldPropagateException() {
        // Arrange
        when(escalaRepository.findById(escalaId)).thenReturn(Optional.of(escalaEntity));
        when(escalaMapper.entityToModel(escalaEntity)).thenThrow(new RuntimeException("Mapping error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            jpaEscalaRepositoryAdapter.findById(escalaId);
        });

        verify(escalaRepository).findById(escalaId);
        verify(escalaMapper).entityToModel(escalaEntity);
    }

    @Test
    void findById_WithDifferentId_ShouldCallRepositoryWithCorrectId() {
        // Arrange
        Integer differentId = 2;
        when(escalaRepository.findById(differentId)).thenReturn(Optional.of(escalaEntity));
        when(escalaMapper.entityToModel(escalaEntity)).thenReturn(escalaModel);

        // Act
        EscalaModel result = jpaEscalaRepositoryAdapter.findById(differentId);

        // Assert
        assertNotNull(result);
        verify(escalaRepository).findById(differentId);
        verify(escalaRepository, never()).findById(escalaId);
    }
}