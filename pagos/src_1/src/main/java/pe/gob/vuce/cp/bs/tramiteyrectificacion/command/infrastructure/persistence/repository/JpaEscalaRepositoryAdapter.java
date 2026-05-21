package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.repository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.exceptions.BusinessError;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.EscalaModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.EscalaRepositoryPort;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.mapper.EscalaMapper;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity.Escala;

/**
 * Adaptador JPA que implementa el puerto {@link EscalaRepositoryPort}
 * para la gestión de escalas dentro del dominio.
 *
 * Utiliza {@link EscalaRepository} para la persistencia y {@link EscalaMapper}
 * para la conversión entre entidad y modelo de dominio.
 *
 * Maneja errores mediante {@link BusinessError} en caso de que la escala no exista.
 *
 */
@Component
public class JpaEscalaRepositoryAdapter implements EscalaRepositoryPort {
    private final EscalaRepository escalaRepository;
    private final EscalaMapper escalaMapper;

    private static final String ESCALA_NOT_FOUND = "Escala not found";
    private static final String ESCALA_NOT_FOUND_CODE = "ESCALA_NOT_FOUND";

    public JpaEscalaRepositoryAdapter(EscalaRepository escalaRepository,
                                      EscalaMapper escalaMapper) {
        this.escalaRepository = escalaRepository;
        this.escalaMapper = escalaMapper;
    }

    /**
     * Obtiene el ID del estado del Due asociado a una escala específica.
     *
     * @param escalaId El ID de la escala.
     * @return El ID del estado del Due.
     * @throws BusinessError Si no se encuentra la escala.
     */
    @Override
    public Integer getEstadoDueId(Integer escalaId) {
        Escala escala = escalaRepository.findById(escalaId).orElseThrow(
                () -> new BusinessError(HttpStatus.NOT_FOUND, ESCALA_NOT_FOUND, null, ESCALA_NOT_FOUND_CODE));
        return escala.getEstadoDueId();
    }

    /**
     * Busca una escala por su ID.
     *
     * @param escalaId El ID de la escala.
     * @return El modelo de escala si se encuentra, de lo contrario {@code null}.
     */
    @Override
    public EscalaModel findById(Integer escalaId) {
        return escalaRepository.findById(escalaId).map(
                escalaMapper::entityToModel
        ).orElse(null);
    }

}
