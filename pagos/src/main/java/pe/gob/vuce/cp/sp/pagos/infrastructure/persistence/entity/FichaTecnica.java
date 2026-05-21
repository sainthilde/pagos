package pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OrderBy;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * @project cp-api-bs-fichatecnica-query
 * @date 1/03/2024
 **/
@Getter
@Setter
@Entity(name = "FichaTecnica")
@Table(schema = "\"FICTEC\"", name = "ficha_tecnica")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FichaTecnica {

  @Id
  @Column(name = "ficha_tecnica_id")
  private Integer fichaTecnicaId;

  @Column(name = "imo")
  private String imo;

  @Column(name = "matricula")
  private String matricula;

  @Column(name = "instancia_camunda_id")
  private String documentInstance;

  @Column(name = "clave_negocio_camunda")
  private String document;

  @OneToMany(
          cascade = CascadeType.ALL,
          fetch = FetchType.LAZY
  )
  @JoinColumn(name = "ficha_tecnica_id")
  @OrderBy("ficha_tecnica_det_id DESC")
  public List<FichaTecnicaDet> fichaTecnicaDet;



}
