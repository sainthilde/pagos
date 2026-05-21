package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.application.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants.ErrorCodes;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.DocumentoResponseDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.exceptions.BusinessError;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.CreateDocumentoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.CreateDocumentoUseCase;

/**
 * Servicio encargado de gestionar la creación de documentos en el sistema.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 21/08/2024
 */
@Service
public class DocumentoService {

    private final CreateDocumentoUseCase createDocumentoUseCase;

    /**
     * Constructor del servicio DocumentoService.
     *
     * @param createDocumentoUseCase Caso de uso para la creación de documentos.
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Fernando Tanta
     * @date 21/08/2024
     */
    public DocumentoService(CreateDocumentoUseCase createDocumentoUseCase) {
        this.createDocumentoUseCase = createDocumentoUseCase;
    }

    /**
     * Crea un documento a partir de un archivo recibido.
     *
     * @param file Archivo recibido que contiene el documento a crear.
     * @return DocumentoResponseDto con la información del documento creado,
     *         incluyendo su GUID de FileNet.
     * @throws BusinessError si ocurre un error durante la creación del documento.
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Fernando Tanta
     * @date 21/08/2024
     */
    public DocumentoResponseDto create(MultipartFile file) {
        try {
            // Convierte el archivo a bytes y lo codifica en Base64.
            byte[] sourceBytes = file.getBytes();
            String encodedString = java.util.Base64.getEncoder().encodeToString(sourceBytes);

            // Crea un modelo de documento con los datos obtenidos del archivo.
            CreateDocumentoModel documentoModel = new CreateDocumentoModel();
            documentoModel.setBytes(encodedString);
            documentoModel.setContentType(file.getContentType());
            documentoModel.setNombre(file.getOriginalFilename());

            // Registra el documento en FileNet y obtiene su GUID.
            String filenetGuid = createDocumentoUseCase.registrarDocumento(documentoModel);

            // Crea el DTO de respuesta con el GUID del documento registrado.
            DocumentoResponseDto documentoResponseDto = new DocumentoResponseDto();
            documentoResponseDto.setFilenetGuid(filenetGuid);

            return documentoResponseDto;
        } catch (Exception e) {
            // Lanza un error de negocio en caso de que ocurra una excepción.
            throw new BusinessError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCodes.INTERNAL_SERVER_ERROR, List.of(),
                    e.getMessage());
        }
    }
}
