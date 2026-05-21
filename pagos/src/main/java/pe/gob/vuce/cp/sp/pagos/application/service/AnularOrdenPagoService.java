package pe.gob.vuce.cp.sp.pagos.application.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.AnularOrdenPagoUseCase;
import pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.response.OrdenPagoResponseDTO;

@Service
@AllArgsConstructor
public class AnularOrdenPagoService {

    private final AnularOrdenPagoUseCase anularOrdenPagoUseCase;

    public OrdenPagoResponseDTO anularOrdenPago(Integer ordenPagoId,String user) {
        return anularOrdenPagoUseCase.anularOrdenPago(ordenPagoId,user);
    }
}
