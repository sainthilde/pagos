package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.specification;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.constants.Constants;

public abstract class BaseSpecification<T> {

    public Predicate applyDefaultFilters(Root<T> root, CriteriaBuilder criteriaBuilder, Predicate predicate) {
        return criteriaBuilder.and(predicate,
                criteriaBuilder.equal(root.get(Constants.ESTADO), Constants.ESTADO_ACTIVO));
    }

    public Predicate applyDueFilter(String due, Root<T> root, CriteriaBuilder criteriaBuilder, Predicate predicate) {
        if (due != null) {
            String[] dueParts = due.split("-");
            predicate = applyDuePartsFilter(dueParts, root, criteriaBuilder, predicate);
        }
        return predicate;
    }

    public Predicate applyDuePartsFilter(String[] dueParts, Root<T> root, CriteriaBuilder criteriaBuilder,
            Predicate predicate) {
        if (dueParts.length >= 1 && !dueParts[0].isEmpty() && !Constants.NULL_VALUE.equals(dueParts[0])) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get(Constants.ESCALA).get(Constants.PUERTO_ESCALA_ID), dueParts[0]));
        }
        if (dueParts.length >= 2 && !dueParts[1].isEmpty() && !Constants.NULL_VALUE.equals(dueParts[1])) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get(Constants.ESCALA).get(Constants.ANNO_ESCALA), dueParts[1]));
        }
        if (dueParts.length == 3 && !dueParts[2].isEmpty() && !Constants.NULL_VALUE.equals(dueParts[2])) {
            try {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get(Constants.ESCALA).get(Constants.NUMERO_ESCALA),
                                Integer.parseInt(dueParts[2])));
            } catch (NumberFormatException e) {
                // Handle the case where dueParts[2] is not a valid integer
            }
        }
        return predicate;
    }

    public Predicate applyDateFilters(String fechaDesde, String fechaHasta, Root<T> root,
            CriteriaBuilder criteriaBuilder, Predicate predicate) {
        if (fechaDesde != null && fechaHasta != null) {
            LocalDate startDate = LocalDate.parse(fechaDesde);
            LocalDate endDate = LocalDate.parse(fechaHasta);

            predicate = criteriaBuilder.and(predicate,
              criteriaBuilder.greaterThanOrEqualTo(root.get(Constants.FECHA_SOLICITUD_DDJJ), startDate));
            predicate = criteriaBuilder.and(predicate,
              criteriaBuilder.lessThan(root.get(Constants.FECHA_SOLICITUD_DDJJ), endDate.atStartOfDay().plusDays(1)));
        }
      return predicate;
    }

    public Predicate applyEntidadFilterDDJJ(Integer entidad, Root<T> root, CriteriaBuilder criteriaBuilder,
            Predicate predicate) {
        if (entidad != null) {
            return criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(
                            root.get(Constants.ACTIVIDAD_ENTIDAD_PUERTO).get(Constants.ENTIDAD).get(Constants.ID),
                            entidad));
        }
        return predicate;
    }

    public Predicate applyAgenciaFilterDDJJ(Integer agencia, Root<T> root, CriteriaBuilder criteriaBuilder,
            Predicate predicate) {
        if (agencia != null) {
            return criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get(Constants.AGENCIA).get(Constants.ID), agencia));
        }
        return predicate;
    }

    public Predicate applyNombreNaveFilter(String nombreNave, Root<T> root, CriteriaBuilder criteriaBuilder,
            Predicate predicate) {
        if (nombreNave != null) {
            Path<String> nombreNavePath = root.get(Constants.ESCALA)
                    .get(Constants.FICHA_TECNICA_DET_IN)
                    .get(Constants.NOMBRE_NAVE);

            Predicate nombreNavePredicate = criteriaBuilder.like(
                    criteriaBuilder.lower(nombreNavePath),
                    "%" + nombreNave.toLowerCase() + "%");

            // Combine the new predicate with the existing predicate using AND
            predicate = criteriaBuilder.and(predicate, nombreNavePredicate);
        }
        return predicate;
    }
}
