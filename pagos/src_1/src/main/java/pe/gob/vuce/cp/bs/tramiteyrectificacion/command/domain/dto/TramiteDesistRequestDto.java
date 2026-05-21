package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
public class TramiteDesistRequestDto {

    @NotNull(message = "no puede ser nulo.")
    private Integer escalaId;

    @JsonProperty("tramiteId")
    private Integer tramiteId;
}
