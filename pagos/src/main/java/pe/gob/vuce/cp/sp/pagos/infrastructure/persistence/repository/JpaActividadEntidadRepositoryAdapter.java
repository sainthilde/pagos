package pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.domain.model.ActividadEntidad;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.ActividadEntidadRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.mapper.ActividadEntidadMapper;
import pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.entity.ActividadEntidadEntity;
import java.util.Optional;

/**
 * Adaptador de repositorio para la entidad ActividadEntidad.
 * Este adaptador implementa la interfaz ActividadEntidadRepositoryPort,
 * proporcionando una capa de acceso a datos utilizando JPA.
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Component
@AllArgsConstructor
public class JpaActividadEntidadRepositoryAdapter implements ActividadEntidadRepositoryPort {

    private final JpaActividadEntidadRepository repository;
    private final ActividadEntidadMapper mapper;

    /**
     * Encuentra una ActividadEntidad basándose en el ID de entidad, ID de actividad
     * y código de puerto nacional proporcionados.
     *
     * @param entidadId El ID de la entidad a buscar.
     * @param actividadId El ID de la actividad a buscar.
     * @param codPuertoNacional El código del puerto nacional asociado.
     * @return Un Optional que contiene la ActividadEntidad si se encuentra, o vacío si no.
     */
    @Override
    public Optional<ActividadEntidad> findByEntidadIdAndDocumentoIdAndPuertoDue(Integer entidadId, Integer actividadId, String codPuertoNacional) {
        Optional<ActividadEntidadEntity> entidadEntity = entidadId > 0 ? repository.findByEntidadIdAndActividadIdAndCodPuertoNacional(entidadId, actividadId, codPuertoNacional)
            : repository.findByActividadIdAndCodPuertoNacional(actividadId,codPuertoNacional);
        return entidadEntity.map(mapper::actividadEntityToModel);
    }
}
