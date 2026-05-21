package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Modelo que representa un trámite en la aplicación.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 19/08/2024
 */
@Getter
@Setter
@NoArgsConstructor
public class TramiteModel extends BaseModel {

    /**
     * Identificador único del trámite.
     */
    @JsonProperty("tramiteId")
    private Integer tramiteId;

    /**
     * Numero correlativo creado del número de suce (número de trámite).
     */
    @JsonProperty("numeroSuce")
    private String numeroSuce;

    /**
     * Fecha de registro del trámite
     */
    @JsonProperty("fechaTramite")
    private LocalDateTime fechaTramite;

    /**
     * Identificador de la escala asociada.
     */
    @JsonProperty("escalaId")
    private Integer escalaId;

    /**
     * Identificador del documento.
     */
    @JsonProperty("documentoId")
    private Integer documentoId;

    /**
     * Identificador de la entidad que atiende el trámite.
     */
    @JsonProperty("actividadEntidadPuertoId")
    private Integer actividadEntidadPuertoId;

    /**
     * Indicador E/S.
     */
    @JsonProperty("indicadorEs")
    private String indicadorEs;

    /**
     * Numero del trámite en la entidad que atiende el documento.
     */
    @JsonProperty("numeroTramiteEntidad")
    private String numeroTramiteEntidad;

    /**
     * Ruc del agente
     */
    @JsonProperty("rucAgente")
    private String rucAgente;

    /**
     * Estado del trámite
     */
    @JsonProperty("estadoTramite")
    private String estadoTramite;

    /**
     * Indicador si el tramite requiere pago (registro en tablas DJ o Orden Pago)
     */
    @JsonProperty("indNoRequierePago")
    private Boolean indNoRequierePago;

    /**
     * Tipo de tramite (S/D) para saber si se debe grabar el id de tramite en pagos
     * o DJ
     */
    @JsonProperty("tipoTramite")
    private String tipoTramite;

    /**
     * Indicador si el expediente del tramite fue registrado manualmente o no
     */
    @JsonProperty("indAsignacionTramiteManual")
    private Boolean indAsignacionTramiteManual;

    /**
     * Fecha de registro asginacion manual del tramite
     */
    @JsonProperty("fechaActNumTramiteManual")
    private LocalDateTime fechaActNumTramiteManual;

    /**
     * GUID del documento de sustento de asignacion manual del expediente en el
     * sistema FileNet.
     */
    @JsonProperty("sustentoActNumTramiteManual")
    private String sustentoActNumTramiteManual;

    /**
     * Tupa a registrar
     */
    @JsonProperty("tupa")
    private String tupa;

    /**
     * campo para realizar busquedas
     */
    @JsonProperty("reglaDeNegocioTextsearch")
    private String reglaDeNegocioTextsearch;

    /**
     * Ids Ordenes de pago con estado pagado
     */
    @JsonProperty("ppCpbPayments")
    private List<String> ppCpbPayments;

    /**
     * Regla de exencion de pago aplicada
     */
    @JsonProperty("reglaPagoExencionAplicada")
    private String reglaPagoExencionAplicada;

    /**
     * Descripcion de tramite
     */
    @JsonProperty("descripcionTramite")
    private String descripcionTramite;

    private List<Object> respuestasAnulacion;
}
