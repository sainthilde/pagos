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
@Table(name = "entidad", schema = "\"MAE\"")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntidadEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "entidad_id", nullable = false)
    private Integer entidadId;

    @Column(name = "ruc", nullable = false, length = 20)
    private String ruc;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "observacion", length = 200)
    private String observacion;

    @Column(name = "estado", nullable = false, length = 1)
    private String estado;

    @Column(name = "grupo_entidad_id")
    private Integer grupoEntidadId;

    @Column(name = "cod_entidad_gp", length = 5)
    private String codEntidadGp;
}