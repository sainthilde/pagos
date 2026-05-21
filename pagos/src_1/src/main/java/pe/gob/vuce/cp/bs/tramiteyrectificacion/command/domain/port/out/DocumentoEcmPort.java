package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.CreateDocumentoModel;

/**
 * Puerto de salida para la integración con el sistema de gestión documental ECM.
 * Proporciona la interfaz para registrar documentos en el ECM.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 21/08/2024
 */

public interface DocumentoEcmPort {

    /**
     * Registra un documento en el sistema de gestión documental ECM.
     *
     * @param documentoModel El modelo que contiene los datos del documento a registrar.
     * @return El identificador del documento registrado en el ECM.
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Fernando Tanta
     * @date 21/08/2024
     */
    String registrarDocumento(CreateDocumentoModel documentoModel);
}
