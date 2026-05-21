package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.escala;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.fictec.FichaTecnicaDet;

/**
 * Entidad que representa una escala en la base de datos.
 * 
 * @project cp-api-bs-tramiteyrectificacion-query
 * @autor Luis Francisco Huertas Mostacero
 * @date 24/08/2024
 */
@Getter
@Setter
@Entity(name = "Escala")
@Table(name = "escala", schema = "\"ESCALA\"")
public class Escala {

    /**
     * Identificador único de la escala.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "escala_id", nullable = false)
    private Integer escalaId;

    /**
     * Relación Many-to-One con la entidad FichaTecnicaDet para la entrada de la
     * escala.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ficha_tecnica_det_ing_id", referencedColumnName = "ficha_tecnica_det_id")
    private FichaTecnicaDet fichaTecnicaDetIn;

    /**
     * Identificador del puerto de escala.
     * 
     * Limite de tamaño establecido en 3 caracteres.
     */
    @Size(max = 3)
    @Column(name = "puerto_escala_id")
    private String puertoEscalaId;

    @Column(name = "anno_escala")
    private Integer annoEscala;

    @Column(name = "numero_escala")
    private Integer numeroEscala;

    @Column(name = "numero_viaje", length = 14)
    private String numeroViaje;

    @Column(name = "eta")
    private LocalDateTime eta;

    @Column(name = "etd")
    private LocalDateTime etd;

    @Column(name = "tipo_trafico_due_id")
    private Integer tipoTraficoDueId;

    @Column(name = "ata")
    private LocalDateTime ata;

    @Column(name = "atd")
    private LocalDateTime atd;

    @Column(name = "fecha_libre_platica")
    private LocalDateTime fechaLibrePlatica;

    /**
     * Estado de la escala.
     * 
     * Este campo es obligatorio y tiene un tamaño máximo de 1 carácter.
     */
    @Column(name = "estado", nullable = false, length = 1)
    private String estado;

}
