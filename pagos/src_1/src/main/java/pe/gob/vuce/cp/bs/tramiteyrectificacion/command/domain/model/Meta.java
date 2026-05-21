package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase que representa los metadatos de una respuesta en la aplicación, incluyendo el resultado, cantidad de registros,
 * mensajes, y atributos adicionales.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 1/03/2024
 */
@Getter
@Setter
@NoArgsConstructor
public class Meta {

    /**
     * Resultado de la operación, que indica si fue exitosa o si hubo un error.
     */
    @JsonProperty("result")
    private String result;

    /**
     * Cantidad de registros afectados o retornados por la operación.
     */
    @JsonProperty("cantidadRegistros")
    private Integer cantidadRegistros;

    /**
     * Lista de mensajes asociados con la operación, como errores o información adicional.
     */
    @JsonProperty("mensajes")
    private List<Mensaje> mensajes;

    /**
     * Mapa de atributos adicionales que pueden ser incluidos en la respuesta.
     */
    @JsonProperty("atributos")
    private Map<String,Object> atributos;
}
