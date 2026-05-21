package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad JPA que representa una trámite dentro del esquema
 * "PAGOS".
 * Esta entidad incluye campos para registrar la información relevante del
 * tramite.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 19/08/2024
 */
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Tramite")
@Table(name = "tramite", schema = "\"PAGOS\"")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Tramite extends BaseEntity {

    /**
     * Identificador único de la trámite.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tramite_id")
    @SequenceGenerator(name = "seq_tramite_id", sequenceName = "\"PAGOS\".seq_tramite_id", allocationSize = 1)
    @Column(name = "tramite_id")
    private Integer tramiteId;

    @Column(name = "numero_suce", nullable = false, length = 12)
    private String numeroSuce;

    @Column(name = "fecha_tramite", nullable = false)
    private LocalDateTime fechaTramite;

    @Column(name = "escala_id")
    private Integer escalaId;

    @Column(name = "documento_id")
    private Integer documentoId;

    @Column(name = "actividad_entidad_puerto_id")
    private Integer actividadEntidadPuertoId;

    @Column(name = "indicador_es", nullable = false, length = 1)
    private String indicadorEs;

    @Column(name = "numero_tramite_entidad", length = 20)
    private String numeroTramiteEntidad;

    @Column(name = "ruc_agente", nullable = false, length = 11)
    private String rucAgente;

    @Column(name = "estado_tramite", nullable = false, length = 2)
    private String estadoTramite;

    @Column(name = "ind_no_requiere_pago", nullable = false)
    private Boolean indNoRequierePago;

    @Column(name = "ind_asignacion_tramite_manual")
    private Boolean indAsignacionTramiteManual;

    @Column(name = "fecha_act_num_tramite_manual")
    private LocalDateTime fechaActNumTramiteManual;

    @Column(name = "sustento_act_num_tramite_manual", nullable = false, length = 40)
    private String sustentoActNumTramiteManual;

    @Column(name = "tupa")
    private String tupa;

    @Column(name = "regla_pago_exencion_aplicada")
    private String reglaPagoExencionAplicada;

    @Column(name = "descripcion_tramite")
    private String descripcionTramite;

}
