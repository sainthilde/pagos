package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa una solicitud de seguimiento de tramite,
 * incluyendo los datos
 * necesarios para realizar el seguimiento del tramite.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 22/08/2024
 */
@Getter
@Setter
public class SeguimientoRequestDto {
    Integer tipoSegId;
    String rucUsuario;
    Boolean indNil;
    Integer escalaId;
    String acronimoDocumento;
    String indicadorEs;
    String comentario;
    String estado;
}
