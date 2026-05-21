package pe.gob.vuce.cp.sp.pagos.infrastructure.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.Mappings;
import org.mapstruct.Mapping;
import pe.gob.vuce.cp.sp.pagos.domain.model.ActividadEntidad;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.ActividadEntidadResponseDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.entity.ActividadEntidadEntity;

/**
 * Mapper que se encarga de la conversión entre las entidades de la base de datos,
 * los modelos de dominio y los DTOs utilizados en la API.
 *
 * Utiliza MapStruct para generar automáticamente la implementación del mapeo entre
 * los distintos objetos.
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ActividadEntidadMapper {
    /**
     * Convierte una entidad de ActividadEntidadEntity en un modelo de ActividadEntidad.
     *
     * @param entity La entidad a convertir.
     * @return El modelo de ActividadEntidad correspondiente.
     */
    @Mappings(value = {
            @Mapping(target = "actividadEntidadId",source = "actividadEntidadId"),
            @Mapping(target = "entidadId", source = "entidadId"),
            @Mapping(target = "actividadId", source = "actividadId"),
            @Mapping(target = "codPuertoNacional",source = "codPuertoNacional"),
            @Mapping(target = "codReglaNegocio", source = "codReglaNegocio"),
            @Mapping(target = "estado",source = "estado")
    })
    ActividadEntidad actividadEntityToModel(ActividadEntidadEntity entity);

    /**
     * Convierte un modelo de ActividadEntidad en un DTO de ActividadEntidadResponseDto.
     *
     * @param actividadEntidad El modelo a convertir.
     * @return El DTO de ActividadEntidadResponseDto correspondiente.
     */
    @Mappings(value = {
            @Mapping(target = "actividadEntidadId",source = "actividadEntidadId"),
            @Mapping(target = "entidadId", source = "entidadId"),
            @Mapping(target = "actividadId", source = "actividadId"),
            @Mapping(target = "codPuertoNacional",source = "codPuertoNacional"),
            @Mapping(target = "codReglaNegocio", source = "codReglaNegocio"),
            @Mapping(target = "estado",source = "estado")
    })
    ActividadEntidadResponseDto actividadEntidadToResponseDto(ActividadEntidad actividadEntidad);
}
