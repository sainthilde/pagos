package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.DeclaracionJuradaModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.escala.Escala;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.mae.Documento;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.pago.DeclaracionJurada;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.mapper.DeclaracionJuradaMapper;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.specification.DjjSpecification;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.DeclaracionJuradaListaDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.GetDjjQueryParamsDto;

class JPADeclaracionJuradaRepositoryAdapterTest {

    @Mock
    private JPADeclaracionJuradaRepository jpaDeclaracionJuradaRepository;

    @Mock
    private DeclaracionJuradaMapper declaracionJuradaMapper;

    @Mock
    private DjjSpecification djjSpecification;

    @InjectMocks
    private JPADeclaracionJuradaRepositoryAdapter jpaDeclaracionJuradaRepositoryAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByEscalaId() {
        // Arrange
        Integer escalaId = 1;
        Escala escala = new Escala();
        escala.setEscalaId(escalaId); // Create the Escala object in the test

        DeclaracionJurada declaracionJuradaEntity = new DeclaracionJurada();
        declaracionJuradaEntity.setId(1);
        List<DeclaracionJurada> entityList = Arrays.asList(declaracionJuradaEntity);

        // Mock the repository to return the expected list
        when(jpaDeclaracionJuradaRepository.findByEscala(any(Escala.class)))
                .thenReturn(entityList);

        DeclaracionJuradaModel declaracionJuradaModel = new DeclaracionJuradaModel();
        declaracionJuradaModel.setId(1);
        List<DeclaracionJuradaModel> modelList = Arrays.asList(declaracionJuradaModel);

        // Mock the mapper to return the expected model list
        when(declaracionJuradaMapper.toModelList(entityList)).thenReturn(modelList);

        // Act
        List<DeclaracionJuradaModel> result = jpaDeclaracionJuradaRepositoryAdapter.findByEscala(escalaId);

        // Assert
        assertEquals(1, result.size()); // Verify the list size
        assertEquals(declaracionJuradaModel.getId(), result.get(0).getId()); // Verify the content
        verify(jpaDeclaracionJuradaRepository, times(1)).findByEscala(any(Escala.class)); // Use any(Escala.class) for
                                                                                          // verification
        verify(declaracionJuradaMapper, times(1)).toModelList(entityList); // Verify mapper call
    }

    @Test
    void testFindDeclaracionJurada() {
        // Arrange
        Integer escalaId = 1;
        String estado = "S";
        String rucAgente = "123456789";
        String estadoordenDePago = "PG";

        Escala escala = new Escala();
        escala.setEscalaId(escalaId);
        DeclaracionJurada declaracionJuradaEntity = new DeclaracionJurada();
        declaracionJuradaEntity.setId(1);
        List<DeclaracionJurada> entityList = Arrays.asList(declaracionJuradaEntity);

        // Use matchers for all arguments
        when(jpaDeclaracionJuradaRepository.findByEscalaAndEstadoAndDocumentoAndEstadoDdjjPagoAndRucAgente(
                any(Escala.class), anyString(), any(Documento.class), anyString(), anyString())).thenReturn(entityList);

        DeclaracionJuradaModel declaracionJuradaModel = new DeclaracionJuradaModel();
        declaracionJuradaModel.setId(1);
        List<DeclaracionJuradaModel> modelList = Arrays.asList(declaracionJuradaModel);
        when(declaracionJuradaMapper.toModelList(entityList)).thenReturn(modelList);

    // Act
    List<DeclaracionJuradaModel> result = jpaDeclaracionJuradaRepositoryAdapter.findDeclaracionJurada(escalaId,
        estado, 1, estadoordenDePago, rucAgente);

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void testGetDjjs() {
        // Arrange
        GetDjjQueryParamsDto params = new GetDjjQueryParamsDto(1, 10);
        DeclaracionJurada declaracionJuradaEntity = new DeclaracionJurada();
        declaracionJuradaEntity.setId(1);
        List<DeclaracionJurada> entityList = Arrays.asList(declaracionJuradaEntity);
        Page<DeclaracionJurada> djjPage = new PageImpl<>(entityList);

        when(djjSpecification.getDjjs(params)).thenReturn(Specification.where(null));
    when(jpaDeclaracionJuradaRepository.findAll(ArgumentMatchers.<Specification<DeclaracionJurada>>any(), any(Pageable.class)))
        .thenReturn(djjPage);

        DeclaracionJuradaListaDto declaracionJuradaListaDto = new DeclaracionJuradaListaDto();
        declaracionJuradaListaDto.setId(1);
        when(declaracionJuradaMapper.toDeclaracionJuradaListaDto(declaracionJuradaEntity))
                .thenReturn(declaracionJuradaListaDto);

        // Act
        Page<DeclaracionJuradaListaDto> result = jpaDeclaracionJuradaRepositoryAdapter.getDjjs(params);

        // Assert
        assertEquals(1, result.getTotalElements());
        assertEquals(declaracionJuradaListaDto.getId(), result.getContent().get(0).getId());
    verify(jpaDeclaracionJuradaRepository, times(1)).findAll(ArgumentMatchers.<Specification<DeclaracionJurada>>any(), any(Pageable.class));
        verify(declaracionJuradaMapper, times(1)).toDeclaracionJuradaListaDto(declaracionJuradaEntity);
    }
}