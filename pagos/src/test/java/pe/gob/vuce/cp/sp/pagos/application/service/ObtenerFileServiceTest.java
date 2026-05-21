package pe.gob.vuce.cp.sp.pagos.application.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.ObtenerFileFeignUseCase;

class ObtenerFileServiceTest {

    private ObtenerFileFeignUseCase obtenerFileFeignUseCase;
    private ObtenerFileService service;

    @BeforeEach
    void setUp() {
        obtenerFileFeignUseCase = Mockito.mock(ObtenerFileFeignUseCase.class);
        service = new ObtenerFileService(obtenerFileFeignUseCase);
    }

    @Test
    void testGetDocument_returnsResource() {
        String filenetGui = "file-guid-123";

        // Crear un Resource de ejemplo, por ejemplo ByteArrayResource
        byte[] data = "contenido del archivo".getBytes();
        Resource expectedResource = new ByteArrayResource(data);

        // Configurar mock para que retorne el recurso esperado
        when(obtenerFileFeignUseCase.obtenerFile(filenetGui)).thenReturn(expectedResource);

        // Invocar el método
        Resource actualResource = service.getDocument(filenetGui);

        // Validar resultado
        assertNotNull(actualResource);
        assertEquals(expectedResource, actualResource);

        // Verificar que el método mock fue llamado con el argumento correcto
        verify(obtenerFileFeignUseCase, times(1)).obtenerFile(filenetGui);
    }
}
