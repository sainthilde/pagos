package pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListMaestroDto {

    Integer maestroId;
    Integer maePadre;
    String codigo;
    String descripcion;
    String orden;
    String estado;
}