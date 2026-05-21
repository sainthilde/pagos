package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in;

import java.util.List;
import java.util.Optional;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.DocumentoModel;

/**
 * Caso de uso para obtener un documento.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 22/08/2024
 */
public interface ObtenerDocumentoUseCase {

    /**
     * Obtener un documento
     *
     * @param id Identificador para obtener el documento.
     * @return El documento de la base de datos.
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Fernando Tanta
     * @date 22/08/2024
     */
    Optional<DocumentoModel> findById(Integer id);

    List<DocumentoModel> findByDescAcronimoIn(List<String> acronimos);
}
