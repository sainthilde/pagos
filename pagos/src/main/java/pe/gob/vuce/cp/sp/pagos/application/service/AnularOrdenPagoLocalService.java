package pe.gob.vuce.cp.sp.pagos.application.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.AnularOrdenPagoLocalUseCase;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.OrdenPagoResponseDto;

@Service
@AllArgsConstructor
public class AnularOrdenPagoLocalService {

    private final AnularOrdenPagoLocalUseCase anularOrdenPagoLocalUseCase;

    public OrdenPagoResponseDto anularOrdenPagoLocal(Integer ordenPagoId,String user) {
        return anularOrdenPagoLocalUseCase.anularOrdenPagoLocal(ordenPagoId,user);
    }
}
