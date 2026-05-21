package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.application.usecase;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.SeguimientoRequestDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.CreateSeguimientoUseCase;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.FeignComunesCommandClientPort;

/**
 * Implementación del caso de uso para la creación de seguimientos de tramiteyrectificacion.
 * 
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 22/08/2024
 */
@AllArgsConstructor
@Component
public class CreateSeguimientoUseCaseImpl implements CreateSeguimientoUseCase {

    private final FeignComunesCommandClientPort feignComunesCommandClientPort;

    /**
     * Crea un seguimiento para una ficha sanitaria.
     * 
     * @param seguimientoRequestDto DTO que contiene los datos del seguimiento.
     * @param user El usuario que está realizando la operación.
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Fernando Tanta
     * @date 22/08/2024
     */
    @Transactional
    @Override
    public void create(SeguimientoRequestDto seguimientoRequestDto, String user) {
        feignComunesCommandClientPort.saveEscalaSeguimiento(seguimientoRequestDto, user);
    }
}