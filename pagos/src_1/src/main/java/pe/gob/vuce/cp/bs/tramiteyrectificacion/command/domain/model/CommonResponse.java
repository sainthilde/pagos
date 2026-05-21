package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase que representa una respuesta común utilizada en la API, que contiene metadatos y datos de respuesta.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 20/08/2024
 */
@Getter
@Setter
@NoArgsConstructor
public class CommonResponse {

    /**
     * Metadatos asociados a la respuesta, como estado, mensajes, etc.
     */
    @JsonProperty("meta")
    private Meta meta;

    /**
     * Datos específicos de la respuesta, que pueden ser de cualquier tipo.
     */
    @JsonProperty("data")
    private Object data;
}
