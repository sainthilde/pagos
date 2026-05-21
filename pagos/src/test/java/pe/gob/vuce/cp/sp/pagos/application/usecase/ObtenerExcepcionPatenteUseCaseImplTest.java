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

class ObtenerExcepcionPatenteUseCaseImplTest {

    private ObtenerExcepcionRepositoryPort obtenerExcepcionRepositoryPort;
    private ObtenerExcepcionPatenteUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        obtenerExcepcionRepositoryPort = mock(ObtenerExcepcionRepositoryPort.class);
        useCase = new ObtenerExcepcionPatenteUseCaseImpl(obtenerExcepcionRepositoryPort);
    }

    @Test
    void testObtenerExcepcionPatente_returnsResponse() {
        // Arrange
        Integer escalaId = 1;
        Integer entidad = 10;

        ExcepcionesDueResponse.DataException dataException = new ExcepcionesDueResponse.DataException();
        ExcepcionesDueResponse expectedResponse = new ExcepcionesDueResponse();
        expectedResponse.setMeta(null);
        expectedResponse.setData(List.of(dataException));

        when(obtenerExcepcionRepositoryPort.obtenerExcepcionPatente(escalaId, entidad))
                .thenReturn(expectedResponse);

        // Act
        ExcepcionesDueResponse actualResponse = useCase.obtenerExcepcionPatente(escalaId, entidad);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(1, actualResponse.getData().size());
        assertEquals(null, actualResponse.getData().get(0).getEscalaId());
        assertEquals(null, actualResponse.getData().get(0).getEntidadId());
        assertEquals(null, actualResponse.getData().get(0).getDue());

        verify(obtenerExcepcionRepositoryPort, times(1)).obtenerExcepcionPatente(escalaId, entidad);
    }
}
