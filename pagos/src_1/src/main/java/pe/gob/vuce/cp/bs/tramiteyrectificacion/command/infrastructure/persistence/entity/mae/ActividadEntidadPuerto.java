package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity.mae;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity.BaseEntity;

@Getter
@Setter
@Entity(name = "ActividadEntidadPuerto")
@Table(name = "actividad_entidad_puerto", schema = "\"MAE\"")
@NoArgsConstructor
public class ActividadEntidadPuerto extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actividad_entidad_puerto_id", nullable = false)
    private Integer id;

    @Column(name = "entidad_id", nullable = false)
    private Integer entidadId;

    @Column(name = "actividad_id", nullable = false)
    private Integer actividadId;

    @Column(name = "cod_puerto_nacional", nullable = false)
    private String codPuertoNacional;
}
