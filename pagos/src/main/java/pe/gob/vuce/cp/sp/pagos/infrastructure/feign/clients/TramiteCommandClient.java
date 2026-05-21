package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import pe.gob.vuce.cp.framework.globallogger.annotation.Loggable;
import pe.gob.vuce.cp.framework.globallogger.constants.LogTypes;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.TramiteCommandRequest;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.TramiteCommandResponse;

@FeignClient(
        name = "tramiteCommandClient", 
        url = "${feign.client.tramites-command-api.base-url}"
    )
public interface TramiteCommandClient {

    @PostMapping("/tramites")
    @Loggable(category = LogTypes.FEIGN)
    TramiteCommandResponse crearTramite(
        @RequestHeader("Authorization") String authorization,
        @RequestHeader("idPerfil") String idPerfil,
        @RequestHeader("user") String user,
        @RequestHeader("ruc") String ruc,
        @RequestBody TramiteCommandRequest request
    );
}