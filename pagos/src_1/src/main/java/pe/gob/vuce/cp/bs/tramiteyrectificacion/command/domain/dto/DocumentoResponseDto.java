package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Objects;

public class DocumentoResponseDto implements Serializable {


    private static final long serialVersionUID = 1L;

    private String filenetGuid;

    public DocumentoResponseDto filenetGuid(String filenetGuid) {
        this.filenetGuid = filenetGuid;
        return this;
    }

    /**
     * Get filenetGuid
     * @return filenetGuid
     */

    @Schema(name = "filenetGuid", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("filenetGuid")
    public String getFilenetGuid() {
        return filenetGuid;
    }

    public void setFilenetGuid(String filenetGuid) {
        this.filenetGuid = filenetGuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DocumentoResponseDto documentoResponseDto = (DocumentoResponseDto) o;
        return Objects.equals(this.filenetGuid, documentoResponseDto.filenetGuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filenetGuid);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class DocumentoResponseDto {\n");
        sb.append("    filenetGuid: ").append(toIndentedString(filenetGuid)).append("\n");
        sb.append("}");
        return sb.toString();
    }


    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
