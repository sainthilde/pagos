package pe.gob.vuce.cp.sp.pagos.application.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.ObtenerFormasPagoUseCase;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.PaymentMethodResponse;
import java.util.List;

@Service
@AllArgsConstructor
public class FormaPagoService {

    private final ObtenerFormasPagoUseCase obtenerFormasPagoUseCase;

    public List<PaymentMethodResponse> getPaymentMethods(Integer canalId, Integer entidadId) {
        return obtenerFormasPagoUseCase.getPaymentMethods(canalId, entidadId);
    }
}
