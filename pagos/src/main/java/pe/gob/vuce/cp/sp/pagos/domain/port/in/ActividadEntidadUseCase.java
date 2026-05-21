package pe.gob.vuce.cp.sp.pagos.domain.port.in;


import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.domain.model.ActividadEntidad;

import java.util.Optional;

/**
 * Caso de uso para operaciones relacionadas con la actividad de la entidad.
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Component
public interface ActividadEntidadUseCase {
    /**
     * Busca una actividad de entidad según los parámetros de entidad, actividad y puerto.
     *
     * @param entidadId          Identificador de la entidad.
     * @param actividadId        Identificador de la actividad.
     * @param codPuertoNacional  Código del puerto nacional.
     * @return                   Un {@code Optional} con la actividad de la entidad si se encuentra.
     */
    Optional<ActividadEntidad> findByEntidadIdAndDocumentoIdAndPuertoDue(Integer entidadId, Integer actividadId, String codPuertoNacional);
}
