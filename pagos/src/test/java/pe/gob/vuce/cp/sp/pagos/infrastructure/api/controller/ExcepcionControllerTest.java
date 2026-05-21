package pe.gob.vuce.cp.sp.pagos.infrastructure.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import pe.gob.vuce.cp.sp.pagos.application.service.ObtenerExcepcionService;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.response.ApiResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ExcepcionesDueResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ExcepcionesResponse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.util.Collections;
import static org.springframework.http.HttpStatus.OK;

 class ExcepcionControllerTest {

    @Mock
    private ObtenerExcepcionService obtenerExcepcionService;

    @InjectMocks
    private ExcepcionController excepcionController;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testObtenerExcepciones() {
        // Arrange
        Integer escalaId = 1;
        Integer entidad = 1;
        ExcepcionesResponse expectedResponse = createExcepcionesResponse();
        when(obtenerExcepcionService.obtenerExcepcion(escalaId, entidad)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<ApiResponse> response = excepcionController.obtenerExcepciones(escalaId, entidad);

        // Assert
        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
    }

    @Test
     void testObtenerExcepciones_EmptyResponse() {
        // Arrange
        Integer escalaId = 1;
        Integer entidad = 1;
        when(obtenerExcepcionService.obtenerExcepcion(escalaId, entidad)).thenReturn(new ExcepcionesResponse());
        ResponseEntity<ApiResponse> response = excepcionController.obtenerExcepciones(escalaId, entidad);
        assertNotNull(response);
    }

    @Test
     void testObtenerExcepcionesZarpe() {
        // Arrange
        Integer escalaId = 1;
        Integer entidad = 1;
        ExcepcionesDueResponse expectedResponse = createExcepcionesDueResponse();
        when(obtenerExcepcionService.obtenerExcepcionZarpe(escalaId, entidad)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<ApiResponse> response = excepcionController.ObtenerExcepcionesZarpe(escalaId, entidad);

        // Assert
        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
    }

    @Test
     void testObtenerExcepcionesDeclaracion() {
        // Arrange
        Integer escalaId = 1;
        Integer entidad = 1;
        ExcepcionesResponse expectedResponse = createExcepcionesResponse();
        when(obtenerExcepcionService.obtenerExcepcionDeclaracion(escalaId, entidad)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<ApiResponse> response = excepcionController.obtenerExcepcionesDeclaración(escalaId, entidad);

        // Assert
        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
    }

    @Test
     void testObtenerExcepcionPatente() {
        // Arrange
        Integer escalaId = 1;
        Integer entidad = 1;
        ExcepcionesDueResponse expectedResponse = createExcepcionesDueResponse();
        when(obtenerExcepcionService.obtenerExcepcionPatente(escalaId, entidad)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<ApiResponse> response = excepcionController.obtenerExcepcionPatente(escalaId, entidad);

        // Assert
        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
    }

    @Test
     void testObtenerExcepciones_Exception() {
        // Arrange
        Integer escalaId = 1;
        Integer entidad = 1;
        when(obtenerExcepcionService.obtenerExcepcion(escalaId, entidad)).thenThrow(new RuntimeException("Service error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            excepcionController.obtenerExcepciones(escalaId, entidad);
        });
    }

    private ExcepcionesResponse createExcepcionesResponse() {
        ExcepcionesResponse response = new ExcepcionesResponse();
        ExcepcionesResponse.DataException dataException = new ExcepcionesResponse.DataException();
        dataException.setEscalaId(1);
        dataException.setMotivo(1);
        dataException.setNaveDeportiva(0);
        dataException.setNaveCientifica(0);
        dataException.setSumaArqueoSinConvoy(0);
        dataException.setSumaArqueo(0.0);
        dataException.setNaveHospital(0);
        dataException.setEntidadId(1);
        dataException.setAmbitoNave(1);
        dataException.setPaisPe(0);

        response.setData(Collections.singletonList(dataException));
        return response;
    }

    private ExcepcionesDueResponse createExcepcionesDueResponse() {
        ExcepcionesDueResponse response = new ExcepcionesDueResponse();
        ExcepcionesDueResponse.DataException dataException = new ExcepcionesDueResponse.DataException();
        dataException.setEscalaId(1);
        dataException.setDue(1);
        dataException.setMotivo(1);
        dataException.setNaveDeportiva(0);
        dataException.setNaveCientifica(0);
        dataException.setSumaArqueoSinConvoy(0);
        dataException.setSumaArqueo(0.0);
        dataException.setNaveHospital(0);
        dataException.setEntidadId(1);
        dataException.setAmbitoNave(1);
        dataException.setPaisPe(0);

        response.setData(Collections.singletonList(dataException));
        return response;
    }
}
