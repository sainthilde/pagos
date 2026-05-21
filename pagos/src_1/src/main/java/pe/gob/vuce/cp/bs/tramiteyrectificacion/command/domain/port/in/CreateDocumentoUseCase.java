package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in;


import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.CreateDocumentoModel;

/**
 * Caso de uso para la creación de documentos. Proporciona una interfaz para
 * registrar un documento en el sistema.
 *
 * @project cp-api-bs-fichasanitaria-command
 * @autor Fernando Tanta
 * @date 21/08/2024
 */
public interface CreateDocumentoUseCase {

    /**
     * Registra un documento en el sistema.
     *
     * @param documentoModel El modelo que contiene los datos del documento a
     *                       registrar.
     * @return El identificador del documento registrado.
     * @project cp-api-bs-fichasanitaria-command
     * @autor Fernando Tanta
     * @date 21/08/2024
     */
    String registrarDocumento(CreateDocumentoModel documentoModel);
}
