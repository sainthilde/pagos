package pe.gob.vuce.cp.sp.pagos.domain.port.in;

import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.TasaResponse;

@Component
public interface CreateTasaSunatUseCase {
    TasaResponse.Tasa obtenerTasa(Integer entidadId, String idComponente, String textSearch);
}
