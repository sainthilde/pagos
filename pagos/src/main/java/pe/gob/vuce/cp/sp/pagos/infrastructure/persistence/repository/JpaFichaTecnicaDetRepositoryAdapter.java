package pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.repository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.domain.constants.Constants;
import pe.gob.vuce.cp.sp.pagos.domain.model.FichaTecnicaDetModel;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.FichaTecnicaDetRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.mapper.FichaTecnicaDetMapper;
import pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.entity.FichaTecnicaDet;

@Component
public class JpaFichaTecnicaDetRepositoryAdapter implements FichaTecnicaDetRepositoryPort {
    private final FichaTecnicaDetRepository fichaTecnicaDetRepository;
    private final FichaTecnicaDetMapper fichaTecnicaDetMapper;

    public JpaFichaTecnicaDetRepositoryAdapter(FichaTecnicaDetRepository fichaTecnicaDetRepository,
                                               FichaTecnicaDetMapper fichaTecnicaDetMapper) {
        this.fichaTecnicaDetRepository = fichaTecnicaDetRepository;
        this.fichaTecnicaDetMapper = fichaTecnicaDetMapper;
    }
    @Override
    public FichaTecnicaDetModel findById(Integer fichaTecnicaDetId) {
        return fichaTecnicaDetRepository.findById(fichaTecnicaDetId).map(
                fichaTecnicaDetMapper::entityToModel
        ).orElse(null);
    }


    @Override
    public FichaTecnicaDetModel findByFichaTecnicaId(Integer fichaTecnicaDetId) {
         FichaTecnicaDet fichaTecnicaDet = fichaTecnicaDetRepository.findByFichaTecnicaIdAndEstadoVersionFtId(fichaTecnicaDetId, Constants.ESTADO_VIGENTE).orElseThrow(
                () -> new EntityNotFoundException(Constants.NOT_FOUND_FICTEC));
        return fichaTecnicaDetMapper.toFichaTecnicaDetDto(fichaTecnicaDet);
    }
}
