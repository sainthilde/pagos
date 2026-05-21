package pe.gob.vuce.cp.sp.pagos.domain.port.out;

import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.TasaResponse;

@Component
public interface TasaSunatRepositoryPort {
    TasaResponse.Tasa obtenerTasa(Integer entidadId, String idComponente, String textSearch);
}
