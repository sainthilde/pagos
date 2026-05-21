package pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Mensaje {
  String codigo;
  String tipo;
  String message;
}
