package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.enums;

/**
 * Enum que representa los posibles estados de un tramite en la aplicación.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 19/08/2024
 */
public enum OrdenPagoEstados {
    CREADO("CR"),
    PENDIENTEPAGO("PP"),
    PAGADO("PG"),
    ANULADO("AN"),
    EXPIRADO("XP"),
    EXTORNADO("EX"),
    PORREASIGNAR("PR"),
    REASIGNADA("RE");

    private final String codigo;

    OrdenPagoEstados(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}
