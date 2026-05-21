package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pe.gob.vuce.cp.framework.globallogger.annotation.Loggable;
import pe.gob.vuce.cp.framework.globallogger.constants.LogTypes;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.config.OAuthClientConfig;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.OAuthResponse;

/**
 * Cliente Feign para interactuar con el servicio OAuth y obtener tokens de
 * autenticación.
 * 
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@FeignClient(name = "oAuthClient", url = "${feign.client.oauth-api.url}", configuration = OAuthClientConfig.class)
public interface OAuthClient {

    /**
     * Obtiene un token OAuth.
     *
     * @param grantType Tipo de autorización.
     * @param scope     Alcance de la solicitud.
     * @return Respuesta con el token OAuth.
     */
    @PostMapping(consumes = "application/x-www-form-urlencoded")
    @Loggable(category = LogTypes.FEIGN)
    OAuthResponse getToken(
            @RequestParam("grant_type") String grantType,
            @RequestParam("scope") String scope);
}
