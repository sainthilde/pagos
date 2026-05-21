package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.ecm;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants.ErrorCodes;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants.MapDocumentEcmConstant;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.DocumentRequestDTO;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.exceptions.BusinessError;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.CreateDocumentoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.DocumentoEcmPort;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.feign.config.client.impl.FeignDocumentClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementación del puerto de salida {@link DocumentoEcmPort} que maneja la integración con el
 * sistema de gestión documental ECM (Enterprise Content Management) utilizando el cliente Feign.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 21/08/2024
 */
@Component
@AllArgsConstructor
public class DocumentEcmFilenetAdapter implements DocumentoEcmPort {

    private final FeignDocumentClient feignDocumentClient;
    private final ObjectMapper objectMapper;


    /**
     * Registra un documento en el sistema de gestión documental ECM.
     *
     * @param documentoModel El modelo que contiene los datos del documento a registrar.
     * @return El ID del documento registrado en el ECM.
     * @throws BusinessError en caso de error durante el registro del documento.
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Fernando Tanta
     * @date 21/08/2024
     */
    @Override
    public String registrarDocumento(CreateDocumentoModel documentoModel) {
        try {
            Map<String, Object> mapaRequest = new HashMap<>();
            mapaRequest.put(MapDocumentEcmConstant.KEY_COMPONENTE, MapDocumentEcmConstant.VALUE_COMPONENTE);
            mapaRequest.put(MapDocumentEcmConstant.KEY_OPCION, MapDocumentEcmConstant.VALUE_OPCION);
            mapaRequest.put(MapDocumentEcmConstant.KEY_FOLDER_EXTRAS, MapDocumentEcmConstant.PATH_TRAMITE);

            Map<String, Object> propiedades = new HashMap<>();
            propiedades.put(MapDocumentEcmConstant.KEY_ADJUNTO_ID, MapDocumentEcmConstant.VALUE_ADJUNTO_ID);
            propiedades.put(MapDocumentEcmConstant.KEY_ADJUNTO_TIPO, documentoModel.getContentType());

            mapaRequest.put(MapDocumentEcmConstant.KEY_PROPIEDADES, propiedades);

            DocumentRequestDTO documentRequestDTO = DocumentRequestDTO.builder()
                    .nombre(documentoModel.getNombre())
                    .data(mapaRequest)
                    .file(documentoModel.getBytes())
                    .build();

            Map<String, Object> responseMap = objectMapper.convertValue(
                    feignDocumentClient.postFile(documentRequestDTO).getData(),
                    new TypeReference<Map<String, Object>>() {});

            return responseMap.get(MapDocumentEcmConstant.KEY_ECM_DOCUMENTO_ID).toString();

        } catch (Exception ex) {
            throw new BusinessError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCodes.INTERNAL_SERVER_ERROR, List.of(),
                    ex.getMessage());
        }
    }
}
