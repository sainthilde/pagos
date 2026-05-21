package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO que representa una solicitud de seguimiento de tramite,
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 19/08/2024
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TramiteCrearRequestDto {

    @NotNull(message = "no puede ser nulo.")
    private Integer escalaId;

    @NotNull(message = "no puede ser nulo.")
    private Integer documentoId;


    @NotNull(message = "no puede ser nulo.")
    @Size(min = 1, max = 1, message = "debe tener exactamente un dígito.")
    @Pattern(regexp = "[ES]", message = "solo puede ser 'E' o 'S'.")
    private String indicadorEs;

    @NotNull(message = "no puede ser nulo.")
    @Pattern(regexp = "^[0-9]{11}$", message = "debe tener exactamente 11 dígitos numéricos.")
    private String rucAgente;

    @NotNull(message = "no puede ser nulo.")
    private Integer actividadEntidadPuertoId;

    @NotNull(message = "no puede ser nulo.")
    private Boolean indNoRequierePago;

    @Size(max = 12, message = "debe tener como máximo doce caracteres.")
    private String tupa;

    private String reglaPagoExencionAplicada;

    private String descripcionTramite;
}
