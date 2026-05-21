package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.ActividadEntidadPuertoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity.mae.ActividadEntidadPuerto;

class ActividadEntidadPuertoMapperTest {

    private final ActividadEntidadPuertoMapper mapper = Mappers.getMapper(ActividadEntidadPuertoMapper.class);

    @Test
    void shouldMapEntityToModel() {
        // Arrange
        ActividadEntidadPuerto entity = new ActividadEntidadPuerto();
        entity.setId(1);
        entity.setEntidadId(100);
        entity.setActividadId(200);
        entity.setCodPuertoNacional("PEN001");

        // Act
        ActividadEntidadPuertoModel model = mapper.toModel(entity);

        // Assert
        assertNotNull(model);
        assertEquals(entity.getId(), model.getId());
        assertEquals(entity.getEntidadId(), model.getEntidadId());
        assertEquals(entity.getActividadId(), model.getActividadId());
        assertEquals(entity.getCodPuertoNacional(), model.getCodPuertoNacional());
    }

    @Test
    void shouldMapModelToEntity() {
        // Arrange
        ActividadEntidadPuertoModel model = new ActividadEntidadPuertoModel();
        model.setId(1);
        model.setEntidadId(100);
        model.setActividadId(200);
        model.setCodPuertoNacional("PEN001");

        // Act
        ActividadEntidadPuerto entity = mapper.toEntity(model);

        // Assert
        assertNotNull(entity);
        assertEquals(model.getId(), entity.getId());
        assertEquals(model.getEntidadId(), entity.getEntidadId());
        assertEquals(model.getActividadId(), entity.getActividadId());
        assertEquals(model.getCodPuertoNacional(), entity.getCodPuertoNacional());
    }
}
