package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DocumentoModel {
    /**
     * Identificador único del documento.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "documento_id", nullable = false)
    private Integer id;

    /**
     * Nombre completo del documento.
     * 
     * Límite de tamaño establecido en 300 caracteres.
     */
    @Size(max = 300)
    @Column(name = "nombre_documento", length = 300)
    private String nombreDocumento;

    /**
     * Acrónimo de la descripción del documento.
     * 
     * Límite de tamaño establecido en 300 caracteres.
     */
    @Size(max = 300)
    @Column(name = "desc_acronimo", length = 300)
    private String descAcronimo;

}
