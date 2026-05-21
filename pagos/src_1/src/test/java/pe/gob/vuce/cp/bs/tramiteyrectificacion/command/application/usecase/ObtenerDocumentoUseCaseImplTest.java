package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.DocumentoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.DocumentoRepositoryPort;

public class ObtenerDocumentoUseCaseImplTest {

    @InjectMocks
    private ObtenerDocumentoUseCaseImpl obtenerDocumentoUseCase;

    @Mock
    private DocumentoRepositoryPort documentoRepositoryPort;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindById_DocumentoExists() {
        // Configuración del mock
        DocumentoModel documento = new DocumentoModel();
        documento.setDocumentoId(1);
        when(documentoRepositoryPort.findById(1)).thenReturn(Optional.of(documento));

        // Ejecución del método
        Optional<DocumentoModel> result = obtenerDocumentoUseCase.findById(1);

        // Verificación
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getDocumentoId());
        verify(documentoRepositoryPort, times(1)).findById(1);
    }

    @Test
    public void testFindById_DocumentoDoesNotExist() {
        // Configuración del mock para que retorne un Optional vacío
        when(documentoRepositoryPort.findById(1)).thenReturn(Optional.empty());

        // Ejecución del método
        Optional<DocumentoModel> result = obtenerDocumentoUseCase.findById(1);

        // Verificación
        assertFalse(result.isPresent());
        verify(documentoRepositoryPort, times(1)).findById(1);
    }

    @Test
    public void testFindByDescAcronimoIn() {
        // Configuración del mock
        List<String> acronimos = Arrays.asList("ABC", "XYZ");
        DocumentoModel documento1 = new DocumentoModel();
        documento1.setDescAcronimo("ABC");
        DocumentoModel documento2 = new DocumentoModel();
        documento2.setDescAcronimo("XYZ");

        when(documentoRepositoryPort.findByDescAcronimoIn(acronimos)).thenReturn(Arrays.asList(documento1, documento2));

        // Ejecución del método
        List<DocumentoModel> result = obtenerDocumentoUseCase.findByDescAcronimoIn(acronimos);

        // Verificación
        assertEquals(2, result.size());
        assertEquals("ABC", result.get(0).getDescAcronimo());
        assertEquals("XYZ", result.get(1).getDescAcronimo());
        verify(documentoRepositoryPort, times(1)).findByDescAcronimoIn(acronimos);
    }

    @Test
    public void testFindByDescAcronimoIn_EmptyResult() {
        // Configuración del mock para que retorne una lista vacía
        List<String> acronimos = Arrays.asList("DEF");
        when(documentoRepositoryPort.findByDescAcronimoIn(acronimos)).thenReturn(Arrays.asList());

        // Ejecución del método
        List<DocumentoModel> result = obtenerDocumentoUseCase.findByDescAcronimoIn(acronimos);

        // Verificación
        assertTrue(result.isEmpty());
        verify(documentoRepositoryPort, times(1)).findByDescAcronimoIn(acronimos);
    }

}
