package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Modelo de datos para representar una declaración jurada en el sistema.
 *
 * @project cp-api-bs-tramiteyrectificacion-query
 * @autor Luis Francisco Huertas Mostacero
 * @date 24/08/2024
 */
@Getter
@Setter
@NoArgsConstructor
public class DeclaracionJuradaModel {

    /**
     * Identificador único de la declaración jurada.
     */
    private Integer id;

    /**
     * Número de la declaración jurada.
     */
    private String numeroDeclaracionJurada;

    /**
     * Estado actual de la declaración jurada.
     */
    private String estadoDeclaracionJurada;

    /**
     * RUC del agente relacionado con la declaración jurada.
     */
    private String rucAgente;

    /**
     * Motivo asociado a la declaración jurada.
     */
    private String motivo;

    /**
     * Detalle de cualquier error relacionado con la declaración jurada.
     */
    private String error;

    /**
     * Identificador del documento relacionado con la declaración jurada.
     */
    private Integer documentoId;

    /**
     * Nombre del documento relacionado con la declaración jurada.
     */
    private String documentoNombre;

    /**
     * Fecha y hora en que se realizó la declaración jurada.
     */
    private LocalDateTime fechaDeclaracionJurada;
    private LocalDateTime fechaDenegacionDdjj;
    private LocalDateTime fechaAprobacionDdjj;

    private Integer entidadId;
}
