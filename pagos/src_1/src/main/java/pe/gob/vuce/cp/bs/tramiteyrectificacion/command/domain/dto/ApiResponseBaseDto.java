package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.exception.ApiResponseMeta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ApiResponseBaseDto<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private ApiResponseMeta meta;

    @Valid
    private List<@Valid T> data;

    public ApiResponseBaseDto<T> meta(ApiResponseMeta meta) {
        this.meta = meta;
        return this;
    }

    @Valid
    @Schema(name = "meta", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("meta")
    public ApiResponseMeta getMeta() {
        return meta;
    }

    public void setMeta(ApiResponseMeta meta) {
        this.meta = meta;
    }

    public ApiResponseBaseDto<T> data(List<@Valid T> data) {
        this.data = data;
        return this;
    }

    public ApiResponseBaseDto<T> addDataItem(T dataItem) {
        if (this.data == null) {
            this.data = new ArrayList<>();
        }
        this.data.add(dataItem);
        return this;
    }

    @Valid
    @Schema(name = "data", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("data")
    public List<@Valid T> getData() {
        return data;
    }

    public void setData(List<@Valid T> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ApiResponseBaseDto<?> that = (ApiResponseBaseDto<?>) o;
        return Objects.equals(this.meta, that.meta) &&
                Objects.equals(this.data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meta, data);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ApiResponseBaseDto {\n");
        sb.append("    meta: ").append(toIndentedString(meta)).append("\n");
        sb.append("    data: ").append(toIndentedString(data)).append("\n");
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