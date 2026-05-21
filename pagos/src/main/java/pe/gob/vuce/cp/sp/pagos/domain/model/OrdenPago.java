package pe.gob.vuce.cp.sp.pagos.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Modelo de dominio para representar una orden de pago.
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("squid:S1104")
public class OrdenPago {
    /**
     * Identificador de la orden de pago.
     */
    public Integer ordenPagoId;
    /**
     * Identificador de la entidad relacionada.
     */
    public Integer entidadId;
    /**
     * Identificador del documento relacionado.
     */
    public Integer documentoId;
    /**
     * Identificador de la escala relacionada.
     */
    public Integer escalaId;
    /**
     * RUC del agente relacionado con la orden de pago.
     */
    public String rucAgente;
    /**
     * Código único de la orden de pago.
     */
    public String codigoOrdenPago;
    /**
     * Monto de la orden de pago.
     */
    public Double monto;
    /**
     * Fecha de generación de la orden de pago.
     */
    public String fechaGeneracion;
    /**
     * Código CPB de la orden de pago.
     */
    public String cpb;
    /**
     * Estado de la orden de pago.
     */
    public String estado;
    /**
     * Fecha de vigencia de la orden de pago.
     */
    public String fechaVigencia;
    /**
     * Identificador del componente relacionado.
     */
    public String idComponente;
    /**
     * Texto utilizado para la búsqueda de la orden.
     */
    public String textSearch;
    /**
     * Identificador interno de la orden de pago.
     */
    public Integer ordenPagoInternaId;
    /**
     * Identificador de la actividad de la entidad en el puerto.
     */
    public Integer actividadEntidadPuertoId;
    /**
     * GUID de Filenet relacionado con la orden de pago.
     */
    public String filenetGuid;
    /**
     * TUPA relacionado con la orden de pago.
     */
    public String gpTupa;
    /**
     * Formato GP de la orden de pago.
     */
    public String gpFormato;
    /**
     * Monto en el sistema GP.
     */
    public BigDecimal gpMonto;
    /**
     * Identificador del procedimiento en el sistema GP.
     */
    public String gpProcedimientoId;
    /**
     * Moneda utilizada en la orden de pago.
     */
    public String gpMonedaSigno;
    /**
     * Etiqueta de la tasa en el sistema GP.
     */
    public String gpEtiquetaTasa;
    /**
     * Versión de la tasa del procedimiento en el sistema GP.
     */
    public String gpProcedimientoTasaVersion;
    /**
     * Versión del procedimiento en el sistema GP.
     */
    public String gpProcedimientoVersion;
    /**
     * Descripción del procedimiento relacionado.
     */
    public String gpDescProcedimiento;
    /**
     * Secuencia relacionada con el sistema GP.
     */
    public String gpSecuencia;
    /**
     * Fecha de guardado del PDF en el sistema CPB.
     */
    public Instant fechaGuardadoPdfCpb;
    /**
     * Fecha de respuesta para la creación del CPB.
     */
    public Instant ppFechaRespuestaCreacionCpb;
    /**
     * Fecha de confirmación de generación del CPB.
     */
    public Instant ppFechaConfGeneracionCpb;
    /**
     * Estado del CPB en formato de texto.
     */
    public String ppEstadoCpbTexto;
    /**
     * Fecha de pago de la orden de pago.
     */
    public Instant fechaPagado;
    /**
     * Fecha de anulación del CPB.
     */
    public Instant fechaAnulacionCpb;
    /**
     * Fecha de extorno de la orden de pago.
     */
    public Instant fechaExtornoOrdenPago;
    /**
     * Fecha de creación de la orden de pago.
     */
    public Instant fechaCreacionOrdenPago;
    /**
     * Código de rechazo por falta de conexión en CPB.
     */
    public String ppCodigorechazoSinConexion;
    /**
     * Descripción corta del error en CPB.
     */
    public String ppDescCortaError;
    /**
     * Mensaje de rechazo por falta de conexión en CPB.
     */
    public String ppMensajeRechazoSinConexion;
    /**
     * Usuario que registró la auditoría.
     */
    public String usuidRegAud;
    /**
     * Usuario que modificó la auditoría.
     */
    public String usuidModAud;
    public Integer tramiteId;
}

