package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * La clase ExcepcionMensajeResponse representa una respuesta de mensaje de excepción,
 * que contiene un mensaje descriptivo y un indicador booleano de validación.
 * Utiliza anotaciones de Lombok para simplificar la generación de métodos de acceso,
 * modificación, constructores y representación en forma de cadena.
 *
 * <p>Anotaciones:
 * <ul>
 *   <li>{@code @Getter} y {@code @Setter}: Generan automáticamente los métodos de
 *       acceso (getters) y modificación (setters) para cada atributo en la clase.</li>
 *   <li>{@code @NoArgsConstructor} y {@code @AllArgsConstructor}: Generan un
 *       constructor sin argumentos y otro con todos los argumentos, respectivamente,
 *       facilitando la creación de instancias de la clase.</li>
 *   <li>{@code @ToString}: Genera automáticamente un método {@code toString()} que
 *       devuelve una representación en forma de cadena de los atributos de la clase.</li>
 * </ul>
 *
 * <p>Atributos:
 * <ul>
 *   <li>{@code mensaje}: Una cadena de texto que contiene el mensaje de excepción
 *       o error.</li>
 *   <li>{@code validator}: Un valor booleano que indica si la excepción o mensaje
 *       es válido o cumple ciertas condiciones de validación.</li>
 * </ul>
 * @author Mateo Huancho
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExcepcionMensajeResponse {
    private String mensaje;
    private Boolean validator;
}
