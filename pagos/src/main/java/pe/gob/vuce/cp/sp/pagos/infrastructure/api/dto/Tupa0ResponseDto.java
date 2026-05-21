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
 * @param esTasa0                     Indicador de si es una tasa 0.
 * @param mensaje                     Mensaje de la respuesta.
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2026-04-04
 */
public record Tupa0ResponseDto(
        Boolean esTasa0,
        String mensaje

) {}
