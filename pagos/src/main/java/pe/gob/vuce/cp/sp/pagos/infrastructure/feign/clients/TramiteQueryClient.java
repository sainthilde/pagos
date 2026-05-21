package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import pe.gob.vuce.cp.framework.globallogger.annotation.Loggable;
import pe.gob.vuce.cp.framework.globallogger.constants.LogTypes;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.TramiteResponse;

@FeignClient(name = "tramiteQueryClient", url = "${feign.client.tramites-query-api.base-url}")
public interface TramiteQueryClient {

    @GetMapping("/tramites/escala/{escalaId}/documento/{documentoId}")
    @Loggable(category = LogTypes.FEIGN)
    TramiteResponse obtenerTramites(
            @RequestHeader("Authorization") String authorization,
            @RequestHeader("idperfil") Integer idPerfil,
            @PathVariable("escalaId") Integer escalaId,
            @PathVariable("documentoId") Integer documentoId,
            @RequestParam("indicadorES") String indicadorES);
}
