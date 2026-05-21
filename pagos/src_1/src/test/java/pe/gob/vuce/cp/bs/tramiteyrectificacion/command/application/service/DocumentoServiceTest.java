package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants.ErrorCodes;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.DocumentoResponseDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.exceptions.BusinessError;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.CreateDocumentoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.CreateDocumentoUseCase;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;

public class DocumentoServiceTest {

    @Mock
    private CreateDocumentoUseCase createDocumentoUseCase;

    @Mock
    private MultipartFile file;

    @InjectMocks
    private DocumentoService documentoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateDocumentSuccess() throws IOException {
        // Arrange
        byte[] fileContent = "test content".getBytes();
        when(file.getBytes()).thenReturn(fileContent);
        when(file.getContentType()).thenReturn("application/pdf");
        when(file.getOriginalFilename()).thenReturn("test.pdf");
        when(createDocumentoUseCase.registrarDocumento(any(CreateDocumentoModel.class))).thenReturn("mockedGuid");

        // Act
        DocumentoResponseDto response = documentoService.create(file);

        // Assert
        assertNotNull(response);
        assertEquals("mockedGuid", response.getFilenetGuid());

        verify(createDocumentoUseCase, times(1)).registrarDocumento(any(CreateDocumentoModel.class));
    }

    @Test
    void testCreateDocumentFailure() throws IOException {
        // Arrange
        when(file.getBytes()).thenThrow(new IOException("File read error"));

        // Act & Assert
        BusinessError exception = assertThrows(BusinessError.class, () -> {
            documentoService.create(file);
        });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getHttpStatus());
        assertEquals(ErrorCodes.INTERNAL_SERVER_ERROR, exception.getErrorCode());
        assertTrue(exception.getMessageParams().isEmpty());
        assertEquals("File read error", exception.getMessage());

        verify(createDocumentoUseCase, never()).registrarDocumento(any(CreateDocumentoModel.class));
    }

}
