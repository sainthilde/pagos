package pe.gob.vuce.cp.sp.pagos.domain.port.out;

import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.response.OrdenPagoResponseDTO;

@Component
public interface CancelarOrdenPagoRepositoryPort {
    OrdenPagoResponseDTO cancelarOrdenPago(Integer ordenPagoId);
}
