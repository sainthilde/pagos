package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase que representa un mensaje dentro de la aplicación, incluyendo su código, tipo y contenido.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 19/08/2024
 */
@Getter
@Setter
@NoArgsConstructor
public class Mensaje {

    /**
     * Código único que identifica el mensaje.
     */
    @JsonProperty("codigo")
    private String codigo;

    /**
     * Tipo del mensaje, como error, información, etc.
     */
    @JsonProperty("tipo")
    private String tipo;

    /**
     * Contenido del mensaje.
     */
    @JsonProperty("mensaje")
    private String message;
}
