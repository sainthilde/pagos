package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out;

import java.util.List;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.ExcepcionMensajeResponseDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.OrdenPagoResponseDto;

/**
 * Puerto de salida para la integración con el servicio comunes-command-api.
 * Proporciona la interfaz para guardar el seguimiento de una escala a través de
 * un cliente Feign.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 22/08/2024
 */
public interface FeignOrdenPagoClientPort {

    /**
     * Guarda el seguimiento de una escala en el servicio comunes-command-api.
     *
     * @param ordenPagoId Identificador del usuario que realiza la operación.
     * @return Una respuesta común que incluye metadatos y el resultado de la
     *         operación.
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Fernando Tanta
     * @date 22/08/2024
     */
    Object anular(Integer ordenPagoId, String user);

    ExcepcionMensajeResponseDto obtenerExcepciones(Integer escalaId, Integer entidad);

    List<OrdenPagoResponseDto> findByEscalaIdAndDocumentoId(Integer escalaId, Integer documentoId);
}
