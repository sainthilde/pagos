package pe.gob.vuce.cp.sp.pagos.domain.port.in;

import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.domain.model.OrdenPago;

import java.util.List;

/**
 * Caso de uso para la selección y búsqueda de órdenes de pago.
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Component
public interface SelectOrdenPagoUseCase {

    /**
     * Busca una orden de pago por su identificador.
     *
     * @param ordenPagoId  Identificador de la orden de pago.
     * @return             La orden de pago si se encuentra.
     */
    OrdenPago findById(Integer ordenPagoId);

    /**
     * Busca órdenes de pago por escala y documento.
     *
     * @param escalaId     Identificador de la escala.
     * @param documentId   Identificador del documento.
     * @return             Lista de órdenes de pago encontradas.
     */
    List<OrdenPago> findByEscalaIdAndDocumentoId(Integer escalaId, Integer documentId);

    /**
     * Busca una orden de pago por su identificador interno.
     *
     * @param ordenPagoInterna  Identificador interno de la orden de pago.
     * @return                  La orden de pago encontrada.
     */
    OrdenPago findByPpIdOrdenPagoInterna(Integer ordenPagoInterna);
}
