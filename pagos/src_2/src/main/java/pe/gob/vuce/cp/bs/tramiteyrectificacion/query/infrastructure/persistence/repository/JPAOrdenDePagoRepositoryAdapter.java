package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.OrdenDePagoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.port.out.OrdenDePagoRepositoryPort;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.mapper.OrdenDePagoModelMapper;

import java.util.List;

/**
 * Implementación del puerto de repositorio para Orden de Pago utilizando JPA.
 * Actúa como un adaptador que convierte las entidades persistentes en modelos
 * de dominio.
 *
 * @project cp-api-bs-tramiteyrectificacion-query
 * @autor Luis Francisco Huertas Mostacero
 * @date 24/08/2024
 */
@Repository
@AllArgsConstructor
public class JPAOrdenDePagoRepositoryAdapter implements OrdenDePagoRepositoryPort {
    private final JPAOrdenDePagoRepository jpaOrdenDePagoRepository;
    private final OrdenDePagoModelMapper ordenDePagoModelMapper;

    @Override
    public List<OrdenDePagoModel> findOrdenesDePago(Integer escalaId, Integer documentoId, String rucAgente,
            String estadoOrdenPago) {

        if (escalaId != null && estadoOrdenPago != null) {
            return ordenDePagoModelMapper.toOrdenDePagoModel(
                    jpaOrdenDePagoRepository.findAllByEscalaIdAndEstadoOrdenPago(escalaId, estadoOrdenPago));
        }
        return ordenDePagoModelMapper.toOrdenDePagoModel(
                jpaOrdenDePagoRepository.findByEscalaIdAndDocumentoIdAndRucAgente(escalaId, documentoId, rucAgente));

    }

}
