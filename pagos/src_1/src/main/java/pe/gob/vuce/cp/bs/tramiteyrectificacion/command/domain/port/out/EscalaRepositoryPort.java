package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.EscalaModel;

public interface EscalaRepositoryPort {
    Integer getEstadoDueId(Integer escalaId);
    EscalaModel findById(Integer escalaId);
}
