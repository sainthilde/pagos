package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity.OrdenDePago;
import java.util.List;

@Repository
public interface OrderPagoRepository extends JpaRepository<OrdenDePago, Integer> {

    List<OrdenDePago> findByTramiteTramiteId(Integer id);

    List<OrdenDePago> findAllByTramiteTramiteIdAndEstadoOrdenPagoIn(Integer id,List<String> estadoOrdenPagos);

    List<OrdenDePago> findAllByEscalaIdAndEstadoOrdenPagoIn(Integer escalaId,List<String> estadoOrdenPagos);

    List<OrdenDePago> findByEscalaId(Integer id);

    List<OrdenDePago> findByDocumentoIdAndEscalaIdAndRucAgenteAndEstado(Integer documentoId, Integer escalaId, String rucAgente,String estado);
}