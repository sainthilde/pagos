package pe.gob.vuce.cp.sp.pagos.application.usecase;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.ObtenerExcepcionUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.ObtenerExcepcionRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ExcepcionesResponse;

@Component
@AllArgsConstructor
public class ObtenerExcepcionUseCaseImpl implements ObtenerExcepcionUseCase {

    private final ObtenerExcepcionRepositoryPort obtenerExcepcionRepositoryPort;

    @Override
    public ExcepcionesResponse obtenerExcepcion(Integer escalaId, Integer entidad) {
        return obtenerExcepcionRepositoryPort.obtenerExcepcion(escalaId, entidad);
    }
}
