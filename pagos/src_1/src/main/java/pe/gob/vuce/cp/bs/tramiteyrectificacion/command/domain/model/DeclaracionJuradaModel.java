package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Modelo que representa una declaración jurada en la aplicación.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 19/08/2024
 */
@Getter
@Setter
@NoArgsConstructor
public class DeclaracionJuradaModel extends BaseModel {
    private Integer declaracionJuradaId;

    private String estadoDdjjPago;

    private String numeroDdjj;

    private LocalDateTime fechaSolicitudDdjj;

    private DocumentoModel documento;

    private Integer escalaId;

    private String motivoDeclaracion;

    private String mensajeError;

    private TramiteModel tramite;

    private String rucAgente;

    private LocalDateTime fechaDenegacionDdjj;

    private LocalDateTime fechaAprobacionDdjj;

    private Integer entidadId;

}
