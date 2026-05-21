package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("all")
public class VuceCP2Exception implements Serializable {

    private static final long serialVersionUID = 1L;

    private String codigo;

    private String tipo;

    private String mensaje;

    @Valid
    private List<String> parametrosDeMensaje;

    public VuceCP2Exception codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    /**
     * Codigo de error de Sistema
     * @return codigo
     */

    @Schema(name = "codigo", description = "Codigo de error de Sistema", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("codigo")
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public VuceCP2Exception tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    /**
     * Tipo de Error de Sistema
     * @return tipo
     */

    @Schema(name = "tipo", description = "Tipo de Error de Sistema", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("tipo")
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public VuceCP2Exception mensaje(String mensaje) {
        this.mensaje = mensaje;
        return this;
    }

    /**
     * Descripcion del error de Sistema
     * @return mensaje
     */

    @Schema(name = "mensaje", description = "Descripcion del error de Sistema", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("mensaje")
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public VuceCP2Exception parametrosDeMensaje(List<String> parametrosDeMensaje) {
        this.parametrosDeMensaje = parametrosDeMensaje;
        return this;
    }

    public VuceCP2Exception addParametrosDeMensajeItem(String parametrosDeMensajeItem) {
        if (this.parametrosDeMensaje == null) {
            this.parametrosDeMensaje = new ArrayList<>();
        }
        this.parametrosDeMensaje.add(parametrosDeMensajeItem);
        return this;
    }

    /**
     * Parametros del mensaje de error
     * @return parametrosDeMensaje
     */

    @Schema(name = "parametrosDeMensaje", description = "Parametros del mensaje de error", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("parametrosDeMensaje")
    public List<String> getParametrosDeMensaje() {
        return parametrosDeMensaje;
    }

    public void setParametrosDeMensaje(List<String> parametrosDeMensaje) {
        this.parametrosDeMensaje = parametrosDeMensaje;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VuceCP2Exception vuceCP2Exception = (VuceCP2Exception) o;
        return Objects.equals(this.codigo, vuceCP2Exception.codigo) &&
                Objects.equals(this.tipo, vuceCP2Exception.tipo) &&
                Objects.equals(this.mensaje, vuceCP2Exception.mensaje) &&
                Objects.equals(this.parametrosDeMensaje, vuceCP2Exception.parametrosDeMensaje);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, tipo, mensaje, parametrosDeMensaje);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class VuceCP2Exception {\n");
        sb.append("    codigo: ").append(toIndentedString(codigo)).append("\n");
        sb.append("    tipo: ").append(toIndentedString(tipo)).append("\n");
        sb.append("    mensaje: ").append(toIndentedString(mensaje)).append("\n");
        sb.append("    parametrosDeMensaje: ").append(toIndentedString(parametrosDeMensaje)).append("\n");
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
