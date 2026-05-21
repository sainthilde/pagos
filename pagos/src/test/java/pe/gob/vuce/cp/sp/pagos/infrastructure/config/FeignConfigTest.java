package pe.gob.vuce.cp.sp.pagos.infrastructure.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.FULL;
import feign.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;

class FeignConfigTest {

    @InjectMocks
    private FeignConfig feignConfig;

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream consoleOutput;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);
        consoleOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(consoleOutput));
    }

    @Test
    void testFeignLoggerLevel_Basic() {
        Logger.Level level = feignConfig.feignLoggerLevel("BASIC");
        assertEquals(Logger.Level.BASIC, level);
    }

    @Test
    void testFeignLoggerLevel_Headers() {
        Logger.Level level = feignConfig.feignLoggerLevel("HEADERS");
        assertEquals(Logger.Level.HEADERS, level);
    }

    @Test
    void testFeignLoggerLevel_Full() {
        Logger.Level level = feignConfig.feignLoggerLevel("FULL");
        assertEquals(Logger.Level.FULL, level);
    }

    @Test
    void testFeignLoggerLevel_Default() {
        Logger.Level level = feignConfig.feignLoggerLevel("UNKNOWN");
        assertEquals(Logger.Level.FULL, level);
    }

    @Test
     void testFeignLogger_LogMethod_LevelFull_OmitsSpecifiedConfigKeys() throws Exception {
        feignConfig.feignLoggerLevel(FULL);

        // Obtén el logger personalizado desde FeignConfig
        Logger customLogger = feignConfig.feignLogger();

        // Usa reflection para acceder al método protegido `log`
        Method logMethod = Logger.class.getDeclaredMethod("log", String.class, String.class, Object[].class);
        logMethod.setAccessible(true);

        // Prueba con un configKey permitido
        String allowedConfigKey = "someKeyWithoutExcludedWords";
        String format = "Test message";
        Object[] args = {};

        // Invoca el método `log` mediante reflection
        logMethod.invoke(customLogger, allowedConfigKey, format, args);

        // Verifica que el mensaje fue registrado en la salida de consola
        String output = consoleOutput.toString();
        assertFalse(output.contains("[Feign Logger] " + allowedConfigKey + " - " + format));
    }

    @Test
     void testFeignLogger_LogMethod_LevelFull_WithExcludedKeys() throws Exception {
        feignConfig.feignLoggerLevel(FULL);

        Logger customLogger = feignConfig.feignLogger();

        // Usa reflection para acceder al método protegido `log`
        Method logMethod = Logger.class.getDeclaredMethod("log", String.class, String.class, Object[].class);
        logMethod.setAccessible(true);

        // Prueba con un configKey que debe ser excluido
        String excludedConfigKey = "ORDEN_PAGO_SUNAT";
        String format = "This log should be omitted";
        Object[] args = {};

        // Limpia la salida de consola anterior
        consoleOutput.reset();

        // Invoca el método `log` mediante reflection
        logMethod.invoke(customLogger, excludedConfigKey, format, args);

        // Verifica que el mensaje NO fue registrado en la salida de consola
        String output = consoleOutput.toString();
        assertTrue(!output.contains("[Feign Logger] " + excludedConfigKey + " - " + format));
    }

    @BeforeEach
     void restoreSystemOut() {
        // Restaura la salida de consola original
        System.setOut(originalOut);
    }
}