package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model;

import lombok.Getter;
import lombok.Setter;

/**
 * La clase ArchivoResponse representa una respuesta con información de un archivo,
 * incluyendo su nombre y contenido. Esta clase utiliza las anotaciones
 * {@code @Getter} y {@code @Setter} de Lombok para generar automáticamente
 * los métodos de acceso y modificación (getters y setters) de sus atributos.
 *
 * <p>Atributos:
 * <ul>
 *   <li>{@code nombre}: Nombre del archivo.</li>
 *   <li>{@code contenido}: Contenido del archivo en formato {@code String}.</li>
 * </ul>
 *
 * <p>Gracias a las anotaciones de Lombok, no es necesario escribir manualmente
 * los métodos {@code getNombre()}, {@code setNombre(String nombre)},
 * {@code getContenido()} y {@code setContenido(String contenido)}.
 * @author Mateo Huancho
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26

 */
@Getter
@Setter
public class ArchivoResponse {
    private String nombre;
    private String contenido;
}
