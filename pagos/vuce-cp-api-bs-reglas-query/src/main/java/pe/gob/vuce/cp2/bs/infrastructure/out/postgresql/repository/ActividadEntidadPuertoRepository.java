package pe.gob.vuce.cp2.bs.infrastructure.out.postgresql.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.gob.vuce.cp2.bs.infrastructure.out.postgresql.entity.ActividadEntidadPuertoEntity;

@Repository
public interface ActividadEntidadPuertoRepository
        extends JpaRepository<ActividadEntidadPuertoEntity, Integer> {

    Optional<ActividadEntidadPuertoEntity>
    findByActividadIdAndCodPuertoNacional(
            Integer actividadId,
            String codPuertoNacional
    );
}