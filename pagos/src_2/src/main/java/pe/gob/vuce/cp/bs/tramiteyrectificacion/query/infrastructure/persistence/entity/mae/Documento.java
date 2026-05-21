package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.mae;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidad que representa un documento en la base de datos.
 * 
 * @project cp-api-bs-tramiteyrectificacion-query
 * @autor Luis Francisco Huertas Mostacero
 * @date 24/08/2024
 */
@Getter
@Setter
@Entity(name = "Documento")
@Table(name = "documento", schema = "\"MAE\"")
public class Documento {

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
     * Descripción corta del documento.
     * 
     * Límite de tamaño establecido en 300 caracteres.
     */
    @Size(max = 300)
    @Column(name = "desc_corta", length = 300)
    private String descCorta;

    /**
     * Acrónimo de la descripción del documento.
     * 
     * Límite de tamaño establecido en 300 caracteres.
     */
    @Size(max = 300)
    @Column(name = "desc_acronimo", length = 300)
    private String descAcronimo;

    /**
     * Estado del documento.
     * 
     * Este campo es obligatorio, tiene un tamaño máximo de 1 carácter, y un valor
     * por defecto de 'S'.
     */
    @Size(max = 1)
    @Column(name = "estado", nullable = false, length = 1, columnDefinition = "'S'::character varying")
    private String estado;

    /**
     * Usuario que registró el documento.
     * 
     * Límite de tamaño establecido en 100 caracteres.
     */
    @Size(max = 100)
    @Column(name = "usuid_reg_aud", length = 100)
    private String usuidRegAud;

    /**
     * Usuario que modificó el documento.
     * 
     * Límite de tamaño establecido en 100 caracteres.
     */
    @Size(max = 100)
    @Column(name = "usuid_mod_aud", length = 100)
    private String usuidModAud;

}
