package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.DocumentoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.TramiteModel;

@AllArgsConstructor
@Getter
@Setter
public class DeclaracionJuradaResponseDto {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("estadoDdjjPago")
    private String estadoDdjjPago;
    @JsonProperty("numeroDdjj")
    private String numeroDdjj;
    @JsonProperty("fechaSolicitudDdjj")
    private LocalDateTime fechaSolicitudDdjj;
    @JsonProperty("documento")
    private DocumentoModel documento;
    @JsonProperty("escalaId")
    private Integer escalaId;
    @JsonProperty("motivoDeclaracion")
    private String motivoDeclaracion;
    @JsonProperty("mensajeError")
    private String mensajeError;
    @JsonProperty("tramite")
    private TramiteModel tramite;
    @JsonProperty("estado")
    private String estado;
    @JsonProperty("rucAgente")
    private String rucAgente;
}
