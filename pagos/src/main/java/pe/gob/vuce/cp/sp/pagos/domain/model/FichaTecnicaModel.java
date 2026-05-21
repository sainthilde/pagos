package pe.gob.vuce.cp.sp.pagos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FichaTecnicaModel {
    /**
     * Identificador único de la ficha técnica.
     */
    private Integer fichaTecnicaId;

    /**
     * Identificador IMO del buque.
     */
    private String imo;

    /**
     * Matrícula de la embarcación.
     */
    private String matricula;

    /**
     * Instancia del documento en Camunda.
     */
    private String documentInstance;

    /**
     * Clave del negocio asociada en Camunda.
     */
    private String document;

    private List<FichaTecnicaDetModel> fichaTecnicaDet;
}
