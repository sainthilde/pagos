package pe.gob.vuce.cp.sp.pagos.application.usecase;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.ObtenerExcepcionPatenteUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.ObtenerExcepcionRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ExcepcionesDueResponse;

@Component
@AllArgsConstructor
public class ObtenerExcepcionPatenteUseCaseImpl implements ObtenerExcepcionPatenteUseCase {

    private final ObtenerExcepcionRepositoryPort obtenerExcepcionRepositoryPort;

    @Override
    public ExcepcionesDueResponse obtenerExcepcionPatente(Integer escalaId, Integer entidad) {
        return obtenerExcepcionRepositoryPort.obtenerExcepcionPatente(escalaId, entidad);
    }
}
