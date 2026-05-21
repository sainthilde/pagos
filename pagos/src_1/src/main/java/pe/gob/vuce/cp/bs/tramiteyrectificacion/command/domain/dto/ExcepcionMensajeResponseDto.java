package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExcepcionMensajeResponseDto {
    private String mensaje;
    private Boolean validator;
}
