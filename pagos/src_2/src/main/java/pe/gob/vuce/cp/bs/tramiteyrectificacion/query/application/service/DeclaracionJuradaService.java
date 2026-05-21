package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.application.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.DeclaracionJuradaModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.port.in.ObtenerDeclaracionJuradaUseCase;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.DeclaracionJuradaListaDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.GetDjjQueryParamsDto;

/**
 * Servicio encargado de gestionar la consulta de declaraciones juradas en el
 * sistema.
 *
 * @project cp-api-bs-tramiteyrectificacion-query
 * @autor Elver
 * @date 24/08/2024
 */
@Service
@AllArgsConstructor
public class DeclaracionJuradaService {
    private final ObtenerDeclaracionJuradaUseCase obtenerDeclaracionJurada;

    /**
     * Busca las declaraciones juradas de un trámite específico basado en su ID.
     *
     * @project cp-api-bs-tramiteyrectificacion-query
     * @autor Elver
     * @date 24/08/2024
     */
    public List<DeclaracionJuradaModel> buscarDeclaracionesJuradas(Integer id) {
        return obtenerDeclaracionJurada.buscarDeclaracionesJuradas(id);
    }

    /**
     * Busca las declaraciones juradas de un trámite específico basado en su ID y
     * otros parámetros.
     *
     * @project cp-api-bs-tramiteyrectificacion-query
     * @autor Elver
     * @date 24/08/2024
     */
    public List<DeclaracionJuradaModel> buscarDeclaracionesJuradas(Integer id, String estado, Integer documentoId,
            String estadoDdjjPago, String rucAgente) {
        return obtenerDeclaracionJurada.buscarDeclaracionesJuradas(id, estado, documentoId, estadoDdjjPago, rucAgente);
    }

    public Page<DeclaracionJuradaListaDto> getDjjs(GetDjjQueryParamsDto params) {
        return obtenerDeclaracionJurada.getDjjs(params);
    }

}
