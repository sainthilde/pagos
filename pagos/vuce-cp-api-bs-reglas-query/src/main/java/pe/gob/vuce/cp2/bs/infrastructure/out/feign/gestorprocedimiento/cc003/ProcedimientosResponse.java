package pe.gob.vuce.cp2.bs.infrastructure.out.feign.gestorprocedimiento.cc003;

import java.util.List;

import lombok.Data;

@Data
public class ProcedimientosResponse {

    private List<ProcedimientoDto> procedimientos;
}
