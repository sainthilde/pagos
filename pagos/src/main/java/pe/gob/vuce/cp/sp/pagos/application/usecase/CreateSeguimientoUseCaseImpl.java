package pe.gob.vuce.cp.sp.pagos.application.usecase;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.SeguimientoRequestDto;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.CreateSeguimientoUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.FeignComunesCommandClientPort;


@Component
@AllArgsConstructor
public class CreateSeguimientoUseCaseImpl implements CreateSeguimientoUseCase {

    private final FeignComunesCommandClientPort feignComunesCommandClientPort;

    @Transactional
    @Override
    public void create(SeguimientoRequestDto seguimientoRequestDto, String user) {
        feignComunesCommandClientPort.saveEscalaSeguimiento(seguimientoRequestDto, user);
    }
}