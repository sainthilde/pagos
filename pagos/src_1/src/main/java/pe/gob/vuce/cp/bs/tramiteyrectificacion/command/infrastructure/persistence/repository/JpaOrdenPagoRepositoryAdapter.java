package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants.Constants;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.OrdenDePagoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.OrdenPagoRepositoryPort;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.mapper.OrdenPagoMapper;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity.OrdenDePago;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class JpaOrdenPagoRepositoryAdapter implements OrdenPagoRepositoryPort {

    private final OrderPagoRepository orderPagoRepository;
    private final OrdenPagoMapper ordenPagoMapper;

    /**
     * Busca una orden de pago por su identificador único.
     *
     * @param id ID de la orden de pago.
     * @return Un {@code Optional} conteniendo la orden de pago si existe.
     */
    @Override
    public Optional<OrdenDePagoModel> findById(Integer id) {
        return orderPagoRepository.findById(id).map(ordenPagoMapper::entityToModel);
    }

    /**
     * Persiste una nueva orden de pago en la base de datos.
     *
     * @param ordenDePago Modelo de orden de pago a guardar.
     * @return Orden de pago persistida con sus datos actualizados.
     */
    @Override
    public OrdenDePagoModel save(OrdenDePagoModel ordenDePago) {
        OrdenDePago ordenDePagoEntity = ordenPagoMapper.modelToEntity(ordenDePago);
        return ordenPagoMapper.entityToModel(orderPagoRepository.save(ordenDePagoEntity));
    }

    /**
     * Actualiza una orden de pago, validando si se debe modificar
     * el campo {@code cancelarDestinoDelPago}.
     *
     * @param ordenPagoModel Orden de pago a actualizar.
     * @return Modelo actualizado de la orden de pago.
     */
    @Override
    public OrdenDePagoModel updateV2(OrdenDePagoModel ordenPagoModel) {
        Optional<OrdenDePago> ordenDePagoOptional = orderPagoRepository.findById(ordenPagoModel.getId());

        if (ordenDePagoOptional.isPresent() && ordenPagoModel.getCancelarDestinoDelPago() != null ) {
            OrdenDePago ordenDePago = ordenDePagoOptional.get();
            ordenDePago.setCancelarDestinoDelPago(ordenPagoModel.getCancelarDestinoDelPago());
            return ordenPagoMapper.entityToModel(orderPagoRepository.save(
                    ordenDePago));
        }
        return ordenPagoMapper.entityToModel(orderPagoRepository.save(
                ordenPagoMapper.modelToEntity(ordenPagoModel)));
    }

    /**
     * Busca todas las órdenes de pago asociadas a un trámite por ID.
     *
     * @param id ID del trámite.
     * @return Lista de órdenes de pago encontradas.
     */
    @Override
    public List<OrdenDePagoModel> findByTramiteTramiteId(Integer id) {
        return ordenPagoMapper.entityListToModelList(orderPagoRepository.findByTramiteTramiteId(id));
    }

    /**
     * Busca órdenes de pago asociadas a un trámite y con estados específicos.
     *
     * @param id ID del trámite.
     * @param estadoOrdenPagos Lista de estados permitidos.
     * @return Lista de órdenes de pago filtradas.
     */
    @Override
    public List<OrdenDePagoModel> findAllByTramiteTramiteIdAndEstadoOrdenPagoIn(Integer id,
            List<String> estadoOrdenPagos) {
        return ordenPagoMapper.entityListToModelList(
                orderPagoRepository.findAllByTramiteTramiteIdAndEstadoOrdenPagoIn(id, estadoOrdenPagos));
    }

    /**
     * Busca órdenes de pago asociadas a una escala y con estados específicos.
     *
     * @param id ID de la escala.
     * @param estadoOrdenPagos Lista de estados permitidos.
     * @return Lista de órdenes de pago encontradas.
     */
    @Override
    public List<OrdenDePagoModel> findAllByEscalaIdAndEstadoOrdenPagoIn(Integer id, List<String> estadoOrdenPagos) {
        return ordenPagoMapper.entityListToModelList(
                orderPagoRepository.findAllByEscalaIdAndEstadoOrdenPagoIn(id, estadoOrdenPagos));
    }

    /**
     * Busca todas las órdenes de pago asociadas a una escala específica.
     *
     * @param id ID de la escala.
     * @return Lista de órdenes de pago.
     */
    @Override
    public List<OrdenDePagoModel> findByEscalaId(Integer id) {
        return ordenPagoMapper.entityListToModelList(orderPagoRepository.findByEscalaId(id));
    }

    /**
     * Busca órdenes de pago asociadas a un documento, escala y RUC del agente,
     * considerando solo las que tienen estado activo por defecto.
     *
     * @param documentoId ID del documento.
     * @param escalaId ID de la escala.
     * @param rucAgente RUC del agente.
     * @return Lista de órdenes de pago encontradas.
     */
    @Override
    public List<OrdenDePagoModel> findByDocumentoIdAndEscalaIdAndRucAgente(Integer documentoId, Integer escalaId,
            String rucAgente) {
        return ordenPagoMapper.entityListToModelList(
                orderPagoRepository.findByDocumentoIdAndEscalaIdAndRucAgenteAndEstado(documentoId, escalaId, rucAgente, Constants.VALOR_POR_DEFECTO_ESTADO));
    }

}
