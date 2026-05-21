package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TramiteResponse {
    @JsonProperty("meta")
    private Meta meta;

    /**
     * Datos específicos de la respuesta, que pueden ser de cualquier tipo.
     */
    @JsonProperty("data")
    private List<Tramites> data;

    @Getter
    @Setter
    public static class Tramites {

        @JsonProperty("id")
        private Integer id;

        @JsonProperty("due")
        private String due;

        @JsonProperty("nombreNave")
        private String nombreNave;

        @JsonProperty("numeroSuce")
        private String numeroSuce;

        @JsonProperty("numeroTramite")
        private String numeroTramite;

        @JsonProperty("entidadNombre")
        private String entidadNombre;

        @JsonProperty("tupa")
        private String tupa;

        @JsonProperty("estadoTramite")
        private String estadoTramite;

        @JsonProperty("cpb")
        private String cpb;

        @JsonProperty("monto")
        private Double monto;

    }
}
