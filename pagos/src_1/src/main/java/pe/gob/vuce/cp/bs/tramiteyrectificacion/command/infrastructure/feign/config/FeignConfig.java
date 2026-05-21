package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.feign.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración para habilitar Feign en la aplicación. Esta configuración escanea y registra
 * automáticamente los clientes Feign definidos en los paquetes especificados.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 19/08/2024
 */
@Configuration
@EnableFeignClients(basePackages = "pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure")
public class FeignConfig {
    // Clase de configuración vacía que habilita y configura Feign en la aplicación.
}
