package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.fictec;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad que representa la ficha técnica en la base de datos.
 *
 * @project cp-api-bs-tramiteyrectificacion-query
 * @autor Luis Francisco Huertas Mostacero
 * @date 15/08/2024
 */
@Getter
@Setter
@NoArgsConstructor
@Entity(name = "FichaTecnica")
@Table(name = "ficha_tecnica", schema = "\"FICTEC\"")
public class FichaTecnica {
    @Id
    @Column(name = "ficha_tecnica_id")
    private Integer fichaTecnicaId;

    @Column(name = "imo")
    private String imo;

    @Column(name = "matricula")
    private String matricula;
}
