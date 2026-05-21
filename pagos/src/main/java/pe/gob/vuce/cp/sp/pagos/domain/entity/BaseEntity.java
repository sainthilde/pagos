package pe.gob.vuce.cp.sp.pagos.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

/**
 * Clase base con campos de auditoría comunes.
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@MappedSuperclass
public class BaseEntity {
    /**
     * Estado de la entidad (por defecto 'S').
     */
    @Column(columnDefinition = "varchar(1) default 'S'")
    private String estado = "S";
    /**
     * Usuario que registró la entidad.
     */
    @CreatedBy
    @Column(name = "usuid_reg_aud", updatable = false, nullable = false)
    String usuidRegAud;
    /**
     * Subusuario que registró la entidad.
     */
    @CreatedBy
    @Column(columnDefinition = "varchar(50) default 'current_user'", name = "usubd_reg_aud", updatable = false, nullable = false)
    String usubdRegAud;
    /**
     * Fecha de registro de la entidad.
     */
    @CreatedDate
    @Column(name = "fecha_reg_aud", updatable = false, nullable = false)
    private Instant fechaRegAud;
    /**
     * Usuario que modificó la entidad.
     */
    @LastModifiedBy
    @Column(name = "usuid_mod_aud")
    String usuidModAud;
    /**
     * Subusuario que modificó la entidad.
     */
    @LastModifiedBy
    @Column(columnDefinition = "varchar(50) default 'current_user'", name = "usubd_mod_aud")
    String usubdModAud;
    /**
     * Fecha de la última modificación.
     */
    @LastModifiedDate
    @Column(name = "fecha_mod_aud")
    private Instant fechaModAud;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUsuidRegAud() {
        return usuidRegAud;
    }

    public void setUsuidRegAud(String usuidRegAud) {
        this.usuidRegAud = usuidRegAud;
    }

    public String getUsubdRegAud() {
        return usubdRegAud;
    }

    public void setUsubdRegAud(String usubdRegAud) {
        this.usubdRegAud = usubdRegAud;
    }

    public Instant getFechaRegAud() {
        return fechaRegAud;
    }

    public void setFechaRegAud(Instant fechaRegAud) {
        this.fechaRegAud = fechaRegAud;
    }

    public String getUsuidModAud() {
        return usuidModAud;
    }

    public void setUsuidModAud(String usuidModAud) {
        this.usuidModAud = usuidModAud;
    }

    public String getUsubdModAud() {
        return usubdModAud;
    }

    public void setUsubdModAud(String usubdModAud) {
        this.usubdModAud = usubdModAud;
    }

    public Instant getFechaModAud() {
        return fechaModAud;
    }

    public void setFechaModAud(Instant fechaModAud) {
        this.fechaModAud = fechaModAud;
    }
}
