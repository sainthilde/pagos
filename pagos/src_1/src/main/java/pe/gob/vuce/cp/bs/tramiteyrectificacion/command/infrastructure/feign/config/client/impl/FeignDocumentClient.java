package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.feign.config.client.impl;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.DocumentRequestDTO;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.CommonResponse;

/**
 * Cliente Feign para interactuar con el servicio de documentos. Permite enviar
 * solicitudes
 * para crear documentos a través de una API externa.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 21/08/2024
 */
@FeignClient(name = "document", url = "${app.document.url}")
public interface FeignDocumentClient {

    /**
     * Envia una solicitud POST para crear un documento en el servicio de
     * documentos.
     *
     * @param documentRequestDTO DTO que contiene los datos del documento que se
     *                           desea crear.
     * @return Una respuesta común que incluye metadatos y el resultado de la
     *         operación.
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Fernando Tanta
     * @date 21/08/2024
     */
    @PostMapping(value = "/documentos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    CommonResponse postFile(@RequestBody DocumentRequestDTO documentRequestDTO);

}
