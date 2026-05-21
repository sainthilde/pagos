package pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa la información necesaria para notificar el pago de una orden de pago.
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Getter
@Setter
public class NotificarPagoDTO {
    /**
     * Identificador de la orden de pago que se está notificando.
     */
    @JsonProperty("ordenPagoId")
    private Integer ordenPagoId;
    /**
     * Monto que ha sido pagado en la orden de pago.
     */
    @JsonProperty("montoPagado")
    private Double montoPagado;
    /**
     * Fecha en que se realizó el pago, formateada como 'yyyyMMdd'.
     */
    @JsonProperty("fechaPago")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    private String fechaPago;
    /**
     * Fecha y hora en que se procesó la notificación del pago, formateada como 'yyyyMMdd HH:mm:ss'.
     */
    @JsonProperty("fechaProcesamiento")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd HH:mm:ss")
    private String fechaProcesamiento;

    /**
     * Identificador del canal a través del cual se está notificando el pago.
     */
    @JsonProperty("canalId")
    private Integer canalId;
    /**
     * Descripción del canal a través del cual se está notificando el pago.
     */
    @JsonProperty("canalDescripcion")
    private String canalDescripcion;
    /**
     * Identificador del banco que procesa el pago.
     */
    @JsonProperty("bancoId")
    private Integer bancoId;
    /**
     * Descripción del banco que procesa el pago.
     */
    @JsonProperty("bancoDescripcion")
    private String bancoDescripcion;
    /**
     * Tipo de pago (por ejemplo, efectivo, tarjeta, etc.).
     */
    @JsonProperty("tipo")
    private Integer tipo;
    /**
     * Descripción del tipo de pago.
     */
    @JsonProperty("tipoDescripcion")
    private String tipoDescripcion;
    /**
     * Estado actual de la notificación del pago.
     */
    @JsonProperty("estado")
    private String estado;
}
