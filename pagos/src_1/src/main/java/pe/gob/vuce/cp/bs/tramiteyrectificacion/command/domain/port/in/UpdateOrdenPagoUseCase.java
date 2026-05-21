package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.OrdenDePagoModel;

public interface UpdateOrdenPagoUseCase {
    OrdenDePagoModel update (OrdenDePagoModel ordenPagoModel);

}
