package pe.gob.vuce.cp2.bs.infrastructure.out.feign.gestorprocedimiento.cc006;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcedimientoTasaResponseDto {

    private List<TasaDto> tasas;
}