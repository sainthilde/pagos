package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.config;

import feign.auth.BasicAuthRequestInterceptor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * Configuración para el cliente OAuth, proporcionando la autenticación básica para Feign.
 * La clase OAuthClientConfig configura las credenciales y propiedades necesarias para
 * la autenticación OAuth en los clientes Feign de la aplicación. Utiliza anotaciones
 * de Spring para cargar los valores desde el archivo de configuración de la aplicación.
 *
 * <p>Anotaciones:
 * <ul>
 *   <li>{@code @Configuration}: Marca esta clase como una clase de configuración
 *       de Spring, permitiendo la creación de beans y la inyección de dependencias.</li>
 *   <li>{@code @Value}: Inyecta valores desde el archivo de configuración de la
 *       aplicación ({@code application.properties} o {@code application.yml})
 *       en los atributos específicos.</li>
 *   <li>{@code @Getter}: Genera automáticamente los métodos de acceso (getters)
 *       para los atributos que requieren ser accesibles desde otras partes de la aplicación.</li>
 * </ul>
 *
 * <p>Atributos:
 * <ul>
 *   <li>{@code username}: Nombre de usuario utilizado para la autenticación básica en OAuth.</li>
 *   <li>{@code password}: Contraseña utilizada para la autenticación básica en OAuth.</li>
 *   <li>{@code grantType}: Tipo de autorización OAuth, como "password" o "client_credentials".</li>
 *   <li>{@code scope}: Ámbito de acceso OAuth que define los permisos o áreas a las que se accederá.</li>
 * </ul>
 *
 * <p>Métodos:
 * <ul>
 *   <li>{@code basicAuthRequestInterceptor}: Define un bean que configura un interceptor de
 *       autenticación básica para las solicitudes Feign, permitiendo el envío automático
 *       de las credenciales de usuario en las cabeceras HTTP de cada solicitud.</li>
 * </ul>
 * @author Mateo Huancho
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Configuration
public class OAuthClientConfig {

    @Value("${feign.client.oauth-api.username}")
    private String username;

    @Value("${feign.client.oauth-api.password}")
    private String password;

    @Getter
    @Value("${feign.client.oauth-api.grant-type}")
    private String grantType;

    @Getter
    @Value("${feign.client.oauth-api.scope}")
    private String scope;

    /**
     * Bean para la autenticación básica en solicitudes Feign.
     *
     * @return Interceptor de autenticación básica.
     */
    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor(username, password);
    }
}

