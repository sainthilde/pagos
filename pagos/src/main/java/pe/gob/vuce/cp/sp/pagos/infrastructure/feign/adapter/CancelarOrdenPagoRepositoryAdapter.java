package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.adapter;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.CancelarOrdenPagoRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.OrdenPagoSunatClient;
import pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.response.OrdenPagoResponseDTO;

@AllArgsConstructor
@Component
public class CancelarOrdenPagoRepositoryAdapter implements CancelarOrdenPagoRepositoryPort {

    private final OrdenPagoSunatClient ordenPagoSunatClient;

    @Override
    public OrdenPagoResponseDTO cancelarOrdenPago(Integer ordenPagoId) {
        return ordenPagoSunatClient.cancelarOrdenPago(ordenPagoId);
    }
}
