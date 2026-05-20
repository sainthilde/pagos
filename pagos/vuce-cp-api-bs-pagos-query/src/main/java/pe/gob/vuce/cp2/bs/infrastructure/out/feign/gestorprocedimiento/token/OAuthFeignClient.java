package pe.gob.vuce.cp2.bs.infrastructure.out.feign.gestorprocedimiento.token;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "oauthClient",
        url = "${feign.client.oauth-api.url}"
)
public interface OAuthFeignClient {

    @PostMapping(
            value = "/pass-through-https-desa/oauth2/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    OAuthTokenResponse getToken(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("grant_type") String grantType,
            @RequestParam("scope") String scope
    );
}