package pe.gob.vuce.cp.sp.pagos.application.usecase;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.ObtenerExcepcionRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ExcepcionesResponse;

class ObtenerExcepcionDeclaracionUseCaseImplTest {

    private ObtenerExcepcionRepositoryPort obtenerExcepcionRepositoryPort;
    private ObtenerExcepcionDeclaracionUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        obtenerExcepcionRepositoryPort = mock(ObtenerExcepcionRepositoryPort.class);
        useCase = new ObtenerExcepcionDeclaracionUseCaseImpl(obtenerExcepcionRepositoryPort);
    }

    @Test
    void testObtenerExcepcionDeclaracion_returnsResponse() {
        // Arrange
        Integer escalaId = 1;
        Integer entidad = 100;

        ExcepcionesResponse.DataException dataException = new ExcepcionesResponse.DataException();
        ExcepcionesResponse expectedResponse = new ExcepcionesResponse();
        expectedResponse.setMeta(null);
        expectedResponse.setData(List.of(dataException));

        when(obtenerExcepcionRepositoryPort.obtenerExcepcionDeclaracion(escalaId, entidad))
                .thenReturn(expectedResponse);

        // Act
        ExcepcionesResponse actualResponse = useCase.obtenerExcepcionDeclaracion(escalaId, entidad);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(1, actualResponse.getData().size());
        assertEquals(null, actualResponse.getData().get(0).getEscalaId());
        assertEquals(null, actualResponse.getData().get(0).getEntidadId());

        verify(obtenerExcepcionRepositoryPort, times(1)).obtenerExcepcionDeclaracion(escalaId, entidad);
    }
}

