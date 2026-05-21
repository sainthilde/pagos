package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.TramiteCrearRequestDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.TramiteDesistResponseDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.TramiteResponseDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.TramiteUpdateRequestDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.TramiteModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity.Tramite;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TramiteMapperTest {

    private TramiteMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(TramiteMapper.class);
    }

    @Test
    void testModelToDto() {
        TramiteModel tramiteModel = new TramiteModel();
        tramiteModel.setTramiteId(1);

        TramiteResponseDto dto = mapper.modelToDto(tramiteModel);

        assertThat(dto).isNotNull();
        assertThat(dto.getIdSuce()).isEqualTo(tramiteModel.getTramiteId());
    }

    @Test
    void testDtoToModelUpdate() {
        TramiteUpdateRequestDto dto = new TramiteUpdateRequestDto();
        dto.setTramiteId(1);
        String user = "testUser";

        TramiteModel tramiteModel = mapper.dtoToModelUpdate(dto, user);

        assertThat(tramiteModel).isNotNull();
        assertThat(tramiteModel.getTramiteId()).isEqualTo(dto.getTramiteId());
        assertThat(tramiteModel.getUsuidModAud()).isEqualTo(user);
    }

    @Test
    void testModelToEntity() {
        TramiteModel tramiteModel = new TramiteModel();
        tramiteModel.setTramiteId(1);


        Tramite tramite = mapper.modelToEntity(tramiteModel);

        assertThat(tramite).isNotNull();
        assertThat(tramite.getTramiteId()).isEqualTo(tramiteModel.getTramiteId());
    }

    @Test
    void testEntityToModel() {
        Tramite tramite = new Tramite();
        tramite.setTramiteId(1);

        TramiteModel tramiteModel = mapper.entityToModel(tramite);

        assertThat(tramiteModel).isNotNull();
        assertThat(tramiteModel.getTramiteId()).isEqualTo(tramite.getTramiteId());
    }

    @Test
    void testDtoToModelCrear() {
        TramiteCrearRequestDto dto = new TramiteCrearRequestDto();
        String user = "testUser";

        TramiteModel tramiteModel = mapper.dtoToModelCrear(dto, user);

        assertThat(tramiteModel).isNotNull();
        assertThat(tramiteModel.getUsuidRegAud()).isEqualTo(user);
    }

    @Test
    void testModelToDtoDesist() {
        TramiteModel tramiteModel = new TramiteModel();
        tramiteModel.setTramiteId(1);

        TramiteDesistResponseDto dto = mapper.modelToDtoDesist(tramiteModel);

        assertThat(dto).isNotNull();
        assertThat(dto.getTramiteId()).isEqualTo(tramiteModel.getTramiteId());
    }

    @Test
    void testEntityListToModelList() {
        Tramite tramite = new Tramite();
        tramite.setTramiteId(1);
        List<Tramite> tramites = Collections.singletonList(tramite);

        List<TramiteModel> tramiteModels = mapper.entityListToModelList(tramites);

        assertThat(tramiteModels).isNotNull();
        assertThat(tramiteModels).hasSize(1);
        TramiteModel tramiteModel = tramiteModels.get(0);
        assertThat(tramiteModel.getTramiteId()).isEqualTo(tramite.getTramiteId());
    }

    @Test
    void testModelListToDtoList() {
        TramiteModel tramiteModel = new TramiteModel();
        tramiteModel.setTramiteId(1);
        List<TramiteModel> tramiteModels = Collections.singletonList(tramiteModel);

        List<TramiteDesistResponseDto> dtos = mapper.modelListToDtoList(tramiteModels);

        assertThat(dtos).isNotNull();
        assertThat(dtos).hasSize(1);
        TramiteDesistResponseDto dto = dtos.get(0);
        assertThat(dto.getTramiteId()).isEqualTo(tramiteModel.getTramiteId());

    }

}
