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
@Table(name = "escala", schema = "\"ESCALA\"")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EscalaEntity implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "escala_id")
    private Integer escalaId;

    @Column(name = "puerto_escala_id", length = 3)
    private String puertoEscalaId;
}