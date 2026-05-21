package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.mapper;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.DeclaracionJuradaModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.TramiteModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.escala.Escala;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.pago.DeclaracionJurada;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.pago.Tramite;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.TramitePagoDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.pago.OrdenDePago;

/**
 * Mapper para la conversión entre entidades persistentes y modelos de dominio
 * relacionados con trámites y declaraciones juradas.
 * Utiliza MapStruct para la implementación automática.
 *
 * @project cp-api-bs-tramiteyrectificacion-query
 * @autor Luis Francisco Huertas Mostacero
 * @date 24/08/2024
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TramiteModelMapper {

    /**
     * Convierte una entidad Tramite y una entidad OrdenDePago en un TramiteModel.
     *
     * @param tramite     La entidad Tramite.
     * @param ordenDePago La entidad OrdenDePago.
     * @return El modelo de dominio TramiteModel.
     */
    @Mapping(target = "id", source = "tramite.id")
    @Mapping(target = "due", source = "tramite", qualifiedByName = "mapDue")
    @Mapping(target = "nombreNave", source = "tramite.escala.fichaTecnicaDetIn.nombreNave")
    @Mapping(target = "numeroTramite", source = "tramite.numeroTramiteEntidad")
    @Mapping(target = "entidadId", source = "tramite.actividadEntidadPuerto.entidad.id")
    @Mapping(target = "entidadNombre", source = "tramite.actividadEntidadPuerto.entidad.nombre")
    @Mapping(target = "entidadRuc", source = "tramite.actividadEntidadPuerto.entidad.ruc")
    @Mapping(target = "agenciaId", source = "tramite.agencia.id")
    @Mapping(target = "agenciaNombre", source = "tramite.agencia.razonSocialAgencia")
    @Mapping(target = "agenciaRuc", source = "tramite.agencia.rucAgencia")
    @Mapping(target = "tupa", source = "tramite.tupa")
    @Mapping(target = "fueTramiteManual", source = "tramite.fueTramiteManual")
    @Mapping(target = "monto", source = "ordenDePago.gpMonto")
    @Mapping(target = "estadoDePago", source = "ordenDePago.estadoOrdenPago")
    @Mapping(target = "fechaDeclaracionJuradaActual", source = "tramite", qualifiedByName = "mapFechaDeclaracionJuradaActual")
    @Mapping(target = "descripcion", source = "tramite.descripcionTramite")
    @Mapping(target = "cpb", source = "ordenDePago.ppCpb")
    @Mapping(target = "declaracionesJuradas", source = "tramite.declaracionesJuradas", qualifiedByName = "mapDeclaracionesJuradas")
    @Mapping(target = "documento", source = "tramite.documento")
    TramiteModel toTramiteModel(Tramite tramite, OrdenDePago ordenDePago);

    /**
     * Mapea el DUE (Documento Único de Existencia) en el formato
     * "PuertoEscalaId-Año-NúmeroEscala".
     *
     * @param tramite La entidad Tramite.
     * @return El DUE formateado.
     */
    @Named("mapDue")
    default String mapDue(Tramite tramite) {
        Escala escala = tramite.getEscala();
        if (escala != null) {
            String formattedNumeroEscala = String.format("%05d", escala.getNumeroEscala());
            return escala.getPuertoEscalaId() + "-" + escala.getAnnoEscala() + "-"
                    + formattedNumeroEscala;
        }
        return null;
    }

    /**
     * Mapea la fecha de la declaración jurada más reciente.
     *
     * @param tramite La entidad Tramite.
     * @return La fecha más reciente de la declaración jurada.
     */
    @Named("mapFechaDeclaracionJuradaActual")
    default LocalDateTime mapFechaDeclaracionJuradaActual(Tramite tramite) {
        return tramite.getDeclaracionesJuradas().stream()
                .map(DeclaracionJurada::getFechaSolicitudDdjj)
                .max(LocalDateTime::compareTo)
                .orElse(null);
    }

    /**
     * Convierte una entidad Tramite en una lista de modelos de dominio
     * TramiteModel.
     * Selecciona la OrdenDePago más reciente para la conversión.
     *
     * @param tramite La entidad Tramite.
     * @return Una lista con un solo elemento TramiteModel.
     */
    default List<TramiteModel> toTramiteModels(Tramite tramite) {
        // Obtener la última OrdenDePago basada en la fecha de creación
        OrdenDePago latestOrdenDePago = tramite.getOrdenesDePago().stream()
                .max(Comparator.comparing(OrdenDePago::getFechaCreacionOrdenPago))
                .orElse(null);

        // Mapear a TramiteModel usando la última OrdenDePago, o null si no se encuentra
        return Collections.singletonList(toTramiteModel(tramite, latestOrdenDePago));
    }

    /**
     * Convierte una lista de entidades DeclaracionJurada en una lista de modelos de
     * dominio DeclaracionJuradaModel.
     *
     * @param declaracionesJuradas La lista de entidades DeclaracionJurada.
     * @return La lista de modelos de dominio DeclaracionJuradaModel.
     */
    @Named("mapDeclaracionesJuradas")
    default List<DeclaracionJuradaModel> mapDeclaracionesJuradas(List<DeclaracionJurada> declaracionesJuradas) {
        return declaracionesJuradas.stream()
                .map(this::toDeclaracionJuradaModel)
                .toList();
    }

    /**
     * Convierte una entidad DeclaracionJurada en un modelo de dominio
     * DeclaracionJuradaModel.
     *
     * @param declaracionJurada La entidad DeclaracionJurada.
     * @return El modelo de dominio DeclaracionJuradaModel.
     */
    @Mapping(target = "numeroDeclaracionJurada", source = "numeroDdjj")
    @Mapping(target = "estadoDeclaracionJurada", source = "estadoDdjjPago")
    @Mapping(target = "rucAgente", source = "rucAgente")
    @Mapping(target = "motivo", source = "motivoDeclaracion")
    @Mapping(target = "error", source = "mensajeError")
    @Mapping(target = "documentoId", source = "documento.id")
    @Mapping(target = "documentoNombre", source = "documento.nombreDocumento")
    @Mapping(target = "fechaDeclaracionJurada", source = "fechaSolicitudDdjj")
    DeclaracionJuradaModel toDeclaracionJuradaModel(DeclaracionJurada declaracionJurada);

    @Mapping(target = "due", source = "tramite", qualifiedByName = "mapDue")
    @Mapping(target = "nombreNave", source = "tramite.escala.fichaTecnicaDetIn.nombreNave")
    @Mapping(target = "numeroTramite", source = "tramite.numeroTramiteEntidad")
    @Mapping(target = "entidadId", source = "tramite.actividadEntidadPuerto.entidad.id")
    @Mapping(target = "entidadNombre", source = "tramite.actividadEntidadPuerto.entidad.nombre")
    @Mapping(target = "entidadRuc", source = "tramite.actividadEntidadPuerto.entidad.ruc")
    @Mapping(target = "agenciaId", source = "tramite.agencia.id")
    @Mapping(target = "agenciaNombre", source = "tramite.agencia.razonSocialAgencia")
    @Mapping(target = "agenciaRuc", source = "tramite.agencia.rucAgencia")
    @Mapping(target = "fechaDeclaracionJuradaActual", source = "tramite", qualifiedByName = "mapFechaDeclaracionJuradaActual")
    @Mapping(target = "declaracionesJuradas", source = "tramite.declaracionesJuradas", qualifiedByName = "mapDeclaracionesJuradas")
    TramiteModel entityToTramiteModel(Tramite tramite);

    /**
     * Convierte una entidad Tramite en un DTO TramitePagoDto.
     *
     * @param tramite La entidad Tramite.
     * @return El DTO TramitePagoDto.
     */
    @Mapping(target = "tramiteId", source = "id")
    @Mapping(target = "escalaId", source = "escala.escalaId")
    @Mapping(target = "reglaPagoExencionAplicada", source = "reglaPagoExencionAplicada")
    TramitePagoDto entityToTramitePagoDto(Tramite tramite);

}
