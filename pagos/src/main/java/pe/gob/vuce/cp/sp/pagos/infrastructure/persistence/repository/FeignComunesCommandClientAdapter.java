package pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.SeguimientoRequestDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.CommonResponse;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.FeignComunesCommandClientPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.FeignComunesCommandClient;


@AllArgsConstructor
@Component
public class FeignComunesCommandClientAdapter implements FeignComunesCommandClientPort {
    private final FeignComunesCommandClient feignComunesCommandClient;

    @Override
    public CommonResponse saveEscalaSeguimiento(SeguimientoRequestDto object, String user) {
        return feignComunesCommandClient.saveEscalaSeguimiento(object, user);
    }
}
