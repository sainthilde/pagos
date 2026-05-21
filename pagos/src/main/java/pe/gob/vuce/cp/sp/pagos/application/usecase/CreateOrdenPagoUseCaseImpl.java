package pe.gob.vuce.cp.sp.pagos.application.usecase;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.domain.constants.OrdenPagoUtils;
import pe.gob.vuce.cp.sp.pagos.domain.model.OrdenPago;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.CreateOrdenPagoUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.OrdenPagoRepositoryPort;
/**
 * Implementación del caso de uso para crear órdenes de pago.
 * Esta clase se encarga de la lógica necesaria para crear
 * una nueva OrdenPago y persistirla en el repositorio correspondiente.
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Component
@AllArgsConstructor
public class CreateOrdenPagoUseCaseImpl implements CreateOrdenPagoUseCase {

    private final OrdenPagoRepositoryPort ordenPagoRepositoryPort;

    /**
     * Método responsable de crear una nueva OrdenPago.
     * Este método construye un nuevo modelo de OrdenPago
     * a partir del objeto proporcionado y lo guarda en el repositorio.
     *
     * @param ordenPago objeto que contiene la información de la OrdenPago
     *                  a crear (required).
     * @return la OrdenPago creada, que incluye su ID generado.
     */
    @Override
    public OrdenPago createOrdenPago(OrdenPago ordenPago) {
        OrdenPago model = OrdenPagoUtils.buildOrdenPagoFrom(ordenPago);
        return ordenPagoRepositoryPort.save(model);
    }
}
