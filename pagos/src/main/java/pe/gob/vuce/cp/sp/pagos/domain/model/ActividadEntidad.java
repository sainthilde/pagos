package pe.gob.vuce.cp.sp.pagos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@SuppressWarnings("squid:S1104")
@AllArgsConstructor

/**
 * Modelo de dominio para representar la actividad de una entidad.
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
public class ActividadEntidad {
    /**
     * Identificador de la actividad de la entidad.
     */
    public Integer actividadEntidadId;
    /**
     * Identificador de la entidad.
     */
    public Integer entidadId;
    /**
     * Identificador de la actividad.
     */
    public Integer actividadId;
    /**
     * Código del puerto nacional relacionado.
     */
    public String codPuertoNacional;
    /**
     * Código de la regla de negocio aplicada.
     */
    public String codReglaNegocio;
    /**
     * Estado de la actividad de la entidad.
     */
    public String estado;
}
