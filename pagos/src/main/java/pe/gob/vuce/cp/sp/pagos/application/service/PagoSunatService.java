package pe.gob.vuce.cp.sp.pagos.application.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.CreateOrdenPagoSunatUseCase;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.OrdenPagoRequestDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.OrdenPagoResponseDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.Tupa0ResponseDto;

@Service
@AllArgsConstructor
public class PagoSunatService {

    private final CreateOrdenPagoSunatUseCase createOrdenPagoSunatUseCase;

    public OrdenPagoResponseDto ejecutar(OrdenPagoRequestDto requestDto, String user) {
        return createOrdenPagoSunatUseCase.ejecutar(requestDto, user);
    }

    public Tupa0ResponseDto validarTupa0(OrdenPagoRequestDto requestDto, String user, String token, String tramite, String indicador) {
        return createOrdenPagoSunatUseCase.validarTupa0(requestDto, user, token, tramite, indicador);
    }
}
