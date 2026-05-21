package pe.gob.vuce.cp.sp.pagos.application.usecase;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.CancelarOrdenPagoUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.CancelarOrdenPagoRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.response.OrdenPagoResponseDTO;

@Component
@AllArgsConstructor
public class CancelarOrdenPagoUseCaseImpl implements CancelarOrdenPagoUseCase {

    private final CancelarOrdenPagoRepositoryPort cancelarOrdenPagoRepositoryPort;

    @Override
    public OrdenPagoResponseDTO cancelarOrdenPago(Integer ordenPagoId) {
        return cancelarOrdenPagoRepositoryPort.cancelarOrdenPago(ordenPagoId);
    }
}
