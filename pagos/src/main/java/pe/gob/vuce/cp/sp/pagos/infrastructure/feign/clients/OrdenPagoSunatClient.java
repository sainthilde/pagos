package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import pe.gob.vuce.cp.framework.globallogger.annotation.Loggable;
import pe.gob.vuce.cp.framework.globallogger.constants.LogTypes;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ArchivoResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.OrdenPagoRequestDTO;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.PasarelaEstatusResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.PaymentMethodResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.response.OrdenPagoResponseDTO;

/**
 * Cliente Feign para interactuar con la API de orden de pago de SUNAT.
 * 
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@FeignClient(name = "ordenPagoClient", url = "${feign.client.ordenpago-sunat-api.base-url}")
public interface OrdenPagoSunatClient {

    /**
     * Crea una orden de pago en SUNAT.
     *
     * @param ordenPagoRequestDto DTO con los datos de la orden de pago.
     * @return Respuesta con los detalles de la orden de pago creada.
     */
    @Loggable(category = LogTypes.FEIGN)
    @PostMapping("/pagos/ordenes-pago/sunat")
    OrdenPagoResponseDTO createOrdenPago(@RequestBody OrdenPagoRequestDTO ordenPagoRequestDto);

    /**
     * Obtiene los métodos de pago configurados para una entidad.
     *
     * @param canalId   ID del canal.
     * @param entidadId ID de la entidad.
     * @return Lista de métodos de pago disponibles.
     */
    @Loggable(category = LogTypes.FEIGN)
    @GetMapping("/configuracion/formas-pago")
    List<PaymentMethodResponse> getPaymentMethods(@RequestParam("canalId") Integer canalId,
            @RequestParam("entidadId") Integer entidadId);

    /**
     * Obtiene el archivo relacionado con una orden de pago.
     *
     * @param ordenPagoId ID de la orden de pago.
     * @return Respuesta con el archivo.
     */
    @Loggable(category = LogTypes.FEIGN)
    @GetMapping("/pagos/ordenes-pago/{ordenPagoId}/archivo")
    ArchivoResponse getArchivo(@PathVariable("ordenPagoId") Integer ordenPagoId);

    /**
     * Cancela una orden de pago en SUNAT.
     *
     * @param ordenPagoId ID de la orden de pago.
     * @return Respuesta con los detalles de la orden de pago cancelada.
     */
    @Loggable(category = LogTypes.FEIGN)
    @PutMapping("/pagos/ordenes-pago/{ordenPagoId}/anulacion")
    OrdenPagoResponseDTO cancelarOrdenPago(@PathVariable("ordenPagoId") Integer ordenPagoId);

    @Loggable(category = LogTypes.FEIGN)
    @GetMapping("/pagos/ordenes-pago/{ordenPagoId}/status")
    PasarelaEstatusResponse getStatus(@PathVariable("ordenPagoId") Integer ordenPagoId);

}
