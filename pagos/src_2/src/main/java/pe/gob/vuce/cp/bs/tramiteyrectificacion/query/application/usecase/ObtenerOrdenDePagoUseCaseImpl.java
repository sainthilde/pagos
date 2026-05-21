package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.application.usecase;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.OrdenDePagoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.port.in.ObtenerOrdenDePagoUseCase;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.port.out.OrdenDePagoRepositoryPort;

import java.util.List;

/**
 * Implementación del caso de uso para la obtención de órdenes de pago en el
 * sistema.
 *
 * @project cp-api-bs-tramiteyrectificacion-query
 * @autor Elver Valverde
 * @date 24/08/2024
 */
@Service
@AllArgsConstructor
public class ObtenerOrdenDePagoUseCaseImpl implements ObtenerOrdenDePagoUseCase {
    private final OrdenDePagoRepositoryPort ordenDePagoRepositoryPort;

    @Override
    public List<OrdenDePagoModel> findOrdenesDePago(Integer escalaId, Integer documentoId, String rucAgente,
            String estadoOrdenPago) {
        return ordenDePagoRepositoryPort.findOrdenesDePago(escalaId, documentoId, rucAgente, estadoOrdenPago);
    }
}
