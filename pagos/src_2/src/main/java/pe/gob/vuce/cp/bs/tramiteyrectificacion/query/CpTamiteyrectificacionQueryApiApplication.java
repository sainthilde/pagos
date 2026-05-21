package pe.gob.vuce.cp.bs.tramiteyrectificacion.query;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Clase principal que inicia la aplicación Spring Boot para la API de consulta
 * de trámites y rectificaciones.
 *
 * @project cp-api-bs-tramiteyrectificacion-query
 * @autor Luis Francisco Huertas Mostacero
 * @date 24/08/2024
 */
@SpringBootApplication(scanBasePackages = {"pe.gob.vuce.cp.bs.tramiteyrectificacion.query",
  "pe.gob.vuce.cp.sp.framework.security", "pe.gob.vuce.cp.framework.globallogger"})
@EnableCaching
@EnableAspectJAutoProxy
public class CpTamiteyrectificacionQueryApiApplication {

    /**
     * Método principal que se ejecuta al iniciar la aplicación.
     *
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        SpringApplication.run(CpTamiteyrectificacionQueryApiApplication.class, args);
    }

}
