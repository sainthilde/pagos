package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model;

import feign.RequestTemplate;
import feign.auth.BasicAuthRequestInterceptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.config.OAuthClientConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

 class OAuthClientConfigTest {

     @InjectMocks
     private OAuthClientConfig oAuthClientConfig;

     @BeforeEach
      void setUp() {
         MockitoAnnotations.openMocks(this);

         // Inject test values
         ReflectionTestUtils.setField(oAuthClientConfig, "username", "testUser");
         ReflectionTestUtils.setField(oAuthClientConfig, "password", "testPass");
         ReflectionTestUtils.setField(oAuthClientConfig, "grantType", "password");
         ReflectionTestUtils.setField(oAuthClientConfig, "scope", "read write");
     }

     @Test
     void testBasicAuthRequestInterceptor() {
         BasicAuthRequestInterceptor interceptor = oAuthClientConfig.basicAuthRequestInterceptor();
         assertNotNull(interceptor);

         // Create a request template to test the interceptor
         RequestTemplate template = new RequestTemplate();
         interceptor.apply(template);

         // Verify that the interceptor adds the correct Authorization header
         String expectedAuthHeader = "Basic " + java.util.Base64.getEncoder().encodeToString("testUser:testPass".getBytes());
         assertEquals(expectedAuthHeader, template.headers().get("Authorization").iterator().next());
     }

     @Test
     void testGrantType() {
         assertEquals("password", oAuthClientConfig.getGrantType());
     }

     @Test
     void testScope() {
         assertEquals("read write", oAuthClientConfig.getScope());
     }
}
