package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity;

import java.time.Instant;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * Clase base para entidades JPA, que proporciona campos comunes para auditoría como
 * estado, usuario creador/modificador, y fechas de creación/modificación.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 19/08/2024
 */
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity {

    /**
     * Estado de la entidad, utilizado para indicar si está activa o inactiva.
     */
    @Size(max = 1)
    @Column(name = "estado", nullable = false, length = 1 )
    private String estado;

    /**
     * ID del usuario que creó la entidad, registrado automáticamente.
     */
    @CreatedBy
    @Size(max = 100)
    @Column(name = "usuid_reg_aud", updatable = false, nullable = false, length = 100)
    private String usuidRegAud;

    /**
     * ID del usuario que modificó la entidad por última vez, registrado automáticamente.
     */
    @LastModifiedBy
    @Size(max = 100)
    @Column(name = "usuid_mod_aud", length = 100)
    private String usuidModAud;

    /**
     * Fecha y hora en que se creó la entidad, registrada automáticamente.
     */
   // @CreatedDate
    @Column(name = "fecha_reg_aud", updatable = false, nullable = false)
    private Instant fechaRegAud;

    /**
     * Fecha y hora en que se modificó la entidad por última vez, registrada automáticamente.
     */
   // @LastModifiedDate
    @Column(name = "fecha_mod_aud")
    private Instant fechaModAud;

    /**
     * Usuario de base de datos que modificó por última vez la entidad
     */
    @LastModifiedBy
    @Column(columnDefinition = "varchar(50) default 'current_user'", name = "usubd_mod_aud")
    private String usubdModAud;

    /**
     * Método de ciclo de vida de JPA que se ejecuta antes de persistir una nueva entidad,
     * estableciendo la fecha de creación.
     */
    @PrePersist
    protected void onCreate() {
        fechaRegAud = Instant.now();
    }

    /**
     * Método de ciclo de vida de JPA que se ejecuta antes de actualizar una entidad existente,
     * estableciendo la fecha de modificación.
     */
    @PreUpdate
    protected void onUpdate() {
        fechaModAud = Instant.now();
    }
}
