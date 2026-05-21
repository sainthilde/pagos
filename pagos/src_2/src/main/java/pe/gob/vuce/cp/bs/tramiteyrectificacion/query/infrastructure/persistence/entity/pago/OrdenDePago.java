package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.pago;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;
import lombok.Getter;
import lombok.Setter;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.mae.Documento;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.mae.Entidad;

/**
 * Entidad que representa una orden de pago en la base de datos.
 *
 * @project cp-api-bs-tramiteyrectificacion-query
 * @autor Luis Francisco Huertas Mostacero
 * @date 24/08/2024
 */
@Getter
@Setter
@Entity
@Table(name = "orden_pago", schema = "\"PAGOS\"")
public class OrdenDePago {

    /**
     * Identificador único de la orden de pago.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orden_pago_id", nullable = false)
    private Integer id;

    /**
     * Relación Many-to-One con la entidad Entidad.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "entidad_id", referencedColumnName = "entidad_id")
    private Entidad entidad;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "documento_id", referencedColumnName = "documento_id", updatable = false )
    private Documento documento;

    @Column(name = "escala_id")
    private Integer escalaId;

    /**
     * RUC del agente asociado a la orden de pago.
     * <p>
     * Este campo tiene un tamaño máximo de 11 caracteres.
     */
    @Column(name = "ruc_agente", length = 11)
    private String rucAgente;

    /**
     * Estado de la orden de pago.
     * <p>
     * Este campo tiene un tamaño máximo de 2 caracteres.
     */
    @Column(name = "estado_orden_pago", length = 2)
    private String estadoOrdenPago;

    /**
     * Fecha de creación de la orden de pago.
     */
    @Column(name = "fecha_creacion_orden_pago")
    private LocalDateTime fechaCreacionOrdenPago;

    @Column(name = "fecha_vencimiento_orden_pago")
    private LocalDateTime fechaVencimientoOrdenPago;

    @Column(name = "fecha_pagado")
    private LocalDateTime fechaPagado;

    @Column(name = "fecha_anulacion_cpb")
    private LocalDateTime fechaAnulacionCpb;

    @Column(name = "cod_autorizador_reasignacion", length = 20)
    private String codAutorizadorReasignacion;

    @Column(name = "motivo_autorizacion_reasignacion", length = 1000)
    private String motivoAutorizacionReasignacion;

    @Column(name = "sustento_reasignacion_filenet_guid", length = 40)
    private String sustentoReasignacionFilenetGuid;

    @Column(name = "pdf_cpb_filenet_guid", length = 40)
    private String pdfCpbFilenetGuid;

    @Column(name = "fecha_guardado_pdf_cpb")
    private LocalDateTime fechaGuardadoPdfCpb;

    /**
     * Relación Many-to-One con la entidad Tramite.
     * <p>
     * Se utiliza @JsonBackReference para evitar la serialización recursiva.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tramite_id", referencedColumnName = "tramite_id")
    @JsonBackReference
    private Tramite tramite;

    @Column(name = "gp_tupa", length = 20)
    private String gpTupa;

    @Column(name = "gp_formato", length = 10)
    private String gpFormato;

    /**
     * Monto de la orden de pago.
     * <p>
     * Campo con precisión de 10 y escala de 2.
     */
    @Column(name = "gp_monto", precision = 10, scale = 2)
    private BigDecimal gpMonto;

    @Column(name = "gp_procedimiento_id", length = 4)
    private String gpProcedimientoId;

    @Column(name = "gp_moneda_signo", length = 5)
    private String gpMonedaSigno;

    @Column(name = "gp_etiqueta_tasa")
    private String gpEtiquetaTasa;

    @Column(name = "gp_procedimiento_tasa_version", length = 2)
    private String gpProcedimientoTasaVersion;

    @Column(name = "gp_procedimiento_version", length = 2)
    private String gpProcedimientoVersion;

    @Column(name = "gp_desc_procedimiento", length = 200)
    private String gpDescProcedimiento;

    @Column(name = "gp_secuencia", length = 2)
    private String gpSecuencia;

    @Column(name = "pp_fecha_respuesta_creacion_cpb")
    private LocalDateTime ppFechaRespuestaCreacionCpb;

    @Column(name = "pp_id_orden_pago_interna")
    private Integer ppIdOrdenPagoInterna;

    @Column(name = "pp_cod_orden_pago", length = 30)
    private String ppCodOrdenPago;

    @Column(name = "pp_cpb", length = 20)
    private String ppCpb;

    @Column(name = "pp_monto", precision = 10, scale = 2)
    private BigDecimal ppMonto;

    @Column(name = "pp_fecha_conf_generacion_cpb")
    private LocalDateTime ppFechaConfGeneracionCpb;

    @Column(name = "pp_estado_cpb_texto", length = 30)
    private String ppEstadoCpbTexto;

    @Column(name = "pp_codigorechazo_sin_conexion", length = 15)
    private String ppCodigorechazoSinConexion;

    @Column(name = "pp_desc_corta_error", length = 50)
    private String ppDescCortaError;

    @Column(name = "pp_mensaje_rechazo_sin_conexion", length = 3000)
    private String ppMensajeRechazoSinConexion;

    /**
     * Estado general de la orden de pago.
     * <p>
     * Este campo tiene un tamaño máximo de 1 carácter.
     */
    @Column(name = "estado", length = 1)
    private String estado;
}
