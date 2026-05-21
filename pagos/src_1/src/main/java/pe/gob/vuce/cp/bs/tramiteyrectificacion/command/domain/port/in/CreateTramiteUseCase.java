package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.TramiteModel;

/**
 * Caso de uso para la creación de tramites. Proporciona una interfaz para
 * registrar un tramite en el sistema.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 19/08/2024
 */
public interface CreateTramiteUseCase {

    /**
     * Registra un tramite en el sistema.
     *
     * @param tramiteModel El modelo que contiene los datos del tramite a
     *                       registrar.
     * @return El identificador del tramite registrado.
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Fernando Tanta
     * @date 19/08/2024
     */
    TramiteModel create(TramiteModel tramiteModel, String ruc,String user);

}
