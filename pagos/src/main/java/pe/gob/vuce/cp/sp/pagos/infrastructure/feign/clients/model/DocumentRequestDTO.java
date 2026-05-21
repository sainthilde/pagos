package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
/**
 * La clase DocumentRequestDTO es un objeto de transferencia de datos (DTO)
 * que representa una solicitud de documento. Contiene información básica del
 * documento, incluyendo su nombre, contenido en archivo y datos adicionales.
 *
 * <p>Anotaciones:
 * <ul>
 *   <li>{@code @Getter}: Genera automáticamente los métodos de acceso (getters)
 *       para cada atributo.</li>
 *   <li>{@code @Setter}: Genera automáticamente los métodos de modificación (setters)
 *       para cada atributo.</li>
 * </ul>
 *
 * <p>Atributos:
 * <ul>
 *   <li>{@code nombre}: Nombre del documento, de tipo {@code String}.</li>
 *   <li>{@code file}: Contenido del archivo del documento, generalmente en formato
 *       codificado, de tipo {@code String}.</li>
 *   <li>{@code data}: Mapa de datos adicionales que contiene pares clave-valor, de
 *       tipo {@code Map<String, Object>}, para información adicional que puede ser
 *       incluida en la solicitud.</li>
 * </ul>
 * @author Mateo Huancho
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Getter
@Setter
public class DocumentRequestDTO {
    private String nombre;
    private String file;
    private Map<String,Object> data;
}
