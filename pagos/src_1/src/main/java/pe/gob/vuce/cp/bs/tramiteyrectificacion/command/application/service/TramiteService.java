package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.TramiteModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.CreateTramiteUseCase;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.DesistTramiteUseCase;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.SearchTramiteUseCase;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.UpdateTramiteUseCase;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.UpdateNumeroTramiteEntidadUseCase;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.UpdateTramitesUseCase;

/**
 * Servicio encargado de gestionar la creación y actualización de los tramites.
 * 
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 19/08/2024
 */
@Service
@AllArgsConstructor
public class TramiteService {

    private final CreateTramiteUseCase createTramiteUseCase;
    private final UpdateTramiteUseCase updateTramiteUseCase;
    private final DesistTramiteUseCase desistTramiteUseCase;
    private final SearchTramiteUseCase findTramiteUseCase;
    private final UpdateTramitesUseCase updateTramitesUseCase;
    private final UpdateNumeroTramiteEntidadUseCase updateNumeroTramiteEntidadUseCase;

    /**
     * Crea un nuevo trámite utilizando el caso de uso correspondiente.
     * 
     * @param tramiteModel El modelo que contiene la información del tramite a crear
     * @return El modelo de trámite creado.
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Fernando Tanta
     * @date 19/08/2024
     */
    public TramiteModel create(TramiteModel tramiteModel, String ruc, String user) {
        return createTramiteUseCase.create(tramiteModel, ruc, user);
    }

    /**
     * Crea un nuevo trámite utilizando el caso de uso correspondiente.
     *
     * @param tramiteModel El modelo que contiene la información del tramite a
     *                     actualizar
     * @return El modelo de trámite actualizado.
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Fernando Tanta
     * @date 19/08/2024
     */
    public TramiteModel update(TramiteModel tramiteModel, String ruc, String user, String operacion) {
        return updateTramiteUseCase.update(tramiteModel, ruc, user, operacion);
    }

    public List<TramiteModel> desist(Integer escalaId, Integer tramited, String user) {
        return desistTramiteUseCase.desist(escalaId, tramited, user);
    }

    public List<TramiteModel> search(Integer id) {
        return findTramiteUseCase.findByEscalaId(id);
    }

    public List<TramiteModel> update(List<TramiteModel> tramiteModel, String ruc, String user, String operacion) {
        return updateTramitesUseCase.update(tramiteModel, ruc, user, operacion);
    }

    /**
     * Actualiza numeroTramiteEntidad, tupa e indicador manual de un tramite
     * existente.
     */
    public TramiteModel updateNumeroTramiteEntidad(Integer tramiteId, Integer escalaId, String numeroTramiteEntidad,
            String tupa, Boolean indAsTramiteManual) {
        return updateNumeroTramiteEntidadUseCase.updateNumeroTramiteEntidad(tramiteId, escalaId, numeroTramiteEntidad,
                tupa, indAsTramiteManual);
    }
}
