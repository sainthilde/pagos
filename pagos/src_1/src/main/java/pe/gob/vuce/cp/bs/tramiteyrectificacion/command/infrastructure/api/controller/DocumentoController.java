package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Parameter;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.application.service.DocumentoService;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.ApiResponseDocumentoResponseDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.DocumentoResponseDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.enums.MetaResults;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.exception.ApiResponseMeta;
import pe.gob.vuce.cp.framework.globallogger.annotation.Loggable;

/**
 * Controlador REST que maneja las solicitudes relacionadas con los documentos
 * en el contexto de la ficha sanitaria.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 21/08/2024
 */
@RestController
public class DocumentoController {

    private final DocumentoService documentoService;

    /**
     * Constructor para inyectar la dependencia del servicio de documentos.
     *
     * @param documentoService El servicio que maneja la lógica de negocio
     *                         relacionada con documentos.
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Fernando Tanta
     * @date 21/08/2024
     */
    public DocumentoController(DocumentoService documentoService) {
        this.documentoService = documentoService;
    }

    /**
     * Maneja la creación de un documento a partir de un archivo subido.
     *
     * @param file El archivo subido que contiene el documento.
     * @return Una respuesta con los detalles del documento creado.
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Fernando Tanta
     * @date 21/08/2024
     */

    @Loggable
    @PostMapping(value = "/documentos", produces = { "application/json" }, consumes = { "multipart/form-data" })
    public ResponseEntity<ApiResponseDocumentoResponseDto> createDocumento(
            @Parameter(name = "file", description = "", required = true) @RequestPart(value = "file", required = true) MultipartFile file) {

        DocumentoResponseDto documentoResponseDto = documentoService.create(file);

        ApiResponseDocumentoResponseDto apiResponse = new ApiResponseDocumentoResponseDto();
        apiResponse.setData(List.of(documentoResponseDto));

        ApiResponseMeta apiResponseMeta = new ApiResponseMeta();
        apiResponseMeta.setResult(MetaResults.SUCCESS.getValue());
        apiResponseMeta.setCantidadRegistros(apiResponse.getData().size());
        apiResponseMeta.setCantidadRegistrosTotal(apiResponse.getData().size());
        apiResponseMeta.setAtributos(Map.of());

        apiResponse.setMeta(apiResponseMeta);

        return ResponseEntity.ok(apiResponse);
    }
}
