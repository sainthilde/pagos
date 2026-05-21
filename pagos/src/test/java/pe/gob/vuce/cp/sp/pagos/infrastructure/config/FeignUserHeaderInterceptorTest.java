package pe.gob.vuce.cp.sp.pagos.infrastructure.config;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestTemplate;

class FeignUserHeaderInterceptorTest {

    private final FeignUserHeaderInterceptor interceptor = new FeignUserHeaderInterceptor();

    @Test
    void should_add_user_header_when_present() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user", "mateo");

        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        RequestTemplate template = mock(RequestTemplate.class);

        interceptor.apply(template);

        verify(template).header("user", "mateo");
    }

    @Test
    void should_not_add_header_when_user_is_missing() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        RequestTemplate template = mock(RequestTemplate.class);

        interceptor.apply(template);

        verify(template, never()).header(eq("user"), any(String[].class));
    }

    @Test   
    void should_not_fail_when_no_request_context() {
        RequestContextHolder.resetRequestAttributes();

        RequestTemplate template = mock(RequestTemplate.class);

        interceptor.apply(template);

        verifyNoInteractions(template);
    }
}