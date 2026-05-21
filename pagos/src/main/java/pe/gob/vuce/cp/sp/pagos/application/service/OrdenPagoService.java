package pe.gob.vuce.cp.sp.pagos.application.service;

import java.util.List;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import pe.gob.vuce.cp.sp.pagos.domain.model.OrdenPago;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.CreateOrdenPagoUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.SelectOrdenPagoUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.UpdateOrdenPagoUseCase;

/**
 * Servicio para gestionar las operaciones relacionadas con la Orden de Pago.
 * Esta clase implementa los casos de uso para crear, seleccionar y actualizar
 * órdenes de pago.
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Service
@AllArgsConstructor
public class OrdenPagoService {

    private final CreateOrdenPagoUseCase createOrdenPagoUseCase;
    private final SelectOrdenPagoUseCase selectOrdenPagoUseCase;
    private final UpdateOrdenPagoUseCase updateOrdenPagoUseCase;
    /**
     * Método responsable de crear una nueva Orden de Pago.
     *
     * @param ordenPago la orden de pago a crear (required).
     * @return la OrdenPago creada.
     */
    public OrdenPago createOrdenPago(OrdenPago ordenPago) {
        return createOrdenPagoUseCase.createOrdenPago(ordenPago);
    }

    /**
     * Método responsable de buscar una Orden de Pago por su ID.
     * Este método se reintenta hasta tres veces en caso de fallos, con un retraso
     * de 5 segundos entre intentos.
     *
     * @param ordenPagoId ID de la orden de pago a buscar (required).
     * @return la OrdenPago correspondiente al ID proporcionado.
     */
    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 5000))
    public OrdenPago findById(Integer ordenPagoId) {
        return selectOrdenPagoUseCase.findById(ordenPagoId);
    }

    /**
     * Método responsable de buscar órdenes de pago basadas en el ID de escala y el ID del documento.
     *
     * @param escalaId  ID de la escala (required).
     * @param documentId ID del documento (required).
     * @return una lista de OrdenPago que coinciden con los criterios proporcionados.
     */
    public List<OrdenPago> findByEscalaIdAndDocumentoId(Integer escalaId, Integer documentId) {
        return selectOrdenPagoUseCase.findByEscalaIdAndDocumentoId(escalaId, documentId);
    }

    /**
     * Método responsable de buscar una Orden de Pago utilizando el ID de pago interna.
     *
     * @param ordenPagoInterna ID de la orden de pago interna (required).
     * @return la OrdenPago correspondiente al ID interno proporcionado.
     */
    public OrdenPago findByPpIdOrdenPagoInterna(Integer ordenPagoInterna) {
        return selectOrdenPagoUseCase.findByPpIdOrdenPagoInterna(ordenPagoInterna);
    }

    /**
     * Método responsable de actualizar una Orden de Pago existente.
     *
     * @param ordenPago la orden de pago con los datos actualizados (required).
     * @return la OrdenPago actualizada.
     */
    public OrdenPago updateOrdenPago(OrdenPago ordenPago) {
        return updateOrdenPagoUseCase.updateOrdenPago(ordenPago);
    }
}
