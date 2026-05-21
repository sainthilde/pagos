package pe.gob.vuce.cp.sp.pagos.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.ObtenerExcepcionDeclaracionUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.ObtenerExcepcionPatenteUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.ObtenerExcepcionUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.ObtenerExcepcionZarpeUseCase;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ExcepcionesDueResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ExcepcionesResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.Meta;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import java.util.Collections;

class ObtenerExcepcionServiceTest {

    private ObtenerExcepcionDeclaracionUseCase obtenerExcepcionDeclaracionUseCase;
    private ObtenerExcepcionPatenteUseCase obtenerExcepcionPatenteUseCase;
    private ObtenerExcepcionUseCase obtenerExcepcionUseCase;
    private ObtenerExcepcionZarpeUseCase obtenerExcepcionZarpeUseCase;
    private ObtenerExcepcionService service;

    @BeforeEach
    void setUp() {
        obtenerExcepcionDeclaracionUseCase = mock(ObtenerExcepcionDeclaracionUseCase.class);
        obtenerExcepcionPatenteUseCase = mock(ObtenerExcepcionPatenteUseCase.class);
        obtenerExcepcionUseCase = mock(ObtenerExcepcionUseCase.class);
        obtenerExcepcionZarpeUseCase = mock(ObtenerExcepcionZarpeUseCase.class);

        service = new ObtenerExcepcionService(
                obtenerExcepcionDeclaracionUseCase,
                obtenerExcepcionPatenteUseCase,
                obtenerExcepcionUseCase,
                obtenerExcepcionZarpeUseCase
        );
    }

    @Test
    void testObtenerExcepcionDeclaracion() {
        Integer escalaId = 1, entidadId = 10;
        ExcepcionesResponse expected = new ExcepcionesResponse(new Meta(), Collections.emptyList());

        when(obtenerExcepcionDeclaracionUseCase.obtenerExcepcionDeclaracion(escalaId, entidadId)).thenReturn(expected);

        ExcepcionesResponse result = service.obtenerExcepcionDeclaracion(escalaId, entidadId);

        assertNotNull(result);
        assertEquals(expected, result);
        verify(obtenerExcepcionDeclaracionUseCase).obtenerExcepcionDeclaracion(escalaId, entidadId);
    }

    @Test
    void testObtenerExcepcionPatente() {
        Integer escalaId = 2, entidadId = 20;
        ExcepcionesDueResponse expected = new ExcepcionesDueResponse(new Meta(), Collections.emptyList());

        when(obtenerExcepcionPatenteUseCase.obtenerExcepcionPatente(escalaId, entidadId)).thenReturn(expected);

        ExcepcionesDueResponse result = service.obtenerExcepcionPatente(escalaId, entidadId);

        assertNotNull(result);
        assertEquals(expected, result);
        verify(obtenerExcepcionPatenteUseCase).obtenerExcepcionPatente(escalaId, entidadId);
    }

    @Test
    void testObtenerExcepcion() {
        Integer escalaId = 3, entidadId = 30;
        ExcepcionesResponse expected = new ExcepcionesResponse(new Meta(), Collections.emptyList());

        when(obtenerExcepcionUseCase.obtenerExcepcion(escalaId, entidadId)).thenReturn(expected);

        ExcepcionesResponse result = service.obtenerExcepcion(escalaId, entidadId);

        assertNotNull(result);
        assertEquals(expected, result);
        verify(obtenerExcepcionUseCase).obtenerExcepcion(escalaId, entidadId);
    }

    @Test
    void testObtenerExcepcionZarpe() {
        Integer escalaId = 4, entidadId = 40;
        ExcepcionesDueResponse expected = new ExcepcionesDueResponse(new Meta(), Collections.emptyList());

        when(obtenerExcepcionZarpeUseCase.obtenerExcepcionZarpe(escalaId, entidadId)).thenReturn(expected);

        ExcepcionesDueResponse result = service.obtenerExcepcionZarpe(escalaId, entidadId);

        assertNotNull(result);
        assertEquals(expected, result);
        verify(obtenerExcepcionZarpeUseCase).obtenerExcepcionZarpe(escalaId, entidadId);
    }
}
