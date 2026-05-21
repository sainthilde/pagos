package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.feign.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestHeader;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.ExcepcionMensajeResponseDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.OrdenPagoResponseDto;

import java.util.List;

/**
 * Cliente Feign para interactuar con el servicio de documentos. Permite enviar
 * solicitudes
 * para crear documentos a través de una API externa.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 21/08/2024
 */
@FeignClient(name = "ordenpago", url = "${app.ordenpago.url}", configuration = OrdenPagoFeignConfig.class)
public interface FeignOrdenPagoClient {

    /**
     * Envia una solicitud POST para crear un documento en el servicio de
     * documentos.
     *
     * @PathVariable ordenPagoId DTO que contiene los datos del documento que se
     *                           desea crear.
     * @return Una respuesta común que incluye metadatos y el resultado de la
     *         operación.
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Fernando Tanta
     * @date 21/08/2024
     */
    @PutMapping(value = "/ordenes-pago/{ordenPagoId}/anular", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> anular(@PathVariable Integer ordenPagoId, @RequestHeader(value = "user") String user);

    /**
     * Envia una solicitud GET obtener las excepciones de pago por escala y entidad
     * documentos.
     *
     * @PathVariable escalaId identificador de la escala.
     * @PathVariable entidad identificador de la entidad.
     * @return Una respuesta común que incluye metadatos y el resultado de la busqueda de
     * la excepcion
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Fernando Tanta
     * @date 26/12/2024
     */
    @GetMapping(value = "/pagos/escala/{escalaId}/detalles/{entidad}", produces = MediaType.APPLICATION_JSON_VALUE)
    ExcepcionMensajeResponseDto obtenerExcepciones(@PathVariable Integer escalaId, @PathVariable Integer entidad);

    /**
     * Envia una solicitud GET obtener las ordeenes de pago por escala y documento
     * documentos.
     *
     * @PathVariable escalaId identificador de la escala.
     * @PathVariable documentoId identificador del documento.
     * @return Una respuesta común que incluye metadatos y el resultado de la busqueda de
     * las ordenes de pago
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Fernando Tanta
     * @date 26/12/2024
     */
    @GetMapping(value = "/ordenes-pago/{escalaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<OrdenPagoResponseDto> findByEscalaIdAndDocumentoId(@PathVariable Integer escalaId, @RequestParam Integer documentoId);
}
