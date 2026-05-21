package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.mapper;

import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.Named;
import org.mapstruct.IterableMapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.OrdenDePagoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.pago.OrdenDePago;

import java.util.List;

/**
 * Clase Mapper para mapear la entidad OrdenDePago a un modelo OrdenDePagoModel
 *
 * @project cp-api-bs-tramiteyrectificacion-query
 * @autor Luis Francisco Huertas Mostacero
 * @date 24/08/2024
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrdenDePagoModelMapper {

    @Named("entityToModel")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "escalaId", source = "escalaId")
    @Mapping(target = "documentoId", source = "documento.id")
    @Mapping(target = "nombreDocumento", source = "documento.nombreDocumento")
    @Mapping(target = "rucAgente", source = "rucAgente")
    @Mapping(target = "estadoOrdenPago", source = "estadoOrdenPago")
    @Mapping(target = "fechaCreacionOrdenPago", source = "fechaCreacionOrdenPago")
    @Mapping(target = "fechaVencimientoOrdenPago", source = "fechaVencimientoOrdenPago")
    @Mapping(target = "fechaPagado", source = "fechaPagado")
    @Mapping(target = "fechaAnulacionCpb", source = "fechaAnulacionCpb")
    @Mapping(target = "codAutorizadorReasignacion", source = "codAutorizadorReasignacion")
    @Mapping(target = "motivoAutorizacionReasignacion", source = "motivoAutorizacionReasignacion")
    @Mapping(target = "sustentoReasignacionFilenetGuid", source = "sustentoReasignacionFilenetGuid")
    @Mapping(target = "pdfCpbFilenetGuid", source = "pdfCpbFilenetGuid")
    @Mapping(target = "tramite", source = "tramite")
    @Mapping(target = "gpTupa", source = "gpTupa")
    @Mapping(target = "gpFormato", source = "gpFormato")
    @Mapping(target = "gpMonto", source = "gpMonto")
    @Mapping(target = "gpProcedimientoId", source = "gpProcedimientoId")
    @Mapping(target = "gpMonedaSigno", source = "gpMonedaSigno")
    @Mapping(target = "gpEtiquetaTasa", source = "gpEtiquetaTasa")
    @Mapping(target = "gpProcedimientoTasaVersion", source = "gpProcedimientoTasaVersion")
    @Mapping(target = "gpProcedimientoVersion", source = "gpProcedimientoVersion")
    @Mapping(target = "gpDescProcedimiento", source = "gpDescProcedimiento")
    @Mapping(target = "gpSecuencia", source = "gpSecuencia")
    @Mapping(target = "ppFechaRespuestaCreacionCpb", source = "ppFechaRespuestaCreacionCpb")
    @Mapping(target = "ppIdOrdenPagoInterna", source = "ppIdOrdenPagoInterna")
    @Mapping(target = "ppCodOrdenPago", source = "ppCodOrdenPago")
    @Mapping(target = "ppCpb", source = "ppCpb")
    @Mapping(target = "ppMonto", source = "ppMonto")
    @Mapping(target = "ppFechaConfGeneracionCpb", source = "ppFechaConfGeneracionCpb")
    @Mapping(target = "ppEstadoCpbTexto", source = "ppEstadoCpbTexto")
    @Mapping(target = "ppCodigorechazoSinConexion", source = "ppCodigorechazoSinConexion")
    @Mapping(target = "ppDescCortaError", source = "ppDescCortaError")
    @Mapping(target = "ppMensajeRechazoSinConexion", source = "ppMensajeRechazoSinConexion")
    @Mapping(target = "estado", source = "estado")
    OrdenDePagoModel entityToModel(
            OrdenDePago ordenDePago);

    @IterableMapping(qualifiedByName = "entityToModel")
    List<OrdenDePagoModel> toOrdenDePagoModel(List<OrdenDePago> ordenDePago);
}
