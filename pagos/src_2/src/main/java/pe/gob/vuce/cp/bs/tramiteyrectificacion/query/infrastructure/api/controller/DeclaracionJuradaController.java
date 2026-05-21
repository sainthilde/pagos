package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.api.DjjApi;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.application.service.DeclaracionJuradaService;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.enums.MetaResults;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.DeclaracionJuradaModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.mapper.DeclaracionJuradaMapper;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.ApiResponseDeclaracionJuradaDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.ApiResponseDeclaracionJuradaListaDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.ApiResponseMetadata;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.DeclaracionJuradaListaDto;
import pe.gob.vuce.cp.framework.globallogger.annotation.Loggable;

/**
 * Controlador que gestiona las declaraciones juradas de una escala específica.
 *
 * @project cp-api-bs-tramiteyrectificacion-query
 * @autor Elver
 * @date 24/08/2024
 */
@RestController
@AllArgsConstructor
public class DeclaracionJuradaController implements DjjApi {

    private final DeclaracionJuradaService declaracionJuradaService;

    private final DeclaracionJuradaMapper declaracionJuradaMapper;

    /**
     * Método que obtiene las declaraciones juradas de una escala específica.
     *
     * @project cp-api-bs-tramiteyrectificacion-query
     * @autor Elver
     * @date 24/08/2024
     */
    @Loggable
    @Override
    public ResponseEntity<ApiResponseDeclaracionJuradaDto> obtenerDeclaracionesJuradas(@PathVariable Integer escalaId) {
        List<DeclaracionJuradaModel> declaracionesJuradasModel = declaracionJuradaService
                .buscarDeclaracionesJuradas(escalaId);

        ApiResponseDeclaracionJuradaDto response = new ApiResponseDeclaracionJuradaDto();
        response.setData(declaracionJuradaMapper.toDeclaracionJuradaDtoList(declaracionesJuradasModel));
        ApiResponseMetadata meta = new ApiResponseMetadata();

        meta.setCantidadRegistros(declaracionesJuradasModel.size());
        meta.setCantidadRegistrosTotal(declaracionesJuradasModel.size());
        meta.setResult(MetaResults.SUCCESS.getValue());
        meta.setAtributos(Map.of());
        response.setMeta(meta);

        return ResponseEntity.ok(response);
    }

    @Loggable
    @Override
    public ResponseEntity<ApiResponseDeclaracionJuradaDto> obtenerDeclaracionesJuradasByEstado(
            @RequestParam Integer escalaId, @RequestParam String rucAgente, @RequestParam String estado,
            @RequestParam Integer documentId, @RequestParam String estadoDdjjPago) {
        List<DeclaracionJuradaModel> declaracionesJuradasModel = declaracionJuradaService
                .buscarDeclaracionesJuradas(escalaId, estado, documentId, estadoDdjjPago, rucAgente);

        ApiResponseDeclaracionJuradaDto response = new ApiResponseDeclaracionJuradaDto();
        response.setData(declaracionJuradaMapper.toDeclaracionJuradaDtoList(declaracionesJuradasModel));
        ApiResponseMetadata meta = new ApiResponseMetadata();

        meta.setResult(MetaResults.SUCCESS.getValue());
        meta.setAtributos(Map.of());
        response.setMeta(meta);
        return ResponseEntity.ok(response);
    }

    @Loggable
    @Override
    public ResponseEntity<ApiResponseDeclaracionJuradaListaDto> obtenerListadoDeclaracionesJuradas(
            pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.@Valid GetDjjQueryParamsDto queryParams) {
        Page<DeclaracionJuradaListaDto> djjs = declaracionJuradaService.getDjjs(queryParams);

        ApiResponseDeclaracionJuradaListaDto response = new ApiResponseDeclaracionJuradaListaDto();
        response.setData(djjs.getContent());

        ApiResponseMetadata meta = new ApiResponseMetadata();
        meta.setResult(MetaResults.SUCCESS.getValue());
        meta.setAtributos(Map.of());
        meta.setCantidadRegistros(response.getData().size());
        meta.setCantidadRegistrosTotal((int) djjs.getTotalElements());

        response.setMeta(meta);

        return ResponseEntity.ok(response);
    }
}
