package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.enums;

/**
 * Enum que representa los posibles estados de un tramite en la aplicación.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 19/08/2024
 */
public enum TramiteEstados {
    EN_TRAMITE("ET"),
    DESISTIDO("DE"),
    AUTORIZADO("AU"),
    EN_RECTIFICACION("RE");

    private final String codigo;

    TramiteEstados(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}
