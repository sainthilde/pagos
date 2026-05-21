package pe.gob.vuce.cp.bs.tramiteyrectificacion.command;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(scanBasePackages = {"pe.gob.vuce.cp.bs.tramiteyrectificacion.command", "pe.gob.vuce.cp.framework.globallogger"})
@EnableCaching
@EnableAspectJAutoProxy
public class CpTramiteyrectificacionCommandApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CpTramiteyrectificacionCommandApiApplication.class, args);
    }

}
