package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity.Tramite;
import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface TramiteRepository extends JpaRepository<Tramite, Integer> {

    int countByFechaTramiteBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Tramite> findAllByEscalaId(Integer escalaId);

    List<Tramite> findAllByEscalaIdAndEstadoTramite(Integer escalaId, String estadoTramite);

    List<Tramite> findAllByEscalaIdAndDocumentoIdIn(Integer escalaId, List<Integer> documentoIds);

    Tramite findByEscalaIdAndDocumentoIdAndEstado(Integer escalaId, Integer documentoId,String estado);

    java.util.Optional<Tramite> findByTramiteIdAndEscalaId(Integer tramiteId, Integer escalaId);
}