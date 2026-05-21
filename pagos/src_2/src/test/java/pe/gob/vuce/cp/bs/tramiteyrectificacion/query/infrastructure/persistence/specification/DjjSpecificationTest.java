package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.specification;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.constants.Constants;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.pago.DeclaracionJurada;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.GetDjjQueryParamsDto;

class DjjSpecificationTest {

    private DjjSpecification djjSpecification;

    private CriteriaBuilder criteriaBuilder;
    private Root<DeclaracionJurada> root;
    private Predicate predicate;
    private Predicate newPredicate;

    @Mock
    private Path<String> numeroDdjjPath;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        criteriaBuilder = mock(CriteriaBuilder.class);
        root = mock(Root.class);
        predicate = mock(Predicate.class);
        newPredicate = mock(Predicate.class);

        // Spy so we can stub BaseSpecification methods
        djjSpecification = spy(new DjjSpecification());

        // Stub inherited filters to just return the incoming predicate
        doAnswer(invocation -> invocation.getArgument(3))
                .when(djjSpecification).applyDueFilter(any(), any(), any(), any());
        doAnswer(invocation -> invocation.getArgument(4))
                .when(djjSpecification).applyDateFilters(any(), any(), any(), any(), any());
        doAnswer(invocation -> invocation.getArgument(3))
                .when(djjSpecification).applyNombreNaveFilter(any(), any(), any(), any());
        doAnswer(invocation -> invocation.getArgument(2))
                .when(djjSpecification).applyDefaultFilters(any(), any(), any());

        // Stub the path retrieval for NUMERO_DECLARACION_JURADA
        when(root.<String>get(Constants.NUMERO_DECLARACION_JURADA))
                .thenReturn(numeroDdjjPath);

        // Stub the LIKE itself
        when(criteriaBuilder.like(eq(numeroDdjjPath), anyString()))
                .thenReturn(newPredicate);

        // Stub `and(...)` to return the combined predicate
        when(criteriaBuilder.and(predicate, newPredicate))
                .thenReturn(newPredicate);
    }

    @Test
    void testApplyNumeroDDJJ_whenNumeroDDJJIsProvided() {
        GetDjjQueryParamsDto dto = new GetDjjQueryParamsDto(0, 10);
        dto.setNumeroDDJJ("DDJJ123");

        // Exercise
        Predicate result = djjSpecification.applyNumeroDDJJ(dto, root, criteriaBuilder, predicate);

        // Verify that we did a LIKE with %DDJJ123%
        verify(criteriaBuilder).like(numeroDdjjPath, "%DDJJ123%");
        verify(criteriaBuilder).and(predicate, newPredicate);

        // And returned the newPredicate
        assertEquals(newPredicate, result);
    }

    @Test
    void testApplyNumeroDDJJ_whenNumeroDDJJIsNull() {
        GetDjjQueryParamsDto dto = new GetDjjQueryParamsDto(0, 10);
        dto.setNumeroDDJJ(null);

        Predicate result = djjSpecification.applyNumeroDDJJ(dto, root, criteriaBuilder, predicate);

        // Should skip LIKE / AND entirely
        assertEquals(predicate, result);
        verify(criteriaBuilder, never()).like(any(), anyString());
        verify(criteriaBuilder, never()).and(any(), any());
    }

    @Test
    void testGetDjjs_withNumeroDDJJ() {
        GetDjjQueryParamsDto dto = new GetDjjQueryParamsDto(0, 10);
        dto.setNumeroDDJJ("DDJJ123");

        // Stub the initial conjunction
        Predicate conj = mock(Predicate.class);
        when(criteriaBuilder.conjunction()).thenReturn(conj);

        // Stub the LIKE branch exactly as above
        when(criteriaBuilder.and(conj, newPredicate)).thenReturn(newPredicate);

        // Build the spec and execute
        Specification<DeclaracionJurada> spec = djjSpecification.getDjjs(dto);
        Predicate result = spec.toPredicate(root, null, criteriaBuilder);

        // Should incorporate our LIKE-based predicate
        assertEquals(newPredicate, result);
    }

    @Test
    void testApplyEntidadFilter_whenEntidadIsProvided() {
        Integer entidadValue = 5;
        // For the updated implementation, we expect a call to
        // root.get(Constants.ENTIDAD_ID)
        when(root.get(Constants.ENTIDAD_ID)).thenReturn(null);
        when(criteriaBuilder.equal(any(), eq(entidadValue))).thenReturn(newPredicate);
        when(criteriaBuilder.and(predicate, newPredicate)).thenReturn(newPredicate);

        Predicate result = djjSpecification.applyEntidadFilterDDJJ(entidadValue, root, criteriaBuilder, predicate);
        assertEquals(newPredicate, result);
        verify(root).get(Constants.ENTIDAD_ID);
        verify(criteriaBuilder).equal(any(), eq(entidadValue));
        verify(criteriaBuilder).and(predicate, newPredicate);
    }

    @Test
    void testApplyEntidadFilter_whenEntidadIsNull() {
        Predicate result = djjSpecification.applyEntidadFilterDDJJ(null, root, criteriaBuilder, predicate);
        assertEquals(predicate, result);
        verify(root, never()).get(anyString());
    }

    @Test
    void testApplyAgenciaFilter_whenAgenciaIsProvided() {
        String agenciaValue = "1234567890";
        // Stub the join for agencia.
        when(root.get(Constants.RUC_AGENTE)).thenReturn(null); // the actual Path is not used

        // Stub the calls to criteriaBuilder.equal and criteriaBuilder.and.
        when(criteriaBuilder.equal(any(), eq(agenciaValue))).thenReturn(newPredicate);
        when(criteriaBuilder.and(predicate, newPredicate)).thenReturn(newPredicate);

        Predicate result = djjSpecification.applyAgenciaRucFilterDDJJ(agenciaValue, root, criteriaBuilder, predicate);
        assertEquals(newPredicate, result);
        verify(criteriaBuilder).equal(any(), eq(agenciaValue));
        verify(criteriaBuilder).and(predicate, newPredicate);
    }

    @Test
    void testApplyAgenciaFilter_whenAgenciaIsNull() {
        // If agencia is null, the predicate remains unchanged.
        Predicate result = djjSpecification.applyAgenciaRucFilterDDJJ(null, root, criteriaBuilder, predicate);
        assertEquals(predicate, result);
        verify(root, never()).join(anyString());
    }

    @Test
    void testGetDjjs_withEmptyParams() {
        GetDjjQueryParamsDto dto = new GetDjjQueryParamsDto(0, 10);
        // All optional parameters are null.
        Predicate conjPredicate = mock(Predicate.class);
        when(criteriaBuilder.conjunction()).thenReturn(conjPredicate);

        Specification<DeclaracionJurada> spec = djjSpecification.getDjjs(dto);
        Predicate result = spec.toPredicate(root, null, criteriaBuilder);
        // Since none of the filters modify the predicate, the result should be the
        // conjunction.
        assertEquals(conjPredicate, result);
    }

}
