package pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.entity.Escala;


@Repository
public interface EscalaRepository extends JpaRepository<Escala, Integer> {
}
