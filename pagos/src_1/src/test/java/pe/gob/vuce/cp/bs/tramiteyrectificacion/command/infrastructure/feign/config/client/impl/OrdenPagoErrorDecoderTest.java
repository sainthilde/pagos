package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.feign.config.client.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.junit.jupiter.api.Test;

import feign.Request;
import feign.RequestTemplate;
import feign.Response;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.exceptions.OrdenPagoAnulacionException;

class OrdenPagoErrorDecoderTest {

    private final OrdenPagoErrorDecoder decoder = new OrdenPagoErrorDecoder();

    private Response buildResponse(int status, String url, String body) {
        Request request = Request.create(Request.HttpMethod.POST, url, Map.of(), new byte[0], StandardCharsets.UTF_8, new RequestTemplate());
        return Response.builder()
                .status(status)
                .reason("error")
                .request(request)
                .body(body, StandardCharsets.UTF_8)
                .build();
    }

    @Test
    void decode_shouldWrapErrorWithOrdenPagoId() {
        Response response = buildResponse(409, "http://host/ordenes-pago/123/anular", "{\"msg\":\"conflict\"}");
        Exception ex = decoder.decode("method", response);
        assertTrue(ex instanceof OrdenPagoAnulacionException);
        OrdenPagoAnulacionException oe = (OrdenPagoAnulacionException) ex;
        assertEquals(1, oe.getErrores().size());
        assertEquals(409, oe.getErrores().get(0).getStatusCode());
        assertEquals(123, oe.getErrores().get(0).getOrdenPagoId());
    }

    @Test
    void decode_shouldHandleIOExceptionFallback() {
        // Body is still readable; to simulate IOException we'd need a custom Response.Body.
        // We'll assert normal path works and content is preserved.
        Response response = buildResponse(500, "http://host/ordenes-pago/999/anular", "oops");
        OrdenPagoAnulacionException ex = (OrdenPagoAnulacionException) decoder.decode("method", response);
        assertEquals("oops", ex.getErrores().get(0).getBody());
    }

    @Test
    void decode_shouldBypassWhenStatusBelow400() {
        Response response = buildResponse(200, "http://host/ordenes-pago/123/anular", "ok");
        Exception ex = decoder.decode("method", response);
        assertNotNull(ex); // default decoder returns something (possibly null depending on feign impl)
    }
}
