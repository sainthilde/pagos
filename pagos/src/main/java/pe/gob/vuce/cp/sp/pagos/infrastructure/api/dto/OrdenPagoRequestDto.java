package pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO (Data Transfer Object) que representa la solicitud para crear o actualizar
 * una orden de pago.
 * Esta clase se utiliza para transferir datos de entrada relacionados con las
 * órdenes de pago en el sistema de pagos de SUNAT.
 * Los campos marcados con @NotNull son obligatorios y deben ser proporcionados
 * en la solicitud.
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdenPagoRequestDto {

    @NotNull
    @JsonProperty("entidadId")
    public Integer entidadId;

    @NotNull
    @JsonProperty("documentoId")
    public Integer documentoId;

    @NotNull
    @JsonProperty("escalaId")
    public Integer escalaId;

    @NotNull
    @JsonProperty("rucAgente")
    public String rucAgente;

    @NotNull
    @JsonProperty("fechaVigencia")
    public String fechaVigencia;

    @NotNull
    @JsonProperty("idComponente")
    public String idComponente;

    @JsonProperty("textSearch")
    public String textSearch;

    @JsonProperty("actividadEntidadPuertoId")
    public Integer actividadEntidadPuertoId;

    @JsonProperty("cantidadOrden")
    public Integer cantidadOrden;

}

