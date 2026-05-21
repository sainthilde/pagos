package pe.gob.vuce.cp.sp.pagos.domain.port.in;

import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.SeguimientoRequestDto;


/**
 * Caso de uso funcional para la creación de seguimientos de fichas sanitarias.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 22/08/2024
 */
@Component
public interface CreateSeguimientoUseCase {

    /**
     * Crea un seguimiento de tramite en el sistema.
     *
     * @param seguimientoRequestDto DTO que contiene los datos del seguimiento.
     * @param user El usuario que realiza la operación.
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Fernando Tanta
     * @date 22/08/2024
     */
     void create(SeguimientoRequestDto seguimientoRequestDto, String user);
}
