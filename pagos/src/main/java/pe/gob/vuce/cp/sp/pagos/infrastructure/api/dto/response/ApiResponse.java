package pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ApiResponse {

    private ApiResponseMeta meta;
    private Object data;

}
