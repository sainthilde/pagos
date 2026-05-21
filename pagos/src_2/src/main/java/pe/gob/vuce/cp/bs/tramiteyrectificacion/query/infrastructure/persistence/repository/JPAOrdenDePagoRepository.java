package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.pago.OrdenDePago;

import java.util.List;

/**
 * Repositorio JPA para la entidad OrdenDePago.
 * Proporciona métodos para realizar operaciones CRUD y consultas dinámicas
 * utilizando especificaciones.
 *
 * @project cp-api-bs-tramiteyrectificacion-query
 * @autor Elver
 * @date 24/08/2024
 */
public interface JPAOrdenDePagoRepository extends JpaRepository<OrdenDePago, Integer>, JpaSpecificationExecutor<OrdenDePago> {
    List<OrdenDePago> findByEscalaIdAndDocumentoIdAndRucAgente(Integer escalaId, Integer documentoId, String rucAgente);

    List<OrdenDePago> findAllByEscalaIdAndEstadoOrdenPago(Integer escalaId,String estadoOrdenPago);

}
