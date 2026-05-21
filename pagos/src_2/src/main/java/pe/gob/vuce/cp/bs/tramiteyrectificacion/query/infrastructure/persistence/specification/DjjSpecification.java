package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.constants.Constants;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.pago.DeclaracionJurada;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.GetDjjQueryParamsDto;

@Component
public class DjjSpecification extends BaseSpecification<DeclaracionJurada> {

    public Specification<DeclaracionJurada> getDjjs(GetDjjQueryParamsDto params) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            predicate = applyFilters(params, root, criteriaBuilder, predicate);
            return predicate;
        };
    }

    public Predicate applyFilters(GetDjjQueryParamsDto params, Root<DeclaracionJurada> root,
            CriteriaBuilder criteriaBuilder, Predicate predicate) {

        predicate = applyDueFilter(params.getDue(), root, criteriaBuilder, predicate);
        predicate = applyDateFilters(params.getDjjFechaDesde(), params.getDjjFechaHasta(), root, criteriaBuilder,
                predicate);
        predicate = applyEntidadFilterDDJJ(params.getEntidad(), root, criteriaBuilder, predicate);
        predicate = applyAgenciaRucFilterDDJJ(params.getAgenciaRuc(), root, criteriaBuilder, predicate);
        predicate = applyNombreNaveFilter(params.getNombreNave(), root, criteriaBuilder, predicate);
        predicate = applyDefaultFilters(root, criteriaBuilder, predicate);
        predicate = applyNumeroDDJJ(params, root, criteriaBuilder, predicate);

        return predicate;
    }

    public Predicate applyNumeroDDJJ(GetDjjQueryParamsDto params, Root<DeclaracionJurada> root,
            CriteriaBuilder criteriaBuilder, Predicate predicate) {
        if (params.getNumeroDDJJ() != null) {
            return criteriaBuilder.and(predicate,
                    criteriaBuilder.like(root.get(Constants.NUMERO_DECLARACION_JURADA),
                            Constants.WILDCARD + params.getNumeroDDJJ() + Constants.WILDCARD));
        }
        return predicate;
    }

    public Predicate applyEntidadFilterDDJJ(Integer entidad,
            Root<DeclaracionJurada> root,
            CriteriaBuilder cb,
            Predicate predicate) {
        if (entidad != null) {
            return cb.and(
                    predicate,
                    cb.equal(root.get(Constants.ENTIDAD_ID), entidad));
        }
        return predicate;
    }

    public Predicate applyAgenciaRucFilterDDJJ(String agenciaRuc,
            Root<DeclaracionJurada> root,
            CriteriaBuilder cb,
            Predicate predicate) {
        if (agenciaRuc != null) {
            return cb.and(
                    predicate,
                    cb.equal(root.get(Constants.RUC_AGENTE), agenciaRuc));
        }
        return predicate;
    }
}
