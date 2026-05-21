package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.DeclaracionJuradaRequestDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.DeclaracionJuradaModel;

public interface CreateDeclaracionJuradaUseCase {
    DeclaracionJuradaModel save (DeclaracionJuradaRequestDto declaracionJurada,String user);
}
