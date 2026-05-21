package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model;

import lombok.Data;

@Data
public class MasterResponse <T>{
    private Meta meta;
    private T data;
}
