package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.TramiteModel;

import java.util.List;

/**
 * Caso de uso para obtener un documento.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 22/08/2024
 */
public interface SearchTramiteUseCase {

    List<TramiteModel> findByEscalaId(Integer id);

}
