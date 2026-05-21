package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ActividadEntidadPuertoModel {
    private Integer id;

    private Integer entidadId;

    private Integer actividadId;

    private String codPuertoNacional;
}
