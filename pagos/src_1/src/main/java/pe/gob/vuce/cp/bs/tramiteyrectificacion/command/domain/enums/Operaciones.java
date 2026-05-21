package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.enums;

public enum Operaciones {
    ASIGNAR_MANUAL("ASIGNAR_MANUAL"),
    AUTORIZAR("AUTORIZAR");

    private final String codigo;

    Operaciones(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}
