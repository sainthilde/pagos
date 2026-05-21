package pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pe.gob.vuce.cp.sp.pagos.domain.entity.BaseEntity;

import java.math.BigDecimal;
import java.time.Instant;
/**
 * La clase OrdenPagoEntity representa la entidad de base de datos para la tabla
 * "orden_pago", que contiene información detallada sobre las órdenes de pago,
 * incluyendo detalles de la entidad, estado, fechas relevantes y otros datos
 * específicos para la gestión de pagos en el sistema. Hereda de {@code BaseEntity},
 * lo cual proporciona campos de auditoría comunes.
 *
 * <p>Anotaciones:
 * <ul>
 *   <li>{@code @Entity}: Marca esta clase como una entidad de JPA para que sea
 *       gestionada por el EntityManager de JPA.</li>
 *   <li>{@code @Table(schema = "\"PAGOS\"", name = "orden_pago")}: Especifica el
 *       esquema y el nombre de la tabla en la base de datos.</li>
 *   <li>{@code @DynamicInsert}: Instruye a JPA para generar sentencias de inserción
 *       SQL dinámicas, solo incluyendo columnas con valores asignados.</li>
 *   <li>{@code @EntityListeners(AuditingEntityListener.class)}: Agrega un listener
 *       para gestionar automáticamente los campos de auditoría.</li>
 *   <li>{@code @Getter} y {@code @Setter}: Generan automáticamente los métodos de
 *       acceso (getters) y modificación (setters) para cada atributo de la clase.</li>
 * </ul>
 *
 * <p>Atributos:
 * <ul>
 *   <li>{@code ordenPagoId}: Identificador único de la orden de pago.</li>
 *   <li>{@code entidadId}: Identificador de la entidad asociada con la orden de pago.</li>
 *   <li>{@code documentoId}: Identificador del documento asociado con la orden de pago.</li>
 *   <li>{@code escalaId}: Identificador de la escala asociada con la orden de pago.</li>
 *   <li>{@code rucAgente}: Número RUC del agente que realiza la orden de pago.</li>
 *   <li>{@code estadoOrdenPago}: Estado actual de la orden de pago, representado
 *       por un código de 2 caracteres.</li>
 *   <li>{@code fechaCreacionOrdenPago}, {@code fechaVencimientoOrdenPago}: Fechas
 *       de creación y vencimiento de la orden de pago.</li>
 *   <li>{@code fechaPagado}, {@code fechaAnulacionCpb}, {@code fechaExtornoOrdenPago},
 *       {@code fechaReasignacionOrdenPago}: Fechas de pago, anulación, extorno y
 *       reasignación de la orden de pago, según sea aplicable.</li>
 *   <li>{@code codAutorizadorReasignacion}, {@code motivoAutorizacionReasignacion},
 *       {@code sustentoReasignacionFilenetGuid}: Información de reasignación, incluyendo
 *       el código del autorizador, motivo y sustento en el sistema de almacenamiento externo.</li>
 *   <li>{@code pdfCpbFilenetGuid}, {@code fechaGuardadoPdfCpb}: Identificador y
 *       fecha del archivo PDF en el sistema de almacenamiento externo.</li>
 *   <li>{@code tramiteId}: Identificador del trámite asociado a la orden de pago.</li>
 *   <li>{@code gpTupa}, {@code gpFormato}, {@code gpMonto}, {@code gpProcedimientoId},
 *       {@code gpMonedaSigno}, {@code gpEtiquetaTasa}, {@code gpProcedimientoTasaVersion},
 *       {@code gpProcedimientoVersion}, {@code gpDescProcedimiento}, {@code gpSecuencia}:
 *       Detalles específicos de la orden de pago, como el TUPA, formato, monto,
 *       versión del procedimiento y otros datos relacionados.</li>
 *   <li>{@code ppFechaRespuestaCreacionCpb}, {@code ppIdOrdenPagoInterna},
 *       {@code ppCodOrdenPago}, {@code ppCpb}, {@code ppMonto}, {@code ppFechaConfGeneracionCpb},
 *       {@code ppEstadoCpbTexto}, {@code ppCodigorechazoSinConexion}, {@code ppDescCortaError},
 *       {@code ppMensajeRechazoSinConexion}: Detalles específicos relacionados
 *       con el estado de la orden de pago en el sistema de pagos externos.</li>
 *   <li>{@code textSearch}: Campo de búsqueda en texto, utilizado para reglas de negocio.</li>
 * </ul>
 * @author Mateo Huancho
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Getter
@Setter
@Entity
@Table(schema = "\"PAGOS\"", name = "orden_pago")
@DynamicInsert
@EntityListeners(AuditingEntityListener.class)
public class OrdenPagoEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orden_pago_id", nullable = false)
    public Integer ordenPagoId;

    @NotNull
    @Column(name = "entidad_id", nullable = false)
    public Integer entidadId;

    @NotNull
    @Column(name = "documento_id", nullable = false)
    public Integer documentoId;

    @NotNull
    @Column(name = "escala_id", nullable = false)
    public Integer escalaId;

    @NotNull
    @Column(name = "ruc_agente", nullable = false, length = 11)
    public String rucAgente;

    @NotNull
    @Column(name = "estado_orden_pago", nullable = false, length = 2)
    public String estadoOrdenPago;

    @NotNull
    @Column(name = "fecha_creacion_orden_pago", nullable = false)
    public Instant fechaCreacionOrdenPago;

    @NotNull
    @Column(name = "fecha_vencimiento_orden_pago", nullable = false)
    public Instant fechaVencimientoOrdenPago;

    @Column(name = "fecha_pagado")
    public Instant fechaPagado;

    @Column(name = "fecha_anulacion_cpb")
    public Instant fechaAnulacionCpb;

    @Column(name = "fecha_extorno_orden_pago")
    public Instant fechaExtornoOrdenPago;

    @Column(name = "fecha_reasignacion_orden_pago")
    public Instant fechaReasignacionOrdenPago;

    @Column(name = "cod_autorizador_reasignacion", length = 20)
    public String codAutorizadorReasignacion;

    @Column(name = "motivo_autorizacion_reasignacion", length = 1000)
    public String motivoAutorizacionReasignacion;

    @Column(name = "sustento_reasignacion_filenet_guid", length = 40)
    public String sustentoReasignacionFilenetGuid;

    @Column(name = "pdf_cpb_filenet_guid", length = 40)
    public String pdfCpbFilenetGuid;

    @Column(name = "fecha_guardado_pdf_cpb")
    public Instant fechaGuardadoPdfCpb;

    @Column(name = "tramite_id")
    public Integer tramiteId;

    @Column(name = "gp_tupa", length = 20)
    public String gpTupa;

    @Column(name = "gp_formato", length = 10)
    public String gpFormato;

    @Column(name = "gp_monto", precision = 10, scale = 2)
    public BigDecimal gpMonto;

    @Column(name = "gp_procedimiento_id", length = 3)
    public String gpProcedimientoId;

    @Column(name = "gp_moneda_signo", length = 5)
    public String gpMonedaSigno;

    @Column(name = "gp_etiqueta_tasa", precision = 10, scale = 2)
    public String gpEtiquetaTasa;

    @Column(name = "gp_procedimiento_tasa_version", length = 2)
    public String gpProcedimientoTasaVersion;

    @Column(name = "gp_procedimiento_version", length = 2)
    public String gpProcedimientoVersion;

    @Column(name = "gp_desc_procedimiento", length = 200)
    public String gpDescProcedimiento;

    @Column(name = "gp_secuencia", length = 2)
    public String gpSecuencia;

    @Column(name = "pp_fecha_respuesta_creacion_cpb")
    public Instant ppFechaRespuestaCreacionCpb;

    @Column(name = "pp_id_orden_pago_interna")
    public Integer ppIdOrdenPagoInterna;

    @Column(name = "pp_cod_orden_pago", length = 30)
    public String ppCodOrdenPago;

    @Column(name = "pp_cpb", length = 20)
    public String ppCpb;

    @Column(name = "pp_monto", precision = 10, scale = 2)
    public BigDecimal ppMonto;

    @Column(name = "pp_fecha_conf_generacion_cpb")
    public Instant ppFechaConfGeneracionCpb;

    @Column(name = "pp_estado_cpb_texto", length = 30)
    public String ppEstadoCpbTexto;

    @Column(name = "pp_codigorechazo_sin_conexion", length = 3)
    public String ppCodigorechazoSinConexion;

    @Column(name = "pp_desc_corta_error", length = 50)
    public String ppDescCortaError;

    @Column(name = "pp_mensaje_rechazo_sin_conexion", length = 3000)
    public String ppMensajeRechazoSinConexion;

    @Column(name = "regla_de_negocio_textsearch", length = 4)
    public String textSearch;
}
