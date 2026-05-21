package pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class BaseEntity {

    @Size(max = 1)
    @Column(name = "estado", nullable = false, length = 1)
    private String estado;

    @CreatedBy
    @Size(max = 100)
    @Column(name = "usuid_reg_aud", updatable = false, nullable = false, length = 100)
    private String usuidRegAud;

    @LastModifiedBy
    @Size(max = 100)
    @Column(name = "usuid_mod_aud", length = 100)
    private String usuidModAud;

    @CreatedDate
    @Column(name = "fecha_reg_aud", updatable = false, nullable = false)
    private Instant fechaRegAud;

    @LastModifiedDate
    @Column(name = "fecha_mod_aud")
    private Instant fechaModAud;
}
