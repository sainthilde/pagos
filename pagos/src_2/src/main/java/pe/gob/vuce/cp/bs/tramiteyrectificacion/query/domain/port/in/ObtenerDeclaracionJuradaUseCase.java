package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.port.in;

import java.util.List;

import org.springframework.data.domain.Page;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.DeclaracionJuradaModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.DeclaracionJuradaListaDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.GetDjjQueryParamsDto;

public interface ObtenerDeclaracionJuradaUseCase {
    List<DeclaracionJuradaModel> buscarDeclaracionesJuradas(Integer escalaId);

    List<DeclaracionJuradaModel> buscarDeclaracionesJuradas(Integer escalaId, String estado, Integer documentoId,
            String estadoDdjjPago, String rucAgente);

    Page<DeclaracionJuradaListaDto> getDjjs(GetDjjQueryParamsDto params);

}
