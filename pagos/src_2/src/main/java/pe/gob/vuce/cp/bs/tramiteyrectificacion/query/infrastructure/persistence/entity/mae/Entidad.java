package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.mae;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidad que representa una entidad en la base de datos.
 * 
 * @project cp-api-bs-tramiteyrectificacion-query
 * @autor Luis Francisco Huertas Mostacero
 * @date 24/08/2024
 */
@Getter
@Setter
@Entity
@Table(name = "entidad", schema = "\"MAE\"")
public class Entidad {

    /**
     * Identificador único de la entidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entidad_id", nullable = false)
    private Integer id;

    /**
     * RUC (Registro Único de Contribuyentes) de la entidad.
     * 
     * Límite de tamaño establecido en 20 caracteres.
     */
    @Column(name = "ruc", length = 20)
    private String ruc;

    /**
     * Nombre de la entidad.
     * 
     * Límite de tamaño establecido en 100 caracteres.
     */
    @Column(name = "nombre", length = 100)
    private String nombre;

    /**
     * Observaciones adicionales sobre la entidad.
     * 
     * Límite de tamaño establecido en 200 caracteres.
     */
    @Column(name = "observacion", length = 200)
    private String observacion;

    /**
     * Estado de la entidad.
     * 
     * Este campo es obligatorio y tiene un tamaño máximo de 1 carácter.
     */
    @Column(name = "estado", nullable = false, length = 1)
    private String estado;

    /**
     * Usuario que registró la entidad.
     * 
     * Límite de tamaño establecido en 100 caracteres.
     */
    @Column(name = "usuid_reg_aud", length = 100)
    private String usuidRegAud;

    /**
     * Usuario que modificó la entidad.
     * 
     * Límite de tamaño establecido en 100 caracteres.
     */
    @Column(name = "usuid_mod_aud", length = 100)
    private String usuidModAud;

    /**
     * Fecha y hora de registro de la entidad.
     * 
     * Este campo es obligatorio.
     */
    @Column(name = "fecha_reg_aud", nullable = false)
    private LocalDateTime fechaRegAud;

    /**
     * Fecha y hora de la última modificación de la entidad.
     * 
     * Este campo es obligatorio.
     */
    @Column(name = "fecha_mod_aud", nullable = false)
    private LocalDateTime fechaModAud;

    /**
     * Sub-unidad del usuario que registró la entidad.
     * 
     * Límite de tamaño establecido en 50 caracteres.
     */
    @Column(name = "usubd_reg_aud", length = 50)
    private String usubdRegAud;

    /**
     * Sub-unidad del usuario que modificó la entidad.
     * 
     * Límite de tamaño establecido en 50 caracteres.
     */
    @Column(name = "usubd_mod_aud", length = 50)
    private String usubdModAud;

    /**
     * Identificador del grupo al que pertenece la entidad.
     */
    @Column(name = "grupo_entidad_id")
    private Integer grupoEntidadId;
}
