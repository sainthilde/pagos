package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.specification;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.constants.Constants;

class BaseSpecificationTest {

    private BaseSpecification<Object> baseSpecification;
    private Root<Object> root;
    private CriteriaBuilder criteriaBuilder;
    private Predicate predicate;

    @BeforeEach
    void setUp() {
        baseSpecification = new BaseSpecification<Object>() {
        };
        root = mock(Root.class);
        criteriaBuilder = mock(CriteriaBuilder.class);
        predicate = mock(Predicate.class);

        // Common stubs for CriteriaBuilder
        Predicate mockPredicate1 = mock(Predicate.class);
        Predicate mockPredicate2 = mock(Predicate.class);
        Predicate mockPredicate3 = mock(Predicate.class);

        // Mock criteriaBuilder.equal to return a non-null Predicate
        when(criteriaBuilder.equal(any(), any())).thenReturn(mockPredicate1);

        // Mock criteriaBuilder.like
        when(criteriaBuilder.like(any(Expression.class), any(String.class))).thenReturn(mockPredicate2);

        // Mock criteriaBuilder.between
        when(criteriaBuilder.between(any(Expression.class), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(mockPredicate3);

        // Mock criteriaBuilder.and to return a non-null Predicate
        when(criteriaBuilder.and(any(Predicate.class), any(Predicate.class)))
                .thenReturn(mockPredicate1);
    }

    @Test
    void testApplyDefaultFilters() {
        // Mock the root.get(Constants.ESTADO) call
        Path<Object> estadoPath = mock(Path.class);
        when(root.get(Constants.ESTADO)).thenReturn(estadoPath);

        // Mock the criteriaBuilder.equal call to return a non-null Predicate
        Predicate mockPredicate = mock(Predicate.class);
        when(criteriaBuilder.equal(estadoPath, Constants.ESTADO_ACTIVO)).thenReturn(mockPredicate);

        // Call the method under test
        Predicate result = baseSpecification.applyDefaultFilters(root, criteriaBuilder, predicate);

        // Verify the result is not null
        assertNotNull(result, "Result should not be null");

        // Verify that criteriaBuilder.and was called
        verify(criteriaBuilder, atLeastOnce()).and(predicate, mockPredicate);
    }

    @Test
    void testApplyDueFilter() {
        // Mock the nested paths
        Path<Object> escalaPath = mock(Path.class);
        Path<Object> puertoEscalaIdPath = mock(Path.class);
        Path<Object> annoEscalaPath = mock(Path.class);
        Path<Object> numeroEscalaPath = mock(Path.class);

        when(root.get(Constants.ESCALA)).thenReturn(escalaPath);
        when(escalaPath.get(Constants.PUERTO_ESCALA_ID)).thenReturn(puertoEscalaIdPath);
        when(escalaPath.get(Constants.ANNO_ESCALA)).thenReturn(annoEscalaPath);
        when(escalaPath.get(Constants.NUMERO_ESCALA)).thenReturn(numeroEscalaPath);

        // Mock the criteriaBuilder.equal calls to return non-null Predicates
        Predicate mockPredicate1 = mock(Predicate.class);
        Predicate mockPredicate2 = mock(Predicate.class);
        Predicate mockPredicate3 = mock(Predicate.class);

        when(criteriaBuilder.equal(puertoEscalaIdPath, "1")).thenReturn(mockPredicate1);
        when(criteriaBuilder.equal(annoEscalaPath, "2021")).thenReturn(mockPredicate2);
        when(criteriaBuilder.equal(numeroEscalaPath, 123)).thenReturn(mockPredicate3);

        // Call the method under test
        Predicate result = baseSpecification.applyDueFilter("1-2021-123", root, criteriaBuilder, predicate);

        // Verify the result is not null
        assertNotNull(result);

        // Verify that criteriaBuilder.and was called
        verify(criteriaBuilder, atLeastOnce()).and(any(Predicate.class), any(Predicate.class));
    }

    @Test
    void testApplyEntidadFilter() {
        // Mock the nested paths
        Path<Object> actividadEntidadPuertoPath = mock(Path.class);
        Path<Object> entidadPath = mock(Path.class);
        Path<Object> idPath = mock(Path.class);

        when(root.get(Constants.ACTIVIDAD_ENTIDAD_PUERTO)).thenReturn(actividadEntidadPuertoPath);
        when(actividadEntidadPuertoPath.get(Constants.ENTIDAD)).thenReturn(entidadPath);
        when(entidadPath.get(Constants.ID)).thenReturn(idPath);

        // Mock the criteriaBuilder.equal call to return a non-null Predicate
        Predicate mockPredicate = mock(Predicate.class);
        when(criteriaBuilder.equal(idPath, 1)).thenReturn(mockPredicate);

        // Call the method under test
        Predicate result = baseSpecification.applyEntidadFilterDDJJ(1, root, criteriaBuilder, predicate);

        // Verify the result is not null
        assertNotNull(result);

        // Verify that criteriaBuilder.and was called
        verify(criteriaBuilder, atLeastOnce()).and(predicate, mockPredicate);
    }

    @Test
    void testApplyAgenciaFilter() {
        // Mock the nested paths
        Path<Object> agenciaPath = mock(Path.class);
        Path<Object> idPath = mock(Path.class);

        when(root.get(Constants.AGENCIA)).thenReturn(agenciaPath);
        when(agenciaPath.get(Constants.ID)).thenReturn(idPath);

        // Mock the criteriaBuilder.equal call to return a non-null Predicate
        Predicate mockPredicate = mock(Predicate.class);
        when(criteriaBuilder.equal(idPath, 1)).thenReturn(mockPredicate);

        // Call the method under test
        Predicate result = baseSpecification.applyAgenciaFilterDDJJ(1, root, criteriaBuilder, predicate);

        // Verify the result is not null
        assertNotNull(result);

        // Verify that criteriaBuilder.and was called
        verify(criteriaBuilder, atLeastOnce()).and(predicate, mockPredicate);
    }

    @Test
    void testApplyNombreNaveFilter() {
        // Mock the nested paths
        Path<Object> escalaPath = mock(Path.class);
        Path<Object> fichaTecnicaDetInPath = mock(Path.class);
        Path<Object> nombreNavePath = mock(Path.class); // Keep as Path<Object>

        when(root.get(Constants.ESCALA)).thenReturn(escalaPath);
        when(escalaPath.get(Constants.FICHA_TECNICA_DET_IN)).thenReturn(fichaTecnicaDetInPath);
        when(fichaTecnicaDetInPath.get(Constants.NOMBRE_NAVE)).thenReturn(nombreNavePath);

        // Mock the criteriaBuilder.like call to return a non-null Predicate
        Predicate mockPredicate = mock(Predicate.class);
        when(criteriaBuilder.like(any(Expression.class), eq("%nave%"))).thenReturn(mockPredicate);

        // Mock the criteriaBuilder.lower call to return the same Expression
        Expression<String> lowerExpression = mock(Expression.class);
        when(criteriaBuilder.lower(any(Expression.class))).thenReturn(lowerExpression);

        // Call the method under test
        Predicate result = baseSpecification.applyNombreNaveFilter("Nave", root, criteriaBuilder, predicate);

        // Verify the result is not null
        assertNotNull(result, "Result should not be null");

        // Verify that criteriaBuilder.like was called with the correct arguments
        verify(criteriaBuilder, atLeastOnce()).like(lowerExpression, "%nave%");

        // Verify that criteriaBuilder.and was called
        verify(criteriaBuilder, atLeastOnce()).and(predicate, mockPredicate);
    }
}
