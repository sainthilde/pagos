package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
/**
 * La clase ProcedimientosResponse representa la respuesta que contiene una lista
 * de procedimientos, proporcionando información detallada sobre cada procedimiento.
 * Utiliza las anotaciones de Lombok para simplificar la generación de métodos de
 * acceso y modificación, y {@code @JsonProperty} para mapear los nombres de los
 * atributos a los campos JSON correspondientes.
 *
 * <p>Anotaciones:
 * <ul>
 *   <li>{@code @Getter} y {@code @Setter}: Generan automáticamente los métodos de
 *       acceso (getters) y modificación (setters) para cada atributo en la clase
 *       y en la clase interna.</li>
 *   <li>{@code @JsonProperty}: Especifica el nombre exacto del campo en el JSON que
 *       se asignará a cada atributo, permitiendo el mapeo de nombres de atributos
 *       en el código a nombres de campos JSON que puedan diferir.</li>
 * </ul>
 *
 * <p>Atributos:
 * <ul>
 *   <li>{@code procedimientos}: Una lista de objetos {@code Procedimiento} que
 *       representa los procedimientos incluidos en la respuesta.</li>
 * </ul>
 *
 * <p>Clase interna {@code Procedimiento}:
 * <ul>
 *   <li>Contiene los detalles de cada procedimiento, con varios atributos para
 *       almacenar información específica de cada uno.</li>
 *   <li>{@code procedimientoId}: Identificador único del procedimiento.</li>
 *   <li>{@code procedimientoVersion}: Versión del procedimiento.</li>
 *   <li>{@code entidadId}: Identificador de la entidad asociada al procedimiento.</li>
 *   <li>{@code siglas}: Siglas del procedimiento.</li>
 *   <li>{@code tupa}: Código TUPA asociado al procedimiento.</li>
 *   <li>{@code formato}: Formato del procedimiento.</li>
 *   <li>{@code cut} y {@code nombreCut}: Código y nombre del CUT (Código Único de Trámite)
 *       del procedimiento.</li>
 *   <li>{@code componente}: Componente asociado al procedimiento.</li>
 *   <li>{@code ayuda}: Información de ayuda asociada al procedimiento.</li>
 *   <li>{@code pago}: Información sobre el pago asociado al procedimiento.</li>
 *   <li>{@code plazo}: Plazo establecido para el procedimiento.</li>
 *   <li>{@code descripcionCalificacion}: Descripción de la calificación del procedimiento.</li>
 * </ul>
 * @author Mateo Huancho
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Getter
@Setter
public class ProcedimientosResponse {

    @JsonProperty("procedimientos")
    private List<Procedimiento> procedimientos;

    /**
     * La clase interna Procedimiento representa los detalles de cada procedimiento,
     * proporcionando atributos específicos para identificar y describir el
     * procedimiento en la respuesta.
     */
    @Getter
    @Setter
    public static class Procedimiento {

        @JsonProperty("procedimientoId")
        public Integer procedimientoId;

        @JsonProperty("procedimientoVersion")
        private Integer procedimientoVersion;

        @JsonProperty("entidadId")
        private Integer entidadId;

        @JsonProperty("siglas")
        private String siglas;

        @JsonProperty("tupa")
        private String tupa;

        @JsonProperty("formato")
        private String formato;

        @JsonProperty("cut")
        private Integer cut;

        @JsonProperty("nombreCut")
        private String nombreCut;

        @JsonProperty("componente")
        private String componente;

        @JsonProperty("ayuda")
        private String ayuda;

        @JsonProperty("pago")
        private String pago;

        @JsonProperty("plazo")
        private String plazo;

        @JsonProperty("descripcionCalificacion")
        private String descripcionCalificacion;

    }
}

