package pe.gob.vuce.cp.sp.pagos.application.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pe.gob.vuce.cp.sp.pagos.domain.model.ActividadEntidad;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.ActividadEntidadUseCase;

import java.util.Optional;

/**
 * Servicio para gestionar las actividades de una entidad.
 * Este servicio implementa el caso de uso relacionado con la actividad de entidades,
 * proporcionando métodos para recuperar información basada en los identificadores de la entidad y documentos.
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Service
@AllArgsConstructor
public class ActividadEntidadService  {
    private final ActividadEntidadUseCase actividadEntidadUseCase;

    /**
     * Método responsable de buscar una ActividadEntidad
     * basándose en los identificadores de entidad, actividad y código de puerto nacional.
     *
     * @param entidadId      ID de la entidad (required).
     * @param actividadId    ID de la actividad (required).
     * @param codPuertoNacional código del puerto nacional (required).
     * @return un Optional que puede contener la ActividadEntidad si se encuentra,
     *         o vacío si no existe.
     */
    public Optional<ActividadEntidad> findByEntidadIdAndDocumentoIdAndPuertoDue(Integer entidadId, Integer actividadId, String codPuertoNacional) {
        return actividadEntidadUseCase.findByEntidadIdAndDocumentoIdAndPuertoDue(entidadId, actividadId, codPuertoNacional);
    }
}
