package pe.gob.vuce.cp.sp.pagos.domain.port.in;

import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.OrdenPagoResponseDto;

@Component
public interface AnularOrdenPagoLocalUseCase {
    OrdenPagoResponseDto anularOrdenPagoLocal(Integer ordenPagoId,String user);
}
