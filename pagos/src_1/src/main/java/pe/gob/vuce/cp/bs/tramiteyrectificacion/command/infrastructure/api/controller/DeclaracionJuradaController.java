package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.application.service.DeclaracionJuradaService;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.DeclaracionJuradaRequestDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.DeclaracionJuradaResponseDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.CommonResponse;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.DeclaracionJuradaModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.Meta;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.mapper.DeclaracionJuradaMapper;

import static pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants.Constants.separador;
import pe.gob.vuce.cp.framework.globallogger.annotation.Loggable;

@RestController
@RequestMapping("/declaracion-jurada")
@AllArgsConstructor
public class DeclaracionJuradaController {
    private final DeclaracionJuradaService declaracionJuradaService;
    private final DeclaracionJuradaMapper declaracionJuradaMapper;

    /**
     * Crea una nueva Declaración Jurada.
     *
     * Este endpoint recibe los datos de una declaración jurada, los transforma y los envía
     * al servicio correspondiente para su creación. Retorna la respuesta en un objeto
     * estándar que contiene metadatos y los datos generados.
     *
     * @param declaracionJurada Objeto con los datos requeridos para registrar la declaración jurada.
     * @param user Identificador del usuario que realiza la operación (cabecera HTTP).
     * @param ruc RUC del agente asociado a la declaración jurada (cabecera HTTP).
     * @return ResponseEntity con estructura estándar {@link CommonResponse} que incluye
     *         los datos creados {@link DeclaracionJuradaResponseDto}.
     */
    @Loggable
    @PostMapping
    ResponseEntity<CommonResponse> save(@RequestBody DeclaracionJuradaRequestDto declaracionJurada,
            @RequestHeader String user, @RequestHeader String ruc) {
        declaracionJurada.setUsuario(separador(user,1));
        declaracionJurada.setRucAgente(ruc);
        DeclaracionJuradaModel result = declaracionJuradaService.createDeclaracionJurada(declaracionJurada,user);
        DeclaracionJuradaResponseDto response = declaracionJuradaMapper.modelToDto(result);
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setMeta(new Meta());
        commonResponse.setData(response);
        return ResponseEntity.ok(commonResponse);
    }
}
