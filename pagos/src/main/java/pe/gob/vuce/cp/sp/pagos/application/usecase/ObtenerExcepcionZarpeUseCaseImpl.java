package pe.gob.vuce.cp.sp.pagos.application.usecase;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.ObtenerExcepcionZarpeUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.ObtenerExcepcionRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ExcepcionesDueResponse;

@Component
@AllArgsConstructor
public class ObtenerExcepcionZarpeUseCaseImpl implements ObtenerExcepcionZarpeUseCase {

    private final ObtenerExcepcionRepositoryPort obtenerExcepcionRepositoryPort;

    @Override
    public ExcepcionesDueResponse obtenerExcepcionZarpe(Integer escalaId, Integer entidad) {
        return obtenerExcepcionRepositoryPort.obtenerExcepcionZarpe(escalaId, entidad);
    }
}
