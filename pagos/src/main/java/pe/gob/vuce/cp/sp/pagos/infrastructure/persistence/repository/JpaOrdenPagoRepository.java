package pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.entity.OrdenPagoEntity;
/**
 * Repositorio JPA para la entidad {@code OrdenPagoEntity}. Proporciona métodos
 * para realizar operaciones CRUD sobre la tabla "orden_pago" y permite consultas
 * específicas en función de los atributos de la entidad.
 *
 * <p>Hereda de {@code JpaRepository}, proporcionando métodos CRUD comunes como
 * guardar, actualizar, eliminar y buscar por ID.
 *
 * <p>Métodos personalizados:
 * <ul>
 *   <li>{@code findByEscalaIdAndDocumentoId}: Busca una lista de instancias de
 *       {@code OrdenPagoEntity} en función de los valores de {@code escalaId} y
 *       {@code documentoId}, lo cual permite obtener todas las órdenes de pago
 *       relacionadas con una escala y un documento específicos.</li>
 *   <li>{@code findByPpIdOrdenPagoInterna}: Busca una instancia de {@code OrdenPagoEntity}
 *       en función del valor de {@code ppIdOrdenPagoInterna}. Devuelve un {@code Optional}
 *       que contiene la entidad si se encuentra una coincidencia, o vacío si no.</li>
 * </ul>
 * @author Mateo Huancho
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Repository
public interface JpaOrdenPagoRepository extends JpaRepository<OrdenPagoEntity, Integer> {
    /**
     * Busca una lista de órdenes de pago en función de la combinación de
     * {@code escalaId} y {@code documentoId}.
     *
     * @param escalaId   ID de la escala asociada.
     * @param documentoId ID del documento asociado.
     * @return Una lista de {@code OrdenPagoEntity} que coinciden con los parámetros.
     */
    List<OrdenPagoEntity> findByEscalaIdAndDocumentoId(@Param("escalaId") Integer escalaId, @Param("documentoId") Integer documentoId);
    /**
     * Busca una orden de pago en función de {@code ppIdOrdenPagoInterna}.
     *
     * @param ordenPagoInterna ID interno de la orden de pago en el sistema de pago.
     * @return Un {@code Optional} que contiene la entidad si se encuentra, o vacío si no.
     */
    Optional<OrdenPagoEntity> findByPpIdOrdenPagoInterna(@Param("ordenPagoInterna") Integer ordenPagoInterna);
    boolean existsByEscalaIdAndGpMonto(Integer escalaId, BigDecimal gpMonto);
    boolean existsByEscalaIdAndGpMontoAndDocumentoId(Integer escalaId, BigDecimal gpMonto, Integer documentoId);
}
