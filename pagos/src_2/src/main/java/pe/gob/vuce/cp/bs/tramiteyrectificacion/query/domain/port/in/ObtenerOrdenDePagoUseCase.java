package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.port.in;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.OrdenDePagoModel;

import java.util.List;

public interface ObtenerOrdenDePagoUseCase {
    List<OrdenDePagoModel> findOrdenesDePago(Integer escalaId, Integer documentoId, String rucAgente, String estadoOrdenPago);
}
