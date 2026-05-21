package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.application.service.DocumentoService;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.ApiResponseDocumentoResponseDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.DocumentoResponseDto;

public class DocumentoControllerTest {

    @Mock
    private DocumentoService documentoService;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private DocumentoController documentoController;

    private DocumentoResponseDto documentoResponseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        documentoResponseDto = new DocumentoResponseDto();
        documentoResponseDto.setFilenetGuid("mockedGuid");
    }

    @Test
    void testCreateDocumento() {
        // Arrange
        when(documentoService.create(any(MultipartFile.class)))
                .thenReturn(documentoResponseDto);

        // Act
        ResponseEntity<ApiResponseDocumentoResponseDto> response = documentoController.createDocumento(multipartFile);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getData().size());
        assertEquals("mockedGuid", response.getBody().getData().get(0).getFilenetGuid());

        verify(documentoService, times(1)).create(any(MultipartFile.class));
    }

}
