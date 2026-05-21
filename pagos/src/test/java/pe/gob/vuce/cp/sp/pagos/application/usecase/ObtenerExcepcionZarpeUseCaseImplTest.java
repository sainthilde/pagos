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
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ExcepcionesDueResponse;

class ObtenerExcepcionZarpeUseCaseImplTest {

    private ObtenerExcepcionRepositoryPort obtenerExcepcionRepositoryPort;
    private ObtenerExcepcionZarpeUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        obtenerExcepcionRepositoryPort = mock(ObtenerExcepcionRepositoryPort.class);
        useCase = new ObtenerExcepcionZarpeUseCaseImpl(obtenerExcepcionRepositoryPort);
    }

    @Test
    void testObtenerExcepcionZarpe_returnsResponse() {
        // Arrange
        Integer escalaId = 1;
        Integer entidad = 100;

        ExcepcionesDueResponse.DataException dataException = new ExcepcionesDueResponse.DataException(
);
        ExcepcionesDueResponse expectedResponse = new ExcepcionesDueResponse();
        expectedResponse.setMeta(null); // o un mock si tienes
        expectedResponse.setData(List.of(dataException));

        when(obtenerExcepcionRepositoryPort.obtenerExcepcionZarpe(escalaId, entidad))
                .thenReturn(expectedResponse);

        // Act
        ExcepcionesDueResponse actualResponse = useCase.obtenerExcepcionZarpe(escalaId, entidad);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(1, actualResponse.getData().size());
        assertEquals(null, actualResponse.getData().get(0).getEscalaId());
        assertEquals(null, actualResponse.getData().get(0).getEntidadId());
        assertEquals(null, actualResponse.getData().get(0).getDue());

        verify(obtenerExcepcionRepositoryPort, times(1)).obtenerExcepcionZarpe(escalaId, entidad);
    }
}
