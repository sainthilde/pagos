package pe.gob.vuce.cp2.bs.infrastructure.out.postgresql.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.gob.vuce.cp2.bs.infrastructure.out.postgresql.entity.MotivoEscalaEntity;

@Repository
public interface MotivoEscalaRepository
        extends JpaRepository<MotivoEscalaEntity, Integer> {

    Optional<MotivoEscalaEntity> findByEscalaIdAndMotivoId(
            Integer escalaId,
            Integer motivoId
    );
}