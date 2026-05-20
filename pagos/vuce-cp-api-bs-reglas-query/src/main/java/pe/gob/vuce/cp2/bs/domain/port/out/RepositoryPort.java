package pe.gob.vuce.cp2.bs.domain.port.out;

import pe.gob.vuce.cp2.bs.domain.model.OperacionModel;

public interface RepositoryPort {

    OperacionModel obtenerPuerto(OperacionModel model);
    OperacionModel buscarActividadEntidadPuerto(OperacionModel model);
    OperacionModel buscarMotivoEscala(OperacionModel model);
    
    OperacionModel buscarConvoy(OperacionModel model);
    OperacionModel buscarEntidad(OperacionModel model);
    OperacionModel buscarOrdenPago(OperacionModel model);
    
}
