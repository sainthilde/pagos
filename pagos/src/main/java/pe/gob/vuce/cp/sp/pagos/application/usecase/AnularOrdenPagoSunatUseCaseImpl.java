package pe.gob.vuce.cp.sp.pagos.application.usecase;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.AnularOrdenPagoUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.AnularOrdenPagoRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.response.OrdenPagoResponseDTO;

@Component
@AllArgsConstructor
public class AnularOrdenPagoSunatUseCaseImpl implements AnularOrdenPagoUseCase {

    private final AnularOrdenPagoRepositoryPort anularOrdenPagoRepositoryPort;

    @Override
    public OrdenPagoResponseDTO anularOrdenPago(Integer ordenPagoId,String user) {
        return anularOrdenPagoRepositoryPort.anularOrdenPago(ordenPagoId,user);
    }
}
