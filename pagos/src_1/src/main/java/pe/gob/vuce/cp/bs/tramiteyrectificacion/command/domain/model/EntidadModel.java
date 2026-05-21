package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EntidadModel {
    private Integer id;

    private String ruc;

    private String nombre;

    private String observacion;

    private String estado;

    private String usuidRegAud;

    private String usuidModAud;

    private LocalDateTime fechaRegAud;

    private LocalDateTime fechaModAud;

    private String usubdRegAud;

    private String usubdModAud;

    private Integer grupoEntidadId;
}
