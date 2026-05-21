package pe.gob.vuce.cp.sp.pagos.application.usecase;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.ObtenerFormasPagoUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.FeignRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.PaymentMethodResponse;
import java.util.List;


/**
 * Implementación del caso de uso para gestionar actividades de entidades.
 * Esta clase proporciona métodos para acceder a la información de actividades
 * a través del repositorio correspondiente.
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Component
@AllArgsConstructor
public class ObtenerFormasPagoUseCaseImpl implements ObtenerFormasPagoUseCase {

    private final FeignRepositoryPort feignRepositoryPort;

    @Override
    public List<PaymentMethodResponse> getPaymentMethods(Integer canalId, Integer entidadId) {
        return feignRepositoryPort.getPaymentMethods(canalId, entidadId);
    }
}
