package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity.mae;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "entidad", schema = "\"MAE\"")
public class Entidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entidad_id", nullable = false)
    private Integer id;

    @Column(name = "ruc", length = 20)
    private String ruc;

    @Column(name = "nombre", length = 100)
    private String nombre;

    @Column(name = "observacion", length = 200)
    private String observacion;

    @Column(name = "estado", nullable = false, length = 1)
    private String estado;

    @Column(name = "usuid_reg_aud", length = 100)
    private String usuidRegAud;

    @Column(name = "usuid_mod_aud", length = 100)
    private String usuidModAud;

    @Column(name = "fecha_reg_aud", nullable = false)
    private LocalDateTime fechaRegAud;

    @Column(name = "fecha_mod_aud", nullable = false)
    private LocalDateTime fechaModAud;

    @Column(name = "usubd_reg_aud", length = 50)
    private String usubdRegAud;

    @Column(name = "usubd_mod_aud", length = 50)
    private String usubdModAud;

    @Column(name = "grupo_entidad_id")
    private Integer grupoEntidadId;
}