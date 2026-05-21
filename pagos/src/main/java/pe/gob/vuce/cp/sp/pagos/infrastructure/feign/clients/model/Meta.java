package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.Map;
/**
 * La clase Meta representa metadatos asociados con una respuesta, incluyendo
 * información sobre el resultado, mensajes, cantidad de registros, y otros
 * atributos adicionales. Esta clase proporciona contexto adicional para los
 * datos devueltos en una respuesta de la aplicación.
 *
 * <p>Anotaciones:
 * <ul>
 *   <li>{@code @Getter} y {@code @Setter}: Generan automáticamente los métodos de
 *       acceso (getters) y modificación (setters) para cada atributo en la clase.</li>
 *   <li>{@code @NoArgsConstructor} y {@code @AllArgsConstructor}: Generan un
 *       constructor sin argumentos y otro con todos los argumentos, respectivamente,
 *       para facilitar la creación de instancias de la clase.</li>
 * </ul>
 *
 * <p>Atributos:
 * <ul>
 *   <li>{@code result}: Un {@code String} que representa el resultado de la operación,
 *       indicando, por ejemplo, si fue exitosa o fallida.</li>
 *   <li>{@code mensajes}: Una lista de {@code String} que contiene mensajes
 *       relevantes para la operación, como advertencias o errores.</li>
 *   <li>{@code cantidadRegistros}: Un valor {@code Integer} que representa la cantidad
 *       de registros en la respuesta actual.</li>
 *   <li>{@code cantidadRegistrosTotal}: Un valor {@code Integer} que indica el total
 *       de registros disponibles, útil en respuestas paginadas.</li>
 *   <li>{@code atributos}: Un mapa {@code Map<String, String>} que contiene pares clave-valor
 *       adicionales, permitiendo incluir atributos opcionales o personalizados
 *       en la respuesta.</li>
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
public class Meta {
    private String result;
    private List<String> mensajes;
    private Integer cantidadRegistros;
    private Integer cantidadRegistrosTotal;
    private Map<String, String> atributos;
}