package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.application.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.OrdenDePagoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.port.in.ObtenerOrdenDePagoUseCase;

import java.util.List;

@Service
@AllArgsConstructor
public class OrdenDePagoService {
    private final ObtenerOrdenDePagoUseCase obtenerOrdenDePago;

    public List<OrdenDePagoModel> findOrdenesDePago(Integer escalaId, Integer documentId, String rucAgente,String estadoOrdenPago) {
        return obtenerOrdenDePago.findOrdenesDePago(escalaId, documentId, rucAgente,estadoOrdenPago);
    }
}
