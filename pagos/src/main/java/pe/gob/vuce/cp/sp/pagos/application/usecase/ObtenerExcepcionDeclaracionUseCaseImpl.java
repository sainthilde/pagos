package pe.gob.vuce.cp.sp.pagos.application.usecase;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.ObtenerExcepcionDeclaracionUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.ObtenerExcepcionRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ExcepcionesResponse;

@Component
@AllArgsConstructor
public class ObtenerExcepcionDeclaracionUseCaseImpl implements ObtenerExcepcionDeclaracionUseCase {

    private final ObtenerExcepcionRepositoryPort obtenerExcepcionRepositoryPort;

    @Override
    public ExcepcionesResponse obtenerExcepcionDeclaracion(Integer escalaId, Integer entidad) {
        return obtenerExcepcionRepositoryPort.obtenerExcepcionDeclaracion(escalaId, entidad);
    }
}
