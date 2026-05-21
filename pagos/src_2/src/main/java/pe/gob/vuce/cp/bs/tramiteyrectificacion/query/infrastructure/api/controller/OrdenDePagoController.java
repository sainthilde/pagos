package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.api.OrdenDePagoApi;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.application.service.OrdenDePagoService;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.enums.MetaResults;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.OrdenDePagoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.mapper.OrdenDePagoDtoMapper;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.ApiResponseMetadata;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.ApiResponseOrdenPagoDto;
import pe.gob.vuce.cp.framework.globallogger.annotation.Loggable;

import java.util.List;
import java.util.Map;

/**
 * Controlador que gestiona las ordenes de pago de una escala específica.
 *
 * @project cp-api-bs-tramiteyrectificacion-query
 * @autor Elver
 * @date 24/08/2024
 */
@RestController
@AllArgsConstructor
public class OrdenDePagoController implements OrdenDePagoApi {
    OrdenDePagoService ordenDePagoService;
    OrdenDePagoDtoMapper ordenDePagoDtoMapper;

    /**
     * Método que obtiene las ordenes de pago de una escala específica.
     *
     * @project cp-api-bs-tramiteyrectificacion-query
     * @autor Elver
     * @date 24/08/2024
     */
    @Loggable
    @Override
    public ResponseEntity<ApiResponseOrdenPagoDto> findOrdenesDePago(Integer escalaId, Integer documentId,
                                                                     String rucAgente, String estadoOrdenPago) {
        List<OrdenDePagoModel> ordenesDePagoModel = ordenDePagoService.findOrdenesDePago(escalaId, documentId,
                rucAgente,estadoOrdenPago);
        ApiResponseOrdenPagoDto response = new ApiResponseOrdenPagoDto();
        response.setData(ordenDePagoDtoMapper.doToDto(ordenesDePagoModel));
        ApiResponseMetadata meta = new ApiResponseMetadata();

        meta.setResult(MetaResults.SUCCESS.getValue());
        meta.setAtributos(Map.of());
        meta.setCantidadRegistros(response.getData().size());
        meta.setCantidadRegistrosTotal(response.getData().size());
        response.setMeta(meta);
        return ResponseEntity.ok(response);

    }
}
