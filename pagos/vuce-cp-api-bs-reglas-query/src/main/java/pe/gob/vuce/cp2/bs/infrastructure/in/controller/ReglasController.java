package pe.gob.vuce.cp2.bs.infrastructure.in.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import pe.gob.vuce.cp2.bs.application.service.ReglasService;
import pe.gob.vuce.cp2.bs.infrastructure.mapper.ReglasMapper;
import pe.gob.vuce.cp2.bs.puertosnacionales.query.contract.api.ReglasApi;
import pe.gob.vuce.cp2.bs.puertosnacionales.query.contract.model.ReglasPagoResponseDto;

@AllArgsConstructor
@RestController
public class ReglasController implements ReglasApi {

    private final ReglasService service;
    private final ReglasMapper mapper;
    @Override
    public ResponseEntity<ReglasPagoResponseDto> listReglasPagos(
            @NotNull @Valid Integer escalaId,
            @NotNull @Valid String movimientoNave, 
            @NotNull @Valid Integer documentoId,
            @NotNull @Valid Integer actividadId) {
        
        return ResponseEntity.ok(
            mapper.modelToResponse(
                service.validarReglaPagos(mapper.parametersToModel(
                    escalaId, movimientoNave, documentoId, actividadId
                    )
                )
            )
        );
    }


    
    
}
