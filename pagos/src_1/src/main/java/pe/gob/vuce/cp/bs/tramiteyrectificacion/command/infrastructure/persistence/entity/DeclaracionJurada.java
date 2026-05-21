package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity.mae.Documento;

@Getter
@Setter
@Entity(name = "DeclaracionJurada")
@Table(name = "declaracion_jurada", schema = "\"PAGOS\"")
public class DeclaracionJurada extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_declaracion_jurada_id")
    @SequenceGenerator(name = "seq_declaracion_jurada_id", sequenceName = "\"PAGOS\".seq_declaracion_jurada_id", allocationSize = 1)
    @Column(name = "declaracion_jurada_id", nullable = false)
    private Integer declaracionJuradaId;

    @Column(name = "estado_ddjj_pago", length = 1)
    private String estadoDdjjPago;

    @Column(name = "numero_ddjj", length = 15, nullable = false, updatable = false)
    private String numeroDdjj;

    @Column(name = "fecha_solicitud_ddjj", updatable = false)
    private LocalDateTime fechaSolicitudDdjj;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY)
    @JoinColumn(name = "documento_id", referencedColumnName = "documento_id", updatable = false)
    private Documento documento;

    @Column(name = "escala_id", updatable = false)
    private Integer escalaId;

    @Column(name = "motivo_declaracion", length = 2000, updatable = false)
    private String motivoDeclaracion;

    @Column(name = "mensaje_error", length = 4000, updatable = false)
    private String mensajeError;

    @ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
    @JoinColumn(name = "tramite_id", referencedColumnName = "tramite_id")
    private Tramite tramite;

    @Column(name = "ruc_agente", length = 11, updatable = false)
    private String rucAgente;

    @Column(name = "fecha_denegacion_ddjj")
    private LocalDateTime fechaDenegacionDdjj;

    @Column(name = "fecha_aceptacion_ddjj")
    private LocalDateTime fechaAprobacionDdjj;

    @Column(name = "entidad_id", updatable = false)
    private Integer entidadId;
}
