package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.port.out;

import java.util.List;

import org.springframework.data.domain.Page;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.DeclaracionJuradaModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.DeclaracionJuradaListaDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.GetDjjQueryParamsDto;

public interface DeclaracionJuradaRepositoryPort {
    List<DeclaracionJuradaModel> findByEscala(Integer escalaId);

    // Avoid infrastructure dependency in the port by using the Documento ID
    List<DeclaracionJuradaModel> findDeclaracionJurada(Integer escalaId, String estado, Integer documentoId,
            String estadoDdjjPago, String rucAgente);

    Page<DeclaracionJuradaListaDto> getDjjs(GetDjjQueryParamsDto params);
}
