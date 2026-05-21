package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.enums;

/**
 * Enum que representa los posibles estados de un tramite en la aplicación.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 19/08/2024
 */
public enum DeclaracionJuradaEstados {
    PEDIENTE("P"),
    ACEPTADA("A"),
    DENEGADA("D");

    private final String codigo;

    DeclaracionJuradaEstados(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}
