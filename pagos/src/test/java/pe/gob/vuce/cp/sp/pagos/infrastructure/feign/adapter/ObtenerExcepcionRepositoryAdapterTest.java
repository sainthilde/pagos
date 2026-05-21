package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.adapter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.ComunesQueryClient;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ExcepcionesDueResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ExcepcionesResponse;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ObtenerExcepcionRepositoryAdapterTest {

    @Mock
    private ComunesQueryClient comunesQueryClient;

    @InjectMocks
    private ObtenerExcepcionRepositoryAdapter adapter;

    @Test
    void testObtenerExcepcionDeclaracion() {
        Integer escalaId = 1, entidad = 100;

        ExcepcionesResponse.DataException data = new ExcepcionesResponse.DataException();
        data.setEscalaId(escalaId);
        data.setEntidadId(entidad);

        ExcepcionesResponse response = new ExcepcionesResponse(null, List.of(data));

        when(comunesQueryClient.obtenerExcepcionDeclaracion(escalaId, entidad)).thenReturn(response);

        ExcepcionesResponse result = adapter.obtenerExcepcionDeclaracion(escalaId, entidad);

        assertNotNull(result);
        assertEquals(1, result.getData().size());
        assertEquals(escalaId, result.getData().get(0).getEscalaId());
        verify(comunesQueryClient).obtenerExcepcionDeclaracion(escalaId, entidad);
    }

    @Test
    void testObtenerExcepcionPatente() {
        Integer escalaId = 2, entidad = 200;

        ExcepcionesDueResponse.DataException data = new ExcepcionesDueResponse.DataException();
        data.setEscalaId(escalaId);
        data.setEntidadId(entidad);

        ExcepcionesDueResponse response = new ExcepcionesDueResponse(null, List.of(data));

        when(comunesQueryClient.obtenerExcepcionPatente(escalaId, entidad)).thenReturn(response);

        ExcepcionesDueResponse result = adapter.obtenerExcepcionPatente(escalaId, entidad);

        assertNotNull(result);
        assertEquals(1, result.getData().size());
        assertEquals(entidad, result.getData().get(0).getEntidadId());
        verify(comunesQueryClient).obtenerExcepcionPatente(escalaId, entidad);
    }

    @Test
    void testObtenerExcepcion() {
        Integer escalaId = 3, entidad = 300;

        ExcepcionesResponse.DataException data = new ExcepcionesResponse.DataException();
        data.setEntidadId(entidad);

        ExcepcionesResponse response = new ExcepcionesResponse(null, List.of(data));

        when(comunesQueryClient.obtenerExcepcion(escalaId, entidad)).thenReturn(response);

        ExcepcionesResponse result = adapter.obtenerExcepcion(escalaId, entidad);

        assertNotNull(result);
        assertEquals(entidad, result.getData().get(0).getEntidadId());
        verify(comunesQueryClient).obtenerExcepcion(escalaId, entidad);
    }

    @Test
    void testObtenerExcepcionZarpe() {
        Integer escalaId = 4, entidad = 400;

        ExcepcionesDueResponse.DataException data = new ExcepcionesDueResponse.DataException();
        data.setEscalaId(escalaId);

        ExcepcionesDueResponse response = new ExcepcionesDueResponse(null, List.of(data));

        when(comunesQueryClient.obtenerExcepcionZarpe(escalaId, entidad)).thenReturn(response);

        ExcepcionesDueResponse result = adapter.obtenerExcepcionZarpe(escalaId, entidad);

        assertNotNull(result);
        assertEquals(escalaId, result.getData().get(0).getEscalaId());
        verify(comunesQueryClient).obtenerExcepcionZarpe(escalaId, entidad);
    }
}

