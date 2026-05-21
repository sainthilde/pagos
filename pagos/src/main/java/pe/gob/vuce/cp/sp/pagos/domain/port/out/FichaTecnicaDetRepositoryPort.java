package pe.gob.vuce.cp.sp.pagos.domain.port.out;

import pe.gob.vuce.cp.sp.pagos.domain.model.FichaTecnicaDetModel;

public interface FichaTecnicaDetRepositoryPort {
    FichaTecnicaDetModel findById(Integer fichaTecnicaDetId);

    FichaTecnicaDetModel findByFichaTecnicaId(Integer fichaTecnicaDetId);
}
