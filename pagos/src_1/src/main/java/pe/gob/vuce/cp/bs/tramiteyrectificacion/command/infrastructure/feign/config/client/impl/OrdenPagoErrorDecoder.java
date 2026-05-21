package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.feign.config.client.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.OrdenPagoErrorResponse;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.exceptions.OrdenPagoAnulacionException;

@Component
public class OrdenPagoErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();
    private static final Pattern ORDEN_PAGO_ID_PATTERN = Pattern.compile("/ordenes-pago/(\\d+)/anular");

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() >= 400) {
            try {
                // Leer el cuerpo completo de la respuesta de error
                String body = Util.toString(response.body().asReader(StandardCharsets.UTF_8));

                // Crear una lista con un error response
                OrdenPagoErrorResponse errorResponse = new OrdenPagoErrorResponse(
                        response.status(),
                        body,
                        extractOrdenPagoIdFromUrl(response.request().url()) // Método helper
                );

                return new OrdenPagoAnulacionException(
                        "Error en servicio de ordenes de pago",
                        List.of(errorResponse));
            } catch (IOException ex) {
                OrdenPagoErrorResponse errorResponse = new OrdenPagoErrorResponse(
                        response.status(),
                        "{\"meta\":{\"result\":\"ERROR\",\"mensaje\":\"Error procesando respuesta\"},\"data\":[]}",
                        null);
                return new OrdenPagoAnulacionException(
                        "Error leyendo respuesta del servicio",
                        List.of(errorResponse));
            }
        }
        return defaultErrorDecoder.decode(methodKey, response);
    }

    private Integer extractOrdenPagoIdFromUrl(String url) {
        try {
            // Extraer el ID de la URL (ej: /ordenes-pago/1234/anular)
            Matcher matcher = ORDEN_PAGO_ID_PATTERN.matcher(url);
            if (matcher.find()) {
                return Integer.parseInt(matcher.group(1));
            }
        } catch (Exception e) {
            // Ignorar si no se puede extraer el ID
        }
        return null;
    }
}