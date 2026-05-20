package pe.gob.vuce.cp2.bs.domain.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ParametrosModel {
    private Integer escalaId;
    private String movimientoNave;
    private Integer documentoId;
    private Integer actividadId;
}
