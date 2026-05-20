package pe.gob.vuce.cp2.bs.domain.port.out;

import pe.gob.vuce.cp2.bs.domain.model.OperacionModel;

public interface FeignPort {

    String obtenerToken();
    OperacionModel obtenerProcedimientoComponente(OperacionModel model);
    OperacionModel obtenerProcedimientoTasa(OperacionModel model);
}
