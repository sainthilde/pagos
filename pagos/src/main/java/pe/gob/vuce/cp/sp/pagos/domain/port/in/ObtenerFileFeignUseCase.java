package pe.gob.vuce.cp.sp.pagos.domain.port.in;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public interface ObtenerFileFeignUseCase {
    Resource obtenerFile(String ecmDocumentoId);
}
