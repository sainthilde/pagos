package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.application.usecase;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants.ErrorCodes;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.exceptions.BusinessError;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.TramiteModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.UpdateTramiteUseCase;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.UpdateTramitesUseCase;

/**
 * Implementación del caso de uso para la actualización de tramite.
 * 
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 22/08/2024
 */
@AllArgsConstructor
@Component
public class UpdateTramitesUseCaseImpl implements UpdateTramitesUseCase {


    private final UpdateTramiteUseCase updateTramiteUseCase;

    /**
     * Actualiza un tramite existente en el sistema.
     * 
     * @param tramiteModel Modelo que contiene los datos del tramite a actualizar.
     * @return El modelo del tramite actualizado.
     * @throws BusinessError en caso de que ocurra un error durante la actualización
     *                       del tramite.
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Fernando Tanta
     * @date 22/08/2024
     */
    @Override
    @Transactional
    public List<TramiteModel> update(List<TramiteModel> tramiteModel, String ruc,String user, String operacion) {
        return tramiteModel.stream()
                .map(tramite -> {
                    try {
                        return updateTramiteUseCase.update(tramite, ruc,user,operacion);
                    } catch (Exception e) {
                        throw new BusinessError(HttpStatus.INTERNAL_SERVER_ERROR,
                                ErrorCodes.INTERNAL_SERVER_ERROR,
                                List.of(),
                                e.getMessage());
                    }
                })
                .toList();
    }

}
