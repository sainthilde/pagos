package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in;

import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.TramiteModel;

/**
 * Caso de uso para actualizar el numero de tramite de la entidad, tupa e
 * indicador manual.
 */
@Component
public interface UpdateNumeroTramiteEntidadUseCase {
    TramiteModel updateNumeroTramiteEntidad(Integer tramiteId, Integer escalaId, String numeroTramiteEntidad,
            String tupa, Boolean indAsTramiteManual);
}
