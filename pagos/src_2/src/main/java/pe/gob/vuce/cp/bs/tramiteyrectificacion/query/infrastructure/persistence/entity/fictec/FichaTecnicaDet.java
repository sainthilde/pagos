package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.fictec;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.BaseEntity;

/**
 * Entidad que representa el detalle de la ficha técnica en la base de datos.
 *
 * @project cp-api-bs-tramiteyrectificacion-query
 * @autor Luis Francisco Huertas Mostacero
 * @date 15/08/2024
 */
@Getter
@Setter
@Entity(name = "FichaTecnicaDet")
@Table(name = "ficha_tecnica_det", schema = "\"FICTEC\"")
public class FichaTecnicaDet extends BaseEntity {

    @Id
    @Column(name = "ficha_tecnica_det_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ficha_tecnica_id", nullable = false)
    private FichaTecnica fichaTecnica;

    @NotNull
    @Column(name = "version_ft", nullable = false)
    private Integer versionFt;

    @NotNull
    @Column(name = "estado_version_ft_id", nullable = false)
    private Integer estadoVersionFt;

    @Column(name = "fecha_matricula")
    private LocalDate fechaMatricula;

    @Size(max = 150)
    @Column(name = "nombre_nave", length = 150)
    private String nombreNave;

    @Size(max = 100)
    @Column(name = "call_sign", length = 100)
    private String callSign;

    @Size(max = 100)
    @Column(name = "inmarsat", length = 100)
    private String inmarsat;

    @Size(max = 100)
    @Column(name = "mmsi", length = 100)
    private String mmsi;

    @Size(max = 100)
    @Column(name = "sociedad_clasificadora", length = 100)
    private String sociedadClasificadora;

    @Size(max = 100)
    @Column(name = "documento_matricula", length = 100)
    private String documentoMatricula;

    @Size(max = 100)
    @Column(name = "dta", length = 100)
    private String dta;

    @Column(name = "ano_construccion")
    private Integer anoConstructor;

    @Size(max = 100)
    @Column(name = "tonelaje_peso_muerto", length = 100)
    private String tonelajePesoMuerto;

    @Column(name = "velocidad")
    private Integer velocidad;

    @Column(name = "eslora")
    private Integer eslora;

    @Column(name = "manga")
    private Integer manga;

    @Column(name = "puntal")
    private Integer puntal;

    @Column(name = "arqueo_neto")
    private Integer arqueoNeto;

    @Column(name = "arqueo_bruto")
    private Integer arqueoBruto;

    @Column(name = "calado_minimo")
    private Integer caladoMinimo;

    @Column(name = "calado_maximo")
    private Integer caladoMaximo;

    @Column(name = "cantidad_maquinas")
    private Integer cantidadMaquinas;

    @Column(name = "doble_caso")
    private Boolean dobleCaso;

}
