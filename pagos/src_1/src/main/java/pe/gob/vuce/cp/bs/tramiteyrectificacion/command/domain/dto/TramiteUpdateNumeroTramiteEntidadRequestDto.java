package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * DTO para actualizar el numero de tramite de la entidad, tupa e indicador de asignacion manual.
 */
@Getter
@Setter
@NoArgsConstructor
public class TramiteUpdateNumeroTramiteEntidadRequestDto {

    @NotNull(message = "tramiteId no puede ser nulo")
    @JsonProperty("tramiteId")
    private Integer tramiteId;

    @NotNull(message = "escalaId no puede ser nulo")
    @JsonProperty("escalaId")
    private Integer escalaId;

    @Size(min = 1, max = 20, message = "numeroTramiteEntidad debe tener entre 1 y 20 caracteres")
    @JsonProperty("numeroTramiteEntidad")
    private String numeroTramiteEntidad;

    @Size(min = 1, max = 100, message = "tupa debe tener entre 1 y 100 caracteres")
    @JsonProperty("tupa")
    private String tupa;

    @JsonProperty("indAsTramiteManual")
    private Boolean indAsTramiteManual;
}
