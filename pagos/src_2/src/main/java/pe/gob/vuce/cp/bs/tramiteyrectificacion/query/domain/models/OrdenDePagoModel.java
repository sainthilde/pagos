package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Modelo de datos para representar una orden de pago en el sistema.
 *
 * @project cp-api-bs-tramiteyrectificacion-query
 * @autor Elver Valverde
 * @date 24/08/2024
 */
@Getter
@Setter
@NoArgsConstructor
public class OrdenDePagoModel {
    /**
     * Identificador único de la orden de pago.
     */
    private Integer id;
    private Integer documentoId;

    @JsonProperty("nombreDocumento")
    private String nombreDocumento;

    private Integer escalaId;
    /**
     * RUC del agente asociado a la orden de pago.
     * <p>
     * Este campo tiene un tamaño máximo de 11 caracteres.
     */
    private String rucAgente;
    /**
     * Estado de la orden de pago.
     * <p>
     * Este campo tiene un tamaño máximo de 2 caracteres.
     */
    private String estadoOrdenPago;
    /**
     * Fecha de creación de la orden de pago.
     */
    private LocalDateTime fechaCreacionOrdenPago;
    private LocalDateTime fechaVencimientoOrdenPago;
    private LocalDateTime fechaPagado;
    private LocalDateTime fechaAnulacionCpb;
    private String codAutorizadorReasignacion;
    private String motivoAutorizacionReasignacion;
    private String sustentoReasignacionFilenetGuid;
    private String pdfCpbFilenetGuid;
    private LocalDateTime fechaGuardadoPdfCpb;
    /**
     * Relación Many-to-One con la entidad Tramite.
     * <p>
     * Se utiliza @JsonBackReference para evitar la serialización recursiva.
     */
    private TramiteModel tramite;
    private String gpTupa;
    private String gpFormato;
    /**
     * Monto de la orden de pago.
     * <p>
     * Campo con precisión de 10 y escala de 2.
     */
    private BigDecimal gpMonto;
    private String gpProcedimientoId;
    private String gpMonedaSigno;
    private String gpEtiquetaTasa;
    private String gpProcedimientoTasaVersion;
    private String gpProcedimientoVersion;
    private String gpDescProcedimiento;
    private String gpSecuencia;
    private LocalDateTime ppFechaRespuestaCreacionCpb;
    private Integer ppIdOrdenPagoInterna;
    private String ppCodOrdenPago;
    private String ppCpb;
    private BigDecimal ppMonto;
    private LocalDateTime ppFechaConfGeneracionCpb;
    private String ppEstadoCpbTexto;
    private String ppCodigorechazoSinConexion;
    private String ppDescCortaError;
    private String ppMensajeRechazoSinConexion;
    /**
     * Estado general de la orden de pago.
     * <p>
     * Este campo tiene un tamaño máximo de 1 carácter.
     */
    private String estado;

}
