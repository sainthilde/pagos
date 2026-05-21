package pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Builder;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseMetadata {
    private String codeInfo;
    private String tipoOperacion;
    private String mensajeOperacion;
    private boolean esExitoso;
    private HttpStatus httpStatus;

    @Builder.Default
    private Map<String, Object> atributos = Collections.emptyMap();

    @Builder.Default
    private List<String> erroresAdicionales = Collections.emptyList();
}

