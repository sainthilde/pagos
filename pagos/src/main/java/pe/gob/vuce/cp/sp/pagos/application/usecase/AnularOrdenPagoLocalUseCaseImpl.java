package pe.gob.vuce.cp.sp.pagos.application.usecase;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.AnularOrdenPagoLocalUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.AnularOrdenPagoRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.OrdenPagoResponseDto;

@Component
@AllArgsConstructor
public class AnularOrdenPagoLocalUseCaseImpl implements AnularOrdenPagoLocalUseCase {

    private final AnularOrdenPagoRepositoryPort anularOrdenPagoRepositoryPort;

    @Override
    public OrdenPagoResponseDto anularOrdenPagoLocal(Integer ordenPagoId, String user) {
       return anularOrdenPagoRepositoryPort.anularOrdenPagoLocal(ordenPagoId,user);
    }
}
