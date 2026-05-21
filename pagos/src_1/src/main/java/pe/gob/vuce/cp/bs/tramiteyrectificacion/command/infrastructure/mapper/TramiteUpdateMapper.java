package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.mapper;

import org.mapstruct.MappingTarget;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity.Tramite;

/**
 * Interfaz que define el mapeo para actualizar una entidad de
 * Tramite existente
 * con los valores de una nueva entidad. Utiliza MapStruct para generar las
 * implementaciones.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 21/08/2024
 */
@Mapper(componentModel = "spring")
public interface TramiteUpdateMapper {

    /**
     * Actualiza una entidad existente de Tramite con los valores
     * de una nueva entidad,
     * ignorando propiedades nulas y el campo de identificador.
     *
     * @param updatedTramite La entidad de Tramite con los nuevos
     *                      valores.
     * @param existingTramite La entidad existente de Tramite a
     *                      actualizar.
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Fernando Tanta
     * @date 21/08/2024
     */
    @Mapping(target = "tramiteId", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTramiteFromDto(Tramite updatedTramite,
            @MappingTarget Tramite existingTramite);
}
