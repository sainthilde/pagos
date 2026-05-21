package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.exceptions.BusinessError;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.ActividadEntidadPuertoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.mapper.ActividadEntidadPuertoMapper;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity.mae.ActividadEntidadPuerto;

@ExtendWith(MockitoExtension.class)
class JpaActividadEntidadPuertoRepositoryAdapterTest {

    @Mock
    private ActividadEntidadPuertoRepository actividadEntidadPuertoRepository;

    @Mock
    private ActividadEntidadPuertoMapper actividadEntidadPuertoMapper;

    @InjectMocks
    private JpaActividadEntidadPuertoRepositoryAdapter adapter;

    @Test
    void findByActividadIdAndCodPuertoNacionalAndEstado_WhenFound_ReturnsModel() {
        // Arrange
        Integer actividadId = 1;
        String codPuertoNacional = "PORT1";
        String estado = "ACTIVE";

        ActividadEntidadPuerto entity = new ActividadEntidadPuerto();
        ActividadEntidadPuertoModel expectedModel = new ActividadEntidadPuertoModel();

        when(actividadEntidadPuertoRepository.findByActividadIdAndCodPuertoNacionalAndEstado(
                actividadId, codPuertoNacional, estado))
                .thenReturn(Optional.of(entity));
        when(actividadEntidadPuertoMapper.toModel(entity)).thenReturn(expectedModel);

        // Act
        ActividadEntidadPuertoModel result = adapter.findByActividadIdAndCodPuertoNacionalAndEstado(
                actividadId, codPuertoNacional, estado);

        // Assert
        assertEquals(expectedModel, result);
        verify(actividadEntidadPuertoRepository).findByActividadIdAndCodPuertoNacionalAndEstado(
                actividadId, codPuertoNacional, estado);
        verify(actividadEntidadPuertoMapper).toModel(entity);
    }

    @Test
    void findByActividadIdAndCodPuertoNacionalAndEstado_WhenNotFound_ThrowsException() {
        // Arrange
        Integer actividadId = 1;
        String codPuertoNacional = "PORT1";
        String estado = "ACTIVE";

        when(actividadEntidadPuertoRepository.findByActividadIdAndCodPuertoNacionalAndEstado(
                actividadId, codPuertoNacional, estado))
                .thenReturn(Optional.empty());

        // Act & Assert
        BusinessError exception = assertThrows(BusinessError.class,
                () -> adapter.findByActividadIdAndCodPuertoNacionalAndEstado(actividadId, codPuertoNacional, estado));

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }
}