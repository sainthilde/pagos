package pe.gob.vuce.cp2.bs.application.usecase;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import pe.gob.vuce.cp2.bs.domain.model.ComprobanteModel;
import pe.gob.vuce.cp2.bs.domain.model.DataModel;
import pe.gob.vuce.cp2.bs.domain.model.DueModel;
import pe.gob.vuce.cp2.bs.domain.model.OperacionModel;
import pe.gob.vuce.cp2.bs.domain.port.in.ProcesarSolicitudPagoUseCase;

@AllArgsConstructor
@Component
public class ProcesarSolicitudPagoUseCaseImpl implements ProcesarSolicitudPagoUseCase {

    @Override
    public OperacionModel procesarSolicitudPago(OperacionModel model) {

        DataModel dataModel = model.getDataModel();
        DueModel dueModel = model.getDueModel();
        ComprobanteModel comprobanteModel = dataModel.getComprobanteModel();

        if (dueModel.isEsArrriboForzoso()) {
            configurarFlujo(
                dataModel,1,false,"Arribo Forzoso",true
            );
            return model;
        }

        if (!dueModel.isEsNavelPrincipal()) {
            configurarFlujo(
                dataModel,2,false,"Convoy",true
            );
            return model;
        }

        boolean esMontoCero = comprobanteModel.getMonto().compareTo(BigDecimal.ZERO) == 0;
        if (dueModel.isComprobanteRegistrado()) {
            if (esMontoCero) {
                configurarFlujo(
                    dataModel,3,false,"Tupa 0",true
                );
            } else {
                configurarFlujo(
                    dataModel,4,true,"Ver CPB",false
                );
            }

            return model;
        }

        if (esMontoCero) {
            configurarFlujo(dataModel,3,true,"Tupa 0",false
            );
        } else {
            configurarFlujo(dataModel,5,true,"Generar CPB",false
            );
        }

        return model;
    }

    private void configurarFlujo(
            DataModel dataModel,
            int idFlujo,
            boolean flujoActivo,
            String descripcion,
            boolean limpiarComprobante
    ) {

        dataModel.setIdFlujo(idFlujo);
        dataModel.setFlujoActivo(flujoActivo);
        dataModel.setDescripcion(descripcion);

        if (limpiarComprobante) {
            dataModel.setComprobanteModel(null);
        }
    }
}
