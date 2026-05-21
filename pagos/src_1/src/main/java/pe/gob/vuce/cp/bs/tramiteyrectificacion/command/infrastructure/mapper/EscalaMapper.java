package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.mapper;

import org.mapstruct.ReportingPolicy;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.EscalaModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity.Escala;
import java.util.List;

/**
 * Interfaz que define los métodos de mapeo entre los modelos de dominio, DTOs,
 * y entidades de persistencia
 * relacionados con el documento. Utiliza MapStruct para generar las
 * implementaciones de los métodos de mapeo.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 22/08/2024
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EscalaMapper {

    /**
     * Convierte una entidad de persistencia en un modelo de documento.
     *
     * @param escala La entidad de escala.
     * @return El modelo de documento correspondiente.
     */
    EscalaModel entityToModel(Escala escala);

    List<EscalaModel> entityToModel(List<Escala> escalaList);

}
