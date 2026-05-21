package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.configuration;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * Configuración de la internacionalización y localización de la aplicación.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Luis Francisco Huertas Mostacero
 * @date 24/06/2024
 */
@Configuration
public class LocaleConfig implements WebMvcConfigurer {

    /**
     * Define el LocaleResolver para gestionar las configuraciones regionales.
     * Utiliza SessionLocaleResolver para almacenar la configuración regional en la
     * sesión.
     * 
     * @return el LocaleResolver configurado con la localización por defecto en
     *         español.
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Luis Francisco Huertas Mostacero
     * @date 24/06/2024
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(new Locale("es"));
        return slr;
    }

    /**
     * Define el interceptor para el cambio de localización.
     * Utiliza el parámetro "lang" para cambiar la configuración regional.
     * 
     * @return el LocaleChangeInterceptor configurado.
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Luis Francisco Huertas Mostacero
     * @date 24/06/2024
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    /**
     * Añade el interceptor de cambio de localización al registro de interceptores.
     * 
     * @param registry el registro de interceptores.
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Luis Francisco Huertas Mostacero
     * @date 24/06/2024
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
