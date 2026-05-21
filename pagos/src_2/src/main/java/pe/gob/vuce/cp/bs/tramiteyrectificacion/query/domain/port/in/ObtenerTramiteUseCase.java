package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.port.in;

import java.util.List;

import org.springframework.data.domain.Page;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.TramiteModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.GetTramiteQueryParamsDto;

/**
 * Caso de uso para la obtención de trámites en el sistema.
 * Define los métodos necesarios para recuperar información de trámites.
 * 
 * @project cp-api-bs-tramiteyrectificacion-query
 * @autor Luis Francisco Huertas Mostacero
 * @date 24/08/2024
 */
public interface ObtenerTramiteUseCase {

    /**
     * Obtiene un trámite específico basado en su ID.
     * 
     * @param id Identificador del trámite a consultar.
     * @return Lista de objetos TramiteModel con los detalles del trámite consultado.
     */
    List<TramiteModel> obtenerTramite(Integer id);

    /**
     * Obtiene una lista paginada de trámites basados en los parámetros de consulta.
     * 
     * @param getTramiteQueryParamsDto Objeto que contiene los parámetros de consulta.
     * @return Página de objetos TramiteModel con los trámites que coinciden con los parámetros de consulta.
     */
    Page<TramiteModel> obtenerTramites(GetTramiteQueryParamsDto getTramiteQueryParamsDto);
}
