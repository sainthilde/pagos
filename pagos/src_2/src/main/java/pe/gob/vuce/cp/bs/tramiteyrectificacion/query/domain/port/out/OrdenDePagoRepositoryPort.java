package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.port.out;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.OrdenDePagoModel;

import java.util.List;



public interface OrdenDePagoRepositoryPort {
    List<OrdenDePagoModel> findOrdenesDePago(Integer escalaId, Integer documentoId, String rucAgente, String estadoOrdenPago);
}
