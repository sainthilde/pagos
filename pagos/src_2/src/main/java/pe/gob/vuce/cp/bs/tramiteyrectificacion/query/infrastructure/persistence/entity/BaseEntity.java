package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase base para entidades JPA, proporcionando campos comunes de auditoría.
 *
 * @project cp-api-bs-tramiteyrectificacion-query
 * @autor Luis Francisco Huertas Mostacero
 * @date 14/08/2024
 */
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class BaseEntity {

    /**
     * Estado de la entidad (activo/inactivo).
     */
    @Size(max = 1)
    @Column(name = "estado", nullable = false, length = 1)
    private String estado;

    /**
     * Identificador del usuario que creó el registro.
     */
    @CreatedBy
    @Size(max = 100)
    @Column(name = "usuid_reg_aud", updatable = false, nullable = false, length = 100)
    private String usuidRegAud;

    /**
     * Identificador del usuario que modificó por última vez el registro.
     */
    @LastModifiedBy
    @Size(max = 100)
    @Column(name = "usuid_mod_aud", length = 100)
    private String usuidModAud;

    /**
     * Fecha y hora en que se creó el registro.
     */
    @CreatedDate
    @Column(name = "fecha_reg_aud", updatable = false, nullable = false)
    private LocalDateTime fechaRegAud;

    /**
     * Fecha y hora en que se modificó por última vez el registro.
     */
    @LastModifiedDate
    @Column(name = "fecha_mod_aud")
    private LocalDateTime fechaModAud;
}
