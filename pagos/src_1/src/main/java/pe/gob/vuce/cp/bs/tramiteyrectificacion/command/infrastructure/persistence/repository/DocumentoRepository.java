package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity.mae.Documento;

import java.util.List;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Integer> {

    List<Documento> findByDescAcronimoIn(List<String> acronimos);
}