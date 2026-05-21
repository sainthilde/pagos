package pe.gob.vuce.cp.sp.pagos.application.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import pe.gob.vuce.cp.sp.pagos.domain.model.OrdenPago;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.FeignRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.ComunesQueryClient;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ArchivoResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.DocumentRequestDTO;
import pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.entity.ListMaestroDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.MasterResponse;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.PARAMETROS_GENERALES;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.COMPONENTE_FILENET;

class UpdateArchivoPDFUseCaseImplTest {

    @Mock
    private FeignRepositoryPort feignRepositoryPort;

    @Mock
    private ComunesQueryClient comunesQueryClient;

    @InjectMocks
    private UpdateArchivoPDFUseCaseImpl updateArchivoPDFUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateArchivoPDF() {
        // Arrange
        Integer ordenPagoVuce = 123;
        OrdenPago ordenPago = new OrdenPago();
        ordenPago.setOrdenPagoId(ordenPagoVuce);

        // Mock del archivo response
        ArchivoResponse archivoResponse = new ArchivoResponse();
        archivoResponse.setContenido("PDF Content");

        // Mock de la respuesta de comunesQueryClient
        ListMaestroDto listMaestroDto = new ListMaestroDto();
        listMaestroDto.setCodigo(COMPONENTE_FILENET);
        listMaestroDto.setDescripcion("CP2");
        List<ListMaestroDto> listaMaestros = Arrays.asList(listMaestroDto);

        MasterResponse<List<ListMaestroDto>> masterResponse = new MasterResponse<>();
        masterResponse.setData(listaMaestros);

        ResponseEntity<MasterResponse<List<ListMaestroDto>>> responseEntity =
                ResponseEntity.ok(masterResponse);

        when(comunesQueryClient.getAllcodeMaster(eq(PARAMETROS_GENERALES)))
                .thenReturn(responseEntity);
        when(feignRepositoryPort.getArchivo(ordenPagoVuce)).thenReturn(archivoResponse);

        String resultEcmStorage = "{\"data\": {\"ecmDocumentoId\": \"12345\"}}";
        when(feignRepositoryPort.postFile(any(DocumentRequestDTO.class))).thenReturn(resultEcmStorage);

        // Act
        OrdenPago result = updateArchivoPDFUseCase.updateArchivoPDF(ordenPagoVuce, ordenPago);

        // Assert
        assertNotNull(result.getFilenetGuid());
        assertEquals("12345", result.getFilenetGuid());
        assertNotNull(result.getFechaGuardadoPdfCpb());
    }

    @Test
    void testGetDocumentRequestDTO() {
        Integer ordenPagoId = 123;
        String componenteFilenet = "CP2";
        ArchivoResponse archivoResponse = new ArchivoResponse();
        archivoResponse.setContenido("PDF Content");

        // Llamar al método estático directamente
        DocumentRequestDTO result = UpdateArchivoPDFUseCaseImpl.getDocumentRequestDTO(
                ordenPagoId, archivoResponse, componenteFilenet);

        assertNotNull(result);
        assertEquals("voucher-123.pdf", result.getNombre());
        assertEquals("PDF Content", result.getFile());
        assertNotNull(result.getData());
    }

    @Test
    void testUpdateArchivoPDF_whenJsonDoesNotHaveData_shouldNotSetFilenetGuidOrFecha() {
        Integer ordenPagoVuce = 123;
        OrdenPago ordenPago = new OrdenPago();
        ordenPago.setOrdenPagoId(ordenPagoVuce);

        // Mock de comunesQueryClient
        mockComunesQueryClient();

        ArchivoResponse archivoResponse = new ArchivoResponse();
        archivoResponse.setContenido("PDF Content");

        when(feignRepositoryPort.getArchivo(ordenPagoVuce)).thenReturn(archivoResponse);
        String resultEcmStorage = "{\"otherKey\": \"someValue\"}";
        when(feignRepositoryPort.postFile(any(DocumentRequestDTO.class))).thenReturn(resultEcmStorage);

        OrdenPago result = updateArchivoPDFUseCase.updateArchivoPDF(ordenPagoVuce, ordenPago);

        assertNull(result.getFilenetGuid());
        assertNull(result.getFechaGuardadoPdfCpb());
    }

    @Test
    void testUpdateArchivoPDF_whenJsonHasDataButNoEcmDoc_shouldNotSetFilenetGuidOrFecha() {
        Integer ordenPagoVuce = 123;
        OrdenPago ordenPago = new OrdenPago();
        ordenPago.setOrdenPagoId(ordenPagoVuce);

        // Mock de comunesQueryClient
        mockComunesQueryClient();

        ArchivoResponse archivoResponse = new ArchivoResponse();
        archivoResponse.setContenido("PDF Content");

        when(feignRepositoryPort.getArchivo(ordenPagoVuce)).thenReturn(archivoResponse);
        String resultEcmStorage = "{\"data\": {\"otherKey\": \"value\"}}";
        when(feignRepositoryPort.postFile(any(DocumentRequestDTO.class))).thenReturn(resultEcmStorage);

        OrdenPago result = updateArchivoPDFUseCase.updateArchivoPDF(ordenPagoVuce, ordenPago);

        assertNull(result.getFilenetGuid());
        assertNull(result.getFechaGuardadoPdfCpb());
    }

    @Test
    void testUpdateArchivoPDF_whenJsonIsInvalid_shouldThrowException() {
        Integer ordenPagoVuce = 123;
        OrdenPago ordenPago = new OrdenPago();
        ordenPago.setOrdenPagoId(ordenPagoVuce);

        // Mock de comunesQueryClient
        mockComunesQueryClient();

        ArchivoResponse archivoResponse = new ArchivoResponse();
        archivoResponse.setContenido("PDF Content");

        when(feignRepositoryPort.getArchivo(ordenPagoVuce)).thenReturn(archivoResponse);
        String resultEcmStorage = "invalid json";
        when(feignRepositoryPort.postFile(any(DocumentRequestDTO.class))).thenReturn(resultEcmStorage);

        assertThrows(org.json.JSONException.class, () -> {
            updateArchivoPDFUseCase.updateArchivoPDF(ordenPagoVuce, ordenPago);
        });
    }

    private void mockComunesQueryClient() {
        ListMaestroDto listMaestroDto = new ListMaestroDto();
        listMaestroDto.setCodigo(COMPONENTE_FILENET);
        listMaestroDto.setDescripcion("CP2");
        List<ListMaestroDto> listaMaestros = Arrays.asList(listMaestroDto);

        MasterResponse<List<ListMaestroDto>> masterResponse = new MasterResponse<>();
        masterResponse.setData(listaMaestros);

        ResponseEntity<MasterResponse<List<ListMaestroDto>>> responseEntity =
                ResponseEntity.ok(masterResponse);

        when(comunesQueryClient.getAllcodeMaster(eq(PARAMETROS_GENERALES)))
                .thenReturn(responseEntity);
    }
}