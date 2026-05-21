package pe.gob.vuce.cp.sp.pagos.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EscalaModel {
    private Integer escalaId;
    private Integer estadoDueId;
    private FichaTecnicaDetModel fichaTecnicaDetIn;
    private String puertoEscalaId;
    private Integer anoEscala;
    private Integer numeroEscala;
}
