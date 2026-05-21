package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.adapter;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.ObtenerExcepcionRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.ComunesQueryClient;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ExcepcionesDueResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ExcepcionesResponse;

@AllArgsConstructor
@Component
public class ObtenerExcepcionRepositoryAdapter implements ObtenerExcepcionRepositoryPort {

    private final ComunesQueryClient comunesQueryClient;

    @Override
    public ExcepcionesResponse obtenerExcepcionDeclaracion(Integer escalaId, Integer entidad) {
        return comunesQueryClient.obtenerExcepcionDeclaracion(escalaId, entidad);
    }

    @Override
    public ExcepcionesDueResponse obtenerExcepcionPatente(Integer escalaId, Integer entidad) {
        return comunesQueryClient.obtenerExcepcionPatente(escalaId, entidad);
    }

    @Override
    public ExcepcionesResponse obtenerExcepcion(Integer escalaId, Integer entidad) {
        return comunesQueryClient.obtenerExcepcion(escalaId, entidad);
    }

    @Override
    public ExcepcionesDueResponse obtenerExcepcionZarpe(Integer escalaId, Integer entidad) {
        return comunesQueryClient.obtenerExcepcionZarpe(escalaId, entidad);
    }
}
