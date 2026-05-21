package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.pago;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.BaseEntity;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.escala.Escala;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.mae.Documento;

/**
 * Entidad que representa una declaración jurada en el contexto de pagos.
 *
 * @project cp-api-bs-tramiteyrectificacion-query
 * @autor Luis Francisco Huertas Mostacero
 * @date 24/08/2024
 */
@Getter
@Setter
@Entity
@Table(name = "declaracion_jurada", schema = "\"PAGOS\"")
public class DeclaracionJurada extends BaseEntity {

    /**
     * Identificador único de la declaración jurada.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "declaracion_jurada_id", nullable = false)
    private Integer id;

    /**
     * Estado de la declaración jurada para pagos.
     * <p>
     * Este campo tiene un tamaño máximo de 1 carácter.
     */
    @Column(name = "estado_ddjj_pago", length = 1)
    private String estadoDdjjPago;

    /**
     * Número de la declaración jurada.
     * <p>
     * Este campo tiene un tamaño máximo de 15 caracteres.
     */
    @Column(name = "numero_ddjj", length = 15)
    private String numeroDdjj;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "escala_id", referencedColumnName = "escala_id")
    private Escala escala;

    /**
     * Fecha de solicitud de la declaración jurada.
     */
    @Column(name = "fecha_solicitud_ddjj")
    private LocalDateTime fechaSolicitudDdjj;

    /**
     * Relación Many-to-One con la entidad Documento.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "documento_id", referencedColumnName = "documento_id")
    private Documento documento;

    /**
     * Motivo de la declaración jurada.
     * <p>
     * Este campo tiene un tamaño máximo de 2000 caracteres.
     */
    @Column(name = "motivo_declaracion", length = 2000)
    private String motivoDeclaracion;

    /**
     * Mensaje de error asociado a la declaración jurada, si lo hubiera.
     * <p>
     * Este campo tiene un tamaño máximo de 4000 caracteres.
     */
    @Column(name = "mensaje_error", length = 4000)
    private String mensajeError;

    /**
     * Relación Many-to-One con la entidad Tramite.
     * <p>
     * Se utiliza @JsonBackReference para evitar la serialización recursiva.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tramite_id", referencedColumnName = "tramite_id")
    @JsonBackReference
    private Tramite tramite;

    /**
     * Estado general de la declaración jurada.
     * <p>
     * Este campo tiene un tamaño máximo de 1 carácter.
     */
    @Column(name = "estado", length = 1)
    private String estado;

    /**
     * RUC del agente asociado a la declaración jurada.
     * <p>
     * Este campo tiene un tamaño máximo de 11 caracteres.
     */
    @Column(name = "ruc_agente", length = 11)
    private String rucAgente;

    @Column(name = "fecha_denegacion_ddjj")
    private LocalDateTime fechaDenegacionDdjj;

    @Column(name = "fecha_aceptacion_ddjj")
    private LocalDateTime fechaAprobacionDdjj;

    @Column(name = "entidad_id")
    private Integer entidadId;
}
