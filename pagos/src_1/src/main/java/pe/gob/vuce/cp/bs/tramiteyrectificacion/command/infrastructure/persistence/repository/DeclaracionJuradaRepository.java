package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity.DeclaracionJurada;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DeclaracionJuradaRepository extends JpaRepository<DeclaracionJurada, Integer> {

    List<DeclaracionJurada> findByTramiteTramiteId(Integer id);

    List<DeclaracionJurada> findByEscalaId(Integer id);

    List<DeclaracionJurada> findByDocumentoDocumentoIdAndEscalaIdAndRucAgenteAndEstado(Integer documentoId, Integer escalaId,String rucAgente,String estado);

    int countByFechaSolicitudDdjjBetween(LocalDateTime startDate, LocalDateTime endDate);
}