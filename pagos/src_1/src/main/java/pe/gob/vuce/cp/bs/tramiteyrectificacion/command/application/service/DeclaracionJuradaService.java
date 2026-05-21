package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.application.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.DeclaracionJuradaRequestDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.DeclaracionJuradaModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.CreateDeclaracionJuradaUseCase;

@Service
@AllArgsConstructor
public class DeclaracionJuradaService {
    private CreateDeclaracionJuradaUseCase createDeclaracionJuradaUseCase;

    public DeclaracionJuradaModel createDeclaracionJurada(DeclaracionJuradaRequestDto declaracionJuradaModel,String user) {
        return createDeclaracionJuradaUseCase.save(declaracionJuradaModel,user);
    }

}
