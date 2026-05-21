package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.mae;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidad que representa una agencia en la base de datos.
 * 
 * @project cp-api-bs-tramiteyrectificacion-query
 * @autor Luis Francisco Huertas Mostacero
 * @date 24/08/2024
 */
@Getter
@Setter
@Entity
@Table(name = "agencia", schema = "\"MAE\"")
public class Agencia {

    /**
     * Identificador único de la agencia.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agencia_id", nullable = false)
    private Integer id;

    /**
     * RUC (Registro Único de Contribuyentes) de la agencia.
     * 
     * Límite de tamaño establecido en 11 caracteres.
     */
    @Size(max = 11)
    @Column(name = "ruc_agencia", length = 11)
    private String rucAgencia;

    /**
     * Razón social de la agencia.
     * 
     * Límite de tamaño establecido en 300 caracteres.
     */
    @Size(max = 300)
    @Column(name = "razon_social_agencia", length = 300)
    private String razonSocialAgencia;

    /**
     * Tipo de agencia.
     * 
     * Límite de tamaño establecido en 300 caracteres.
     */
    @Size(max = 300)
    @Column(name = "tipo", length = 300)
    private String tipo;

    /**
     * Estado de la agencia.
     * 
     * Este campo es obligatorio, tiene un tamaño máximo de 1 carácter, y un valor por defecto de 'S'.
     */
    @Size(max = 1)
    @Column(name = "estado", nullable = false, length = 1, columnDefinition = "'S'::character varying")
    private String estado;

}
