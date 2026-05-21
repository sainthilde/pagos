package pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa la respuesta de una orden de pago.
 * Incluye detalles sobre la orden, como el monto, estado y
 * la información del usuario relacionado.
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrdenPagoResponseDTO {
    /**
     * Identificador único de la orden de pago.
     */
    private Integer ordenPagoId;
    /**
     * Código asociado a la orden de pago.
     */
    private String codigoOrdenPago;
    /**
     * Monto total de la orden de pago.
     */
    private Double monto;

    /**
     * Fecha en que se generó la orden de pago.
     */
    private String fechaGeneracion;
    /**
     * Código del comprobante de pago asociado a la orden.
     */
    private String cpb;
    /**
     * Estado actual de la orden de pago (e.g., pendiente, completada).
     */
    private String estado;
    /**
     * Fecha asociada a la orden de pago, puede ser una fecha relevante
     * para el proceso de pago.
     */
    private String fecha;
    /**
     * Tipo de documento del usuario que realiza la operación.
     */
    private String tipoDocumentoUsuario;
    /**
     * Número del documento del usuario que realiza la operación.
     */
    private String numeroDocumentoUsuario;
    /**
     * Tipo de operación de la orden de pago.
     */
    private String tipOper;
    /**
     * RUC del operador asociado a la orden de pago.
     */
    private String rucOper;
}

