package pe.gob.vuce.cp.sp.pagos.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.ORDEN_PAGO;

/**
 * Clase de configuración que habilita la ejecución asíncrona en la aplicación
 * y define un ejecutor de tareas para manejar operaciones en segundo plano.
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    /**
     * Crea un bean de ejecutor de tareas asíncronas con una configuración de
     * tamaño de pool que permite manejar múltiples hilos de ejecución.
     *
     * @return Un objeto Executor que se utiliza para ejecutar tareas asíncronas.
     */
    @Primary
    @Bean(name = "taskExecutorDefault")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix(ORDEN_PAGO);
        executor.initialize();
        return executor;
    }

}

