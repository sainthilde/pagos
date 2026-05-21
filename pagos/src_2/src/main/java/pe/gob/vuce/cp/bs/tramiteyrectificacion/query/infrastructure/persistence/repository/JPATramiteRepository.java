package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.pago.Tramite;

import java.util.Optional;

/**
 * Repositorio JPA para la entidad Tramite.
 * Proporciona métodos para realizar operaciones CRUD y consultas dinámicas
 * utilizando especificaciones.
 *
 * @project cp-api-bs-tramiteyrectificacion-query
 * @autor Luis Francisco Huertas Mostacero
 * @date 24/08/2024
 */
@Repository
public interface JPATramiteRepository extends JpaRepository<Tramite, Integer>, JpaSpecificationExecutor<Tramite> {

    Optional<Tramite> findByEscalaEscalaIdAndDocumentoIdAndEstado(Integer escalaId, Integer documentoId, String estado);

    Optional<Tramite> findByEscalaEscalaIdAndIndicadorEsAndDocumentoId(Integer escalaId, String indicadorEs,
            Integer documentoId);
}
