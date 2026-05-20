package pe.gob.vuce.cp2.bs.infrastructure.out.postgresql.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "convoy_escala", schema = "\"ESCALA\"")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConvoyEscalaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "convoy_escala_id")
    private Integer convoyEscalaId;

    @Column(name = "convoy_id")
    private Integer convoyId;

    @Column(name = "escala_id")
    private Integer escalaId;

    @Column(name = "principal")
    private Boolean principal;

    @Column(name = "estado", nullable = false, length = 1)
    @Builder.Default
    private String estado = "S";


}