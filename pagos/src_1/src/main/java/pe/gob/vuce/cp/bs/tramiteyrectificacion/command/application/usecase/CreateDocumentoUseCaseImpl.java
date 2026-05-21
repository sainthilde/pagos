package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.application.usecase;

import org.springframework.stereotype.Component;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.CreateDocumentoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.CreateDocumentoUseCase;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.DocumentoEcmPort;

/**
 * Implementación del caso de uso para la creación de documentos.
 * 
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 21/08/2024
 */
@Component
public class CreateDocumentoUseCaseImpl implements CreateDocumentoUseCase {

    private final DocumentoEcmPort documentoEcmPort;

    /**
     * Constructor de la clase CreateDocumentoUseCaseImpl.
     * 
     * @param documentoEcmPort Puerto para la comunicación con el ECM para registrar
     *                         documentos.
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Fernando Tanta
     * @date 21/08/2024
     */
    public CreateDocumentoUseCaseImpl(DocumentoEcmPort documentoEcmPort) {
        this.documentoEcmPort = documentoEcmPort;
    }

    /**
     * Registra un documento en el ECM utilizando el modelo proporcionado.
     * 
     * @param documentoModel Modelo que contiene los datos del documento a
     *                       registrar.
     * @return El GUID del documento registrado en el ECM.
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Fernando Tanta
     * @date 21/08/2024
     */
    @Override
    public String registrarDocumento(CreateDocumentoModel documentoModel) {
        return documentoEcmPort.registrarDocumento(documentoModel);
    }
}
