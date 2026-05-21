package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.repository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.exceptions.BusinessError;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.ActividadEntidadPuertoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.ActividadEntidadPuertoPort;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.mapper.ActividadEntidadPuertoMapper;

import java.util.List;

/**
 * Adaptador de repositorio JPA que implementa el puerto {@link ActividadEntidadPuertoPort}
 * para la gestión de relaciones entre actividades y puertos nacionales.
 *
 * Este componente se encarga de interactuar con la base de datos a través del repositorio
 **/
@Component
@AllArgsConstructor
public class JpaActividadEntidadPuertoRepositoryAdapter implements ActividadEntidadPuertoPort {
    private final ActividadEntidadPuertoRepository actividadEntidadPuertoRepository;
    private final ActividadEntidadPuertoMapper actividadEntidadPuertoMapper;

    @Override
    public ActividadEntidadPuertoModel findByActividadIdAndCodPuertoNacionalAndEstado(Integer actividadId,
            String codPuertoNacional, String estado) {
        return actividadEntidadPuertoRepository.findByActividadIdAndCodPuertoNacionalAndEstado(actividadId,
                codPuertoNacional, estado)
                .map(actividadEntidadPuertoMapper::toModel)
                .orElseThrow(() -> new BusinessError(
                        HttpStatus.NOT_FOUND,
                        "ERROR.ACTIVIDAD_PUERTO.NOT_FOUND",
                        List.of(actividadId.toString(), codPuertoNacional),
                        String.format("No se encontró la actividad puerto con ID %d y código de puerto %s",
                                actividadId, codPuertoNacional)));
    }

}
