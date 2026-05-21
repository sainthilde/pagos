package pe.gob.vuce.cp.sp.pagos.infrastructure.mapper;

import org.mapstruct.ReportingPolicy;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import pe.gob.vuce.cp.sp.pagos.domain.model.EscalaModel;
import pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.entity.Escala;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EscalaMapper {
    EscalaModel entityToModel(Escala escala);

}
