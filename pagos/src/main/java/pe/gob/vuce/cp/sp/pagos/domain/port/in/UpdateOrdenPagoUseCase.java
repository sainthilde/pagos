package pe.gob.vuce.cp.sp.pagos.domain.port.in;

import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.domain.model.OrdenPago;

/**
 * Caso de uso para la actualización de órdenes de pago.
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Component
public interface UpdateOrdenPagoUseCase {

    /**
     * Actualiza una orden de pago existente.
     *
     * @param ordenPago  Orden de pago a actualizar.
     * @return           La orden de pago actualizada.
     */
    OrdenPago updateOrdenPago(OrdenPago ordenPago);

}
