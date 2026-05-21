package pe.gob.vuce.cp.sp.pagos.domain.port.out;

import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.OrdenPagoResponseDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.response.OrdenPagoResponseDTO;

@Component
public interface AnularOrdenPagoRepositoryPort {
    OrdenPagoResponseDTO anularOrdenPago(Integer ordenPagoId,String user);
    OrdenPagoResponseDto anularOrdenPagoLocal(Integer ordenPagoId,String user);
}
