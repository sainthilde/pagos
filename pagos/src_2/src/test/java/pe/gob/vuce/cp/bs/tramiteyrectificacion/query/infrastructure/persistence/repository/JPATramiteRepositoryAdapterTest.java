package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.TramiteModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.pago.Tramite;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.mapper.TramiteModelMapper;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.specification.TramiteSpecification;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.GetTramiteQueryParamsDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.TramitePagoDto;

class JPATramiteRepositoryAdapterTest {

    @Mock
    private JPATramiteRepository jpaTramiteRepository;

    @Mock
    private TramiteModelMapper tramiteModelMapper;

    @Mock
    private TramiteSpecification tramiteSpecification;

    @InjectMocks
    private JPATramiteRepositoryAdapter jpaTramiteRepositoryAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerTramiteAndObtenerTramites() {
        // Test for obtenerTramite

        // Arrange
        Integer tramiteId = 1;
        Tramite tramite = new Tramite();
        tramite.setId(tramiteId);

        List<TramiteModel> expectedModels = List.of(new TramiteModel());

        when(jpaTramiteRepository.findById(tramiteId)).thenReturn(Optional.of(tramite));
        when(tramiteModelMapper.toTramiteModels(tramite)).thenReturn(expectedModels);

        // Act
        List<TramiteModel> resultObtenerTramite = jpaTramiteRepositoryAdapter.obtenerTramite(tramiteId);

        // Assert
        assertNotNull(resultObtenerTramite);
        assertEquals(expectedModels.size(), resultObtenerTramite.size());
        verify(jpaTramiteRepository, times(1)).findById(tramiteId);
        verify(tramiteModelMapper, times(1)).toTramiteModels(tramite);

        // Test for obtenerTramites

        // Arrange
        GetTramiteQueryParamsDto queryParams = new GetTramiteQueryParamsDto();
        queryParams.setPageNumber(1);
        queryParams.setPageSize(10);

        Tramite tramiteEntity = new Tramite();
        Page<Tramite> tramitePage = new PageImpl<>(Collections.singletonList(tramiteEntity));
        List<TramiteModel> tramiteModels = Collections.singletonList(new TramiteModel());

        when(tramiteSpecification.getTramites(queryParams)).thenReturn(mock(Specification.class));
        when(jpaTramiteRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(tramitePage);
        when(tramiteModelMapper.toTramiteModels(tramiteEntity)).thenReturn(tramiteModels);

        // Act
        Page<TramiteModel> resultObtenerTramites = jpaTramiteRepositoryAdapter.obtenerTramites(queryParams);

        // Assert
        assertNotNull(resultObtenerTramites);
        assertEquals(1, resultObtenerTramites.getTotalElements());
        assertEquals(1, resultObtenerTramites.getContent().size());
        verify(tramiteSpecification, times(1)).getTramites(queryParams);
        verify(jpaTramiteRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
        verify(tramiteModelMapper, times(1)).toTramiteModels(tramiteEntity);
    }

    @Test
    void testFindByEscalaIdAndDocumentoIdAndIndicadorES() {
        // Arrange
        Integer escalaId = 1;
        Integer documentoId = 1;
        String estado = "S";

        Tramite tramite = new Tramite();
        TramiteModel expectedModel = new TramiteModel();

        when(jpaTramiteRepository.findByEscalaEscalaIdAndDocumentoIdAndEstado(escalaId, documentoId, estado))
                .thenReturn(Optional.of(tramite));
        when(tramiteModelMapper.entityToTramiteModel(tramite)).thenReturn(expectedModel);

        // Act
        Optional<TramiteModel> result = jpaTramiteRepositoryAdapter.findByEscalaIdAndDocumentoId(escalaId, documentoId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedModel, result.orElse(null));
        verify(jpaTramiteRepository, times(1)).findByEscalaEscalaIdAndDocumentoIdAndEstado(escalaId, documentoId, estado);
        verify(tramiteModelMapper, times(1)).entityToTramiteModel(tramite);
    }

    @Test
    void testGetIndNoRequierePagoByEscalaIdAndIndicadorEs() {
        // Arrange
        Integer escalaId = 1;
        Integer documentoId = 1;
        String indicadorEs = "E";

        Tramite tramite = new Tramite();
        tramite.setId(1);
        tramite.setIndicadorEs(indicadorEs);
        tramite.setReglaPagoExencionAplicada("Test rule");

        TramitePagoDto expectedDto = new TramitePagoDto();
        expectedDto.setTramiteId(1);
        expectedDto.setEscalaId(escalaId);
        expectedDto.setReglaPagoExencionAplicada("Test rule");

        when(jpaTramiteRepository.findByEscalaEscalaIdAndIndicadorEsAndDocumentoId(escalaId, indicadorEs, documentoId))
                .thenReturn(Optional.of(tramite));
        when(tramiteModelMapper.entityToTramitePagoDto(tramite)).thenReturn(expectedDto);

        // Act
        TramitePagoDto result = jpaTramiteRepositoryAdapter.getIndNoRequierePagoByEscalaIdAndIndicadorEs(escalaId, indicadorEs, documentoId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedDto.getTramiteId(), result.getTramiteId());
        assertEquals(expectedDto.getEscalaId(), result.getEscalaId());
        assertEquals(expectedDto.getReglaPagoExencionAplicada(), result.getReglaPagoExencionAplicada());
        verify(jpaTramiteRepository, times(1)).findByEscalaEscalaIdAndIndicadorEsAndDocumentoId(escalaId, indicadorEs, documentoId);
        verify(tramiteModelMapper, times(1)).entityToTramitePagoDto(tramite);
    }

    @Test
    void testGetIndNoRequierePagoByEscalaIdAndIndicadorEs_NotFound() {
        // Arrange
        Integer escalaId = 1;
        Integer documentoId = 1;
        String indicadorEs = "E";

        when(jpaTramiteRepository.findByEscalaEscalaIdAndIndicadorEsAndDocumentoId(escalaId, indicadorEs, documentoId))
                .thenReturn(Optional.empty());

        // Act
        TramitePagoDto result = jpaTramiteRepositoryAdapter.getIndNoRequierePagoByEscalaIdAndIndicadorEs(escalaId, indicadorEs, documentoId);

        // Assert
        assertEquals(null, result);
        verify(jpaTramiteRepository, times(1)).findByEscalaEscalaIdAndIndicadorEsAndDocumentoId(escalaId, indicadorEs, documentoId);
        verify(tramiteModelMapper, times(0)).entityToTramitePagoDto(any());
    }
}
