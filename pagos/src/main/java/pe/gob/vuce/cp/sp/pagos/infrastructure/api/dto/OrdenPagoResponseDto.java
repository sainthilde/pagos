package pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
/**
 * DTO (Data Transfer Object) que representa la respuesta de una orden de pago.
 *
 * Este registro se utiliza para transferir datos sobre órdenes de pago en el
 * sistema de pagos de SUNAT.
 *
 * Se excluyen los campos con valores nulos al serializarse a JSON.
 *
 * @param ordenPagoId                     Identificador único de la orden de pago.
 * @param entidadId                       Identificador de la entidad asociada a la orden de pago.
 * @param documentoId                     Identificador del documento relacionado con la orden de pago.
 * @param escalaId                        Identificador de la escala asociada a la orden de pago.
 * @param rucAgente                       RUC del agente responsable de la orden de pago.
 * @param codigoOrdenPago                 Código de la orden de pago.
 * @param monto                           Monto total de la orden de pago.
 * @param fechaGeneracion                 Fecha en la que se generó la orden de pago.
 * @param cpb                             Código de comprobante de pago asociado.
 * @param estado                          Estado actual de la orden de pago.
 * @param fechaVigencia                   Fecha de vigencia de la orden de pago.
 * @param fechaPagado                     Fecha en la que se realizó el pago.
 * @param fechaAnulacionCpb               Fecha en la que se anuló el comprobante de pago.
 * @param fechaExtornoOrdenPago           Fecha en la que se realizó el extorno de la orden de pago.
 * @param gpMonto                         Monto del gasto público asociado a la orden de pago.
 * @param fechaCreacionOrdenPago          Fecha de creación de la orden de pago.
 * @param ppFechaConfGeneracionCpb       Fecha de confirmación de generación del comprobante de pago.
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
public record OrdenPagoResponseDto(
        Integer ordenPagoId,
        Integer entidadId,
        Integer documentoId,
        Integer escalaId,
        String rucAgente,
        String codigoOrdenPago,
        Double monto,
        String fechaGeneracion,
        String cpb,
        String estado,
        String fechaVigencia,
        String fechaPagado,
        String fechaAnulacionCpb,
        String fechaExtornoOrdenPago,
        Double gpMonto,
        String fechaCreacionOrdenPago,
        String ppFechaConfGeneracionCpb,
        String gpDescProcedimiento
) {}
