package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.api.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.api.TramiteApi;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.application.service.TramiteService;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.TramiteModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.configuration.ControllerResponseUtil;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.mapper.TramiteDtoMapper;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.ApiResponseTramiteDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.TramiteDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.ApiResponseTramiteDetalleDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.TramiteDetalleDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.ApiResponseTramitePagoDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.TramitePagoDto;
import pe.gob.vuce.cp.framework.globallogger.annotation.Loggable;


/**
 * Controlador REST para la gestión de trámites.
 * Implementa las operaciones definidas en la interfaz TramiteApi.
 *
 * @project cp-api-bs-tramiteyrectificacion-query
 * @autor Luis Francisco Huertas Mostacero
 * @date 24/08/2024
 */
@RestController
public class TramiteController implements TramiteApi {

    private final TramiteService tramiteService;
    private final TramiteDtoMapper tramiteDtoMapper;

    /**
     * Constructor del controlador TramiteController.
     *
     * @param tramiteService   Servicio que maneja la lógica de negocio relacionada
     *                         con los trámites.
     * @param tramiteDtoMapper Mapper para convertir modelos de trámite a DTOs.
     * @project cp-api-bs-tramiteyrectificacion-query
     * @autor Luis Francisco Huertas Mostacero
     * @date 24/08/2024
     */
    public TramiteController(TramiteService tramiteService, TramiteDtoMapper tramiteDtoMapper) {
        this.tramiteService = tramiteService;
        this.tramiteDtoMapper = tramiteDtoMapper;
    }

    /**
     * Endpoint para obtener una lista paginada de trámites basada en los parámetros
     * de consulta.
     *
     * @param queryParams DTO que contiene los parámetros de consulta.
     * @return ResponseEntity con el ApiResponseTramiteDto que incluye la lista de
     *         trámites y los metadatos de la respuesta.
     */
    @Loggable
    @Override
    public ResponseEntity<ApiResponseTramiteDto> obtenerTramites(
            pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.@Valid GetTramiteQueryParamsDto queryParams) {

        Page<TramiteModel> tramites = tramiteService.obtenerTramites(queryParams);
        List<TramiteDto> data = tramiteDtoMapper.toTramiteDtoList(tramites.getContent());

        ApiResponseTramiteDto response = ControllerResponseUtil.buildResponse(
                new ApiResponseTramiteDto(),
                data,
                data.size(),
                (int) tramites.getTotalElements()
        );

        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para obtener un trámite específico basado en su ID.
     *
     * @param idTramite Identificador del trámite a consultar.
     * @return ResponseEntity con el ApiResponseTramiteDto que incluye los detalles
     *         del trámite y los metadatos de la respuesta.
     */
    @Loggable
    @Override
    public ResponseEntity<ApiResponseTramiteDetalleDto> obtenerTramite(Integer idTramite) {
        List<TramiteModel> tramite = tramiteService.obtenerTramite(idTramite);
        List<TramiteDetalleDto> data = tramiteDtoMapper.toTramiteDetalleDtoList(tramite);

        ApiResponseTramiteDetalleDto response = ControllerResponseUtil.buildResponse(
                new ApiResponseTramiteDetalleDto(),
                data,
                1,
                1
        );

        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para obtener un trámite específico basado en escalaId, documentoId e
     * indicadorES.
     *
     * @param escalaId    Identificador de la escala.
     * @param documentoId Identificador del documento.
     * @return ResponseEntity con el ApiResponseTramiteDetalleDto que incluye los
     *         detalles
     *         del trámite y los metadatos de la respuesta.
     */
    @Loggable
    @Override
    public ResponseEntity<ApiResponseTramiteDetalleDto> obtenerTramitePorEscalaYDocumento(
            Integer escalaId, Integer documentoId) {

        Optional<TramiteModel> tramiteOptional = tramiteService.obtenerTramitePorEscalaYDocumento(escalaId,
                documentoId);

        List<TramiteDetalleDto> data = tramiteOptional
                .map(tramiteDtoMapper::toTramiteDetalleDto)
                .map(List::of)
                .orElse(List.of());

        ApiResponseTramiteDetalleDto response = ControllerResponseUtil.buildResponse(
                new ApiResponseTramiteDetalleDto(),
                data,
                data.size(),
                data.size()
        );

        return ResponseEntity.ok(response);
    }

    @Loggable
    @Override
    public ResponseEntity<ApiResponseTramitePagoDto> obtenerTramiteReglaPago(Integer escalaId, Integer documentoId,
            String indicadorEs) {
        TramitePagoDto tramitePagoDto = tramiteService.getIndNoRequierePagoByEscalaIdAndIndicadorEs(escalaId, indicadorEs, documentoId);

        List<TramitePagoDto> data = tramitePagoDto != null ? List.of(tramitePagoDto) : List.of();

        ApiResponseTramitePagoDto response = ControllerResponseUtil.buildResponse(
                new ApiResponseTramitePagoDto(),
                data,
                data.size(),
                data.size()
        );

        return ResponseEntity.ok(response);
    }
}
