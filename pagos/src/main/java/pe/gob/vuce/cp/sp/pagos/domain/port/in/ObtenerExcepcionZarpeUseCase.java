package pe.gob.vuce.cp.sp.pagos.domain.port.in;

import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ExcepcionesDueResponse;

@Component
public interface ObtenerExcepcionZarpeUseCase {
    ExcepcionesDueResponse obtenerExcepcionZarpe(Integer escalaId, Integer entidad);
}
