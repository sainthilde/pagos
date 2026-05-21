package pe.gob.vuce.cp.sp.pagos.infrastructure.api.controller;

import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pe.gob.vuce.cp.sp.pagos.application.service.ActividadEntidadService;
import pe.gob.vuce.cp.sp.pagos.domain.model.ActividadEntidad;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.ActividadEntidadResponseDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.GenericResponseDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.mapper.ActividadEntidadMapper;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class EntidadControllerTest {

    @Mock
    private ActividadEntidadService actividadEntidadService;

    @Mock
    private ActividadEntidadMapper actividadEntidadMapper;

    @InjectMocks
    private EntidadController entidadController;

    private ActividadEntidad actividadEntidad;
    private ActividadEntidadResponseDto responseDto;

    @BeforeEach
    void setUp() {
        actividadEntidad = new ActividadEntidad();
        actividadEntidad.setEntidadId(100);
        actividadEntidad.setActividadId(200);
        actividadEntidad.setCodPuertoNacional("PUERTO001");

    }

    @Test
    void getCodReglaNegocio_WhenDataExists_ShouldReturnOkResponse() {
        // Arrange
        when(actividadEntidadService.findByEntidadIdAndDocumentoIdAndPuertoDue(anyInt(), anyInt(), anyString()))
                .thenReturn(Optional.of(actividadEntidad));
        when(actividadEntidadMapper.actividadEntidadToResponseDto(any(ActividadEntidad.class)))
                .thenReturn(responseDto);

        // Act
        ResponseEntity<Object> response = entidadController.getCodReglaNegocio(100, 200, "PUERTO001");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertInstanceOf(GenericResponseDto.class, response.getBody());

        GenericResponseDto<?> responseBody = (GenericResponseDto<?>) response.getBody();
        assertFalse(responseBody.getData().isEmpty());
    }



    @Test
    void getCodReglaNegocio_WhenNullParameters_ShouldHandleGracefully() {
        // Act
        ResponseEntity<Object> response = entidadController.getCodReglaNegocio(null, null, null);

        // Assert
        assertNotNull(response);

    }

    @Test
    void getCodReglaNegocio_WhenFeignExceptionThrown_ShouldReturnErrorResponse() {
        // Arrange
        FeignException feignException = mock(FeignException.class);
        when(feignException.status()).thenReturn(503);
        when(feignException.getLocalizedMessage()).thenReturn("Servicio no disponible");
        when(feignException.getMessage()).thenReturn("Feign error: 503 Service Unavailable");

        when(actividadEntidadService.findByEntidadIdAndDocumentoIdAndPuertoDue(anyInt(), anyInt(), anyString()))
                .thenThrow(feignException);

        // Act
        ResponseEntity<Object> response = entidadController.getCodReglaNegocio(100, 200, "PUERTO001");

        // Assert
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        assertNotNull(response.getBody());
        assertInstanceOf(GenericResponseDto.class, response.getBody());

        GenericResponseDto<?> responseBody = (GenericResponseDto<?>) response.getBody();
        assertNotNull(responseBody.getMeta());
        assertEquals("ERROR", responseBody.getMeta().getResult());
        assertEquals(0, responseBody.getMeta().getCantidadRegistros());
    }

}