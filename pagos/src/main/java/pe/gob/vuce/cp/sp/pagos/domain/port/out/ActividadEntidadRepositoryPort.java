package pe.gob.vuce.cp.sp.pagos.domain.port.out;

import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.domain.model.ActividadEntidad;

import java.util.Optional;

/**
 * Puerto de repositorio para la actividad de entidad.
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Component
public interface ActividadEntidadRepositoryPort {
    /**
     * Busca una actividad de entidad por entidad, actividad y puerto.
     *
     * @param entidadId          Identificador de la entidad.
     * @param actividadId        Identificador de la actividad.
     * @param codPuertoNacional  Código del puerto nacional.
     * @return                   Un {@code Optional} con la actividad de la entidad si se encuentra.
     */
    Optional<ActividadEntidad> findByEntidadIdAndDocumentoIdAndPuertoDue(Integer entidadId, Integer actividadId, String codPuertoNacional);
}
