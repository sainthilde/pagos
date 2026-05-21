package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in;

import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.TramiteModel;

import java.util.List;

/**
 * Caso de uso funcional para la actualización de fichas sanitarias.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 21/08/2024
 */
@FunctionalInterface
@Component
public interface UpdateTramitesUseCase {

    /**
     * Actualiza una ficha sanitaria existente en el sistema.
     *
     * @param tramiteModel El modelo que contiene los datos del tramite a actualizar.
     * @return El modelo del tramite actualizado.
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Fernando Tanta
     * @date 21/08/2024
     */
    List<TramiteModel> update(List<TramiteModel> tramiteModel, String ruc,String user, String operacion);
}
