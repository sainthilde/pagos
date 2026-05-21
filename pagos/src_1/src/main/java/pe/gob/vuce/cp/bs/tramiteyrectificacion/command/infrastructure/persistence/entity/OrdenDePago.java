package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity;

import java.math.BigDecimal;
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
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity.mae.Entidad;

@Getter
@Setter
@Entity
@Table(name = "orden_pago", schema = "\"PAGOS\"")
public class OrdenDePago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orden_pago_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "entidad_id", referencedColumnName = "entidad_id")
    private Entidad entidad;

    @Column(name = "documento_id")
    private Integer documentoId;

    @Column(name = "escala_id")
    private Integer escalaId;

    @Column(name = "ruc_agente", length = 11)
    private String rucAgente;

    @Column(name = "estado_orden_pago", length = 2)
    private String estadoOrdenPago;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tramite_id", referencedColumnName = "tramite_id")
    @JsonBackReference
    private Tramite tramite;

    @Column(name = "gp_tupa", length = 12)
    private String gpTupa;

    @Column(name = "gp_formato", length = 10)
    private String gpFormato;

    @Column(name = "gp_monto", precision = 10, scale = 2)
    private BigDecimal gpMonto;

    @Column(name = "gp_procedimiento_id", length = 4)
    private String gpProcedimientoId;

    @Column(name = "gp_moneda_signo", length = 5)
    private String gpMonedaSigno;

    @Column(name = "gp_etiqueta_tasa", length = 30)
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

    @Column(name = "pp_codigorechazo_sin_conexion", length = 3)
    private String ppCodigorechazoSinConexion;

    @Column(name = "pp_desc_corta_error", length = 50)
    private String ppDescCortaError;

    @Column(name = "pp_mensaje_rechazo_sin_conexion", length = 3000)
    private String ppMensajeRechazoSinConexion;

    @Column(name = "estado", length = 1)
    private String estado;

    @Column(name = "regla_de_negocio_textsearch", length = 4)
    private String reglaDeNegocioTextsearch;

    @Column(name = "cancelar_destino_del_pago", length = 1)
    private String cancelarDestinoDelPago;
}
