package pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.FeignComunesCommandClient;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.CommonResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.SeguimientoRequestDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

 class FeignComunesCommandClientAdapterTest {

    @Mock
    private FeignComunesCommandClient feignComunesCommandClient;

    @InjectMocks
    private FeignComunesCommandClientAdapter adapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveEscalaSeguimiento_Success() {
        SeguimientoRequestDto requestDto = new SeguimientoRequestDto();
        String user = "testUser";
        CommonResponse expectedResponse = new CommonResponse();

        // Mockeamos la respuesta del cliente Feign
        when(feignComunesCommandClient.saveEscalaSeguimiento(requestDto, user)).thenReturn(expectedResponse);

        // Llamamos al método que estamos probando
        CommonResponse actualResponse = adapter.saveEscalaSeguimiento(requestDto, user);

        // Verificamos el resultado
        assertEquals(expectedResponse, actualResponse);

        // Verificamos que el mock fue llamado correctamente
        verify(feignComunesCommandClient, times(1)).saveEscalaSeguimiento(requestDto, user);
    }
}