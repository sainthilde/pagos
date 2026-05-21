package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.adapter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.DocumentClient;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.OrdenPagoSunatClient;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ArchivoResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.DocumentRequestDTO;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.OrdenPagoRequestDTO;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.PaymentMethodResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.response.OrdenPagoResponseDTO;
import org.springframework.core.io.Resource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(MockitoExtension.class)
class FeignRepositoryAdapterTest {

    @Mock
    private DocumentClient documentClient;

    @Mock
    private OrdenPagoSunatClient ordenPagoSunatClient;

    @InjectMocks
    private FeignRepositoryAdapter feignRepositoryAdapter;

    @Test
    void testCreateOrdenPago() {
        OrdenPagoRequestDTO request = new OrdenPagoRequestDTO();
        OrdenPagoResponseDTO response = new OrdenPagoResponseDTO();
        response.setOrdenPagoId(1);

        Mockito.when(ordenPagoSunatClient.createOrdenPago(request)).thenReturn(response);

        OrdenPagoResponseDTO result = feignRepositoryAdapter.createOrdenPago(request);
        assertNotNull(result);
        assertEquals(1, result.getOrdenPagoId());
    }

    @Test
    void testGetArchivo() {
        ArchivoResponse response = new ArchivoResponse();
        response.setNombre("archivo.pdf");

        Mockito.when(ordenPagoSunatClient.getArchivo(10)).thenReturn(response);

        ArchivoResponse result = feignRepositoryAdapter.getArchivo(10);
        assertNotNull(result);
        assertEquals("archivo.pdf", result.getNombre());
    }

    @Test
    void testPostFile() {
        DocumentRequestDTO request = new DocumentRequestDTO();
        request.setNombre("doc.txt");

        Mockito.when(documentClient.postFile(request)).thenReturn("12345");

        String result = feignRepositoryAdapter.postFile(request);
        assertEquals("12345", result);
    }

    @Test
    void testObtenerFile() {
        String ecmId = "ecm123";
        Resource mockResource = Mockito.mock(Resource.class);

        Mockito.when(documentClient.obtenerFile(ecmId)).thenReturn(mockResource);

        Resource result = feignRepositoryAdapter.obtenerFile(ecmId);
        assertNotNull(result);
        assertEquals(mockResource, result);
    }

    @Test
    void testGetPaymentMethods() {
        PaymentMethodResponse method = new PaymentMethodResponse();
        method.setTitulo("Pago en Banco");

        List<PaymentMethodResponse> expected = List.of(method);

        Mockito.when(ordenPagoSunatClient.getPaymentMethods(1, 2)).thenReturn(expected);

        List<PaymentMethodResponse> result = feignRepositoryAdapter.getPaymentMethods(1, 2);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Pago en Banco", result.get(0).getTitulo());
    }
}

