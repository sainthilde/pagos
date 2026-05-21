package pe.gob.vuce.cp.sp.pagos.application.usecase;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.CreateTasaSunatUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.TasaSunatRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.TasaResponse;

@Component
@AllArgsConstructor
public class CreateTasaSunatUseCaseImpl implements CreateTasaSunatUseCase {

    private final TasaSunatRepositoryPort tasaSunatRepositoryPort;

    @Override
    public TasaResponse.Tasa obtenerTasa(Integer entidadId, String idComponente, String textSearch) {
       return tasaSunatRepositoryPort.obtenerTasa(entidadId, idComponente, textSearch);
    }
}
