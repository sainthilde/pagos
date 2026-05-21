package pe.gob.vuce.cp.sp.pagos.infrastructure.config;

import java.util.concurrent.Executor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AsyncConfigTest {

    private AsyncConfig asyncConfig;

    @BeforeEach
    void setUp() {
        asyncConfig = new AsyncConfig();
    }

    @Test
    void testTaskExecutorBeanCreation() {
        Executor executor = asyncConfig.taskExecutor();
        assertNotNull(executor, "El executor no debe ser null");
        assertTrue(executor instanceof ThreadPoolTaskExecutor, "Debe ser instancia de ThreadPoolTaskExecutor");

        ThreadPoolTaskExecutor threadPoolExecutor = (ThreadPoolTaskExecutor) executor;
        assertEquals(2, threadPoolExecutor.getCorePoolSize(), "Core pool size debe ser 2");
        assertEquals(10, threadPoolExecutor.getMaxPoolSize(), "Max pool size debe ser 10");
        assertEquals(500, threadPoolExecutor.getQueueCapacity(), "Queue capacity debe ser 500");
        assertFalse(threadPoolExecutor.getThreadNamePrefix().contains("ORDEN_PAGO"), "El prefijo del nombre del hilo debe contener 'ORDEN_PAGO'");
    }

    @Test
    void testBeanAnnotation() throws NoSuchMethodException {
        var method = AsyncConfig.class.getDeclaredMethod("taskExecutor");
        var beanAnnotation = AnnotationUtils.findAnnotation(method, org.springframework.context.annotation.Bean.class);
        assertNotNull(beanAnnotation, "El método debe tener la anotación @Bean");
        assertArrayEquals(new String[] { "taskExecutorDefault" }, beanAnnotation.name(), "El nombre del bean debe ser 'taskExecutorDefault'");

        var primaryAnnotation = AnnotationUtils.findAnnotation(method, org.springframework.context.annotation.Primary.class);
        assertNotNull(primaryAnnotation, "El método debe tener la anotación @Primary");
    }
}
