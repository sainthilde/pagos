package pe.gob.vuce.cp2.bs.infrastructure.out.postgresql.entity;


import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "actividad_entidad_puerto", schema = "\"MAE\"")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActividadEntidadPuertoEntity implements Serializable{

    @Id
    @Column(name = "actividad_entidad_puerto_id")
    private Integer actividadEntidadPuertoId;

    @Column(name = "actividad_id", nullable = false)
    private Integer actividadId;

    @Column(name = "cod_puerto_nacional", nullable = false, length = 3)
    private String codPuertoNacional;

    @Column(name = "entidad_id", nullable = false)
    private Integer entidadId;

    @Column(name = "cod_regla_negocio", length = 15)
    private String codReglaNegocio;

    @Column(name = "estado", nullable = false, length = 1)
    private String estado;
}