package pe.gob.vuce.cp.sp.pagos.infrastructure.api.controller;

import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import pe.gob.vuce.cp.framework.globallogger.annotation.Loggable;
import pe.gob.vuce.cp.sp.pagos.application.service.ActividadEntidadService;
import pe.gob.vuce.cp.sp.pagos.domain.constants.ResponseUtil;
import pe.gob.vuce.cp.sp.pagos.domain.model.ActividadEntidad;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.ActividadEntidadResponseDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.GenericResponseDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.mapper.ActividadEntidadMapper;

import java.util.List;
import java.util.Optional;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.MESSAGE;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.MESSAGE_RESP;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.STATUS;

/**
 * El controlador EntidadController expone los endpoints REST para gestionar y
 * consultar información de entidades en el contexto de actividades. Utiliza
 * servicios y mappers inyectados para acceder a los datos de actividades y
 * convertirlos en respuestas adecuadas para la API.
 *
 * <p>
 * Anotaciones:
 * <ul>
 * <li>{@code @RestController}: Marca esta clase como un controlador REST de
 * Spring,
 * lo que permite manejar solicitudes HTTP y devolver respuestas JSON.</li>
 * <li>{@code @RequestMapping("/")}: Define la ruta base para todos los
 * endpoints
 * dentro de este controlador.</li>
 * </ul>
 *
 * <p>
 * Dependencias:
 * <ul>
 * <li>{@code actividadEntidadService}: Servicio que gestiona las operaciones
 * de datos relacionadas con la entidad {@code ActividadEntidad}.</li>
 * <li>{@code actividadEntidadmapper}: Mapper que convierte objetos de tipo
 * {@code ActividadEntidad} en DTOs para su exposición en la API.</li>
 * </ul>
 *
 * <p>
 * Método principal:
 * <ul>
 * <li>{@code getCodReglaNegocio}: Método que busca una actividad de entidad
 * específica usando los identificadores de entidad, actividad y código de
 * puerto nacional. Devuelve un {@code ResponseEntity} con los datos de la
 * actividad
 * encontrada en formato {@code ActividadEntidadResponseDto} o un mensaje de
 * error
 * personalizado en caso de no encontrarse la información.</li>
 * </ul>
 * 
 * @author Mateo Huancho
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@RestController
@RequestMapping("/")
@AllArgsConstructor
public class EntidadController {
    private final ActividadEntidadService actividadEntidadService;
    private final ActividadEntidadMapper actividadEntidadmapper;

    /**
     * Método responsable de buscar una ActividadEntidad utilizando los
     * identificadores
     * de entidad, actividad y código de puerto nacional.
     *
     * @param entidadId         ID de la entidad (requerido).
     * @param actividadId       ID de la actividad (requerido).
     * @param codPuertoNacional Código del puerto nacional (requerido).
     * @return un ResponseEntity que puede contener la ActividadEntidadResponseDto
     *         si se encuentra,
     *         o un mensaje de error si no existe.
     */
    @Loggable
    @GetMapping("/ordenes-pago/regla-negocio")
    public ResponseEntity<Object> getCodReglaNegocio(
            @RequestParam Integer entidadId,
            @RequestParam Integer actividadId,
            @RequestParam String codPuertoNacional) {
        try {
            Optional<ActividadEntidad> actividadEntidadOpt = actividadEntidadService
                    .findByEntidadIdAndDocumentoIdAndPuertoDue(entidadId, actividadId, codPuertoNacional);

            if (actividadEntidadOpt.isPresent()) {
                ActividadEntidadResponseDto responseDto = actividadEntidadmapper
                        .actividadEntidadToResponseDto(actividadEntidadOpt.get());
                GenericResponseDto<Object> response = new GenericResponseDto<>();
                response.setMeta(ResponseUtil.createResponseMetaDataDto());
                response.setData(Collections.singletonList(responseDto));
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                // Devuelve un mensaje de error personalizado en caso de no encontrar la
                // información
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put(MESSAGE, MESSAGE_RESP);
                errorResponse.put(STATUS, HttpStatus.INTERNAL_SERVER_ERROR.toString());
                GenericResponseDto<Object> response = ResponseUtil.createApiResponseVuceCP2Exception(
                        String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                        MESSAGE_RESP, List.of());
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (FeignException e) {
            GenericResponseDto<Object> response = ResponseUtil.createApiResponseVuceCP2Exception(
                    String.valueOf(e.status()),
                    e.getLocalizedMessage(),
                    List.of(e.getMessage()));
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(e.status()));
        }
    }
}
