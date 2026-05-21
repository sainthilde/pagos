package pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa la información necesaria para notificar la anulación de una orden de pago.
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Getter
@Setter
public class NotificarAnulacionDTO {
    /**
     * Identificador de la orden de pago que se está anulando.
     */
    @JsonProperty("ordenPagoId")
    private Integer ordenPagoId;
    /**
     * Fecha y hora en que se realizó la anulación, formateada como 'yyyyMMdd HH:mm:ss'.
     */
    @JsonProperty("fechaAnulacion")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd HH:mm:ss")
    private String fechaAnulacion;

    /**
     * Fecha y hora en que se procesó la notificación de anulación, formateada como 'yyyyMMdd HH:mm:ss'.
     */
    @JsonProperty("fechaProcesamiento")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd HH:mm:ss")
    private String fechaProcesamiento;
    /**
     * Identificador del canal a través del cual se está notificando la anulación.
     */
    @JsonProperty("canalId")
    private Integer canalId;
    /**
     * Descripción del canal a través del cual se está notificando la anulación.
     */
    @JsonProperty("canalDescripcion")
    private String canalDescripcion;
    /**
     * Estado actual de la notificación de anulación.
     */
    @JsonProperty("estado")
    private String estado;
}
