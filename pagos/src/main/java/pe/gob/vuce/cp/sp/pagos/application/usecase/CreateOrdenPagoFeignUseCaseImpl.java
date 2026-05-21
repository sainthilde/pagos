package pe.gob.vuce.cp.sp.pagos.application.usecase;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.CreateOrdenPagoFeignUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.FeignRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.OrdenPagoRequestDTO;
import pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.response.OrdenPagoResponseDTO;

/**
 * Implementación del caso de uso para crear órdenes de pago.
 * Esta clase se encarga de la lógica necesaria para crear
 * una nueva OrdenPago y persistirla en el repositorio correspondiente.
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Component
@AllArgsConstructor
public class CreateOrdenPagoFeignUseCaseImpl implements CreateOrdenPagoFeignUseCase {

    private final FeignRepositoryPort feignRepositoryPort;

    @Override
    public OrdenPagoResponseDTO createOrdenPago(OrdenPagoRequestDTO ordenPagoRequestDto) {
        return feignRepositoryPort.createOrdenPago(ordenPagoRequestDto);
    }
}
