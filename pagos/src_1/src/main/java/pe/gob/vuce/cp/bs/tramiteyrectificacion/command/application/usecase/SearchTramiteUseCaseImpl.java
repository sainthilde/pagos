package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.application.usecase;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.TramiteModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.SearchTramiteUseCase;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.TramiteRepositoryPort;

/**
 * Implementación del caso de uso obtener un documento.
 * 
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 22/08/2024
 */
@AllArgsConstructor
@Component
public class SearchTramiteUseCaseImpl implements SearchTramiteUseCase {

    private final TramiteRepositoryPort tramiteRepositoryPort;

    @Override
    @Transactional
    public List<TramiteModel> findByEscalaId(Integer id) {
        return tramiteRepositoryPort.findByEscalaId(id);
    }

}
