package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.mapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.DeclaracionJuradaModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.escala.Escala;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.pago.DeclaracionJurada;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.DeclaracionJuradaDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.DeclaracionJuradaListaDto;

/**
 * Clase Mapper para mapear la entidad DeclaracionJurada a un modelo
 * DeclaracionJuradaModel
 * 
 * @project cp-api-bs-tramiteyrectificacion-query
 * @autor Luis Francisco Huertas Mostacero
 * @date 24/08/2024
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DeclaracionJuradaMapper {

    DeclaracionJuradaMapper INSTANCE = Mappers.getMapper(DeclaracionJuradaMapper.class);

    @Mapping(source = "numeroDdjj", target = "numeroDeclaracionJurada")
    @Mapping(source = "estadoDdjjPago", target = "estadoDeclaracionJurada")
    @Mapping(source = "motivoDeclaracion", target = "motivo")
    @Mapping(source = "mensajeError", target = "error")
    @Mapping(source = "documento.id", target = "documentoId")
    @Mapping(source = "documento.nombreDocumento", target = "documentoNombre")
    @Mapping(source = "fechaSolicitudDdjj", target = "fechaDeclaracionJurada")
    DeclaracionJuradaModel toModel(DeclaracionJurada declaracionJurada);

    List<DeclaracionJuradaModel> toModelList(List<DeclaracionJurada> declaracionJuradaList);

    List<DeclaracionJuradaDto> toDeclaracionJuradaDtoList(List<DeclaracionJuradaModel> declaracionJuradaModels);

    @Mapping(target = "motivo", source = "motivoDeclaracion")
    @Mapping(target = "error", source = "mensajeError")
    @Mapping(target = "tramiteId", source = "tramite.id")
    @Mapping(target = "due", source = "declaracionJurada", qualifiedByName = "mapDue")
    @Mapping(target = "nombreTramite", source = "documento.nombreDocumento")
    @Mapping(target = "nombreNave", source = "declaracionJurada.escala.fichaTecnicaDetIn.nombreNave")
    @Mapping(target = "numeroDeclaracionJurada", source = "declaracionJurada.numeroDdjj")
    @Mapping(target = "estadoDeclaracionJurada", source = "declaracionJurada.estadoDdjjPago")
    @Mapping(target = "agenciaRuc", source = "declaracionJurada.rucAgente")
    @Mapping(target = "entidadId", source = "declaracionJurada.entidadId")
    @Mapping(target = "fechaDeclaracionJurada", source = "declaracionJurada.fechaRegAud", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(target = "fechaAceptacionDenegacion", source = "declaracionJurada", qualifiedByName = "mapFechaAceptacionDenegacion", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    DeclaracionJuradaListaDto toDeclaracionJuradaListaDto(DeclaracionJurada declaracionJurada);

    // Map de fecha de aceptacion o denegacion si ambos son nulos retorna null
    @Named("mapFechaAceptacionDenegacion")
    default Date mapFechaAceptacionDenegacion(DeclaracionJurada declaracionJurada) {
        LocalDateTime selectedDateTime = null;
        if (declaracionJurada.getFechaAprobacionDdjj() != null) {
            selectedDateTime = declaracionJurada.getFechaAprobacionDdjj();
        } else if (declaracionJurada.getFechaDenegacionDdjj() != null) {
            selectedDateTime = declaracionJurada.getFechaDenegacionDdjj();
        }

        return (selectedDateTime == null)
                ? null
                : Date.from(selectedDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    @Named("mapDue")
    default String mapDue(DeclaracionJurada declaracionJurada) {
        Escala escala = declaracionJurada.getEscala();
        if (escala != null) {
            String formattedNumeroEscala = String.format("%05d", escala.getNumeroEscala());
            return escala.getPuertoEscalaId() + "-" + escala.getAnnoEscala() + "-"
                    + formattedNumeroEscala;
        }
        return null;
    }

}