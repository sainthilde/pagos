package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.specification;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.constants.Constants;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.pago.Tramite;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.GetTramiteQueryParamsDto;

class TramiteSpecificationTest {

    private TramiteSpecification tramiteSpecification;

    @Mock
    private Root<Tramite> root;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private CriteriaQuery<?> query;

    @Mock
    private Predicate predicate;

    @Mock
    private Join<Object, Object> declaracionJuradaJoin;

    @Mock
    private Path<Object> estadoTramitePath;

    @Mock
    private Path<String> numeroSucePath;

    @Mock
    private Path<String> numeroTramiteEntidadPath;

    @Mock
    private Path<String> cpbPath;

    @Mock
    private Path<String> tupaPath;

    @Mock
    private Path<Object> fechaTramitePath;

    @Mock
    private Path<Object> fueTramiteManualPath;

    @Mock
    private Path<Object> fechaTramiteManualPath;

    @Mock
    private Subquery<LocalDateTime> subquery;

    @Mock
    private Expression<String> lowerTupaExpression;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        tramiteSpecification = spy(new TramiteSpecification());

        // Configuración común para métodos heredados
        doAnswer(invocation -> invocation.getArgument(3))
                .when(tramiteSpecification).applyNombreNaveFilter(any(), any(), any(), any());
        doAnswer(invocation -> invocation.getArgument(3))
                .when(tramiteSpecification).applyEntidadFilterDDJJ(any(), any(), any(), any());
        doAnswer(invocation -> invocation.getArgument(3))
                .when(tramiteSpecification).applyAgenciaFilterDDJJ(any(), any(), any(), any());
        doAnswer(invocation -> invocation.getArgument(3))
                .when(tramiteSpecification).applyDueFilter(any(), any(), any(), any());
        doAnswer(invocation -> invocation.getArgument(2))
                .when(tramiteSpecification).applyDefaultFilters(any(), any(), any());

        // Configuración común para CriteriaBuilder
        when(criteriaBuilder.conjunction()).thenReturn(predicate);
        when(criteriaBuilder.and(any(Predicate.class), any(Predicate.class))).thenReturn(predicate);
        when(criteriaBuilder.and(any(Predicate.class), any(Predicate.class), any(Predicate.class)))
                .thenReturn(predicate);
        when(criteriaBuilder.equal(any(), any())).thenReturn(predicate);
        when(criteriaBuilder.notEqual(any(), any())).thenReturn(predicate);
        when(criteriaBuilder.greaterThanOrEqualTo(any(Expression.class), any(Comparable.class))).thenReturn(predicate);
        when(criteriaBuilder.lessThanOrEqualTo(any(Expression.class), any(Comparable.class))).thenReturn(predicate);
        when(criteriaBuilder.isNull(any())).thenReturn(predicate);
        when(criteriaBuilder.isNotNull(any())).thenReturn(predicate);
        when(criteriaBuilder.or(any(Predicate.class), any(Predicate.class))).thenReturn(predicate);
        when(criteriaBuilder.lower(any())).thenReturn(lowerTupaExpression);
        when(criteriaBuilder.like(any(Expression.class), any(String.class))).thenReturn(predicate);

        // Configuración de paths
        when(root.get(Constants.ESTADO_TRAMITE)).thenReturn(estadoTramitePath);
        when(root.<String>get(Constants.NUMERO_SUCE)).thenReturn(numeroSucePath);
        when(root.<String>get(Constants.NUMERO_TRAMITE_ENTIDAD)).thenReturn(numeroTramiteEntidadPath);
        when(root.<String>get(Constants.TUPA)).thenReturn(tupaPath);
        when(root.get(Constants.FECHA_TRAMITE)).thenReturn(fechaTramitePath);
        when(root.get(Constants.FUE_TRAMITE_MANUAL)).thenReturn(fueTramiteManualPath);
        when(root.get(Constants.FECHA_TRAMITE_MANUAL)).thenReturn(fechaTramiteManualPath);

        // Configuración de joins
        Join<Object, Object> ordenesDePagoJoin = mock(Join.class);
        when(root.join(Constants.ORDENES_DE_PAGO)).thenReturn(ordenesDePagoJoin);
        // tell Mockito that getting PP_CPB returns your Path<String>:
        when(ordenesDePagoJoin.<String>get(Constants.PP_CPB)).thenReturn(cpbPath);
        Join<Object, Object> declaracionesJuradasJoin = mock(Join.class);
        when(root.join(Constants.DECLARACIONES_JURADAS)).thenReturn(declaracionesJuradasJoin);
        when(declaracionesJuradasJoin.get(Constants.FECHA_SOLICITUD_DDJJ)).thenReturn(mock(Path.class));

        // Configuración de subquery
        when(query.subquery(LocalDateTime.class)).thenReturn(subquery);
        when(subquery.from(Tramite.class)).thenReturn(root);
        when(subquery.select(any())).thenReturn(subquery);
        when(subquery.where(any(Predicate.class))).thenReturn(subquery);
    }

    @Test
    void testGetTramites() {
        GetTramiteQueryParamsDto params = new GetTramiteQueryParamsDto();
        Specification<Tramite> spec = tramiteSpecification.getTramites(params);

        assertNotNull(spec);
    }



    @Test
    void testApplyEstadoTramiteFilter_WithEstadoTramite() {
        GetTramiteQueryParamsDto params = new GetTramiteQueryParamsDto();
        params.setEstadoTramite("AP");

        when(criteriaBuilder.equal(estadoTramitePath, "AP")).thenReturn(predicate);

        Predicate result = tramiteSpecification.applyEstadoTramiteFilter(params, root, criteriaBuilder, predicate);

        assertNotNull(result);
        verify(criteriaBuilder).equal(estadoTramitePath, "AP");
        verify(criteriaBuilder).and(predicate, predicate);
    }

    @Test
    void testApplyNumeroSuceFilter_WithNumeroSuce() {
        GetTramiteQueryParamsDto params = new GetTramiteQueryParamsDto();
        params.setNumeroSuce("12345");

        // stub a LIKE with wildcards
        when(criteriaBuilder.like(
                numeroSucePath,
                Constants.WILDCARD + "12345" + Constants.WILDCARD))
                .thenReturn(predicate);

        Predicate result = tramiteSpecification
                .applyNumeroSuceFilter(params, root, criteriaBuilder, predicate);

        assertNotNull(result);

        // verify we fetched the path
        verify(root).get(Constants.NUMERO_SUCE);
        // verify LIKE was called with "%12345%"
        verify(criteriaBuilder).like(
                numeroSucePath,
                Constants.WILDCARD + "12345" + Constants.WILDCARD);
        // and that it got wrapped in an and(...)
        verify(criteriaBuilder).and(predicate, predicate);
    }

    @Test
    void testApplyNumeroTramiteFilter_WithNumeroTramite() {
        GetTramiteQueryParamsDto params = new GetTramiteQueryParamsDto();
        params.setNumeroTramite("TRAM-001");

        // stub a LIKE with wildcards
        when(criteriaBuilder.like(numeroTramiteEntidadPath, "%TRAM-001%"))
                .thenReturn(predicate);

        Predicate result = tramiteSpecification
                .applyNumeroTramiteFilter(params, root, criteriaBuilder, predicate);

        assertNotNull(result);

        // verify we pulled the path as a String
        verify(root).get(Constants.NUMERO_TRAMITE_ENTIDAD);
        // now verify like(...) was called with the wildcarded value
        verify(criteriaBuilder).like(numeroTramiteEntidadPath, "%TRAM-001%");
        // and that it got wrapped in an and(...)
        verify(criteriaBuilder).and(predicate, predicate);
    }

    @Test
    void testApplyCpbFilter_WithCpb() {
        GetTramiteQueryParamsDto params = new GetTramiteQueryParamsDto();
        params.setCpb("CPB-001");

        // now criteriaBuilder.like(cpbPath, "%CPB-001%") lines up
        when(criteriaBuilder.like(cpbPath, "%CPB-001%")).thenReturn(predicate);

        Predicate result = tramiteSpecification
                .applyCpbFilter(params, root, criteriaBuilder, predicate);

        assertNotNull(result);
        verify(root).join(Constants.ORDENES_DE_PAGO);
        verify(criteriaBuilder).like(cpbPath, "%CPB-001%");
        verify(criteriaBuilder).and(predicate, predicate);
    }

    @Test
    void testApplyDeclaracionJuradaDateFilters_WithoutDates() {
        GetTramiteQueryParamsDto params = new GetTramiteQueryParamsDto();

        Predicate result = tramiteSpecification.applyDateFilters(
                params, root, criteriaBuilder, predicate);

        assertSame(predicate, result);
        verify(query, never()).subquery(LocalDateTime.class);
    }

    @Test
    void testApplyExpedientesFilter_WithExpedientesFalse() {
        GetTramiteQueryParamsDto params = new GetTramiteQueryParamsDto();
        params.setExpedientes(false);

        Predicate result = tramiteSpecification.applyExpedientesFilter(params, root, criteriaBuilder, predicate);

        assertSame(predicate, result);
        verify(criteriaBuilder, never()).or(any(Predicate.class), any(Predicate.class));
    }

    @Test
    void testApplyTupaFilter_WithTupa() {
        GetTramiteQueryParamsDto params = new GetTramiteQueryParamsDto();
        params.setTupa("TestTupa");

        when(criteriaBuilder.lower(tupaPath)).thenReturn(lowerTupaExpression);
        when(criteriaBuilder.equal(lowerTupaExpression, "testtupa")).thenReturn(predicate);

        Predicate result = tramiteSpecification.applyTupaFilter(params, root, criteriaBuilder, predicate);

        assertNotNull(result);
        verify(criteriaBuilder).lower(tupaPath);
        verify(criteriaBuilder).equal(lowerTupaExpression, "testtupa");
        verify(criteriaBuilder).and(predicate, predicate);
    }

    @Test
    void testApplyTupaFilter_WithoutTupa() {
        GetTramiteQueryParamsDto params = new GetTramiteQueryParamsDto();

        Predicate result = tramiteSpecification.applyTupaFilter(params, root, criteriaBuilder, predicate);

        assertSame(predicate, result);
        verify(criteriaBuilder, never()).lower(tupaPath);
    }

    @Test
    void testApplyFilters() {
        GetTramiteQueryParamsDto params = new GetTramiteQueryParamsDto();

        // Configurar los mocks para que los métodos devuelvan el predicate esperado
        doReturn(predicate).when(tramiteSpecification).applyEstadoTramiteFilter(any(), any(), any(), any());
        doReturn(predicate).when(tramiteSpecification).applyNumeroSuceFilter(any(), any(), any(), any());
        doReturn(predicate).when(tramiteSpecification).applyNumeroTramiteFilter(any(), any(), any(), any());
        doReturn(predicate).when(tramiteSpecification).applyCpbFilter(any(), any(), any(), any());
        doReturn(predicate).when(tramiteSpecification).applyDateFilters(any(), any(), any(),
                any());
        doReturn(predicate).when(tramiteSpecification).applyExpedientesFilter(any(), any(), any(), any());
        doReturn(predicate).when(tramiteSpecification).applyTupaFilter(any(), any(), any(), any());
        doReturn(predicate).when(tramiteSpecification).applyDefaultFilters(any(), any(), any());

        Predicate result = tramiteSpecification.applyFilters(params, root, criteriaBuilder, predicate);

        assertNotNull(result);

        // Verificar que se llaman los métodos esperados
        verify(tramiteSpecification).applyEstadoTramiteFilter(params, root, criteriaBuilder, predicate);
        verify(tramiteSpecification).applyNumeroSuceFilter(params, root, criteriaBuilder, predicate);
        verify(tramiteSpecification).applyNumeroTramiteFilter(params, root, criteriaBuilder, predicate);
        verify(tramiteSpecification).applyCpbFilter(params, root, criteriaBuilder, predicate);
        verify(tramiteSpecification).applyDateFilters(params, root, criteriaBuilder, predicate);
        verify(tramiteSpecification).applyExpedientesFilter(params, root, criteriaBuilder, predicate);
        verify(tramiteSpecification).applyTupaFilter(params, root, criteriaBuilder, predicate);
        verify(tramiteSpecification).applyDefaultFilters(root, criteriaBuilder, predicate);
    }

    @Test
    void testGetTramites_IntegrationWithApplyFilters() {
        GetTramiteQueryParamsDto params = new GetTramiteQueryParamsDto();

        Specification<Tramite> spec = tramiteSpecification.getTramites(params);
        assertNotNull(spec);

        // Ejecutar la specification para verificar que no hay errores
        spec.toPredicate(root, query, criteriaBuilder);
    }
}
