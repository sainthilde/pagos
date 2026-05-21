package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdenPagoResponseDto {
    private Integer ordenPagoId;
    private Integer entidadId;
    private Integer documentoId;
    private Integer escalaId;
    private String rucAgente;
    private String codigoOrdenPago;
    private Double monto;
    private String fechaGeneracion;
    private String cpb;
    private String estado;
    private String fechaVigencia;
    private String fechaPagado;
    private String fechaAnulacionCpb;
    private String fechaExtornoOrdenPago;
    private Double gpMonto;
    private String fechaCreacionOrdenPago;
    private String ppFechaConfGeneracionCpb;
    private String gpDescProcedimiento;
}
