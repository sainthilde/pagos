package pe.gob.vuce.cp2.bs.application.usecase;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import pe.gob.vuce.cp2.bs.domain.model.DueModel;
import pe.gob.vuce.cp2.bs.domain.model.OperacionModel;
import pe.gob.vuce.cp2.bs.domain.port.in.ObtenerInformacionDueUseCase;
import pe.gob.vuce.cp2.bs.domain.port.out.RepositoryPort;

@AllArgsConstructor
@Component
public class ObtenerInformacionDueUseCaseImpl implements ObtenerInformacionDueUseCase {

    private final RepositoryPort repositoryPort;
    @Override
    public OperacionModel ObtenerInformacionDue(OperacionModel model) {

        repositoryPort.obtenerPuerto(model);    
        repositoryPort.buscarActividadEntidadPuerto(model);
        repositoryPort.buscarMotivoEscala(model);
        repositoryPort.buscarConvoy(model);
        repositoryPort.buscarOrdenPago(model);
        repositoryPort.buscarEntidad(model);

        DueModel dueModel = model.getDueModel();
        System.out.println("CodigoPuerto: " + dueModel.getCodigoPuerto());
        System.out.println("EntidadId: " + dueModel.getEntidadId());
        System.out.println("ReglaNegocio: " + dueModel.getReglaNegocio());
        System.out.println("ComprobanteRegistrado: " + dueModel.isComprobanteRegistrado());
        System.out.println("EsArrriboForzoso: " + dueModel.isEsArrriboForzoso());
        System.out.println("EsNavelPrincipal: " + dueModel.isEsNavelPrincipal());
        System.out.println("CodEntidadGp: " + dueModel.getCodEntidadGp());
        return model;
    }

}
