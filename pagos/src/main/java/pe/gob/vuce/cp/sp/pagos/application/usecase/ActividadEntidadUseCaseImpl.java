package pe.gob.vuce.cp.sp.pagos.application.usecase;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.domain.model.ActividadEntidad;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.ActividadEntidadUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.ActividadEntidadRepositoryPort;
import java.util.Optional;


/**
 * Implementación del caso de uso para gestionar actividades de entidades.
 * Esta clase proporciona métodos para acceder a la información de actividades
 * a través del repositorio correspondiente.
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Component
@AllArgsConstructor
public class ActividadEntidadUseCaseImpl implements ActividadEntidadUseCase {

    private final ActividadEntidadRepositoryPort actividadEntidadRepositoryPort;
    /**
     * Método responsable de buscar una ActividadEntidad utilizando los identificadores
     * de entidad, actividad y código de puerto nacional.
     *
     * @param actividadEntidadId  ID de la entidad de actividad (required).
     * @param actividadId         ID de la actividad (required).
     * @param codPuertoNacional   código del puerto nacional (required).
     * @return un Optional que puede contener la ActividadEntidad si se encuentra,
     *         o vacío si no existe.
     */
    @Override
    public Optional<ActividadEntidad> findByEntidadIdAndDocumentoIdAndPuertoDue(Integer actividadEntidadId, Integer actividadId, String codPuertoNacional) {
        return actividadEntidadRepositoryPort.findByEntidadIdAndDocumentoIdAndPuertoDue(actividadEntidadId, actividadId, codPuertoNacional)
                .stream()
                .findFirst();
    }
}
