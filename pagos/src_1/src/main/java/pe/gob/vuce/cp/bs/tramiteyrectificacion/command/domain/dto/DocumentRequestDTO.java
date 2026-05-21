package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa una solicitud de documento, incluyendo el nombre del
 * archivo,
 * el contenido del archivo codificado en base64, y un mapa de datos
 * adicionales.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 21/08/2024
 */
@Getter
@Setter
@Builder
public class DocumentRequestDTO {
      String nombre;
      String file;
      Map<String, Object> data;
}
