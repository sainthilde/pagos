package pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.entity.ActividadEntidadEntity;

import java.util.Optional;
/**
 * Repositorio JPA para la entidad {@code ActividadEntidadEntity}. Proporciona métodos
 * para realizar operaciones de base de datos CRUD sobre la tabla "actividad_entidad_puerto"
 * y permite consultas específicas en función de los atributos de la entidad.
 *
 * <p>Hereda de {@code JpaRepository}, lo que proporciona métodos CRUD comunes como
 * guardar, actualizar, eliminar y buscar por ID.
 *
 * <p>Métodos personalizados:
 * <ul>
 *   <li>{@code findByEntidadIdAndActividadIdAndCodPuertoNacional}: Busca una instancia
 *       de {@code ActividadEntidadEntity} en función de los valores de {@code entidadId},
 *       {@code actividadId} y {@code codPuertoNacional}. Devuelve un {@code Optional} que
 *       contiene la entidad si se encuentra una coincidencia, o vacío si no.</li>
 * </ul>
 * @author Mateo Huancho
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Repository
public interface JpaActividadEntidadRepository extends JpaRepository<ActividadEntidadEntity, Integer> {
    /**
     * Busca una instancia de ActividadEntidadEntity en función de la combinación de
     * {@code entidadId}, {@code actividadId} y {@code codPuertoNacional}.
     *
     * @param entidadId         ID de la entidad.
     * @param actividadId       ID de la actividad.
     * @param codPuertoNacional Código del puerto nacional.
     * @return un {@code Optional} que contiene la entidad si se encuentra, o vacío si no.
     */
    Optional<ActividadEntidadEntity> findByEntidadIdAndActividadIdAndCodPuertoNacional(
            @Param("entidadId") Integer entidadId,
            @Param("actividadId") Integer actividadId,
            @Param("codPuertoNacional") String codPuertoNacional);


    /**
     * Busca una instancia de ActividadEntidadEntity en función de la combinación de
     * {@code entidadId}, {@code actividadId} y {@code codPuertoNacional}.
     *
     * @param actividadId       ID de la actividad.
     * @param codPuertoNacional Código del puerto nacional.
     * @return un {@code Optional} que contiene la entidad si se encuentra, o vacío si no.
     */
    Optional<ActividadEntidadEntity> findByActividadIdAndCodPuertoNacional(
            @Param("actividadId") Integer actividadId,
            @Param("codPuertoNacional") String codPuertoNacional);

}
