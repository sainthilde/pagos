package pe.gob.vuce.cp.sp.pagos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * La clase principal CpSpPagosApplication es el punto de entrada de la
 * aplicación Spring Boot
 * para el módulo de pagos. Configura y ejecuta la aplicación y define los
 * paquetes base
 * para el escaneo de componentes y clientes Feign.
 *
 * <p>
 * Anotaciones:
 * <ul>
 * <li>{@code @EnableFeignClients(basePackages = "pe.gob.vuce.cp.sp.pagos.infrastructure.api.clients")}:
 * Habilita la creación de clientes Feign para realizar solicitudes HTTP a
 * servicios
 * externos. Especifica el paquete donde se encuentran los clientes Feign.</li>
 * <li>{@code @SpringBootApplication(scanBasePackages =
 * {"pe.gob.vuce.cp.sp.pagos"})}:
 * Marca esta clase como la clase principal de configuración de Spring Boot,
 * habilitando el escaneo automático de componentes y configuraciones en el
 * paquete base especificado.</li>
 * </ul>
 *
 * <p>
 * Método {@code main}:
 * <ul>
 * <li>Inicia la ejecución de la aplicación Spring Boot. Este método es el punto
 * de entrada
 * de la aplicación y permite que Spring Boot configure y arranque el contexto
 * de la aplicación.</li>
 * </ul>
 *
 * <p>
 * Ejemplo de uso:
 * 
 * <pre>
 *     java -jar cp-sp-pagos.jar
 * </pre>
 * 
 * @author Mateo Huancho
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @since 2024-10-26
 */
@EnableFeignClients(basePackages = "pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients")
@SpringBootApplication(scanBasePackages = {
    "pe.gob.vuce.cp.sp.pagos",
    "pe.gob.vuce.cp.framework.globallogger"
})
@EnableAspectJAutoProxy
public class CpSpPagosApplication {
  /**
   * Método principal que inicia la ejecución de la aplicación Spring Boot para el
   * módulo de pagos.
   *
   * @param args argumentos de línea de comandos
   */
  public static void main(String... args) {
    SpringApplication.run(CpSpPagosApplication.class, args);
  }
}