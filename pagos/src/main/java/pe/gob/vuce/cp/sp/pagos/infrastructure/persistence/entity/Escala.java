package pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity(name = "Escala")
@Table(name = "escala", schema = "\"ESCALA\"")
public class Escala extends BaseEntity{

    /**
     * Identificador único de la escala.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "escala_id", nullable = false)
    private Integer escalaId;

    @Column(name = "estado_due_id")
    private Integer estadoDueId;

    @ManyToOne
    @JoinColumn(name = "ficha_tecnica_det_ing_id", referencedColumnName = "ficha_tecnica_det_id")
    private FichaTecnicaDet fichaTecnicaDetIn;

    @Column(name = "puerto_escala_id")
    private String puertoEscalaId;

    @Column(name = "anno_escala")
    private Integer anoEscala;

    @Column(name = "numero_escala")
    private Integer numeroEscala;
}
