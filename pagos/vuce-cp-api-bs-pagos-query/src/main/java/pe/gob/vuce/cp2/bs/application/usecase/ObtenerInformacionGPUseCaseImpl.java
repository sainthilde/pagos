package pe.gob.vuce.cp2.bs.application.usecase;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import pe.gob.vuce.cp2.bs.domain.model.GestorProcedimientoModel;
import pe.gob.vuce.cp2.bs.domain.model.OperacionModel;
import pe.gob.vuce.cp2.bs.domain.port.in.ObtenerInformacionGPUseCase;
import pe.gob.vuce.cp2.bs.domain.port.out.FeignPort;


@AllArgsConstructor
@Component
public class ObtenerInformacionGPUseCaseImpl implements ObtenerInformacionGPUseCase {

    private final FeignPort feignPort;
    @Override
    public OperacionModel obtenerInformacionGP(OperacionModel model) {
            model.setGestorProcedimientoModel(
                    GestorProcedimientoModel.builder()
                        .token("Bearer " + feignPort.obtenerToken())
                        .componente("CPN")
                        //.entidadId(Integer.parseInt(dueModel.getCodEntidadGp()))
                        //.textSearch(dueModel.getReglaNegocio())
                    .build()
                );
            feignPort.obtenerProcedimientoComponente(model);
            feignPort.obtenerProcedimientoTasa(model);
        
        return model;
    }


}
