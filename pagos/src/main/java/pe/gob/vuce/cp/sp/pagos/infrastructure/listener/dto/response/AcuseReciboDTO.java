package pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa el acuse de recibo de una orden de pago procesada.
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Getter
@Setter
@Builder
public class AcuseReciboDTO {
    /**
     * Identificador de la orden de pago para la cual se genera el acuse de recibo.
     */
    @JsonProperty("ordenPagoId")
    private Integer ordenPagoId;
    /**
     * Fecha y hora en que se procesó la orden de pago,
     * formateada como 'yyyyMMdd HH:mm:ss'.
     */
    @JsonProperty("fechaProcesamiento")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd HH:mm:ss")
    private String fechaProcesamiento;
}
