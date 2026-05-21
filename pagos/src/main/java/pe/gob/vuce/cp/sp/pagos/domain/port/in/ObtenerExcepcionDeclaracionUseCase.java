package pe.gob.vuce.cp.sp.pagos.domain.port.in;

import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ExcepcionesResponse;

@Component
public interface ObtenerExcepcionDeclaracionUseCase {

    ExcepcionesResponse obtenerExcepcionDeclaracion(Integer escalaId, Integer entidad);
}
