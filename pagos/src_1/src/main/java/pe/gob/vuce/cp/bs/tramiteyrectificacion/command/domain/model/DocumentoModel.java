package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Modelo que representa un documento en la aplicación.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 22/08/2024
 */
@Getter
@Setter
@NoArgsConstructor
public class DocumentoModel extends BaseModel {

    /**
     * Identificador único del documento.
     */
    @JsonProperty("documentoId")
    private Integer documentoId;

    /**
     * Acronimo del documento
     */
    @JsonProperty("descAcronimo")
    private String descAcronimo;

}
