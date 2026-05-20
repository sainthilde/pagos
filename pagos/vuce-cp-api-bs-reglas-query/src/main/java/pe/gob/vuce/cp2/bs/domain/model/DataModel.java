package pe.gob.vuce.cp2.bs.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DataModel {

    private Integer idFlujo;
    private String descripcion;
    private Boolean flujoActivo;
    private String mensaje;
    private ComprobanteModel comprobanteModel;
}
