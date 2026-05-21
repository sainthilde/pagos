package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO que representa una solicitud de seguimiento de tramite,
 * 
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 19/08/2024
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TramiteUpdateRequestDto {
    @NotNull(message = "no puede ser nulo.")
    private Integer tramiteId;

    @JsonProperty("sustentoActNumTramiteManual")
    private String sustentoActNumTramiteManual;

    @Size(min = 1, max = 20, message = "debe tener entre 1 y 20 un dígitos.")
    private String numeroTramiteEntidad;

    @Size(min = 11, max = 11, message = "debe tener 11 dígitos.")
    private String rucUsuario;

    @NotNull(message = "no puede ser nulo.")
    private String operacion;

}
