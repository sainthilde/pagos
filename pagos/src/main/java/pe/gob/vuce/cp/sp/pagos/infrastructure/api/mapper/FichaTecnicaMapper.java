package pe.gob.vuce.cp.sp.pagos.infrastructure.api.mapper;

import org.mapstruct.ReportingPolicy;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import pe.gob.vuce.cp.sp.pagos.domain.model.FichaTecnicaModel;
import pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.entity.FichaTecnica;
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface FichaTecnicaMapper {

    @Mapping(source = "fichaTecnicaId", target = "fichaTecnicaId")
    FichaTecnicaModel toDomain(FichaTecnica fichaTecnica);
}
