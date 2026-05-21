package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.feign.config;

import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verifyNoInteractions;

public class InterceptorFeignConfigTest {

    private HttpServletRequest mockRequest;
    private ServletRequestAttributes mockAttributes;
    private RequestTemplate requestTemplate;

    private InterceptorFeignConfig interceptorFeignConfig;

    @BeforeEach
    void setUp() {
        mockRequest = mock(HttpServletRequest.class);
        mockAttributes = mock(ServletRequestAttributes.class);
        requestTemplate = mock(RequestTemplate.class);

        when(mockAttributes.getRequest()).thenReturn(mockRequest);
        interceptorFeignConfig = new InterceptorFeignConfig();

        RequestContextHolder.setRequestAttributes(mockAttributes);
    }

    @AfterEach
    void tearDown() {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    void testApplyHeadersAddedFromHttpServletRequest() {
        // Arrange
        when(mockRequest.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer token123");
        when(mockRequest.getHeader("idPerfil")).thenReturn("99");

        // Act
        interceptorFeignConfig.apply(requestTemplate);

        // Assert
        verify(requestTemplate).header(HttpHeaders.AUTHORIZATION, "Bearer token123");
        verify(requestTemplate).header("Host", "landing-desa.vuce.gob.pe");
        verify(requestTemplate).header("idPerfil", "99");
    }

    @Test
    void testApplyWhenNoRequestAttributes_NoHeadersAdded() {
        // Arrange
        RequestContextHolder.resetRequestAttributes();

        // Act
        interceptorFeignConfig.apply(requestTemplate);

        // Assert
        // No interactions expected with requestTemplate
        verifyNoInteractions(requestTemplate);
    }
}
