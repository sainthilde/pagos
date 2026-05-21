package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.application.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.CreateDocumentoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.DocumentoEcmPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

public class CreateDocumentoUseCaseImplTest {

    @Mock
    private DocumentoEcmPort documentoEcmPort;

    @InjectMocks
    private CreateDocumentoUseCaseImpl createDocumentoUseCase;

    private CreateDocumentoModel documentoModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        documentoModel = new CreateDocumentoModel();
        documentoModel.setBytes("dGVzdCBjb250ZW50"); // Base64 encoded "test content"
        documentoModel.setNombre("test.pdf");
        documentoModel.setContentType("application/pdf");
    }

    @Test
    void testRegistrarDocumentoSuccess() {
        // Arrange
        String expectedGuid = "mockedGuid";
        when(documentoEcmPort.registrarDocumento(any(CreateDocumentoModel.class))).thenReturn(expectedGuid);

        // Act
        String actualGuid = createDocumentoUseCase.registrarDocumento(documentoModel);

        // Assert
        assertEquals(expectedGuid, actualGuid);
        verify(documentoEcmPort, times(1)).registrarDocumento(any(CreateDocumentoModel.class));
    }

    @Test
    void testRegistrarDocumentoFailure() {
        // Arrange
        when(documentoEcmPort.registrarDocumento(any(CreateDocumentoModel.class)))
                .thenThrow(new RuntimeException("Error during document registration"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            createDocumentoUseCase.registrarDocumento(documentoModel);
        });

        assertEquals("Error during document registration", exception.getMessage());
        verify(documentoEcmPort, times(1)).registrarDocumento(any(CreateDocumentoModel.class));
    }

}
