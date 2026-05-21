package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity.mae;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity.BaseEntity;

/**
 * Entidad JPA que representa un documento del esquema
 * "PAGOS".
 * Esta entidad incluye campos para registrar la información relevante del documento.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 22/08/2024
 */
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Documento")
@Table(name = "documento", schema = "\"MAE\"")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Documento extends BaseEntity {

    /**
     * Identificador único del documento
     */
    @Id
    @Column(name = "documento_id", nullable = false)
    private Integer documentoId;

    @Column(name = "desc_acronimo")
    private String descAcronimo;

}
