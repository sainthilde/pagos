package pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa la información necesaria para notificar el extorno de una orden de pago.
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Getter
@Setter
public class NotificarExtornoDTO {
    /**
     * Identificador de la orden de pago que se está extornando.
     */
    @JsonProperty("ordenPagoId")
    private Integer ordenPagoId;
    /**
     * Monto que ha sido extornado de la orden de pago.
     */
    @JsonProperty("montoExtornado")
    private Double montoExtornado;
    /**
     * Fecha y hora en que se realizó el extorno, formateada como 'yyyyMMdd HH:mm:ss'.
     */
    @JsonProperty("fechaExtorno")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd HH:mm:ss")
    private String fechaExtorno;
    /**
     * Fecha y hora en que se procesó la notificación de extorno, formateada como 'yyyyMMdd HH:mm:ss'.
     */
    @JsonProperty("fechaProcesamiento")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd HH:mm:ss")
    private String fechaProcesamiento;
    /**
     * Identificador del canal a través del cual se está notificando el extorno.
     */
    @JsonProperty("canalId")
    private Integer canalId;
    /**
     * Descripción del canal a través del cual se está notificando el extorno.
     */
    @JsonProperty("canalDescripcion")
    private String canalDescripcion;
    /**
     * Estado actual de la notificación de extorno.
     */
    @JsonProperty("estado")
    private String estado;
}
