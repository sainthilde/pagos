package pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.repository;

import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.domain.model.EscalaModel;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.EscalaRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.mapper.EscalaMapper;

@Component
public class JpaEscalaRepositoryAdapter implements EscalaRepositoryPort {
    private final EscalaRepository escalaRepository;
    private final EscalaMapper escalaMapper;

    public JpaEscalaRepositoryAdapter(EscalaRepository escalaRepository,
                                      EscalaMapper escalaMapper) {
        this.escalaRepository = escalaRepository;
        this.escalaMapper = escalaMapper;
    }
    @Override
    public EscalaModel findById(Integer escalaId) {
        return escalaRepository.findById(escalaId).map(
                escalaMapper::entityToModel
        ).orElse(null);
    }
}
