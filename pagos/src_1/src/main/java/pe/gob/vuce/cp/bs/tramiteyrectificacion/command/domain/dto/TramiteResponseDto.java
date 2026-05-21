package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa una solicitud de seguimiento de tramite,
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 19/08/2024
 */
@Getter
@Setter
@AllArgsConstructor
public class TramiteResponseDto {

    @JsonProperty("idSuce")
    Integer idSuce;
    @JsonProperty("numeroSuce")
    String numeroSuce;
}
