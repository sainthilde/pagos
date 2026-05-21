package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import pe.gob.vuce.cp.framework.globallogger.annotation.Loggable;
import pe.gob.vuce.cp.framework.globallogger.constants.LogTypes;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.SeguimientoRequestDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.CommonResponse;

/**
 * Cliente Feign para interactuar con el servicio comunes-command-api. Permite
 * enviar solicitudes
 * para guardar el seguimiento de la escala a través de una API externa.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 22/08/2024
 */
@FeignClient(name = "comunes-command-api", url = "${feign.client.comunes-command-api.base-url}")
public interface FeignComunesCommandClient {

    /**
     * Envia una solicitud POST para guardar el seguimiento de una escala en el
     * servicio comunes-command-api.
     *
     * @param object DTO que contiene los datos del seguimiento de la escala.
     * @param user   Identificador del usuario que realiza la operación, enviado en
     *               el encabezado de la solicitud.
     * @return Una respuesta común que incluye metadatos y el resultado de la
     *         operación.
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Fernando Tanta
     * @date 22/08/2024
     */
    @Loggable(category = LogTypes.FEIGN)
    @PostMapping(value = "/escalaseguimiento/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    CommonResponse saveEscalaSeguimiento(@RequestBody SeguimientoRequestDto object,
            @RequestHeader(value = "user") String user);
}
