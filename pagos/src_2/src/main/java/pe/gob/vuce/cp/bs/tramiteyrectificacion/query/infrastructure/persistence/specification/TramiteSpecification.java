package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.constants.Constants;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.pago.Tramite;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.GetTramiteQueryParamsDto;

@Component
public class TramiteSpecification extends BaseSpecification<Tramite> {

    public Specification<Tramite> getTramites(GetTramiteQueryParamsDto params) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            predicate = applyFilters(params, root, criteriaBuilder, predicate);

            return predicate;
        };
    }

    public Predicate applyFilters(GetTramiteQueryParamsDto params, Root<Tramite> root, CriteriaBuilder criteriaBuilder,
            Predicate predicate) {

        predicate = applyNombreNaveFilter(params.getNombreNave(), root, criteriaBuilder, predicate);
        predicate = applyEntidadFilterDDJJ(params.getEntidad(), root, criteriaBuilder, predicate);
        predicate = applyEstadoTramiteFilter(params, root, criteriaBuilder, predicate);
        predicate = applyNumeroSuceFilter(params, root, criteriaBuilder, predicate);
        predicate = applyNumeroTramiteFilter(params, root, criteriaBuilder, predicate);
        predicate = applyCpbFilter(params, root, criteriaBuilder, predicate);
        predicate = applyAgenciaFilterDDJJ(params.getAgencia(), root, criteriaBuilder, predicate);
        predicate = applyDateFilters(params, root, criteriaBuilder, predicate);
        predicate = applyDueFilter(params.getDue(), root, criteriaBuilder, predicate);
        predicate = applyExpedientesFilter(params, root, criteriaBuilder, predicate);
        predicate = applyTupaFilter(params, root, criteriaBuilder, predicate);
        predicate = applyDefaultFilters(root, criteriaBuilder, predicate);

        return predicate;
    }

    public Predicate applyEstadoTramiteFilter(GetTramiteQueryParamsDto params, Root<Tramite> root,
            CriteriaBuilder criteriaBuilder, Predicate predicate) {
        if (params.getEstadoTramite() != null) {
            return criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get(Constants.ESTADO_TRAMITE), params.getEstadoTramite()));
        }
        return predicate;
    }

    public Predicate applyNumeroSuceFilter(GetTramiteQueryParamsDto params, Root<Tramite> root,
            CriteriaBuilder criteriaBuilder, Predicate predicate) {
        if (params.getNumeroSuce() != null) {
            return criteriaBuilder.and(predicate,
                    criteriaBuilder.like(root.get(Constants.NUMERO_SUCE),
                            Constants.WILDCARD + params.getNumeroSuce() + Constants.WILDCARD));
        }
        return predicate;
    }

    public Predicate applyNumeroTramiteFilter(GetTramiteQueryParamsDto params, Root<Tramite> root,
            CriteriaBuilder criteriaBuilder, Predicate predicate) {
        if (params.getNumeroTramite() != null) {
            return criteriaBuilder.and(predicate,
                    criteriaBuilder.like(root.get(Constants.NUMERO_TRAMITE_ENTIDAD),
                            Constants.WILDCARD + params.getNumeroTramite() + Constants.WILDCARD));
        }
        return predicate;
    }

    public Predicate applyCpbFilter(GetTramiteQueryParamsDto params, Root<Tramite> root,
            CriteriaBuilder criteriaBuilder, Predicate predicate) {
        if (params.getCpb() != null) {
            return criteriaBuilder.and(predicate,
                    criteriaBuilder.like(root.join(Constants.ORDENES_DE_PAGO).get(Constants.PP_CPB),
                            Constants.WILDCARD + params.getCpb() + Constants.WILDCARD));
        }
        return predicate;
    }

    public Predicate applyDateFilters(GetTramiteQueryParamsDto params, Root<Tramite> root,
            CriteriaBuilder criteriaBuilder, Predicate predicate) {
        if (params.getTramiteFechaDesde() != null && params.getTramiteFechaHasta() != null) {
            LocalDate startDate = LocalDate.parse(params.getTramiteFechaDesde());
            LocalDate endDate = LocalDate.parse(params.getTramiteFechaHasta());

            // Apply filter directly to fecha_tramite field
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.greaterThanOrEqualTo(root.get(Constants.FECHA_TRAMITE), startDate));
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.lessThan(root.get(Constants.FECHA_TRAMITE), endDate.atStartOfDay().plusDays(1)));
        }
        return predicate;
    }

    public Predicate applyExpedientesFilter(GetTramiteQueryParamsDto params, Root<Tramite> root,
            CriteriaBuilder criteriaBuilder, Predicate predicate) {
        if (Boolean.TRUE.equals(params.getExpedientes())) {
            LocalDateTime thirtyDaysAgo = LocalDateTime.now().minus(30, ChronoUnit.DAYS);
            Predicate condition1 = criteriaBuilder.and(
                    criteriaBuilder.isNull(root.get(Constants.NUMERO_TRAMITE_ENTIDAD)),
                    criteriaBuilder.greaterThanOrEqualTo(root.get(Constants.FECHA_TRAMITE), thirtyDaysAgo));

            LocalDateTime sevenDaysAgo = LocalDateTime.now().minus(7, ChronoUnit.DAYS);
            Predicate condition2 = criteriaBuilder.and(
                    criteriaBuilder.isNotNull(root.get(Constants.NUMERO_TRAMITE_ENTIDAD)),
                    criteriaBuilder.equal(root.get(Constants.FUE_TRAMITE_MANUAL), true),
                    criteriaBuilder.greaterThanOrEqualTo(root.get(Constants.FECHA_TRAMITE_MANUAL), sevenDaysAgo));

            Predicate expedientesPredicate = criteriaBuilder.or(condition1, condition2);

            return criteriaBuilder.and(predicate, expedientesPredicate);
        }
        return predicate;
    }

    public Predicate applyTupaFilter(GetTramiteQueryParamsDto params, Root<Tramite> root,
            CriteriaBuilder criteriaBuilder, Predicate predicate) {
        if (params.getTupa() != null) {
            return criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(criteriaBuilder.lower(root.get(Constants.TUPA)),
                            params.getTupa().toLowerCase()));
        }
        return predicate;
    }
}
