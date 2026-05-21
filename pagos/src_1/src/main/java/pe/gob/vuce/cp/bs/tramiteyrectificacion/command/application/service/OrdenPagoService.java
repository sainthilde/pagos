package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.application.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.OrdenDePagoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.UpdateOrdenPagoUseCase;

@Service
@AllArgsConstructor
public class OrdenPagoService {

    private final UpdateOrdenPagoUseCase updateOrdenPagoUseCase;

    public OrdenDePagoModel udpate(OrdenDePagoModel ordenPagoModel) {
        return updateOrdenPagoUseCase.update(ordenPagoModel);
    }

}
