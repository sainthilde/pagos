package pe.gob.vuce.cp.sp.pagos.domain.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
/**
 * Clase que representa una respuesta de error personalizada.
 * <p>
 * Incluye detalles sobre el error, un mensaje descriptivo y la fecha/hora en la que ocurrió.
 * </p>
 * @author MATEO HUANCHO
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-29
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomErrorResponse {
    /**
     * Fecha y hora en que ocurrió el error.
     */
    private LocalDateTime datetime;
    /**
     * Mensaje descriptivo del error.
     */
    private String message;
    /**
     * Detalles adicionales sobre el error.
     */
    private String details;

}