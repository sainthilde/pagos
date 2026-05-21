package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.application.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.TramiteModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.port.out.TramiteRepositoryPort;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.GetTramiteQueryParamsDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.TramitePagoDto;

/**
 * Servicio encargado de gestionar la consulta de trámites en el sistema.
 * 
 * @project cp-api-bs-tramiteyrectificacion-query
 * @autor Luis Francisco Huertas Mostacero
 * @date 24/08/2024
 */
@Service
public class TramiteService {

    private final TramiteRepositoryPort tramiteRepositoryPort;

    /**
     * Constructor del servicio TramiteService.
     * 
     * @param tramiteRepositoryPort Puerto del repositorio para la consulta de
     *                              trámites.
     * @project cp-api-bs-tramiteyrectificacion-query
     * @autor Luis Francisco Huertas Mostacero
     * @date 24/08/2024
     */
    public TramiteService(TramiteRepositoryPort tramiteRepositoryPort) {
        this.tramiteRepositoryPort = tramiteRepositoryPort;
    }

    /**
     * Obtiene un trámite específico basado en su ID.
     * 
     * @param id Identificador del trámite a consultar.
     * @return Lista de objetos TramiteModel con los detalles del trámite
     *         consultado.
     * @project cp-api-bs-tramiteyrectificacion-query
     * @autor Luis Francisco Huertas Mostacero
     * @date 24/08/2024
     */
    public List<TramiteModel> obtenerTramite(Integer id) {
        return tramiteRepositoryPort.obtenerTramite(id);
    }

    /**
     * Obtiene una lista paginada de trámites basados en los parámetros de consulta.
     * 
     * @param getTramiteQueryParamsDto Objeto que contiene los parámetros de
     *                                 consulta.
     * @return Página de objetos TramiteModel con los trámites que coinciden con los
     *         parámetros de consulta.
     * @project cp-api-bs-tramiteyrectificacion-query
     * @autor Luis Francisco Huertas Mostacero
     * @date 24/08/2024
     */
    public Page<TramiteModel> obtenerTramites(GetTramiteQueryParamsDto getTramiteQueryParamsDto) {
        return tramiteRepositoryPort.obtenerTramites(getTramiteQueryParamsDto);
    }

    public Optional<TramiteModel> obtenerTramitePorEscalaYDocumento(Integer escalaId, Integer documentoId) {
        return tramiteRepositoryPort.findByEscalaIdAndDocumentoId(escalaId, documentoId);
    }

    public TramitePagoDto getIndNoRequierePagoByEscalaIdAndIndicadorEs(Integer escalaId, String indicadorEs,
            Integer documentoId) {
        return tramiteRepositoryPort.getIndNoRequierePagoByEscalaIdAndIndicadorEs(escalaId, indicadorEs, documentoId);
    }
}
