package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
/**
 * La clase TasaResponse representa la respuesta que contiene una lista de tasas
 * aplicables a procedimientos específicos. Cada tasa está definida en la clase
 * interna {@code Tasa}, que contiene detalles sobre el procedimiento, monto y
 * características de la moneda. Utiliza anotaciones de Lombok para simplificar la
 * generación de métodos de acceso y modificación, y {@code @JsonProperty} para mapear
 * los nombres de los atributos a los campos JSON correspondientes.
 *
 * <p>Anotaciones:
 * <ul>
 *   <li>{@code @Getter} y {@code @Setter}: Generan automáticamente los métodos de
 *       acceso (getters) y modificación (setters) para cada atributo en la clase
 *       y en la clase interna.</li>
 *   <li>{@code @JsonProperty}: Especifica el nombre exacto del campo en el JSON
 *       que se asignará a cada atributo, permitiendo el mapeo de nombres de atributos
 *       en el código a nombres de campos JSON que puedan diferir.</li>
 * </ul>
 *
 * <p>Atributos:
 * <ul>
 *   <li>{@code tasas}: Una lista de objetos {@code Tasa} que representa las tasas
 *       aplicables incluidas en la respuesta.</li>
 * </ul>
 *
 * <p>Clase interna {@code Tasa}:
 * <ul>
 *   <li>Contiene los detalles de cada tasa aplicable, con atributos específicos para
 *       identificar el procedimiento, monto, y detalles de la moneda utilizada.</li>
 *   <li>{@code procedimientoId}: Identificador único del procedimiento asociado a la tasa.</li>
 *   <li>{@code procedimientoVersion}: Versión del procedimiento.</li>
 *   <li>{@code procedimientoTasaVersion}: Versión específica de la tasa para el procedimiento.</li>
 *   <li>{@code secuencia}: Número de secuencia de la tasa.</li>
 *   <li>{@code monto}: Monto específico de la tasa.</li>
 *   <li>{@code etiqueta}: Etiqueta descriptiva de la tasa.</li>
 *   <li>{@code descripcion}: Descripción de la tasa.</li>
 *   <li>{@code codigoMoneda}: Código de la moneda en la que se expresa la tasa.</li>
 *   <li>{@code monedaDescripcion}: Descripción de la moneda.</li>
 *   <li>{@code monedaSigno}: Símbolo de la moneda (por ejemplo, "$" para dólares).</li>
 * </ul>
 * @author Mateo Huancho
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Getter
@Setter
public class TasaResponse {

    @JsonProperty("tasas")
    private List<Tasa> tasas;
    /**
     * La clase interna Tasa representa una tasa específica aplicable a un procedimiento,
     * con detalles sobre el procedimiento, monto, y características de la moneda.
     */
    @Getter
    @Setter
    public static class Tasa {

        @JsonProperty("procedimientoId")
        private Integer procedimientoId;

        @JsonProperty("procedimientoVersion")
        private Integer procedimientoVersion;

        @JsonProperty("procedimientoTasaVersion")
        private Integer procedimientoTasaVersion;

        @JsonProperty("secuencia")
        private Integer secuencia;

        @JsonProperty("monto")
        private Double monto;

        @JsonProperty("etiqueta")
        private String etiqueta;

        @JsonProperty("descripcion")
        private String descripcion;

        @JsonProperty("codigoMoneda")
        private String codigoMoneda;

        @JsonProperty("monedaDescripcion")
        private String monedaDescripcion;

        @JsonProperty("monedaSigno")
        private String monedaSigno;

        @JsonProperty("tupa")
        private String tupa;

    }
}

