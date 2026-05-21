package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.ecm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants.ErrorCodes;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants.MapDocumentEcmConstant;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.DocumentRequestDTO;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.exceptions.BusinessError;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.CommonResponse;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.CreateDocumentoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.Mensaje;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.Meta;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.feign.config.client.impl.FeignDocumentClient;

public class DocumentEcmFilenetAdapterTest {

    @Mock
    private FeignDocumentClient feignDocumentClient;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private DocumentEcmFilenetAdapter documentEcmFilenetAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarDocumento_success() throws Exception {
        // Arrange
        CreateDocumentoModel documentoModel = new CreateDocumentoModel();
        documentoModel.setBytes("testBytes");
        documentoModel.setNombre("testNombre");
        documentoModel.setContentType("application/pdf");

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put(MapDocumentEcmConstant.KEY_ECM_DOCUMENTO_ID, "doc123");

        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setData(responseMap);

        Meta meta = new Meta();

        meta.setResult("success");
        meta.setCantidadRegistros(1);

        Mensaje mensaje = new Mensaje();

        mensaje.setCodigo("0000");
        mensaje.setMessage("Success");
        mensaje.setTipo("INFO");

        meta.setMensajes(List.of(mensaje));

        commonResponse.setMeta(meta);

        // Mocking Feign Client response and ObjectMapper behavior
        when(feignDocumentClient.postFile(any(DocumentRequestDTO.class)))
                .thenReturn(commonResponse);

        when(objectMapper.convertValue(any(), any(TypeReference.class)))
                .thenReturn(responseMap);

        // Act
        String documentId = documentEcmFilenetAdapter.registrarDocumento(documentoModel);

        // Assert
        assertEquals("doc123", documentId);
        verify(feignDocumentClient, times(1)).postFile(any(DocumentRequestDTO.class));
    }

    @Test
    void registrarDocumento_failure() throws Exception {
        // Arrange
        CreateDocumentoModel documentoModel = new CreateDocumentoModel();
        documentoModel.setBytes("testBytes");
        documentoModel.setNombre("testNombre");
        documentoModel.setContentType("application/pdf");

        when(feignDocumentClient.postFile(any(DocumentRequestDTO.class)))
                .thenThrow(new RuntimeException("Feign client error"));

        // Act & Assert
        BusinessError exception = assertThrows(BusinessError.class, () -> {
            documentEcmFilenetAdapter.registrarDocumento(documentoModel);
        });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getHttpStatus());
        assertEquals(ErrorCodes.INTERNAL_SERVER_ERROR, exception.getErrorCode());
        verify(feignDocumentClient, times(1)).postFile(any(DocumentRequestDTO.class));
    }

}
