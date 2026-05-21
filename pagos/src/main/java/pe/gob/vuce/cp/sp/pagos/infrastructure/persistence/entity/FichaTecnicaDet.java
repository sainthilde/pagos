package pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "FichaTecnicaDet")
@Table(name = "ficha_tecnica_det", schema = "\"FICTEC\"")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class FichaTecnicaDet{
    @Id
    @Column(name = "ficha_tecnica_det_id")
    private Integer fichaTecnicaDetId;

    /**
     * Id de la ficha tecnica
     */
    @Column(name = "ficha_tecnica_id")
    private Integer fichaTecnicaId;

    /**
     * version del detalle de la ficha tecnica
     */
    @Column(name = "version_ft")
    private String versionFt;

    /**
     * id del estado version del detalle de la ficha tecnica
     */
    @Column(name = "estado_version_ft_id")
    private Integer estadoVersionFtId;


    @Column(name = "instancia_kamunda_id")
    private String instanciaKamundaId;

    @OneToMany(mappedBy = "fichaTecnicaDetIn", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Escala> escalasIn;
    /**
     * fecha de matricula del detalle de la ficha tecnica
     */
    @Column(name = "fecha_matricula")
    private String fechaMatricula;

    /**
     * nombre de nave del detalle de la ficha tecnica
     */
    @Column(name = "nombre_nave")
    private String nombreNave;

    /**
     * callSign del detalle de la ficha tecnica
     */
    @Column(name = "call_sign")
    private String callSign;

    @Column(name = "inmarsat")
    private String inmarsat;

    @Column(name = "mmsi")
    private String mmsi;

    @Column(name = "sociedad_clasificadora")
    private String sociedadClasificadora;

    @Column(name = "documento_matricula")
    private String documentoMatricula;

    @Column(name = "dta")
    private String dta;

    @Column(name = "constructor_nombre")
    private String constructorNombre;

    @Column(name = "ano_construccion")
    private String anoConstruccion;

    @Column(name = "tonelaje_peso_muerto")
    private String tonelajePesoMuerto;

    @Column(name = "velocidad")
    private String velocidad;

    @Column(name = "eslora")
    private String eslora;

    @Column(name = "manga")
    private String manga;

    @Column(name = "puntal")
    private String puntal;
    @Column(name = "arqueo_neto")
    private String arqueoNeto;

    @Column(name = "arqueo_bruto")
    private String arqueoBruto;

    @Column(name = "calado_minimo")
    private String caladoMinimo;

    @Column(name = "calado_maximo")
    private String caladoMaximo;

    @Column(name = "cantidad_maquinas")
    private Integer cantidadMaquinas;

    @Column(name = "doble_caso")
    private Boolean dobleCaso;

    /**
     * estado del registro
     */
    @Column(name = "estado")
    private String estado;

    /**
     * usuario de registro
     */
    @Column(name = "usuid_reg_aud")
    private String usuidRegAud;

    /**
     * usuario de modificacion
     */
    @Column(name = "usuid_mod_aud")
    private String usuidModAud;

    /**
     * fecha de registro
     */
    @Column(name = "fecha_reg_aud")
    private String fechaRegAud;

    /**
     * fecha de modificacion
     */
    @Column(name = "fecha_mod_aud")
    private String fechaModAud;

    @Column(name = "usubd_reg_aud")
    private String usubdRegAud;

    @Column(name = "usubd_mod_aud")
    private String usubdModAud;


}
