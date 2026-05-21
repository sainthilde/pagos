package pe.gob.vuce.cp.sp.pagos.domain.port.in;

import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.PaymentMethodResponse;
import java.util.List;

@Component
public interface ObtenerFormasPagoUseCase {
    List<PaymentMethodResponse> getPaymentMethods(Integer canalId, Integer entidadId);
}
