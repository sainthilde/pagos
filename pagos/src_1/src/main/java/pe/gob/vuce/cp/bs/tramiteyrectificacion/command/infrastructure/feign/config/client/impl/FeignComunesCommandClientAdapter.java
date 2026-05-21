package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.feign.config.client.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.SeguimientoRequestDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.CommonResponse;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.FeignComunesCommandClientPort;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.feign.config.FeignComunesCommandClient;

@AllArgsConstructor
@Component
public class FeignComunesCommandClientAdapter implements FeignComunesCommandClientPort {
    private final FeignComunesCommandClient feignComunesCommandClient;

    @Override
    public CommonResponse saveEscalaSeguimiento(SeguimientoRequestDto object, String user) {
        return feignComunesCommandClient.saveEscalaSeguimiento(object, user);
    }
}
