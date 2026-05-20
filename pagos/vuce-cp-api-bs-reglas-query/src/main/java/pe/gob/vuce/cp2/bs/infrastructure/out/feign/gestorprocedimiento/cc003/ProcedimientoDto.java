package pe.gob.vuce.cp2.bs.infrastructure.out.feign.gestorprocedimiento.cc003;

import lombok.Data;

@Data
public class ProcedimientoDto {

    private Integer procedimientoId;
    private Integer procedimientoVersion;
    private Integer entidadId;
    private String siglas;
    private String tupa;
    private String formato;
    private Integer cut;
    private String nombreCut;
    private String componente;
    private String ayuda;
    private String pago;
    private String plazo;
    private String descripcionCalificacion;
    private String procedimientoCodigo;
    private String descripcionSilencioAdmin;
    private String informacionAdicional;
    private Integer orden;
}