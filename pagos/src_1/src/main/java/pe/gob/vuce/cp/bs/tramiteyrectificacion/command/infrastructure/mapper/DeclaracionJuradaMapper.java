package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.DeclaracionJuradaRequestDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.DeclaracionJuradaResponseDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.DeclaracionJuradaModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity.DeclaracionJurada;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

public interface DeclaracionJuradaMapper {
    @Mappings({
            @Mapping(target = "usuidRegAud", source = "user"),
            @Mapping(target = "tramite.tramiteId", source = "dto.tramiteId")
    })
    DeclaracionJurada toEntity(DeclaracionJuradaRequestDto dto, String user);

    DeclaracionJuradaResponseDto toDto(DeclaracionJurada entity);

    @Mapping(target = "usuidModAud", source = "model.usuidModAud")
    DeclaracionJurada modelToEntity(DeclaracionJuradaModel model);

    DeclaracionJuradaModel entityToModel(DeclaracionJurada entity);

    List<DeclaracionJuradaModel> entityListToModelList(List<DeclaracionJurada> entity);

    List<DeclaracionJurada> modelListToEntityList(List<DeclaracionJuradaModel> model);

    @Mapping(target = "usuidRegAud", source = "user")
    @Mapping(target = "declaracionJuradaId", source = "dto.id")
    DeclaracionJuradaModel dtoToModelCrear(DeclaracionJuradaRequestDto dto, String user);

    @Mapping(target = "id", source = "declaracionJuradaId")
    DeclaracionJuradaResponseDto modelToDto(DeclaracionJuradaModel entity);

}
