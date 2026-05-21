package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import pe.gob.vuce.cp.framework.globallogger.annotation.Loggable;
import pe.gob.vuce.cp.framework.globallogger.constants.LogTypes;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.DocumentRequestDTO;

/**
 * Cliente Feign para interactuar con la API de documentos.
 */
@FeignClient(name = "documentClient", url = "${feign.client.document-api.base-url}")
@Component
public interface DocumentClient {

    /**
     * Envía un archivo a la API de documentos.
     *
     * @param documentRequestDTO DTO con los datos del documento a enviar.
     * @return Respuesta de la API como String.
     * @author CPLX
     * @version 1.0
     * @project vuce-cp-api-sp-pagos
     * @date 2024-10-26
     */
    @Loggable(category = LogTypes.FEIGN)
    @PostMapping(value = "/documentos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    String postFile(@RequestBody DocumentRequestDTO documentRequestDTO);

    /**
     * Obtiene un archivo de la API de documentos por su ID.
     *
     * @param ecmDocumentoId ID del documento en el ECM.
     * @return Archivo como recurso.
     */
    @Loggable(category = LogTypes.FEIGN)
    @GetMapping(value = "/documentos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Resource obtenerFile(@RequestParam String ecmDocumentoId);

}
