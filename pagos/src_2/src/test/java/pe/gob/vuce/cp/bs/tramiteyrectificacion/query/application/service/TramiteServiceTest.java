package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.application.service;

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
import org.springframework.data.domain.Pageable;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.TramiteModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.port.out.TramiteRepositoryPort;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.GetTramiteQueryParamsDto;

class TramiteServiceTest {

    @Mock
    private TramiteRepositoryPort tramiteRepositoryPort;

    @InjectMocks
    private TramiteService tramiteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerTramite() {
        // Arrange
        Integer id = 1;
        TramiteModel tramiteModel = new TramiteModel();
        tramiteModel.setId(id);
        List<TramiteModel> expectedTramites = Collections.singletonList(tramiteModel);

        when(tramiteRepositoryPort.obtenerTramite(id)).thenReturn(expectedTramites);

        // Act
        List<TramiteModel> actualTramites = tramiteService.obtenerTramite(id);

        // Assert
        assertEquals(expectedTramites, actualTramites);
        verify(tramiteRepositoryPort).obtenerTramite(id);
    }

    @Test
    void testObtenerTramites() {
        // Arrange
        GetTramiteQueryParamsDto paramsDto = new GetTramiteQueryParamsDto();
        Pageable pageable = PageRequest.of(0, 10);
        TramiteModel tramiteModel = new TramiteModel();
        List<TramiteModel> tramiteList = Collections.singletonList(tramiteModel);
        Page<TramiteModel> expectedPage = new PageImpl<>(tramiteList, pageable, tramiteList.size());

        when(tramiteRepositoryPort.obtenerTramites(paramsDto)).thenReturn(expectedPage);

        // Act
        Page<TramiteModel> actualPage = tramiteService.obtenerTramites(paramsDto);

        // Assert
        assertEquals(expectedPage, actualPage);
        verify(tramiteRepositoryPort).obtenerTramites(paramsDto);
    }


    @Test
    void testObtenerTramitePorEscalaYDocumento() {
        // Arrange
        Integer escalaId = 1;
        Integer documentoId = 2;
        TramiteModel tramiteModel = new TramiteModel();
        Optional<TramiteModel> expectedTramite = Optional.of(tramiteModel);

        when(tramiteRepositoryPort.findByEscalaIdAndDocumentoId(escalaId, documentoId))
                .thenReturn(expectedTramite);

        // Act
        Optional<TramiteModel> actualTramite = tramiteService.obtenerTramitePorEscalaYDocumento(escalaId, documentoId);

        // Assert
        assertEquals(expectedTramite, actualTramite);
        verify(tramiteRepositoryPort).findByEscalaIdAndDocumentoId(escalaId, documentoId);
    }
}
