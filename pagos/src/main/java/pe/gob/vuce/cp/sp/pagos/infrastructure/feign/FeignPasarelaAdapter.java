package pe.gob.vuce.cp.sp.pagos.infrastructure.feign;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.FeignPasarelaPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.OrdenPagoSunatClient;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.PasarelaEstatusResponse;

@AllArgsConstructor
@Component
public class FeignPasarelaAdapter implements FeignPasarelaPort {
    private final OrdenPagoSunatClient ordenPagoSunatClient;
    @Override
    public PasarelaEstatusResponse obtenerEstatusOrdenPago(Integer ordenPagoId) {
        try {
            return ordenPagoSunatClient.getStatus(ordenPagoId);
        } catch (Exception e) {
            return null;
        }        
    }


}
