package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.feign.config.client.impl;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.ExcepcionMensajeResponseDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.OrdenPagoErrorResponse;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.OrdenPagoResponseDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.exceptions.OrdenPagoAnulacionException;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.FeignOrdenPagoClientPort;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.feign.config.FeignOrdenPagoClient;

@AllArgsConstructor
@Component
public class FeignOrdenPagoClientAdapter implements FeignOrdenPagoClientPort {
    private final FeignOrdenPagoClient feignOrdenPagoClient;

    @Override
    public Object anular(Integer ordenPagoId, String user) {
        try {
            ResponseEntity<String> response = feignOrdenPagoClient.anular(ordenPagoId, user);

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody(); // Éxito
            } else {
                // Esto no debería ejecutarse porque los errores serán capturados por el
                // ErrorDecoder
                return new OrdenPagoErrorResponse(
                        response.getStatusCodeValue(),
                        response.getBody(),
                        ordenPagoId);
            }
        } catch (OrdenPagoAnulacionException e) {
            // Capturar la excepción del ErrorDecoder
            return new OrdenPagoErrorResponse(
                    e.getErrores().get(0).getStatusCode(),
                    e.getErrores().get(0).getBody(),
                    ordenPagoId);
        } catch (Exception e) {
            // Otros errores
            return new OrdenPagoErrorResponse(
                    500,
                    "{\"meta\":{\"result\":\"ERROR\",\"mensaje\":\"Error de conexión: " + e.getMessage()
                            + "\"},\"data\":[]}",
                    ordenPagoId);
        }
    }

    @Override
    public ExcepcionMensajeResponseDto obtenerExcepciones(Integer escalaId, Integer entidad) {
        return feignOrdenPagoClient.obtenerExcepciones(escalaId, entidad);
    }

    @Override
    public List<OrdenPagoResponseDto> findByEscalaIdAndDocumentoId(Integer escalaId, Integer documentoId) {
        return feignOrdenPagoClient.findByEscalaIdAndDocumentoId(escalaId, documentoId);
    }
}
