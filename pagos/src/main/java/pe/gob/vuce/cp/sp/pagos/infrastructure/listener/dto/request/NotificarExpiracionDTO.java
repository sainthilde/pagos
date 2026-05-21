package pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa la información necesaria para notificar la expiracion de una orden de pago.
 *
 * @author Gustavo Tito Verdi
 * @since 20/08/2024
 */

@Getter
@Setter
public class NotificarExpiracionDTO {

    @JsonProperty("ordenPagoId")
    private Integer ordenPagoId;

    @JsonProperty("fechaExpiracion")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd HH:mm:ss")
    private String fechaExtorno;

    @JsonProperty("fechaProcesamiento")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd HH:mm:ss")
    private String fechaProcesamiento;

    @JsonProperty("canalId")
    private Integer canalId;

    @JsonProperty("canalDescripcion")
    private String canalDescripcion;

    @JsonProperty("estado")
    private String estado;

}
