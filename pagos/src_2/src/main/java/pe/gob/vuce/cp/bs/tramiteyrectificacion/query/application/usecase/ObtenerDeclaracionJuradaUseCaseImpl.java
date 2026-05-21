package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.application.usecase;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.DeclaracionJuradaModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.port.in.ObtenerDeclaracionJuradaUseCase;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.port.out.DeclaracionJuradaRepositoryPort;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.DeclaracionJuradaListaDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.GetDjjQueryParamsDto;

@Service
@AllArgsConstructor
public class ObtenerDeclaracionJuradaUseCaseImpl implements ObtenerDeclaracionJuradaUseCase {
    DeclaracionJuradaRepositoryPort declaracionJuradaRepositoryPort;

    @Override
    public List<DeclaracionJuradaModel> buscarDeclaracionesJuradas(Integer escalaId) {
        return declaracionJuradaRepositoryPort.findByEscala(escalaId);
    }

    @Override
    public List<DeclaracionJuradaModel> buscarDeclaracionesJuradas(Integer escalaId, String estado, Integer documentoId,
            String estadoDdjjPago, String rucAgente) {
    return declaracionJuradaRepositoryPort.findDeclaracionJurada(escalaId, estado, documentoId, estadoDdjjPago,
                rucAgente);
    }

    @Override
    public Page<DeclaracionJuradaListaDto> getDjjs(GetDjjQueryParamsDto params) {
        return declaracionJuradaRepositoryPort.getDjjs(params);
    }
}
