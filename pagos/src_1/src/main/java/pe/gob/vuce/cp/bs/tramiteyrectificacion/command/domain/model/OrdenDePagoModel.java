package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Modelo que representa una orden de pago en la aplicacion.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 19/08/2024
 */
@Getter
@Setter
@NoArgsConstructor
public class OrdenDePagoModel extends BaseModel {
    /**
     * Identificador único
     */
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("entidadId")
    private EntidadModel entidad;
    @JsonProperty("documentoId")
    private Integer documentoId;
    @JsonProperty("escalaId")
    private Integer escalaId;
    @JsonProperty("rucAgente")
    private String rucAgente;
    @JsonProperty("estadoOrdenPago")
    private String estadoOrdenPago;
    @JsonProperty("fechaOrdenPago")
    private LocalDateTime fechaCreacionOrdenPago;
    @JsonProperty("fechaVencimientoOrdenPago")
    private LocalDateTime fechaVencimientoOrdenPago;
    @JsonProperty("fechaPagado")
    private LocalDateTime fechaPagado;
    @JsonProperty("fechaAnulacionCpb")
    private LocalDateTime fechaAnulacionCpb;
    @JsonProperty("fechaExtornoOrdenPago")
    private LocalDateTime fechaExtornoOrdenPago;
    @JsonProperty("fechaReasignacionOrdenPago")
    private LocalDateTime fechaReasignacionOrdenPago;
    @JsonProperty("codAutorizadorReasignacion")
    private String codAutorizadorReasignacion;
    @JsonProperty("motivoAutorizacionReasignacion")
    private String motivoAutorizacionReasignacion;
    @JsonProperty("sustentoReasignacionFilenetGuid")
    private String sustentoReasignacionFilenetGuid;
    @JsonProperty("pdfCpbFilenetGuid")
    private String pdfCpbFilenetGuid;
    @JsonProperty("fechaGuardadoPdfCpb")
    private LocalDateTime fechaGuardadoPdfCpb;
    @JsonProperty("tramiteId")
    private TramiteModel tramite;
    @JsonProperty("gpTupa")
    private String gpTupa;
    @JsonProperty("gpFormato")
    private String gpFormato;
    @JsonProperty("gpMonto")
    private String gpMonto;
    @JsonProperty("gpProcedimiento")
    private String gpProcedimiento;
    @JsonProperty("gpMonedaSigno")
    private String gpMonedaSigno;
    @JsonProperty("gpEtiquetaTasa")
    private String gpEtiquetaTasa;
    @JsonProperty("gpProcedimientoTasaVersion")
    private String gpProcedimientoTasaVersion;
    @JsonProperty("gpDescProcedimiento")
    private String gpDescProcedimiento;
    @JsonProperty("gpSecuencia")
    private String gpSecuencia;
    @JsonProperty("ppFechaRespuestaCreacionCpb")
    private LocalDateTime ppFechaRespuestaCreacionCpb;
    @JsonProperty("ppIdOrdenPagoInterna")
    private Integer ppIdOrdenPagoInterna;
    @JsonProperty("ppCodOrdenPago")
    private String ppCodOrdenPago;
    @JsonProperty("ppCpb")
    private String ppCpb;
    @JsonProperty("ppMonto")
    private Double ppMonto;
    @JsonProperty("ppFechaConfGeneracionCpb")
    private LocalDateTime ppFechaConfGeneracionCpb;
    @JsonProperty("ppEstadoCpbTexto")
    private String ppEstadoCpbTexto;
    @JsonProperty("ppCodigoRechazoSinConexion")
    private String ppCodigoRechazoSinConexion;
    @JsonProperty("ppDescCortaError")
    private String ppDescCortaError;
    @JsonProperty("ppMensajeRechazoSinConexion")
    private String ppMensajeRechazoSinConexion;

    @JsonProperty("cancelarDestinoDelPago")
    private String cancelarDestinoDelPago;
}
