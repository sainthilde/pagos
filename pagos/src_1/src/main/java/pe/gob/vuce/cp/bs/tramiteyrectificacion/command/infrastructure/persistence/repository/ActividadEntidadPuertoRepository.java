package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity.mae.ActividadEntidadPuerto;

@Repository
public interface ActividadEntidadPuertoRepository extends JpaRepository<ActividadEntidadPuerto, Integer> {
    Optional<ActividadEntidadPuerto> findByActividadIdAndCodPuertoNacionalAndEstado(Integer actividadId,
            String codPuertoNacional,
            String estado);
}
