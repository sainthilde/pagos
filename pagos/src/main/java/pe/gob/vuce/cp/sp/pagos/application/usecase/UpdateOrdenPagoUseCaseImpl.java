package pe.gob.vuce.cp.sp.pagos.application.usecase;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.domain.constants.OrdenPagoUtils;
import pe.gob.vuce.cp.sp.pagos.domain.model.OrdenPago;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.UpdateOrdenPagoUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.OrdenPagoRepositoryPort;

/**
 * Implementación del caso de uso para actualizar órdenes de pago.
 * Esta clase se encarga de la lógica necesaria para modificar
 * los registros de órdenes de pago en el repositorio correspondiente.
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Component
@AllArgsConstructor
public class UpdateOrdenPagoUseCaseImpl implements UpdateOrdenPagoUseCase {

    private final OrdenPagoRepositoryPort ordenPagoRepositoryPort;
    /**
     *
     * @param ordenPago objeto OrdenPago que contiene los datos a actualizar (required).
     * @return la OrdenPago actualizada después de guardar los cambios en el repositorio.
     */
    @Override
    public OrdenPago updateOrdenPago(OrdenPago ordenPago) {
        OrdenPago model = OrdenPagoUtils.buildOrdenPagoFrom(ordenPago);
        return ordenPagoRepositoryPort.update(model);
    }

}
