package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model;

import java.time.Instant;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * Clase base que proporciona atributos comunes para las entidades de dominio en
 * la aplicación.
 * Esta clase está mapeada como una superclase en JPA, por lo que no se mapea
 * directamente a una tabla.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 19/08/2024
 */
@MappedSuperclass
@Getter
@Setter
public class BaseModel {

    /**
     * Estado de la entidad, utilizado para indicar si está activa o inactiva.
     */
    private String estado;

    /**
     * ID del usuario que creó la entidad, registrado automáticamente.
     */
    private String usuidRegAud;

    /**
     * ID del usuario que modificó la entidad por última vez, registrado
     * automáticamente.
     */
    private String usuidModAud;

    /**
     * Fecha y hora en que se creó la entidad, registrada automáticamente.
     */
    // @CreatedDate
    private Instant fechaRegAud;

    /**
     * Fecha y hora en que se modificó la entidad por última vez, registrada
     * automáticamente.
     */
    // @LastModifiedDate
    private Instant fechaModAud;

}
