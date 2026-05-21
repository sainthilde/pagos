package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasarelaEstatusResponse {

    private String cpb;
    private String estado;
    private String fechaOperacion;
    private String fechaProcesamiento;

}
