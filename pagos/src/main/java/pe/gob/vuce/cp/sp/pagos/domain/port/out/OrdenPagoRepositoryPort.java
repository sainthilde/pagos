package pe.gob.vuce.cp.sp.pagos.domain.port.out;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import pe.gob.vuce.cp.sp.pagos.domain.model.OrdenPago;

/**
 * Puerto de repositorio para operaciones con órdenes de pago.
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Component
public interface OrdenPagoRepositoryPort {

    /**
     * Guarda una nueva orden de pago.
     *
     * @param ordenPago  Orden de pago a guardar.
     * @return           La orden de pago guardada.
     */
    OrdenPago save(OrdenPago ordenPago);

    /**
     * Busca una orden de pago por su identificador.
     *
     * @param ordenPagoId  Identificador de la orden de pago.
     * @return             La orden de pago encontrada.
     */
    OrdenPago findById(Integer ordenPagoId);

    /**
     * Busca una orden de pago por su identificador interno.
     *
     * @param ordenPagoInterna  Identificador interno de la orden de pago.
     * @return                  La orden de pago encontrada.
     */
    OrdenPago findByPpIdOrdenPagoInterna(Integer ordenPagoInterna);

    /**
     * Busca órdenes de pago por escala y documento.
     *
     * @param escalaId     Identificador de la escala.
     * @param documentId   Identificador del documento.
     * @return             Lista de órdenes de pago encontradas.
     */
    List<OrdenPago> findByEscalaIdAndDocumentoId(Integer escalaId, Integer documentId);

    /**
     * Actualiza una orden de pago existente.
     *
     * @param ordenPago  Orden de pago a actualizar.
     * @return           La orden de pago actualizada.
     */
    OrdenPago update(OrdenPago ordenPago);

    boolean existeEscalaTupaCero(Integer escalaId, BigDecimal gpMonto);
    boolean existeEscalaTupaCero(Integer escalaId, BigDecimal gpMonto, Integer documentoId);
}

