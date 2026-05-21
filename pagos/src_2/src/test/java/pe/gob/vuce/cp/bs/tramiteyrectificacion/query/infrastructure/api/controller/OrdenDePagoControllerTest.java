package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.application.service.OrdenDePagoService;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.OrdenDePagoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.mapper.OrdenDePagoDtoMapper;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.ApiResponseOrdenPagoDto;
import static org.springframework.http.HttpStatus.OK;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

 class OrdenDePagoControllerTest {

    @InjectMocks
    private OrdenDePagoController ordenDePagoController;

    @Mock
    private OrdenDePagoService ordenDePagoService;

    @Mock
    private OrdenDePagoDtoMapper ordenDePagoDtoMapper;

    @BeforeEach
     void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("findOrdenesDePago returns list of OrdenDePagoModel when valid parameters are passed")
     void findOrdenesDePagoReturnsListWhenValidParametersPassed() {
        // Given
        Integer escalaId = 1;
        Integer documentId = 1;
        String rucAgente = "123456789";
        String ordenDePago = "PG";

        List<OrdenDePagoModel> ordenesDePagoModel = Collections.singletonList(new OrdenDePagoModel());
        when(ordenDePagoService.findOrdenesDePago(escalaId, documentId, rucAgente, ordenDePago)).thenReturn(ordenesDePagoModel);

        // When
        ResponseEntity<ApiResponseOrdenPagoDto> response = ordenDePagoController.findOrdenesDePago(escalaId, documentId, rucAgente,ordenDePago);

        // Then
        assertEquals(OK, response.getStatusCode());
    }

    @Test
    @DisplayName("findOrdenesDePago returns empty list when no OrdenDePagoModel found")
     void findOrdenesDePagoReturnsEmptyListWhenNoOrdenDePagoModelFound() {
        // Given
        Integer escalaId = 1;
        Integer documentId = 1;
        String rucAgente = "123456789";
        String ordenDePago = "PG";
        when(ordenDePagoService.findOrdenesDePago(escalaId, documentId, rucAgente, ordenDePago)).thenReturn(Collections.emptyList());

        // When
        ResponseEntity<ApiResponseOrdenPagoDto> response = ordenDePagoController.findOrdenesDePago(escalaId, documentId, rucAgente, ordenDePago);

        // Then
        assertEquals(OK, response.getStatusCode());
    }
}