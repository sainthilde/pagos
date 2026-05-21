package pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.entity.FichaTecnicaDet;
import java.util.Optional;

@Repository
public interface FichaTecnicaDetRepository extends JpaSpecificationExecutor<FichaTecnicaDet>,
        JpaRepository<FichaTecnicaDet, Integer> {
    Optional<FichaTecnicaDet> findByFichaTecnicaIdAndEstadoVersionFtId(Integer fichaTecnicaDetId, Integer estadoVigente);
}
