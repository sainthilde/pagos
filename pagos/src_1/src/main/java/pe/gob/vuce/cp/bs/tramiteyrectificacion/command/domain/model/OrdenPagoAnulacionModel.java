package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrdenPagoAnulacionModel {

    @JsonProperty("ordenPagoId")
    private Integer ordenPagoId;

    @JsonProperty("codigoOrdenPago")
    private String codigoOrdenPago;

    @JsonProperty("monto")
    private Double monto;

    @JsonProperty("fechaGeneracion")
    private String fechaGeneracion;

    @JsonProperty("cpb")
    private String cpb;

    @JsonProperty("estado")
    private String estado;

    @JsonProperty("fecha")
    private String fecha;

    @JsonProperty("tipoDocumentoUsuario")
    private String tipoDocumentoUsuario;

    @JsonProperty("numeroDocumentoUsuario")
    private String numeroDocumentoUsuario;

    @JsonProperty("tipOper")
    private String tipOper;

    @JsonProperty("rucOper")
    private String rucOper;
}
