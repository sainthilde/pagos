package pe.gob.vuce.cp.sp.pagos.application.usecase;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.CreateOrdenPagoSunatUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.OrdenPagoSunatRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.OrdenPagoRequestDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.OrdenPagoResponseDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.Tupa0ResponseDto;

@Component
@AllArgsConstructor
public class CreateOrdenPagoSunatUseCaseImpl implements CreateOrdenPagoSunatUseCase {

    private final OrdenPagoSunatRepositoryPort ordenPagoSunatRepositoryPort;

    @Override
    public OrdenPagoResponseDto ejecutar(OrdenPagoRequestDto requestDto, String user) {
        return ordenPagoSunatRepositoryPort.ejecutar(requestDto, user);
    }

    @Override
    public Tupa0ResponseDto validarTupa0(OrdenPagoRequestDto requestDto, String user, String token, String tramite,String indicador) {
         return ordenPagoSunatRepositoryPort.validarTupa0(requestDto, user, token, tramite, indicador);
    }
    
}
