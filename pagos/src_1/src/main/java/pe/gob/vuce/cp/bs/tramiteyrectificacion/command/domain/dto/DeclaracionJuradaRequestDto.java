package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DeclaracionJuradaRequestDto {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("estadoDdjjPago")
    private String estadoDdjjPago;
    @JsonProperty("numeroDdjj")
    private String numeroDdjj;
    @JsonProperty("fechaSolicitudDdjj")
    private LocalDateTime fechaSolicitudDdjj;
    @JsonProperty("documento")
    private DocumentoDDJJRequestDto documento;
    @JsonProperty("tramiteId")
    private Integer tramiteId;
    @JsonProperty("escalaId")
    private Integer escalaId;
    @JsonProperty("motivoDeclaracion")
    private String motivoDeclaracion;
    @JsonProperty("mensajeError")
    private String mensajeError;
    @JsonProperty("estado")
    private String estado;
    @JsonProperty("rucAgente")
    private String rucAgente;
    private String usuario;
    private Integer activityId;
    private String codPuerto;
}
