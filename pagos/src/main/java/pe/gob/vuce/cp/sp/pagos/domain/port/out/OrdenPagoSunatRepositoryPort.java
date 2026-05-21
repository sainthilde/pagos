package pe.gob.vuce.cp.sp.pagos.domain.port.out;

import org.springframework.stereotype.Component;

import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.OrdenPagoRequestDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.OrdenPagoResponseDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.Tupa0ResponseDto;

@Component
public interface OrdenPagoSunatRepositoryPort {
    OrdenPagoResponseDto ejecutar(OrdenPagoRequestDto requestDto, String user);
    Tupa0ResponseDto validarTupa0(OrdenPagoRequestDto requestDto, String user, String token, String tramite, String indicador);
}
