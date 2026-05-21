package pe.gob.vuce.cp.sp.pagos.application.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.CreateTasaSunatUseCase;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.TasaResponse;

@Service
@AllArgsConstructor
public class TasaService {

    private final CreateTasaSunatUseCase createTasaSunatUseCase;

    public TasaResponse.Tasa obtenerTasa(Integer entidadId, String idComponente, String textSearch) {
        return createTasaSunatUseCase.obtenerTasa(entidadId, idComponente, textSearch);
    }
}
