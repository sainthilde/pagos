package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.feign.config;

import org.springframework.cloud.openfeign.support.SortJacksonModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.openfeign.support.PageJacksonModule;

/**
 * Configuración para la serialización y deserialización de objetos de
 * paginación y ordenación
 * en Feign, utilizando módulos de Jackson.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 19/08/2024
 */
@Configuration
public class PageConfig {

    /**
     * Configura el módulo Jackson para manejar la serialización y deserialización
     * de objetos de paginación en Feign.
     *
     * @return Una instancia de PageJacksonModule.
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Fernando Tanta
     * @date 19/08/2024
     */
    @Bean
    public PageJacksonModule pageJacksonModule() {
        return new PageJacksonModule();
    }

    /**
     * Configura el módulo Jackson para manejar la serialización y deserialización
     * de objetos de ordenación en Feign.
     *
     * @return Una instancia de SortJacksonModule.
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Fernando Tanta
     * @date 19/08/2024
     */
    @Bean
    public SortJacksonModule sortJacksonModule() {
        return new SortJacksonModule();
    }

}
