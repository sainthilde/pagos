package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.feign.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Configuración del interceptor de Feign para agregar encabezados personalizados a las solicitudes HTTP.
 * Este interceptor agrega el encabezado "Host" a todas las solicitudes realizadas a través de Feign.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 19/08/2024
 */
@Configuration
public class InterceptorFeignConfig implements RequestInterceptor {

    /**
     * Aplica el encabezado "Host" a la plantilla de solicitud de Feign.
     *
     * @param requestTemplate La plantilla de solicitud de Feign a la que se aplicará el encabezado.
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Fernando Tanta
     * @date 19/08/2024
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            final HttpServletRequest httpServletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
            requestTemplate.header(HttpHeaders.AUTHORIZATION, httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION));
            requestTemplate.header("Host", "landing-desa.vuce.gob.pe");
            requestTemplate.header("idPerfil", httpServletRequest.getHeader("idPerfil"));

        }
    }
}
