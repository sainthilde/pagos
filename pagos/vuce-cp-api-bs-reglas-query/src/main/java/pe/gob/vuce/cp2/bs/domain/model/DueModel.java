package pe.gob.vuce.cp2.bs.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DueModel {

    private String codigoPuerto;
    private Integer entidadId;
    private String reglaNegocio;
    private boolean comprobanteRegistrado;
    private boolean esArrriboForzoso;
    private boolean esNavelPrincipal;
    private String codEntidadGp;
}
