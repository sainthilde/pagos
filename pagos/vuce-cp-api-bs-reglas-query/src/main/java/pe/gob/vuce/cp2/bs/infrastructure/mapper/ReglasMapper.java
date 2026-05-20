package pe.gob.vuce.cp2.bs.infrastructure.mapper;

import org.springframework.stereotype.Component;

import pe.gob.vuce.cp2.bs.domain.model.ComprobanteModel;
import pe.gob.vuce.cp2.bs.domain.model.DataModel;
import pe.gob.vuce.cp2.bs.domain.model.DueModel;
import pe.gob.vuce.cp2.bs.domain.model.MetaModel;
import pe.gob.vuce.cp2.bs.domain.model.OperacionModel;
import pe.gob.vuce.cp2.bs.domain.model.ParametrosModel;
import pe.gob.vuce.cp2.bs.puertosnacionales.query.contract.model.ComprobanteCreacionDto;
import pe.gob.vuce.cp2.bs.puertosnacionales.query.contract.model.MetaDto;
import pe.gob.vuce.cp2.bs.puertosnacionales.query.contract.model.ReglaDto;
import pe.gob.vuce.cp2.bs.puertosnacionales.query.contract.model.ReglasPagoResponseDto;

@Component
public class ReglasMapper {

    public OperacionModel parametersToModel(
        Integer escalaId,
        String movimientoNave,
        Integer documentoId,
        Integer actividadId
    ) {
        return OperacionModel.builder()
            .parametrosModel(
                ParametrosModel.builder()
                    .escalaId(escalaId)
                    .movimientoNave(movimientoNave)
                    .documentoId(documentoId)
                    .actividadId(actividadId)
                    .build()
            )
            .metaModel(MetaModel.builder().build())
            .dataModel(DataModel.builder()
                .comprobanteModel(ComprobanteModel.builder().build())
                .build()
            )
            .dueModel(DueModel.builder().build())
            .build();
    }

    public ReglasPagoResponseDto modelToResponse(OperacionModel model) {
        DueModel dueModel = model.getDueModel();
        DataModel dataModel = model.getDataModel();
        ComprobanteModel comprobanteModel = dataModel.getComprobanteModel();
        return new ReglasPagoResponseDto()
            .meta(new MetaDto())
            .data(new ReglaDto()
                .idFlujo(dataModel.getIdFlujo())
                .flujoActivo(dataModel.getFlujoActivo())
                .descipcion(dataModel.getDescripcion())
                .mensaje(dataModel.getMensaje())
                .comprobanteCreacion(
                    comprobanteModel != null ?
                    new ComprobanteCreacionDto()
                        .textSearch(dueModel.getReglaNegocio())
                        .entidadId(dueModel.getEntidadId())
                        .monto(comprobanteModel.getMonto().doubleValue())
                        .codigoMoneda(comprobanteModel.getCodigoMoneda())
                        .descripcion(comprobanteModel.getDescripcion())
                        .escalaId(comprobanteModel.getEscalaId())
                        .fechaVigencia(comprobanteModel.getFechaVigencia())
                        .idComponente(comprobanteModel.getIdComponente())
                        .procedimientoId(comprobanteModel.getProcedimientoId())
                        .procedimientoVersion(comprobanteModel.getProcedimientoVersion())
                        .procedimientoTasaVersion(comprobanteModel.getProcedimientoTasaVersion())
                        .rucAgente(comprobanteModel.getRucAgente())
                        .secuencia(comprobanteModel.getSecuencia())
                        .actividadEntidadPuertoId(comprobanteModel.getActividadEntidadPuertoId())
                        .actividadId(comprobanteModel.getActividadId())
                        .monedaDescripcion(comprobanteModel.getMonedaDescripcion())
                        .monedaSigno(comprobanteModel.getMonedaSigno())
                        .etiqueta(comprobanteModel.getEtiqueta()
                    ): null
                ) 
            );
    }   

}
