package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
/**
 * La clase Data representa una entidad de datos genéricos que contiene información
 * multilingüe y un mapa para otras columnas adicionales. Utiliza anotaciones de
 * Lombok para simplificar la creación de métodos de acceso, modificación y
 * constructores.
 *
 * <p>Anotaciones:
 * <ul>
 *   <li>{@code @Getter}: Genera automáticamente los métodos de acceso (getters)
 *       para cada atributo.</li>
 *   <li>{@code @Setter}: Genera automáticamente los métodos de modificación (setters)
 *       para cada atributo.</li>
 *   <li>{@code @NoArgsConstructor}: Genera un constructor sin argumentos,
 *       necesario para algunos frameworks de serialización y deserialización de objetos.</li>
 *   <li>{@code @AllArgsConstructor}: Genera un constructor con todos los atributos de la clase.</li>
 * </ul>
 *
 * <p>Atributos:
 * <ul>
 *   <li>{@code id}: Identificador único para el objeto de tipo {@code Integer}.</li>
 *   <li>{@code descriptionEs}: Descripción en español de tipo {@code String}.</li>
 *   <li>{@code descriptionEn}: Descripción en inglés de tipo {@code String}.</li>
 *   <li>{@code othersColumns}: Mapa de columnas adicionales que almacena pares
 *       clave-valor de tipo {@code String}, permitiendo la inclusión de información
 *       adicional que no está directamente representada por los otros atributos.</li>
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
public class Data {
    private Integer id;
    private String descriptionEs;
    private String descriptionEn;
    private Map<String, String> othersColumns;
}