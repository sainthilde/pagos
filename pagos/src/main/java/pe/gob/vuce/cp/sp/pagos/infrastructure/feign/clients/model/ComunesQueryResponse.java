package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;
/**
 * La clase ComunesQueryResponse representa una respuesta común que incluye
 * metadatos y una lista de datos. Utiliza anotaciones de Lombok para simplificar
 * la generación de código repetitivo.
 *
 * <p>Anotaciones:
 * <ul>
 *   <li>{@code @Getter}: Genera automáticamente los métodos de acceso (getters)
 *       para cada atributo.</li>
 *   <li>{@code @Setter}: Genera automáticamente los métodos de modificación (setters)
 *       para cada atributo.</li>
 *   <li>{@code @NoArgsConstructor}: Genera un constructor sin argumentos, necesario
 *       para algunos frameworks de serialización y deserialización de objetos.</li>
 *   <li>{@code @AllArgsConstructor}: Genera un constructor con argumentos para
 *       inicializar todos los atributos de la clase.</li>
 * </ul>
 *
 * <p>Atributos:
 * <ul>
 *   <li>{@code meta}: Objeto {@code Meta} que contiene los metadatos relacionados
 *       con la respuesta, como paginación, estado, u otra información adicional.</li>
 *   <li>{@code data}: Lista de objetos {@code Data} que representan los datos
 *       específicos devueltos en la respuesta.</li>
 * </ul>
 *
 * <p>Gracias a Lombok, no es necesario escribir manualmente los métodos de acceso
 * y modificación, ni los constructores por defecto y completo.
 * @author Mateo Huancho
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComunesQueryResponse {
    private Meta meta;
    private List<Data> data;
}

