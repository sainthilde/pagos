package pe.gob.vuce.cp2.bs.domain.model;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class GestorProcedimientoModel {

    private String token;
    private Integer procedimiento;
    private String componente;
    private Integer entidadId;
    private String tipoDoc;
    private String operacionId;
    private String textSearch;
    private Integer secuencia;

    private BigDecimal monto;
    private String etiqueta;
    private String descripcion;
}
