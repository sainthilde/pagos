package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.mae;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidad que representa la relación entre una actividad y una entidad en un
 * puerto.
 * 
 * @project cp-api-bs-tramiteyrectificacion-query
 * @autor Luis Francisco Huertas Mostacero
 * @date 24/08/2024
 */
@Getter
@Setter
@Entity
@Table(name = "actividad_entidad_puerto", schema = "\"MAE\"")
public class ActividadEntidadPuerto {

    /**
     * Identificador único de la relación actividad-entidad-puerto.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actividad_entidad_puerto_id", nullable = false)
    private Integer id;

    /**
     * Relación One-to-One con la entidad Entidad.
     */
    @OneToOne
    @JoinColumn(name = "entidad_id", nullable = false)
    private Entidad entidad;

    /**
     * Identificador de la actividad asociada.
     * 
     * Este campo es obligatorio.
     */
    @Column(name = "actividad_id", nullable = false)
    private Integer actividadId;

    /**
     * Código del puerto nacional asociado.
     * 
     * Este campo tiene un límite de 3 caracteres.
     */
    @Column(name = "cod_puerto_nacional", length = 3)
    private String codPuertoNacional;

    /**
     * Código de la regla de negocio asociada.
     * 
     * Este campo tiene un límite de 15 caracteres.
     */
    @Column(name = "cod_regla_negocio", length = 15)
    private String codReglaNegocio;

    /**
     * Estado de la relación actividad-entidad-puerto.
     * 
     * Este campo es obligatorio y tiene un tamaño máximo de 1 carácter.
     */
    @Column(name = "estado", nullable = false, length = 1)
    private String estado;

}
