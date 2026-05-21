package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.application.usecase;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.DocumentoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.ObtenerDocumentoUseCase;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.DocumentoRepositoryPort;

/**
 * Implementación del caso de uso obtener un documento.
 * 
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 22/08/2024
 */
@AllArgsConstructor
@Component
public class ObtenerDocumentoUseCaseImpl implements ObtenerDocumentoUseCase {

    private final DocumentoRepositoryPort documentoRepositoryPort;

    @Override
    public Optional<DocumentoModel> findById(Integer id) {
        return documentoRepositoryPort.findById(id);
    }

    @Override
    public List<DocumentoModel> findByDescAcronimoIn(List<String> acronimos) {
        return documentoRepositoryPort.findByDescAcronimoIn(acronimos);
    }
}
