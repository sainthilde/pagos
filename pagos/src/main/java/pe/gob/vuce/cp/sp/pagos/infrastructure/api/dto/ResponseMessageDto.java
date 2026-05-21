package pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @project cp-api-bs-fichatecnica-command
 * @author Luis Huertas
 * @since 04/07/2024
 **/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessageDto {
     String codigo;
     String tipo;
     String mensaje;
     List<String> parametrosDeMensaje;
}