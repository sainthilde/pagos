package pe.gob.vuce.cp.sp.pagos.infrastructure.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CacheConfigTest {

    private CacheConfig cacheConfig;

    @BeforeEach
    void setUp() {
        cacheConfig = new CacheConfig();

        // Inyectamos valores "mockeados" a los campos @Value usando reflexión
        injectValue(cacheConfig, "redisHost", "localhost");
        injectValue(cacheConfig, "redisPort", 6379);
        injectValue(cacheConfig, "redisUsername", "user");
        injectValue(cacheConfig, "redisPassword", "pass");
        injectValue(cacheConfig, "redisTtl", 2); // horas
    }

    @Test
    void testRedisConnectionFactoryCreation() {
        RedisConnectionFactory factory = cacheConfig.redisConnectionFactory();
        assertNotNull(factory);
        assertInstanceOf(LettuceConnectionFactory.class, factory);
    }

    @Test
    void testRedisTemplateCreation() {
        RedisConnectionFactory factory = cacheConfig.redisConnectionFactory();
        RedisTemplate<String, Object> template = cacheConfig.redisTemplate(factory);

        assertNotNull(template);
        assertEquals(StringRedisSerializer.class, template.getKeySerializer().getClass());
        assertEquals(GenericJackson2JsonRedisSerializer.class, template.getValueSerializer().getClass());
    }

    @Test
    void testCacheManagerCreation() {
        RedisConnectionFactory factory = cacheConfig.redisConnectionFactory();
        CacheManager cacheManager = cacheConfig.cacheManager(factory);

        assertNotNull(cacheManager);
        assertInstanceOf(RedisCacheManager.class, cacheManager);
    }

    // Utilidad para inyectar campos privados
    private void injectValue(Object target, String fieldName, Object value) {
        try {
            Field field = CacheConfig.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException("Error setting field: " + fieldName, e);
        }
    }
}
