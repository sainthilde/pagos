package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.application.usecase;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.OrdenDePagoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.UpdateOrdenPagoUseCase;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.OrdenPagoRepositoryPort;

@Component
@AllArgsConstructor
public class UpdateOrdenDePagoUseCaseImpl implements UpdateOrdenPagoUseCase {

    OrdenPagoRepositoryPort ordenPagoRepositoryPort;

    @Override
    public OrdenDePagoModel update(OrdenDePagoModel ordenPagoModel) {
        return ordenPagoRepositoryPort.updateV2(ordenPagoModel);

    }
}
