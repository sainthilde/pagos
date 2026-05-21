package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TramiteCommandRequest {

    @JsonProperty("escalaId")
    private Integer escalaId;

    @JsonProperty("documentoId")
    private Integer documentoId;

    @JsonProperty("indicadorEs")
    private String indicadorEs;

    @JsonProperty("rucAgente")
    private String rucAgente;

    @JsonProperty("actividadEntidadPuertoId")
    private Integer actividadEntidadPuertoId;

    @JsonProperty("indNoRequierePago")
    private Boolean indNoRequierePago = true;

    @JsonProperty("tupa")
    private String tupa;

    @JsonProperty("reglaPagoExencionAplicada")
    private String reglaPagoExencionAplicada = "NO PAGA POR TUPA 0";

    @JsonProperty("descripcionTramite")
    private String descripcionTramite;
}
