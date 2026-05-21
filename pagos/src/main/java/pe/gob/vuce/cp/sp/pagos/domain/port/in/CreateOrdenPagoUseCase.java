package pe.gob.vuce.cp.sp.pagos.domain.port.in;

import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.domain.model.OrdenPago;

/**
 * Caso de uso para la creación de una orden de pago.
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Component
public interface CreateOrdenPagoUseCase {

    /**
     * Crea una nueva orden de pago.
     *
     * @param ordenPago  Datos de la orden de pago a crear.
     * @return           La orden de pago creada.
     */
    OrdenPago createOrdenPago(OrdenPago ordenPago);

}
