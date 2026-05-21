package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models;

import java.util.List;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Modelo de datos para representar un trámite en el sistema.
 * 
 * @project cp-api-bs-tramiteyrectificacion-query
 * @autor Luis Francisco Huertas Mostacero
 * @date 24/08/2024
 */
@Getter
@Setter
@NoArgsConstructor
public class TramiteModel {

    /**
     * Identificador único del trámite.
     */
    private Integer id;

    /**
     * DUE (Documento Único de Existencia) asociado al trámite.
     */
    private String due;

    /**
     * Nombre de la nave o establecimiento relacionado con el trámite.
     */
    private String nombreNave;

    /**
     * Número SUCE (Sistema Único de Control de Expedientes) del trámite.
     */
    private String numeroSuce;

    /**
     * Número único del trámite.
     */
    private String numeroTramite;

    /**
     * Identificador de la entidad asociada al trámite.
     */
    private Integer entidadId;

    /**
     * Nombre de la entidad asociada al trámite.
     */
    private String entidadNombre;

    /**
     * RUC (Registro Único de Contribuyentes) de la entidad asociada al trámite.
     */
    private String entidadRuc;

    /**
     * TUPA (Términos de Referencia Únicos de Procedimientos Administrativos) del
     * trámite.
     */
    private String tupa;

    /**
     * Estado actual del trámite.
     */
    private String estadoTramite;

    /**
     * Monto asociado al trámite.
     */
    private Double monto;

    /**
     * Fecha del trámite.
     */
    private LocalDateTime fechaTramite;

    /**
     * Estado de pago del trámite.
     */
    private String estadoDePago;

    /**
     * Identificador de la agencia relacionada con el trámite.
     */
    private Integer agenciaId;

    /**
     * Nombre de la agencia relacionada con el trámite.
     */
    private String agenciaNombre;

    /**
     * RUC (Registro Único de Contribuyentes) de la agencia relacionada con el
     * trámite.
     */
    private String agenciaRuc;

    /**
     * Fecha y hora de la declaración jurada actual asociada al trámite.
     */
    private LocalDateTime fechaDeclaracionJuradaActual;

    /**
     * Descripción detallada del trámite.
     */
    private String descripcion;

    /**
     * Código CPB (Código de Procedimiento Básico) asociado al trámite.
     */
    private String cpb;

    /**
     * Indica si el trámite fue realizado de manera manual.
     */
    private Boolean fueTramiteManual;

    /**
     * Listado de declaraciones juradas asociadas al trámite.
     */
    private List<DeclaracionJuradaModel> declaracionesJuradas;

    /**
     * Indicador si el tramite no requiere pago
     */
    private Boolean indNoRequierePago;

    /**
     * Regla de exencion de pago asociado al trámite.
     */
    private String reglaPagoExencionAplicada;

    /**
     * Descripcion de procedimiento de la orden de pago asociada.
     */
    private String descripcionTramite;

    private DocumentoModel documento;
}
