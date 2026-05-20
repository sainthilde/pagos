package pe.gob.vuce.cp2.bs.infrastructure.out.postgresql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.gob.vuce.cp2.bs.infrastructure.out.postgresql.entity.EscalaEntity;

@Repository
public interface EscalaRepository extends JpaRepository<EscalaEntity, Integer> {
}