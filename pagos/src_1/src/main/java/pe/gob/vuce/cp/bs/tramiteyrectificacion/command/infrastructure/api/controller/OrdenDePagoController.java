package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.application.service.OrdenPagoService;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.OrdenPagoUpdateRequestDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.CommonResponse;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.Meta;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.OrdenDePagoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.mapper.OrdenPagoMapper;

import pe.gob.vuce.cp.framework.globallogger.annotation.Loggable;

import static pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants.Constants.separador;

/**
 * Controlador REST responsable de exponer los endpoints relacionados
 * con la entidad Orden de Pago en el contexto de trámite y rectificación.
 *
 * Proporciona operaciones para actualizar órdenes de pago.
 *
 */
@RestController
@RequestMapping("/ordenpago")
@AllArgsConstructor
public class OrdenDePagoController {
    private final OrdenPagoService ordenPagoService;
    private final OrdenPagoMapper ordenPagoMapper;

    /**
     * Actualiza una orden de pago existente con la información proporcionada en el request.
     *
     * @param ordenPagoUpdateRequestDto DTO que contiene los datos actualizados de la orden de pago.
     * @param user                      Cabecera que representa el identificador del usuario que realiza la operación.
     * @return {@link ResponseEntity} con un {@link CommonResponse} que incluye la orden actualizada.
     */
    @Loggable
    @PutMapping
    ResponseEntity<CommonResponse> update(@Valid  @RequestBody OrdenPagoUpdateRequestDto ordenPagoUpdateRequestDto, @RequestHeader(value = "user") String user){
        OrdenDePagoModel ordenPagoModel = ordenPagoMapper.dtoToModel(ordenPagoUpdateRequestDto,separador(user,1));
        OrdenDePagoModel result = ordenPagoService.udpate(ordenPagoModel);
        OrdenPagoUpdateRequestDto response = ordenPagoMapper.modelToDto(result);
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setMeta(new Meta());
        commonResponse.setData(response);
        return ResponseEntity.ok(commonResponse);
    }
}
