package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.TramiteModel;

/**
 * Puerto de salida para las operaciones de persistencia de la entidad Tramite.
 * Proporciona la interfaz para guardar y buscar tramites en el repositorio.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 19/08/2024
 */
@Component
public interface TramiteRepositoryPort {

    /**
     * Guarda un nuevo tramite en el repositorio.
     *
     * @param tramite La entidad Tramite que se va a guardar.
     * @return La entidad guardada.
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Fernando Tanta
     * @date 19/08/2024
     */
    TramiteModel save(TramiteModel tramite);

    /**
     * Busca un tramite por su ID en el repositorio.
     *
     * @param id El identificador del tramite
     * @return Un Optional que contiene la entidad encontrada, o vacío si no se
     *         encuentra.
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Fernando Tanta
     * @date 19/08/2024
     */

    /**
     * Actualiza un tramite en el repositorio.
     *
     * @param tramite La entidad Tramite que se va a actualizar.
     * @return La entidad actualizada.
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Fernando Tanta
     * @date 19/08/2024
     */
    TramiteModel update(TramiteModel tramite);

    Optional<TramiteModel> findById(Integer id);
    Optional<TramiteModel> findByIdAndEscalaId(Integer tramiteId, Integer escalaId);

    List<TramiteModel> findByEscalaId(Integer id);

    /**
     * Obtiene un correlativo del tramite en el repositorio.
     *
     * @param inicio El identificador de la fecha inicio del correlativo a obtener
     * @param fin    El identificador de la fecha fin del correlativo a obtener
     * @return Un Integer que contiene el correlativo para un nuevo tramite
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Fernando Tanta
     * @date 19/08/2024
     */
    Integer getNumeroTramitePorAnio(LocalDateTime inicio, LocalDateTime fin);
    TramiteModel findByEscalaIdAndDocumentoId(Integer escalaId,Integer documentoId);
    List<TramiteModel> findAllByEscalaId(Integer escalaId);

    List<TramiteModel> findAllByEscalaIdAndDocumentoIdIn(Integer escalaId, List<Integer> documentoIds);
}
