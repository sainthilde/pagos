package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.application.usecase;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.TramiteModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.port.in.ObtenerTramiteUseCase;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.port.out.TramiteRepositoryPort;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.GetTramiteQueryParamsDto;

/**
 * Implementación del caso de uso para la obtención de trámites en el sistema.
 * 
 * @project cp-api-bs-tramiteyrectificacion-query
 * @autor Luis Francisco Huertas Mostacero
 * @date 24/08/2024
 */
@Service
public class ObtenerTramiteUseCaseImpl implements ObtenerTramiteUseCase {

    private final TramiteRepositoryPort tramiteRepositoryPort;

    /**
     * Constructor de la implementación ObtenerTramiteUseCaseImpl.
     * 
     * @param tramiteRepositoryPort Puerto del repositorio para la consulta de trámites.
     * @project cp-api-bs-tramiteyrectificacion-query
     * @autor Luis Francisco Huertas Mostacero
     * @date 24/08/2024
     */
    public ObtenerTramiteUseCaseImpl(TramiteRepositoryPort tramiteRepositoryPort) {
        this.tramiteRepositoryPort = tramiteRepositoryPort;
    }

    /**
     * Obtiene un trámite específico basado en su ID.
     * 
     * @param id Identificador del trámite a consultar.
     * @return Lista de objetos TramiteModel con los detalles del trámite consultado.
     * @project cp-api-bs-tramiteyrectificacion-query
     * @autor Luis Francisco Huertas Mostacero
     * @date 24/08/2024
     */
    @Override
    public List<TramiteModel> obtenerTramite(Integer id) {
        return tramiteRepositoryPort.obtenerTramite(id);
    }

    /**
     * Obtiene una lista paginada de trámites basados en los parámetros de consulta.
     * 
     * @param getTramiteQueryParamsDto Objeto que contiene los parámetros de consulta.
     * @return Página de objetos TramiteModel con los trámites que coinciden con los parámetros de consulta.
     * @project cp-api-bs-tramiteyrectificacion-query
     * @autor Luis Francisco Huertas Mostacero
     * @date 24/08/2024
     */
    @Override
    public Page<TramiteModel> obtenerTramites(GetTramiteQueryParamsDto getTramiteQueryParamsDto) {
        return tramiteRepositoryPort.obtenerTramites(getTramiteQueryParamsDto);
    }

}
