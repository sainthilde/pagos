package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.feign.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.feign.config.client.impl.OrdenPagoErrorDecoder;

@Configuration
public class OrdenPagoFeignConfig {

    @Bean
    public OrdenPagoErrorDecoder errorDecoder() {
        return new OrdenPagoErrorDecoder();
    }

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL; // Para debugging
    }
}