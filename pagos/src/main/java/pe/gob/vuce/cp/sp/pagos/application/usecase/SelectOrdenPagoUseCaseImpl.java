package pe.gob.vuce.cp.sp.pagos.application.usecase;

import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.AN;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.ANULADO;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.EX;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.EXTORNADO;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.NOTIFICATION_TOPIC_STATUS_EXPIRED;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.PAGADO;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.PAYMENT_ORDER_STATUS_EXPIRED;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.PG;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.PP;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pe.gob.vuce.cp.sp.pagos.domain.model.OrdenPago;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.SelectOrdenPagoUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.FeignPasarelaPort;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.OrdenPagoRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.PasarelaEstatusResponse;
/**
 * Implementación del caso de uso para seleccionar órdenes de pago.
 * Esta clase se encarga de la lógica necesaria para recuperar
 * órdenes de pago desde el repositorio correspondiente.
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Component
@AllArgsConstructor
@Log4j2
public class SelectOrdenPagoUseCaseImpl implements SelectOrdenPagoUseCase {

    private final OrdenPagoRepositoryPort ordenPagoRepositoryPort;
    private final FeignPasarelaPort feignPasarelaPort;
    /**
     * Método responsable de encontrar una OrdenPago por su ID.
     *
     * @param ordenPagoId ID de la OrdenPago a buscar (required).
     * @return la OrdenPago correspondiente al ID proporcionado, o null si no se encuentra.
     */
    @Override
    public OrdenPago findById(Integer ordenPagoId) {
        return ordenPagoRepositoryPort.findById(ordenPagoId);
    }

    /**
     * Método responsable de encontrar órdenes de pago por escala y documento.
     *
     * @param escalaId ID de la escala asociada a las órdenes de pago (required).
     * @param documentId ID del documento asociado a las órdenes de pago (required).
     * @return lista de órdenes de pago que coinciden con los parámetros proporcionados.
     */
    @Override
    public List<OrdenPago> findByEscalaIdAndDocumentoId(Integer escalaId, Integer documentId) {
        List<OrdenPago> ordenPagos = ordenPagoRepositoryPort
                .findByEscalaIdAndDocumentoId(escalaId, documentId);

        ordenPagos.forEach(this::procesarOrdenPago);

        return ordenPagos;
    }

    /**
     * Método responsable de encontrar una OrdenPago por su ID interna.
     *
     * @param ordenPagoInterna ID interna de la OrdenPago a buscar (required).
     * @return la OrdenPago correspondiente al ID interna proporcionado, o null si no se encuentra.
     */
    @Override
    public OrdenPago findByPpIdOrdenPagoInterna(Integer ordenPagoInterna) {
        return ordenPagoRepositoryPort.findByPpIdOrdenPagoInterna(ordenPagoInterna);
    }

    private Instant getFechaActual(String fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(fecha, formatter);
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant();
    }

    private void procesarOrdenPago(OrdenPago ordenPago) {
        if (!PP.equals(ordenPago.getEstado())) {
            return;
        }

        PasarelaEstatusResponse response =
                feignPasarelaPort.obtenerEstatusOrdenPago(ordenPago.getOrdenPagoInternaId());

        if (response == null) {
            return;
        }

        String estatus = response.getEstado();
        Instant fechaOperacion = getFechaActual(response.getFechaOperacion());

        Consumer<OrdenPago> accion = accionesPorEstatus(fechaOperacion).get(estatus);

        if (accion != null) {
            aplicarCambiosComunes(ordenPago, estatus);
            accion.accept(ordenPago);
            ordenPagoRepositoryPort.update(ordenPago);
        }
    }
    private Map<String, Consumer<OrdenPago>> accionesPorEstatus(Instant fechaOperacion) {
    return Map.of(
            PAGADO, op -> {
                op.setEstado(PG);
                op.setFechaPagado(fechaOperacion);
            },
            ANULADO, op -> {
                op.setEstado(AN);
                op.setFechaAnulacionCpb(fechaOperacion);
            },
            EXTORNADO, op -> {
                op.setEstado(EX);
                op.setFechaExtornoOrdenPago(fechaOperacion);
            },
            NOTIFICATION_TOPIC_STATUS_EXPIRED, op -> {
                op.setEstado(PAYMENT_ORDER_STATUS_EXPIRED);
                op.setFechaExtornoOrdenPago(fechaOperacion);
            }
        );
    }

    private void aplicarCambiosComunes(OrdenPago ordenPago, String estatus) {
        ordenPago.setPpEstadoCpbTexto(estatus);
        ordenPago.setUsuidModAud("PASARELADEPAGOS");
    }
}
