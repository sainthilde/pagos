package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
/**
 * La clase ExcepcionesResponse representa una respuesta que contiene metadatos
 * y una lista de excepciones específicas de negocio, encapsuladas en objetos
 * {@code DataException}. Utiliza anotaciones de Lombok para simplificar la generación
 * de código y un supresor de advertencias para omitir advertencias específicas en
 * el compilador.
 *
 * <p>Anotaciones:
 * <ul>
 *   <li>{@code @Getter} y {@code @Setter}: Generan automáticamente los métodos de
 *       acceso (getters) y modificación (setters) para cada atributo en la clase.</li>
 *   <li>{@code @NoArgsConstructor} y {@code @AllArgsConstructor}: Generan un
 *       constructor sin argumentos y otro con todos los argumentos, respectivamente.</li>
 *   <li>{@code @SuppressWarnings("all")}: Suprime todas las advertencias del compilador
 *       en esta clase, lo que puede ser útil si existen advertencias que no son
 *       relevantes o críticas para el funcionamiento de la clase.</li>
 * </ul>
 *
 * <p>Atributos:
 * <ul>
 *   <li>{@code meta}: Un objeto {@code Meta} que contiene metadatos relacionados
 *       con la respuesta, como información de paginación o detalles adicionales.</li>
 *   <li>{@code data}: Una lista de objetos {@code DataException} que representan
 *       excepciones específicas de negocio.</li>
 * </ul>
 *
 * <p>Clase interna {@code DataException}:
 * <ul>
 *   <li>Representa los detalles de cada excepción, con atributos específicos para
 *       identificar y caracterizar la excepción.</li>
 *   <li>Atributos como {@code escalaId}, {@code motivo}, {@code naveDeportiva}, etc.,
 *       permiten almacenar información detallada sobre el tipo de excepción y
 *       las condiciones que la rodean.</li>
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
@SuppressWarnings("all")
public class ExcepcionesDueResponse {
    private Meta meta;
    private List<DataException> data;

    /**
     * La clase interna DataException representa una excepción de negocio específica,
     * proporcionando detalles sobre la entidad, tipo de nave, ámbito y otras
     * características relevantes para la excepción.
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DataException {
        Integer escalaId;
        Integer due;
        Integer motivo;
        Integer naveDeportiva;
        Integer naveCientifica;
        Integer sumaArqueoSinConvoy;
        Double sumaArqueo;
        Integer naveHospital;
        Integer entidadId;
        Integer ambitoNave;
        Integer paisPe;
    }
}