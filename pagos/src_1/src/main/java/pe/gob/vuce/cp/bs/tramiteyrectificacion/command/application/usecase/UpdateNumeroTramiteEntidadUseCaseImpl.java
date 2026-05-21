package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.application.usecase;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants.ErrorCodes;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants.Messages;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.exceptions.BusinessError;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.TramiteModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.UpdateNumeroTramiteEntidadUseCase;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.TramiteRepositoryPort;

/**
 * Implementacion del caso de uso para actualizar numeroTramiteEntidad, tupa e
 * indicador manual.
 */
@AllArgsConstructor
@Component
public class UpdateNumeroTramiteEntidadUseCaseImpl implements UpdateNumeroTramiteEntidadUseCase {

    private final TramiteRepositoryPort tramiteRepositoryPort;

    @Override
    public TramiteModel updateNumeroTramiteEntidad(Integer tramiteId, Integer escalaId, String numeroTramiteEntidad,
            String tupa, Boolean indAsTramiteManual) {
        TramiteModel tramite = tramiteRepositoryPort.findByIdAndEscalaId(tramiteId, escalaId)
                .orElseThrow(() -> new BusinessError(HttpStatus.BAD_REQUEST, ErrorCodes.NOT_FOUND, List.of(),
                        Messages.TRAMITE_NO_ENCONTRADO + tramiteId));

        tramite.setNumeroTramiteEntidad(numeroTramiteEntidad);
        tramite.setTupa(tupa);
        tramite.setIndAsignacionTramiteManual(indAsTramiteManual);
        // Auditoria: se mantiene criterio del otro servicio: usuidModAud = usuidRegAud
        tramite.setUsuidModAud(tramite.getUsuidRegAud());
        return tramiteRepositoryPort.update(tramite);
    }
}
