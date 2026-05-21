package pe.gob.vuce.cp.sp.pagos.domain.port.out;

import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.PasarelaEstatusResponse;

public interface FeignPasarelaPort {

    PasarelaEstatusResponse obtenerEstatusOrdenPago(Integer ordenPagoId);
}
