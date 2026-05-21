package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @project cp-api-bs-tramiteyrectificacion-command
 *
 * @date 21/08/2024
 **/
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateDocumentoModel implements Serializable {
     String bytes;
     String nombre;
     String contentType;
}
