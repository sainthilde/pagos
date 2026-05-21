package pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.Map;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApiResponseMeta {

  private String result;
  private Integer cantidadRegistros;
  private Integer cantidadRegistrosTotal;
  private List<Mensaje> mensajes;
  private Map<String, Object> atributos;

}
