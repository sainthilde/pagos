package pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
/**
 * DTO (Data Transfer Object) que representa la respuesta de una actividad de entidad.
 *
 * Este registro se utiliza para transferir datos sobre actividades de entidades en
 * el sistema de pagos de SUNAT.
 *
 * Se excluyen los campos con valores nulos al serializarse a JSON.
 *
 * @param actividadEntidadId  Identificador único de la actividad de entidad.
 * @param entidadId           Identificador de la entidad asociada.
 * @param actividadId         Identificador de la actividad relacionada.
 * @param codPuertoNacional   Código del puerto nacional asociado a la actividad.
 * @param codReglaNegocio     Código de la regla de negocio aplicable.
 * @param estado              Estado actual de la actividad de entidad.
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
public record ActividadEntidadResponseDto(
        Integer actividadEntidadId,
        Integer entidadId,
        Integer actividadId,
        String codPuertoNacional,
        String codReglaNegocio,
        String estado
) {
}
