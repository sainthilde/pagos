package pe.gob.vuce.cp.sp.pagos.domain.port.out;


import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.SeguimientoRequestDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.CommonResponse;

/**
 * Puerto de salida para la integración con el servicio comunes-command-api.
 * Proporciona la interfaz para guardar el seguimiento de una escala a través de un cliente Feign.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 22/08/2024
 */
@Component
public interface FeignComunesCommandClientPort {

    /**
     * Guarda el seguimiento de una escala en el servicio comunes-command-api.
     *
     * @param object DTO que contiene los datos del seguimiento de la escala.
     * @param user   Identificador del usuario que realiza la operación.
     * @return Una respuesta común que incluye metadatos y el resultado de la operación.
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Fernando Tanta
     * @date 22/08/2024
     */
    CommonResponse saveEscalaSeguimiento(SeguimientoRequestDto object, String user);
}
