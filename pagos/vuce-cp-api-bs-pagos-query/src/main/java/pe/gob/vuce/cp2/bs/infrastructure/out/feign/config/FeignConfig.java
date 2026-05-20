package pe.gob.vuce.cp2.bs.infrastructure.out.feign.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "pe.gob.vuce.cp2.bs.infrastructure.out.feign")
public class FeignConfig {


}