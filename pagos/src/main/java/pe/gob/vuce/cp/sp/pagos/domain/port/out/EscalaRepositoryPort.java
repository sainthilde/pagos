package pe.gob.vuce.cp.sp.pagos.domain.port.out;

import pe.gob.vuce.cp.sp.pagos.domain.model.EscalaModel;

public interface EscalaRepositoryPort {
    EscalaModel findById(Integer escalaId);
}
