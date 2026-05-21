package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@JsonTypeName("ApiResponse_meta")
public class ApiResponseMeta implements Serializable {

    private static final long serialVersionUID = 1L;

    private String result;

    @Valid
    private List<@Valid VuceCP2Exception> mensajes = new ArrayList<>();

    private Integer cantidadRegistros = 0;

    private Integer cantidadRegistrosTotal;

    @Valid
    private Map<String, String> atributos = new HashMap<>();

    public ApiResponseMeta result(String result) {
        this.result = result;
        return this;
    }

    /**
     * Get result
     * @return result
     */

    @Schema(name = "result", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("result")
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ApiResponseMeta mensajes(List<@Valid VuceCP2Exception> mensajes) {
        this.mensajes = mensajes;
        return this;
    }

    public ApiResponseMeta addMensajesItem(VuceCP2Exception mensajesItem) {
        if (this.mensajes == null) {
            this.mensajes = new ArrayList<>();
        }
        this.mensajes.add(mensajesItem);
        return this;
    }

    /**
     * Get mensajes
     * @return mensajes
     */
    @Valid
    @Schema(name = "mensajes", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("mensajes")
    public List<@Valid VuceCP2Exception> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<@Valid VuceCP2Exception> mensajes) {
        this.mensajes = mensajes;
    }

    public ApiResponseMeta cantidadRegistros(Integer cantidadRegistros) {
        this.cantidadRegistros = cantidadRegistros;
        return this;
    }

    /**
     * Get cantidadRegistros
     * @return cantidadRegistros
     */

    @Schema(name = "cantidadRegistros", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("cantidadRegistros")
    public Integer getCantidadRegistros() {
        return cantidadRegistros;
    }

    public void setCantidadRegistros(Integer cantidadRegistros) {
        this.cantidadRegistros = cantidadRegistros;
    }

    public ApiResponseMeta cantidadRegistrosTotal(Integer cantidadRegistrosTotal) {
        this.cantidadRegistrosTotal = cantidadRegistrosTotal;
        return this;
    }

    /**
     * Get cantidadRegistrosTotal
     * @return cantidadRegistrosTotal
     */

    @Schema(name = "cantidadRegistrosTotal", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("cantidadRegistrosTotal")
    public Integer getCantidadRegistrosTotal() {
        return cantidadRegistrosTotal;
    }

    public void setCantidadRegistrosTotal(Integer cantidadRegistrosTotal) {
        this.cantidadRegistrosTotal = cantidadRegistrosTotal;
    }

    public ApiResponseMeta atributos(Map<String, String> atributos) {
        this.atributos = atributos;
        return this;
    }

    public ApiResponseMeta putAtributosItem(String key, String atributosItem) {
        if (this.atributos == null) {
            this.atributos = new HashMap<>();
        }
        this.atributos.put(key, atributosItem);
        return this;
    }

    /**
     * Get atributos
     * @return atributos
     */

    @Schema(name = "atributos", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("atributos")
    public Map<String, String> getAtributos() {
        return atributos;
    }

    public void setAtributos(Map<String, String> atributos) {
        this.atributos = atributos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ApiResponseMeta apiResponseMeta = (ApiResponseMeta) o;
        return Objects.equals(this.result, apiResponseMeta.result) &&
                Objects.equals(this.mensajes, apiResponseMeta.mensajes) &&
                Objects.equals(this.cantidadRegistros, apiResponseMeta.cantidadRegistros) &&
                Objects.equals(this.cantidadRegistrosTotal, apiResponseMeta.cantidadRegistrosTotal) &&
                Objects.equals(this.atributos, apiResponseMeta.atributos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(result, mensajes, cantidadRegistros, cantidadRegistrosTotal, atributos);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ApiResponseMeta {\n");
        sb.append("    result: ").append(toIndentedString(result)).append("\n");
        sb.append("    mensajes: ").append(toIndentedString(mensajes)).append("\n");
        sb.append("    cantidadRegistros: ").append(toIndentedString(cantidadRegistros)).append("\n");
        sb.append("    cantidadRegistrosTotal: ").append(toIndentedString(cantidadRegistrosTotal)).append("\n");
        sb.append("    atributos: ").append(toIndentedString(atributos)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
    
}
