package pe.gob.vuce.cp2.bs.infrastructure.out.feign.gestorprocedimiento.cc006;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "procedimientoTasaClient",
        url = "${feign.client.gestor-procedimiento-api.url}"
)
public interface Procedimiento006FeignClient {

    @GetMapping("/procedimiento-tasa")
    ProcedimientoTasaResponseDto obtenerTasa(
            @RequestHeader("Authorization") String bearerToken,
            @RequestParam("procedimiento") Integer procedimiento,
            @RequestParam("secuencia") Integer secuencia
    );
}