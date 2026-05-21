package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.pago;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.BaseEntity;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.escala.Escala;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.mae.ActividadEntidadPuerto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.mae.Agencia;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.mae.Documento;

/**
 * Entidad que representa un trámite en el contexto de pagos.
 * 
 * @project cp-api-bs-tramiteyrectificacion-query
 * @autor Luis Francisco Huertas Mostacero
 * @date 24/08/2024
 */
@Getter
@Setter
@Entity
@Table(name = "tramite", schema = "\"PAGOS\"")
public class Tramite extends BaseEntity {

    /**
     * Identificador único del trámite.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tramite_id", nullable = false)
    private Integer id;

    /**
     * Número SUCE (Sistema Único de Control de Expedientes) del trámite.
     * 
     * Este campo tiene un tamaño máximo de 12 caracteres.
     */
    @Column(name = "numero_suce", length = 12)
    private String numeroSuce;

    /**
     * Fecha y hora en que se registró el trámite.
     */
    @Column(name = "fecha_tramite")
    private LocalDateTime fechaTramite;

    /**
     * Relación Many-to-One con la entidad Escala.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "escala_id", referencedColumnName = "escala_id")
    private Escala escala;

    /**
     * Relación One-to-One con la entidad ActividadEntidadPuerto.
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "actividad_entidad_puerto_id", referencedColumnName = "actividad_entidad_puerto_id")
    private ActividadEntidadPuerto actividadEntidadPuerto;

    @Column(name = "indicador_es", length = 1)
    private String indicadorEs;

    /**
     * Número del trámite asignado por la entidad.
     * 
     * Este campo tiene un tamaño máximo de 20 caracteres.
     */
    @Column(name = "numero_tramite_entidad", length = 20)
    private String numeroTramiteEntidad;

    /**
     * Relación Many-to-One con la entidad Agencia.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ruc_agente", referencedColumnName = "ruc_agencia")
    private Agencia agencia;

    @Column(name = "estado_tramite", length = 2)
    private String estadoTramite;

    /**
     * Estado general del trámite.
     * 
     * Este campo tiene un tamaño máximo de 1 carácter.
     */
    @Column(name = "estado", length = 1)
    private String estado;

    /**
     * Indicador de si el trámite requiere o no pago.
     */
    @Column(name = "ind_no_requiere_pago")
    private Boolean indNoRequierePago;

    @Column(name = "tupa", length = 12)
    private String tupa;

    /**
     * Indicador de si el trámite fue asignado manualmente.
     */
    @Column(name = "ind_asignacion_tramite_manual")
    private Boolean fueTramiteManual;

    /**
     * Relación One-to-Many con la entidad DeclaracionJurada.
     * 
     * Se utiliza @JsonManagedReference para gestionar la serialización JSON.
     */
    @OneToMany(mappedBy = "tramite", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<DeclaracionJurada> declaracionesJuradas;

    /**
     * Relación One-to-Many con la entidad OrdenDePago.
     * 
     * Se utiliza @JsonManagedReference para gestionar la serialización JSON.
     */
    @OneToMany(mappedBy = "tramite", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<OrdenDePago> ordenesDePago;

    /**
     * Fecha y hora en que se realizó el registró manual del trámite.
     */
    @Column(name = "fecha_act_num_tramite_manual")
    private LocalDateTime fechaTramiteManual;

    /**
     * Relación One-to-One con la entidad Documento.
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "documento_id", referencedColumnName = "documento_id")
    private Documento documento;

    @Column(name = "regla_pago_exencion_aplicada", length = 200)
    private String reglaPagoExencionAplicada;

    @Column(name = "descripcion_tramite", length = 200)
    private String descripcionTramite;
}
