package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.TramiteModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.TramiteDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.DeclaracionJuradaModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.DeclaracionJuradaDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.TramiteDetalleDto;

import java.util.List;

/**
 * Mapper para la conversión entre modelos de dominio y DTOs relacionados con
 * trámites y declaraciones juradas.
 * Utiliza MapStruct para la implementación automática.
 * 
 * @project cp-api-bs-tramiteyrectificacion-query
 * @autor Luis Francisco Huertas Mostacero
 * @date 24/08/2024
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TramiteDtoMapper {

    /**
     * Convierte un TramiteModel a un TramiteDto.
     * 
     * @param tramiteModel El modelo de dominio del trámite.
     * @return El DTO del trámite.
     */
    @Mapping(target = "fechaDeclaracionJuradaActual", source = "fechaDeclaracionJuradaActual", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(target = "declaracionesJuradasCantidad", expression = "java(tramiteModel.getDeclaracionesJuradas() != null ? tramiteModel.getDeclaracionesJuradas().size() : 0)")
    @Mapping(target = "documento", source = "tramiteModel.documento.descAcronimo")
    @Mapping(target = "fechaTramite", source = "fechaTramite", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    TramiteDto toTramiteDto(TramiteModel tramiteModel);

    /**
     * Convierte un TramiteModel a un TramiteDetalleDto.
     * 
     * @param tramiteModel El modelo de dominio del trámite.
     * @return El DTO del trámite.
     */
    @Mapping(target = "fechaDeclaracionJuradaActual", source = "fechaDeclaracionJuradaActual", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    TramiteDetalleDto toTramiteDetalleDto(TramiteModel tramiteModel);

    /**
     * Convierte un DeclaracionJuradaModel a un DeclaracionJuradaDto.
     * 
     * @param declaracionJuradaModel El modelo de dominio de la declaración jurada.
     * @return El DTO de la declaración jurada.
     */
    @Mapping(target = "fechaDeclaracionJurada", source = "fechaDeclaracionJurada", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    DeclaracionJuradaDto toDeclaracionJuradaDto(DeclaracionJuradaModel declaracionJuradaModel);

    /**
     * Convierte una lista de TramiteModel a una lista de TramiteDto.
     * 
     * @param tramiteModels La lista de modelos de dominio de trámites.
     * @return La lista de DTOs de trámites.
     */
    List<TramiteDto> toTramiteDtoList(List<TramiteModel> tramiteModels);

    /**
     * Convierte una lista de TramiteModel a una lista de TramiteDetalleDto.
     * 
     * @param tramiteModels La lista de modelos de dominio de trámites.
     * @return La lista de DTOs de trámites.
     */
    List<TramiteDetalleDto> toTramiteDetalleDtoList(List<TramiteModel> tramiteModels);

    /**
     * Convierte una lista de DeclaracionJuradaModel a una lista de
     * DeclaracionJuradaDto.
     * 
     * @param declaracionJuradaModels La lista de modelos de dominio de
     *                                declaraciones juradas.
     * @return La lista de DTOs de declaraciones juradas.
     */
    List<DeclaracionJuradaDto> toDeclaracionJuradaDtoList(List<DeclaracionJuradaModel> declaracionJuradaModels);

    /**
     * Convierte un DeclaracionJuradaDto a un DeclaracionJuradaModel.
     * 
     * @param declaracionJuradaDto El DTO de la declaración jurada.
     * @return El modelo de dominio de la declaración jurada.
     */
    @Mapping(target = "fechaDeclaracionJurada", source = "fechaDeclaracionJurada", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    DeclaracionJuradaModel toDeclaracionJuradaModel(DeclaracionJuradaDto declaracionJuradaDto);
}
