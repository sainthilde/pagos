package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentoDDJJRequestDto {
    @JsonProperty("documentoId")
    private Integer documentoId;
    @JsonProperty("descAcronimo")
    private String descAcronimo;
}
