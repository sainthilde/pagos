package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.DocumentoModel;

/**
 * Puerto de salida para las operaciones de persistencia de la entidad
 * documento.
 * Proporciona la interfaz para guardar y buscar tramites en el repositorio.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 22/08/2024
 */
@Component
public interface DocumentoRepositoryPort {
    /**
     * Obtiene el acronimo de un documento en el repositorio.
     *
     * @param id El identificador del documento
     * @return Un objeto documento
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Fernando Tanta
     * @date 22/08/2024
     */
    Optional<DocumentoModel> findById(Integer id);

    List<DocumentoModel> findByDescAcronimoIn(List<String> acronimos);
}
