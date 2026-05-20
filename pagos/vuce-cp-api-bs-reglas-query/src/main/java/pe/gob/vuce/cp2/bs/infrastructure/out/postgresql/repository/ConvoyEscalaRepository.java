package pe.gob.vuce.cp2.bs.infrastructure.out.postgresql.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.gob.vuce.cp2.bs.infrastructure.out.postgresql.entity.ConvoyEscalaEntity;

@Repository
public interface ConvoyEscalaRepository extends JpaRepository<ConvoyEscalaEntity, Integer> {

    Optional<ConvoyEscalaEntity> findByEscalaIdAndPrincipal(
        Integer escalaId,
        Boolean principal
);
}
