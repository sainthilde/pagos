package pe.gob.vuce.cp2.bs.infrastructure.out.feign.gestorprocedimiento.cc006;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TasaDto {

    private Integer procedimientoId;
    private Integer procedimientoVersion;
    private Integer procedimientoTasaVersion;
    private Integer secuencia;
    private BigDecimal monto;
    private String etiqueta;
    private String descripcion;
    private String codigoMoneda;
    private String monedaDescripcion;
    private String monedaSigno;
}