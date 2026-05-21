package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.port.out;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.TramiteModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.GetTramiteQueryParamsDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.TramitePagoDto;

/**
 * Puerto del repositorio para la consulta de trámites.
 * Define los métodos necesarios para interactuar con la capa de persistencia.
 * 
 * @project cp-api-bs-tramiteyrectificacion-query
 * @autor Luis Francisco Huertas Mostacero
 * @date 24/08/2024
 */
public interface TramiteRepositoryPort {

    /**
     * Obtiene un trámite específico basado en su ID.
     * 
     * @param id Identificador del trámite a consultar.
     * @return Lista de objetos TramiteModel con los detalles del trámite
     *         consultado.
     */
    List<TramiteModel> obtenerTramite(Integer id);

    /**
     * Obtiene una lista paginada de trámites basados en los parámetros de consulta.
     * 
     * @param getTramiteQueryParamsDto Objeto que contiene los parámetros de
     *                                 consulta.
     * @return Página de objetos TramiteModel con los trámites que coinciden con los
     *         parámetros de consulta.
     */
    Page<TramiteModel> obtenerTramites(GetTramiteQueryParamsDto getTramiteQueryParamsDto);

    Optional<TramiteModel> findByEscalaIdAndDocumentoId(Integer escalaId, Integer documentoId);

    TramitePagoDto getIndNoRequierePagoByEscalaIdAndIndicadorEs(Integer escalaId, String indicadorEs,
            Integer documentoId);
}
