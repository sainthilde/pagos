package pe.gob.vuce.cp.sp.pagos.application.service;

import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.ObtenerFileFeignUseCase;

/**

 * @author Mateo Huancho
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Service
@AllArgsConstructor
public class ObtenerFileService {

    private final ObtenerFileFeignUseCase obtenerFileFeignUseCase;

   public Resource getDocument(String filenetGui){
       return obtenerFileFeignUseCase.obtenerFile(filenetGui);
    }
}
