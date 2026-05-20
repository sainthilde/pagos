package pe.gob.vuce.cp2.bs.infrastructure.out.feign.gestorprocedimiento.cc003;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "procedimiento003Client",
        url = "${feign.client.gestor-procedimiento-api.url}"
)
public interface Procedimiento003FeignClient {

    @GetMapping("/procedimientos-componente")
    ProcedimientosResponse obtenerProcedimientos(
            @RequestHeader("Authorization") String bearerToken,
            @RequestParam("componente") String componente,
            @RequestParam("entidadId") Integer entidadId,
            @RequestParam("textSearch") String textSearch
    );
}