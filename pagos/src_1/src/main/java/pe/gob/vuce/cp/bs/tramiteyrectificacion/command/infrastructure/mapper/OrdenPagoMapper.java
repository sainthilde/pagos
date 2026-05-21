package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.OrdenPagoUpdateRequestDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.OrdenDePagoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity.OrdenDePago;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrdenPagoMapper {

    @Mapping(target = "documentoId", source = "documentoId")
    OrdenDePago modelToEntity(OrdenDePagoModel model);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "documentoId", source = "documentoId")
    @Mapping(target = "escalaId", source = "escalaId")
    @Mapping(target = "estadoOrdenPago", source = "estadoOrdenPago")
    @Mapping(target = "cancelarDestinoDelPago", source = "cancelarDestinoDelPago")
    OrdenDePagoModel entityToModel(OrdenDePago ordenDePago);

    List<OrdenDePagoModel> entityListToModelList(List<OrdenDePago> ordenDePagos);

    @Mapping(target = "usuidRegAud", source = "user")
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "documentoId", source = "dto.documentoId")
    @Mapping(target = "estadoOrdenPago", source = "dto.estadoOrdenPago")
    @Mapping(target = "escalaId", source = "dto.escalaId")
    @Mapping(target = "cancelarDestinoDelPago", source = "dto.cancelarDestinoDelPago")
    OrdenDePagoModel dtoToModel(OrdenPagoUpdateRequestDto dto, String user);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "documentoId", source = "documentoId")
    @Mapping(target = "estadoOrdenPago", source = "estadoOrdenPago")
    @Mapping(target = "escalaId", source = "escalaId")
    @Mapping(target = "cancelarDestinoDelPago", source = "cancelarDestinoDelPago")
    OrdenPagoUpdateRequestDto modelToDto(OrdenDePagoModel entity);

}
