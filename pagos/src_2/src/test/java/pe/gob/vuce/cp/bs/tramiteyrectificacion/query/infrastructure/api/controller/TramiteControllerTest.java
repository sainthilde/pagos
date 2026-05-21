package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.application.service.TramiteService;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.enums.MetaResults;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.TramiteModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.mapper.TramiteDtoMapper;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.ApiResponseTramiteDetalleDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.ApiResponseTramiteDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.ApiResponseTramitePagoDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.GetTramiteQueryParamsDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.TramiteDetalleDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.TramiteDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.TramitePagoDto;

class TramiteControllerTest {

    @Mock
    private TramiteService tramiteService;

    @Mock
    private TramiteDtoMapper tramiteDtoMapper;

    @InjectMocks
    private TramiteController tramiteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerTramites() {
        // Arrange
        GetTramiteQueryParamsDto queryParams = new GetTramiteQueryParamsDto();
        queryParams.setPageNumber(1);
        queryParams.setPageSize(10);

        TramiteModel tramiteModel = new TramiteModel();
        List<TramiteModel> tramiteList = Collections.singletonList(tramiteModel);
        Page<TramiteModel> tramites = new PageImpl<>(tramiteList,
                PageRequest.of(queryParams.getPageNumber(), queryParams.getPageSize()), 1); // Set the total elements to
                                                                                            // 1

        TramiteDto tramiteDto = new TramiteDto();
        List<TramiteDto> tramiteDtoList = Collections.singletonList(tramiteDto);

        when(tramiteService.obtenerTramites(queryParams)).thenReturn(tramites);
        when(tramiteDtoMapper.toTramiteDtoList(tramites.getContent())).thenReturn(tramiteDtoList);

        // Act
        ResponseEntity<ApiResponseTramiteDto> responseEntity = tramiteController.obtenerTramites(queryParams);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(tramiteDtoList, responseEntity.getBody().getData());
        assertEquals(MetaResults.SUCCESS.getValue(), responseEntity.getBody().getMeta().getResult());
        assertEquals(1, responseEntity.getBody().getMeta().getCantidadRegistros());
        assertEquals(11, responseEntity.getBody().getMeta().getCantidadRegistrosTotal());

        verify(tramiteService).obtenerTramites(queryParams);
        verify(tramiteDtoMapper).toTramiteDtoList(tramites.getContent());
    }

    @Test
    void testObtenerTramite() {
        // Arrange
        Integer idTramite = 1;
        TramiteModel tramiteModel = new TramiteModel();
        List<TramiteModel> tramiteList = Collections.singletonList(tramiteModel);

        TramiteDetalleDto tramiteDto = new TramiteDetalleDto();
        List<TramiteDetalleDto> tramiteDtoList = Collections.singletonList(tramiteDto);

        when(tramiteService.obtenerTramite(idTramite)).thenReturn(tramiteList);
        when(tramiteDtoMapper.toTramiteDetalleDtoList(tramiteList)).thenReturn(tramiteDtoList);

        // Act
        ResponseEntity<ApiResponseTramiteDetalleDto> responseEntity = tramiteController.obtenerTramite(idTramite);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(tramiteDtoList, responseEntity.getBody().getData());
        assertEquals(MetaResults.SUCCESS.getValue(), responseEntity.getBody().getMeta().getResult());
        assertEquals(1, responseEntity.getBody().getMeta().getCantidadRegistros());
        assertEquals(1, responseEntity.getBody().getMeta().getCantidadRegistrosTotal());

        verify(tramiteService).obtenerTramite(idTramite);
        verify(tramiteDtoMapper).toTramiteDetalleDtoList(tramiteList);
    }

    @Test
    void testObtenerTramitePorEscalaYDocumento() {
        // Arrange
        Integer escalaId = 1;
        Integer documentoId = 1;

        TramiteModel tramiteModel = new TramiteModel();
        Optional<TramiteModel> tramiteOptional = Optional.of(tramiteModel);

        TramiteDetalleDto tramiteDto = new TramiteDetalleDto();
        List<TramiteDetalleDto> tramiteDtoList = Collections.singletonList(tramiteDto);

        when(tramiteService.obtenerTramitePorEscalaYDocumento(escalaId, documentoId)).thenReturn(tramiteOptional);
        when(tramiteDtoMapper.toTramiteDetalleDto(tramiteModel)).thenReturn(tramiteDto);

        // Act
        ResponseEntity<ApiResponseTramiteDetalleDto> responseEntity = tramiteController.obtenerTramitePorEscalaYDocumento(escalaId, documentoId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(tramiteDtoList, responseEntity.getBody().getData());
        assertEquals(MetaResults.SUCCESS.getValue(), responseEntity.getBody().getMeta().getResult());
        assertEquals(1, responseEntity.getBody().getMeta().getCantidadRegistros());
        assertEquals(1, responseEntity.getBody().getMeta().getCantidadRegistrosTotal());

        verify(tramiteService).obtenerTramitePorEscalaYDocumento(escalaId, documentoId);
        verify(tramiteDtoMapper).toTramiteDetalleDto(tramiteModel);
    }

    @Test
    void testObtenerTramiteReglaPago() {
        // Arrange
        Integer escalaId = 1;
        Integer documentoId = 1;
        String indicadorEs = "E";

        TramitePagoDto tramitePagoDto = new TramitePagoDto();
        tramitePagoDto.setTramiteId(1);
        tramitePagoDto.setEscalaId(escalaId);
        tramitePagoDto.setReglaPagoExencionAplicada("Test rule");

        when(tramiteService.getIndNoRequierePagoByEscalaIdAndIndicadorEs(escalaId, indicadorEs, documentoId))
                .thenReturn(tramitePagoDto);

        // Act
        ResponseEntity<ApiResponseTramitePagoDto> responseEntity = tramiteController.obtenerTramiteReglaPago(escalaId, documentoId, indicadorEs);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().getData().size());
        assertEquals(tramitePagoDto, responseEntity.getBody().getData().get(0));
        assertEquals(MetaResults.SUCCESS.getValue(), responseEntity.getBody().getMeta().getResult());
        assertEquals(1, responseEntity.getBody().getMeta().getCantidadRegistros());
        assertEquals(1, responseEntity.getBody().getMeta().getCantidadRegistrosTotal());

        verify(tramiteService).getIndNoRequierePagoByEscalaIdAndIndicadorEs(escalaId, indicadorEs, documentoId);
    }

    @Test
    void testObtenerTramiteReglaPago_NotFound() {
        // Arrange
        Integer escalaId = 1;
        Integer documentoId = 1;
        String indicadorEs = "E";

        when(tramiteService.getIndNoRequierePagoByEscalaIdAndIndicadorEs(escalaId, indicadorEs, documentoId))
                .thenReturn(null);

        // Act
        ResponseEntity<ApiResponseTramitePagoDto> responseEntity = tramiteController.obtenerTramiteReglaPago(escalaId, documentoId, indicadorEs);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, responseEntity.getBody().getData().size());
        assertEquals(MetaResults.SUCCESS.getValue(), responseEntity.getBody().getMeta().getResult());
        assertEquals(0, responseEntity.getBody().getMeta().getCantidadRegistros());
        assertEquals(0, responseEntity.getBody().getMeta().getCantidadRegistrosTotal());

        verify(tramiteService).getIndNoRequierePagoByEscalaIdAndIndicadorEs(escalaId, indicadorEs, documentoId);
    }
}
