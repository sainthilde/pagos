package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.api.controller;

import static pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants.Constants.separador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.application.service.TramiteService;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.ApiResponseTramiteDesistResponseDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.ApiResponseTramiteResponseDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.OrdenPagoErrorResponse;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.TramiteCrearRequestDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.TramiteDesistRequestDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.TramiteDesistResponseDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.TramiteResponseDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.TramiteUpdateNumeroTramiteEntidadRequestDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.TramiteUpdateRequestDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.enums.MetaResults;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.exceptions.OrdenPagoAnulacionException;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.TramiteModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.exception.ApiResponseMeta;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.mapper.TramiteMapper;
import pe.gob.vuce.cp.framework.globallogger.annotation.Loggable;

/**
 * Controlador REST que maneja las solicitudes relacionadas con el tramite
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 19/08/2024
 */

@RestController
@RequestMapping("/tramites")
@AllArgsConstructor
public class TramiteController {

    private final TramiteService tramiteService;
    private final TramiteMapper tramiteMapper;
    private final HttpServletRequest request;

    /**
     * Crea un nuevo trámite a partir del cuerpo de la solicitud.
     *
     * @param tramiteCrearRequestDto Información del trámite a crear.
     * @return Respuesta con los datos del trámite creado.
     */
    @Loggable
    @PostMapping
    public ResponseEntity<ApiResponseTramiteResponseDto> createTramite(
            @Valid @RequestBody TramiteCrearRequestDto tramiteCrearRequestDto) {

        String user = request.getHeader("user");
        String ruc = request.getHeader("ruc");
        TramiteModel tramiteyrectificacionModel = tramiteMapper.dtoToModelCrear(tramiteCrearRequestDto,
                separador(user, 1));

        TramiteModel result = tramiteService.create(tramiteyrectificacionModel, ruc, user);

        TramiteResponseDto response = new TramiteResponseDto(result.getTramiteId(), result.getNumeroSuce());
        return ResponseEntity.ok(buildTramiteResponse(List.of(response)));
    }

    /**
     * Actualiza el numero de tramite de la entidad, tupa e indicador manual.
     */
    @Loggable
    @PutMapping(value = "/update-numero-tramite-entidad")
    public ResponseEntity<ApiResponseTramiteResponseDto> updateNumeroTramiteEntidad(
            @Valid @RequestBody TramiteUpdateNumeroTramiteEntidadRequestDto dto) {

        TramiteModel updated = tramiteService.updateNumeroTramiteEntidad(dto.getTramiteId(), dto.getEscalaId(),
                dto.getNumeroTramiteEntidad(), dto.getTupa(), dto.getIndAsTramiteManual());

        TramiteResponseDto response = tramiteMapper.modelToDto(updated);
        return ResponseEntity.ok(buildTramiteResponse(List.of(response)));
    }

    /**
     * Actualiza parcialmente un trámite existente con la información proporcionada.
     *
     * @param tramiteUpdateRequestDto Información para actualizar el trámite.
     * @return Respuesta con el trámite actualizado.
     */
    @Loggable
    @PatchMapping
    public ResponseEntity<ApiResponseTramiteResponseDto> updateTramite(
            @Valid @RequestBody TramiteUpdateRequestDto tramiteUpdateRequestDto) {

        String user = request.getHeader("user");
        String ruc = request.getHeader("ruc");

        TramiteModel tramiteModel = tramiteMapper.dtoToModelUpdate(tramiteUpdateRequestDto, separador(user, 1));

        TramiteModel result = tramiteService.update(tramiteModel, ruc, user, tramiteUpdateRequestDto.getOperacion());

        ApiResponseTramiteResponseDto apiResponse = new ApiResponseTramiteResponseDto();

        TramiteResponseDto response = tramiteMapper.modelToDto(result);
        apiResponse.setData(List.of(response));
        apiResponse.setMeta(buildSuccessMeta(apiResponse.getData().size()));
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Actualiza todos los trámites asociados a una escala desde el contexto de
     * ficha sanitaria.
     *
     * @param escalaId                ID de la escala asociada.
     * @param tramiteUpdateRequestDto Información para actualizar los trámites.
     * @return Respuesta con los trámites actualizados.
     */
    @Loggable
    @PutMapping("/{escalaId}")
    public ResponseEntity<ApiResponseTramiteResponseDto> updateTramiteFromFichaSanitaria(
            @PathVariable Integer escalaId, @Valid @RequestBody TramiteUpdateRequestDto tramiteUpdateRequestDto) {

        String user = request.getHeader("user");

        List<TramiteModel> tramiteModelFound = tramiteService.search(escalaId);
        if (tramiteModelFound == null) {
            return ResponseEntity.notFound().build();
        }

        List<TramiteModel> tramitesToUpdate = tramiteModelFound.stream()
                .map(tramite -> {
                    tramiteUpdateRequestDto.setTramiteId(tramite.getTramiteId());
                    return tramiteMapper.dtoToModelUpdate(tramiteUpdateRequestDto, separador(user, 1));
                })
                .toList();

        List<TramiteModel> updatedTramites = tramiteService.update(tramitesToUpdate,
                tramiteUpdateRequestDto.getRucUsuario(), user, tramiteUpdateRequestDto.getOperacion());

        List<TramiteResponseDto> response = tramiteMapper.modelListTramiteToDtoList(updatedTramites);
        return ResponseEntity.ok(buildTramiteResponse(response));
    }

    /**
     * Desiste uno o varios trámites asociados a un ID de escala.
     *
     * @param tramiteDesistRequestDto Datos del trámite a desistir.
     * @return Respuesta con los trámites desistidos.
     */
    @Loggable
    @PostMapping("/desistir")
    public ResponseEntity<Object> desistirTramites(
            @Valid @RequestBody TramiteDesistRequestDto tramiteDesistRequestDto) {
        String user = request.getHeader("user");

        try {
            List<TramiteModel> result = tramiteService.desist(tramiteDesistRequestDto.getEscalaId(),
                    tramiteDesistRequestDto.getTramiteId(), user);

            // Éxito - construir respuesta normal
            List<TramiteDesistResponseDto> response = tramiteMapper.modelListToDtoList(result);
            return ResponseEntity.ok(buildTramiteDesistResponse(response));

        } catch (OrdenPagoAnulacionException e) {
            // Verificar que hay errores
            if (e.getErrores() == null || e.getErrores().isEmpty()) {
                // Si no hay errores específicos, devolver error genérico
                Map<String, Object> errorResponse = new HashMap<>();
                Map<String, Object> meta = new HashMap<>();
                meta.put("result", "ERROR");
                meta.put("mensaje", "Error desconocido al anular órdenes de pago");
                errorResponse.put("meta", meta);
                errorResponse.put("data", List.of());

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            }

            // Obtener el primer error
            OrdenPagoErrorResponse primerError = e.getErrores().get(0);

            try {
                // Parsear el JSON del body para devolverlo como objeto
                ObjectMapper mapper = new ObjectMapper();
                Object errorBody = mapper.readValue(primerError.getBody(), Object.class);

                // Devolver con el mismo status code y body exacto
                return ResponseEntity.status(primerError.getStatusCode()).body(errorBody);

            } catch (Exception parseException) {
                // Si hay error parsing, devolver el body como string
                return ResponseEntity.status(primerError.getStatusCode())
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(primerError.getBody());
            }
        } catch (Exception e) {
            // Manejar cualquier otra excepción
            Map<String, Object> errorResponse = new HashMap<>();
            Map<String, Object> meta = new HashMap<>();
            meta.put("result", "ERROR");
            meta.put("mensaje", "Error interno: " + e.getMessage());
            errorResponse.put("meta", meta);
            errorResponse.put("data", List.of());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // ===================== Helper methods to reduce duplicated response code
    // =====================
    private ApiResponseMeta buildSuccessMeta(int size) {
        ApiResponseMeta meta = new ApiResponseMeta();
        meta.setResult(MetaResults.SUCCESS.getValue());
        meta.setCantidadRegistros(size);
        meta.setCantidadRegistrosTotal(size);
        meta.setAtributos(Map.of());
        return meta;
    }

    private ApiResponseTramiteResponseDto buildTramiteResponse(List<TramiteResponseDto> data) {
        ApiResponseTramiteResponseDto api = new ApiResponseTramiteResponseDto();
        api.setData(data);
        api.setMeta(buildSuccessMeta(data.size()));
        return api;
    }

    private ApiResponseTramiteDesistResponseDto buildTramiteDesistResponse(List<TramiteDesistResponseDto> data) {
        ApiResponseTramiteDesistResponseDto api = new ApiResponseTramiteDesistResponseDto();
        api.setData(data);
        api.setMeta(buildSuccessMeta(data.size()));
        return api;
    }
}
