package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.enums;

/**
 * Enum que representa los tipos de seguimiento en las inspecciones sanitarias.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 22/08/2024
 */
public enum TipoSeguimiento {

    /**
     * Tipo de seguimiento para tramite.
     */
    ASIGNACION_TRAMITE(38),
    GENERAR_TRAMITE(48),
    DESISTIDO_TRAMITE(50),
    AUTORIZAR_TRAMITE(49),
    GENERAR_DDJJ(45),
    ACEPTAR_DDJJ(46),
    DENEGAR_DDJJ(47);

    private final Integer value;

    /**
     * Constructor del enum.
     *
     * @param value Valor numérico asociado al tipo de seguimiento.
     */
    TipoSeguimiento(Integer value) {
        this.value = value;
    }

    /**
     * Obtiene el valor numérico asociado al tipo de seguimiento.
     *
     * @return El valor numérico del tipo de seguimiento.
     */
    public Integer getValue() {
        return value;
    }
}
