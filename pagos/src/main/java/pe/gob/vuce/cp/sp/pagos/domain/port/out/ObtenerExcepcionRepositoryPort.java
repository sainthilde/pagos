package pe.gob.vuce.cp.sp.pagos.domain.port.out;

import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ExcepcionesDueResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ExcepcionesResponse;

@Component
public interface ObtenerExcepcionRepositoryPort {
    ExcepcionesResponse obtenerExcepcionDeclaracion(Integer escalaId, Integer entidad);
    ExcepcionesDueResponse obtenerExcepcionPatente(Integer escalaId, Integer entidad);
    ExcepcionesResponse obtenerExcepcion(Integer escalaId, Integer entidad);
    ExcepcionesDueResponse obtenerExcepcionZarpe(Integer escalaId, Integer entidad);
}
