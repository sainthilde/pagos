package pe.gob.vuce.cp2.bs.application.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import pe.gob.vuce.cp2.bs.domain.model.OperacionModel;
import pe.gob.vuce.cp2.bs.domain.port.in.ObtenerInformacionDueUseCase;
import pe.gob.vuce.cp2.bs.domain.port.in.ObtenerInformacionGPUseCase;
import pe.gob.vuce.cp2.bs.domain.port.in.ProcesarSolicitudPagoUseCase;


@AllArgsConstructor
@Service
public class ReglasService  {

    private final ObtenerInformacionGPUseCase obtenerInformacionGPUseCase;
    private final ObtenerInformacionDueUseCase obtenerInformacionDueUseCase;
    private final ProcesarSolicitudPagoUseCase procesarSolicitudPagoUseCase;

    public OperacionModel validarReglaPagos(OperacionModel model) {
        obtenerInformacionDueUseCase.ObtenerInformacionDue(model);
        obtenerInformacionGPUseCase.obtenerInformacionGP(model); 
        procesarSolicitudPagoUseCase.procesarSolicitudPago(model);
        return model;
    }
}
