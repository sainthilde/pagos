package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.TramiteCrearRequestDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.TramiteDesistResponseDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.TramiteResponseDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.TramiteUpdateRequestDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.TramiteModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity.Tramite;

/**
 * Interfaz que define los métodos de mapeo entre los modelos de dominio, DTOs,
 * y entidades de persistencia
 * relacionados con el tramite. Utiliza MapStruct para generar las
 * implementaciones de los métodos de mapeo.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 19/08/2024
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TramiteMapper {

    /**
     * Convierte un modelo de tramite en un DTO de respuesta.
     *
     * @param tramiteModel El modelo de tramite
     * @return El DTO de respuesta correspondiente.
     */
    @Mapping(target = "idSuce", source = "tramiteId")
    TramiteResponseDto modelToDto(TramiteModel tramiteModel);

    @Mapping(source = "dto.tramiteId", target = "tramiteId")
    @Mapping(target = "usuidModAud", source = "user")
    TramiteModel dtoToModelUpdate(TramiteUpdateRequestDto dto, String user);

    /**
     * Convierte un modelo de tramite en una entidad de persistencia.
     *
     * @param tramiteModel El modelo de ficha sanitaria.
     * @return La entidad de persistencia correspondiente.
     */
    Tramite modelToEntity(TramiteModel tramiteModel);

    /**
     * Convierte una entidad de persistencia en un modelo de tramite.
     *
     * @param tramite La entidad de tramite.
     * @return El modelo de ficha sanitaria correspondiente.
     */
    @Named("entityToModel")
    TramiteModel entityToModel(Tramite tramite);

    /**
     * Convierte un DTO de tramite creacion de en un modelo de tramite
     *
     * @param tramiteCrearRequestDto El DTO de creación de inspección sanitaria.
     * @param user                   El identificador del usuario que realiza la
     *                               operación.
     * @return El modelo de ficha sanitaria correspondiente.
     */
    @Mapping(target = "usuidRegAud", source = "user")
    TramiteModel dtoToModelCrear(TramiteCrearRequestDto tramiteCrearRequestDto, String user);

    /**
     * Convierte un modelo de tramite en un DTO de respuesta.
     *
     * @param tramiteModel El modelo de tramite
     * @return El DTO de respuesta correspondiente.
     */
    @Named("modelToDtoDesist")
    TramiteDesistResponseDto modelToDtoDesist(TramiteModel tramiteModel);

    @IterableMapping(qualifiedByName = "entityToModel")
    List<TramiteModel> entityListToModelList(List<Tramite> tramiteList);

    @IterableMapping(qualifiedByName = "modelToDtoDesist")
    List<TramiteDesistResponseDto> modelListToDtoList(List<TramiteModel> tramiteList);

    @Named("modelToDtoResponse")
    TramiteResponseDto modelToDtoResponse(TramiteModel tramiteModel);

    @IterableMapping(qualifiedByName = "modelToDtoResponse")
    List<TramiteResponseDto> modelListTramiteToDtoList(List<TramiteModel> tramiteList);

    default TramiteDesistResponseDto modelToDesistDto(TramiteModel model) {
        TramiteDesistResponseDto dto = new TramiteDesistResponseDto();
        dto.setTramiteId(model.getTramiteId());
        dto.setEstadoTramite(model.getEstadoTramite());
        dto.setNumeroSuce(model.getNumeroSuce());
        dto.setPpCpbPayments(model.getPpCpbPayments());
        dto.setExito(true);
        dto.setMensajeError("Desistimiento exitoso");
        return dto;
    }

}
