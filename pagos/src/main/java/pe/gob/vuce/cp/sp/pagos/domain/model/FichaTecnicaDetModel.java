package pe.gob.vuce.cp.sp.pagos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FichaTecnicaDetModel {
    private Integer fichaTecnicaDetId;
    private Integer fichaTecnicaId;
    private String versionFt;
    private Integer estadoVersionFtId;
    private String instanciaKamundaId;
    private String fechaMatricula;
    private String nombreNave;
    private String callSign;
    private EscalaModel escala;
    private String inmarsat;
    private String mmsi;
    private String sociedadClasificadora;
    private String documentoMatricula;
    private String dta;
    private String constructorNombre;
    private String anoConstruccion;
    private String tonelajePesoMuerto;
    private String velocidad;
    private String eslora;
    private String manga;
    private String puntal;
    private String arqueoNeto;
    private String arqueoBruto;
    private String caladoMinimo;
    private String caladoMaximo;
    private Integer cantidadMaquinas;
    private Boolean dobleCaso;
    private String estado;
    private String usuidRegAud;
    private String usuidModAud;
    private String fechaRegAud;
    private String fechaModAud;
    private String usubdRegAud;
    private String usubdModAud;
}
