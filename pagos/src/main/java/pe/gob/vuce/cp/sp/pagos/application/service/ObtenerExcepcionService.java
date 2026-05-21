package pe.gob.vuce.cp.sp.pagos.application.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.ObtenerExcepcionDeclaracionUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.ObtenerExcepcionPatenteUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.ObtenerExcepcionUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.ObtenerExcepcionZarpeUseCase;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ExcepcionesDueResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ExcepcionesResponse;

@Service
@AllArgsConstructor
public class ObtenerExcepcionService {

    private final ObtenerExcepcionDeclaracionUseCase obtenerExcepcionDeclaracionUseCase;
    private final ObtenerExcepcionPatenteUseCase obtenerExcepcionPatenteUseCase;
    private final ObtenerExcepcionUseCase obtenerExcepcionUseCase;
    private final ObtenerExcepcionZarpeUseCase obtenerExcepcionZarpeUseCase;

    public ExcepcionesResponse obtenerExcepcionDeclaracion(Integer escalaId, Integer entidad) {
        return obtenerExcepcionDeclaracionUseCase.obtenerExcepcionDeclaracion(escalaId, entidad);
    }

    public ExcepcionesDueResponse obtenerExcepcionPatente(Integer escalaId, Integer entidad) {
        return obtenerExcepcionPatenteUseCase.obtenerExcepcionPatente(escalaId, entidad);
    }

    public ExcepcionesResponse obtenerExcepcion(Integer escalaId, Integer entidad) {
        return obtenerExcepcionUseCase.obtenerExcepcion(escalaId, entidad);
    }

    public ExcepcionesDueResponse obtenerExcepcionZarpe(Integer escalaId, Integer entidad) {
        return obtenerExcepcionZarpeUseCase.obtenerExcepcionZarpe(escalaId, entidad);
    }
}
