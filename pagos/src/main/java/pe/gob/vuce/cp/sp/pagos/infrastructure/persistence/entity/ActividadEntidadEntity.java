package pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pe.gob.vuce.cp.sp.pagos.domain.entity.BaseEntity;
/**
 * La clase ActividadEntidadEntity representa la entidad de base de datos para la
 * tabla "actividad_entidad_puerto", que contiene información sobre las actividades
 * de entidades y sus puertos asociados. Hereda de {@code BaseEntity}, lo cual
 * proporciona campos de auditoría comunes y comportamientos adicionales.
 *
 * <p>Anotaciones:
 * <ul>
 *   <li>{@code @Entity}: Marca esta clase como una entidad de JPA para que sea
 *       gestionada por el EntityManager de JPA.</li>
 *   <li>{@code @Table(schema = "\"MAE\"", name = "actividad_entidad_puerto")}:
 *       Especifica el esquema y el nombre de la tabla en la base de datos.</li>
 *   <li>{@code @EntityListeners(AuditingEntityListener.class)}: Agrega un listener
 *       para gestionar automáticamente los campos de auditoría, como fecha de
 *       creación y de última modificación.</li>
 *   <li>{@code @SuppressWarnings("all")}: Suprime advertencias específicas del compilador
 *       en esta clase.</li>
 * </ul>
 *
 * <p>Atributos:
 * <ul>
 *   <li>{@code actividadEntidadId}: Identificador único de la actividad de entidad y
 *       puerto, mapeado a la columna "actividad_entidad_puerto_id" en la tabla.</li>
 *   <li>{@code entidadId}: Identificador de la entidad asociada, mapeado a la columna "entidad_id".</li>
 *   <li>{@code actividadId}: Identificador de la actividad asociada, mapeado a la columna "actividad_id".</li>
 *   <li>{@code codPuertoNacional}: Código del puerto nacional asociado, mapeado a la columna "cod_puerto_nacional".</li>
 *   <li>{@code codReglaNegocio}: Código de la regla de negocio asociada, mapeado a la columna "cod_regla_negocio".</li>
 *   <li>{@code estado}: Estado de la actividad de entidad y puerto, mapeado a la columna "estado".</li>
 * </ul>
 *
 * <p>Getters y Setters: Métodos de acceso y modificación para cada atributo,
 * permitiendo gestionar los valores de cada campo en la entidad
 * @author Mateo Huancho
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Entity
@Table(schema = "\"MAE\"", name = "actividad_entidad_puerto")
@EntityListeners(AuditingEntityListener.class)
@SuppressWarnings("all")
public class ActividadEntidadEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actividad_entidad_puerto_id", nullable = false)
    private Integer actividadEntidadId;

    @Column(name = "entidad_id")
    private Integer entidadId;

    @Column(name = "actividad_id")
    private Integer actividadId;

    @Column(name = "cod_puerto_nacional")
    private String codPuertoNacional;

    @Column(name = "cod_regla_negocio")
    private String codReglaNegocio;

    @Column(name = "estado")
    private String estado;

    // Getters and Setters
    public Integer getActividadEntidadId() {
        return actividadEntidadId;
    }

    public void setActividadEntidadId(Integer actividadEntidadId) {
        this.actividadEntidadId = actividadEntidadId;
    }

    public Integer getEntidadId() {
        return entidadId;
    }

    public void setEntidadId(Integer entidadId) {
        this.entidadId = entidadId;
    }

    public Integer getActividadId() {
        return actividadId;
    }

    public void setActividadId(Integer actividadId) {
        this.actividadId = actividadId;
    }

    public String getCodPuertoNacional() {
        return codPuertoNacional;
    }

    public void setCodPuertoNacional(String codPuertoNacional) {
        this.codPuertoNacional = codPuertoNacional;
    }

    public String getCodReglaNegocio() {
        return codReglaNegocio;
    }

    public void setCodReglaNegocio(String codReglaNegocio) {
        this.codReglaNegocio = codReglaNegocio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

