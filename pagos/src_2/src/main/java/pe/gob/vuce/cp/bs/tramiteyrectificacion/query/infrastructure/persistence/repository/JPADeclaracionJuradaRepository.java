package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.escala.Escala;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.mae.Documento;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.pago.DeclaracionJurada;

import java.util.List;

/**
 * Repositorio JPA para la entidad DeclaracionJurada.
 * Proporciona métodos para realizar operaciones CRUD y consultas dinámicas
 * utilizando especificaciones.
 * 
 * @project cp-api-bs-tramiteyrectificacion-query
 * @autor Elver
 * @date 24/08/2024
 */
@Repository
public interface JPADeclaracionJuradaRepository
                extends JpaRepository<DeclaracionJurada, Integer>, JpaSpecificationExecutor<DeclaracionJurada> {

        List<DeclaracionJurada> findByEscala(Escala escala);

        List<DeclaracionJurada> findByEscalaAndEstadoAndDocumentoAndEstadoDdjjPagoAndRucAgente(Escala escala,
                        String estado, Documento documento, String estadoDdjjPago, String rucAgente);
}
