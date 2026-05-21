package pe.gob.vuce.cp.sp.pagos.domain.port.in;

import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.response.OrdenPagoResponseDTO;

@Component
public interface CancelarOrdenPagoUseCase {
    OrdenPagoResponseDTO cancelarOrdenPago(Integer ordenPagoId);
}
