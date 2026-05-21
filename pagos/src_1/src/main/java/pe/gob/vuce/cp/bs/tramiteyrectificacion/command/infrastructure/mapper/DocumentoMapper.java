package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.mapper;

import java.util.List;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.DocumentoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity.mae.Documento;

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
public interface DocumentoMapper {

    /**
     * Convierte una entidad de persistencia en un modelo de documento.
     *
     * @param tramite La entidad de documento.
     * @return El modelo de documento correspondiente.
     */
    DocumentoModel entityToModel(Documento tramite);

    List<DocumentoModel> entityToModel(List<Documento> tramites);

}
