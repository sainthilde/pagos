package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model;

import java.util.List;
import java.util.Map;

import lombok.Data;


@Data
public class TramiteCommandResponse {
    private Meta meta;
    private List<DataItem> data;

    @lombok.Data
    public static class Meta {
        private String result;
        private List<Object> mensajes;
        private Integer cantidadRegistros;
        private Integer cantidadRegistrosTotal;
        private Map<String, Object> atributos;
    }

    @lombok.Data
    public static class DataItem {
        private Integer idSuce;
        private String numeroSuce;
    }

}
