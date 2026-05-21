package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.OrdenDePagoModel;

/**
 * Puerto de salida para las operaciones de persistencia de la entidad Tramite.
 * Proporciona la interfaz para guardar, actualizar, y buscar fichas sanitarias
 * en el repositorio.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Luis Francisco Huertas Mostacero
 * @date 15/08/2024
 */
@Component
public interface OrdenPagoRepositoryPort {

    /**
     * Guarda una nueva ficha sanitaria en el repositorio.
     *
     * @param ordenDePago La entidad Tramite que se va a guardar.
     * @return La entidad guardada.
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Luis Francisco Huertas Mostacero
     * @date 15/08/2024
     */
    OrdenDePagoModel save(OrdenDePagoModel ordenDePago);

    /**
     * Actualiza una ficha sanitaria existente en el repositorio.
     *
     * @param ordenDePago La entidad Tramite que se va a actualizar.
     * @return La entidad actualizada.
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Luis Francisco Huertas Mostacero
     * @date 15/08/2024
     */
    OrdenDePagoModel updateV2(OrdenDePagoModel ordenDePago);

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
    Optional<OrdenDePagoModel> findById(Integer id);

    List<OrdenDePagoModel> findByTramiteTramiteId(Integer id);

    List<OrdenDePagoModel> findAllByTramiteTramiteIdAndEstadoOrdenPagoIn(Integer id, List<String> estadoOrdenPagos);

    List<OrdenDePagoModel> findAllByEscalaIdAndEstadoOrdenPagoIn(Integer escalaId, List<String> estadoOrdenPagos);

    List<OrdenDePagoModel> findByEscalaId(Integer id);

    List<OrdenDePagoModel> findByDocumentoIdAndEscalaIdAndRucAgente(Integer documentoId, Integer escalaId,
            String rucAgente);
}
