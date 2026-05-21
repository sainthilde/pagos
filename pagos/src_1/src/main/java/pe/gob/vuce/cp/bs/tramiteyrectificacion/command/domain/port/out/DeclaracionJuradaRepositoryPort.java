package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.DeclaracionJuradaModel;

/**
 * Puerto de salida para las operaciones de persistencia de la entidad Tramite.
 * Proporciona la interfaz para guardar, actualizar, y buscar fichas sanitarias
 * en el repositorio.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Luis Francisco Huertas Mostacero
 * @date 15/08/2024
 */
public interface DeclaracionJuradaRepositoryPort {

    /**
     * Guarda una nueva ficha sanitaria en el repositorio.
     *
     * @param declaracionJurada La entidad Tramite que se va a guardar.
     * @return La entidad guardada.
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Luis Francisco Huertas Mostacero
     * @date 15/08/2024
     */
    DeclaracionJuradaModel save(DeclaracionJuradaModel declaracionJurada);

    /**
     * Busca una ficha sanitaria por su ID en el repositorio.
     *
     * @param id El identificador de la ficha sanitaria.
     * @return Un Optional que contiene la entidad encontrada, o vacío si no se
     *         encuentra.
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Luis Francisco Huertas Mostacero
     * @date 15/08/2024
     */
    Optional<DeclaracionJuradaModel> findById(Integer id);

    List<DeclaracionJuradaModel> findByTramiteTramiteId(Integer id);

    List<DeclaracionJuradaModel> findByEscalaId(Integer id);

    List<DeclaracionJuradaModel> findByDocumentoIdAndEscalaIdAndRucAgente(Integer documentoId, Integer escalaId,
            String rucAgente);

    int countByFechaSolicitudDdjjBetween(LocalDateTime startDate, LocalDateTime endDate);
}
