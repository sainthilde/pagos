package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.application.service.DeclaracionJuradaService;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.DeclaracionJuradaModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.mapper.DeclaracionJuradaMapper;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.ApiResponseDeclaracionJuradaDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.ApiResponseDeclaracionJuradaListaDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.DeclaracionJuradaListaDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.GetDjjQueryParamsDto;

class DeclaracionJuradaControllerTest {

        @InjectMocks
        private DeclaracionJuradaController declaracionJuradaController;

        @Mock
        private DeclaracionJuradaService declaracionJuradaService;

        @Mock
        private DeclaracionJuradaMapper declaracionJuradaMapper;

        @BeforeEach
         void setup() {
                MockitoAnnotations.openMocks(this);
        }

        @Test
        @DisplayName("obtenerDeclaracionesJuradas returns list of DeclaracionJuradaModel when valid escalaId is passed")
        void obtenerDeclaracionesJuradasReturnsListWhenValidEscalaIdPassed() {
                Integer escalaId = 1;
                List<DeclaracionJuradaModel> declaracionesJuradasModel = Collections
                                .singletonList(new DeclaracionJuradaModel());
                when(declaracionJuradaService.buscarDeclaracionesJuradas(escalaId))
                                .thenReturn(declaracionesJuradasModel);

                ResponseEntity<ApiResponseDeclaracionJuradaDto> response = declaracionJuradaController
                                .obtenerDeclaracionesJuradas(escalaId);

            assertEquals(HttpStatus.OK, response.getStatusCode());

        }

        @Test
        @DisplayName("obtenerDeclaracionesJuradas returns empty list when no DeclaracionJuradaModel found")
        void obtenerDeclaracionesJuradasReturnsEmptyListWhenNoDeclaracionJuradaModelFound() {
                Integer escalaId = 1;
                when(declaracionJuradaService.buscarDeclaracionesJuradas(escalaId)).thenReturn(Collections.emptyList());

                ResponseEntity<ApiResponseDeclaracionJuradaDto> response = declaracionJuradaController
                                .obtenerDeclaracionesJuradas(escalaId);

            assertEquals(HttpStatus.OK, response.getStatusCode());

        }

        @Test
        @DisplayName("obtenerDeclaracionesJuradasByEstado returns list of DeclaracionJuradaModel when valid parameters are passed")
        void obtenerDeclaracionesJuradasByEstadoReturnsListWhenValidParametersPassed() {
                Integer escalaId = 1;
                String rucAgente = "123456789";
                String estado = "estado";
                Integer documentId = 1;
                String estadoDdjjPago = "estadoDdjjPago";
                List<DeclaracionJuradaModel> declaracionesJuradasModel = Collections
                                .singletonList(new DeclaracionJuradaModel());
                when(declaracionJuradaService.buscarDeclaracionesJuradas(escalaId, estado, documentId, estadoDdjjPago,
                                rucAgente)).thenReturn(declaracionesJuradasModel);

                ResponseEntity<ApiResponseDeclaracionJuradaDto> response = declaracionJuradaController
                                .obtenerDeclaracionesJuradasByEstado(escalaId, rucAgente, estado, documentId,
                                                estadoDdjjPago);

            assertEquals(HttpStatus.OK, response.getStatusCode());

        }

        @Test
        @DisplayName("obtenerDeclaracionesJuradasByEstado returns empty list when no DeclaracionJuradaModel found")
        void obtenerDeclaracionesJuradasByEstadoReturnsEmptyListWhenNoDeclaracionJuradaModelFound() {
                Integer escalaId = 1;
                String rucAgente = "123456789";
                String estado = "estado";
                Integer documentId = 1;
                String estadoDdjjPago = "estadoDdjjPago";
                when(declaracionJuradaService.buscarDeclaracionesJuradas(escalaId, estado, documentId, estadoDdjjPago,
                                rucAgente)).thenReturn(Collections.emptyList());

                ResponseEntity<ApiResponseDeclaracionJuradaDto> response = declaracionJuradaController
                                .obtenerDeclaracionesJuradasByEstado(escalaId, rucAgente, estado, documentId,
                                                estadoDdjjPago);

            assertEquals(HttpStatus.OK, response.getStatusCode());

        }

        @Test
        @DisplayName("obtenerListadoDeclaracionesJuradas returns paginated list of DeclaracionJuradaListaDto when valid queryParams are passed")
        void obtenerListadoDeclaracionesJuradasReturnsPaginatedListWhenValidQueryParamsPassed() {
                GetDjjQueryParamsDto queryParams = new GetDjjQueryParamsDto();
                // Set queryParams properties as needed
                List<DeclaracionJuradaListaDto> declaracionesJuradasLista = Collections
                                .singletonList(new DeclaracionJuradaListaDto());
                Page<DeclaracionJuradaListaDto> paginatedDjjs = new PageImpl<>(declaracionesJuradasLista,
                                PageRequest.of(0, 10), 1);
                when(declaracionJuradaService.getDjjs(queryParams)).thenReturn(paginatedDjjs);

                ResponseEntity<ApiResponseDeclaracionJuradaListaDto> response = declaracionJuradaController
                                .obtenerListadoDeclaracionesJuradas(queryParams);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(1, response.getBody().getData().size());
        }

        @Test
        @DisplayName("obtenerListadoDeclaracionesJuradas returns empty list when no DeclaracionJuradaListaDto found")
        void obtenerListadoDeclaracionesJuradasReturnsEmptyListWhenNoDeclaracionJuradaListaDtoFound() {
                GetDjjQueryParamsDto queryParams = new GetDjjQueryParamsDto();
                // Set queryParams properties as needed
                Page<DeclaracionJuradaListaDto> paginatedDjjs = new PageImpl<>(Collections.emptyList(),
                                PageRequest.of(0, 10), 0);
                when(declaracionJuradaService.getDjjs(queryParams)).thenReturn(paginatedDjjs);

                ResponseEntity<ApiResponseDeclaracionJuradaListaDto> response = declaracionJuradaController
                                .obtenerListadoDeclaracionesJuradas(queryParams);

            assertEquals(HttpStatus.OK, response.getStatusCode());

            assertEquals(0, response.getBody().getData().size());
        }
}