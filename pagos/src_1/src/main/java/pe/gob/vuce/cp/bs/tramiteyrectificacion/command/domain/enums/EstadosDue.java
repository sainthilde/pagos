package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.enums;

public enum EstadosDue {
    ARRIBO_ANUNCIADO(1, "ARRIBO ANUNCIADO"),
    ARRIBO_CONFIRMADO(2, "ARRIBO CONFIRMADO"),
    ARRIBO_AUTORIZADO(3, "ARRIBO AUTORIZADO"),
    ARRIBADO(4, "ARRIBADO"),
    RECEPCIONADO(5, "RECEPCIONADO"),
    ZARPE_ANUNCIADO(6, "ZARPE ANUNCIADO"),
    ZARPE_CONFIRMADO(7, "ZARPE CONFIRMADO"),
    ZARPE_AUTORIZADO(8, "ZARPE AUTORIZADO"),
    DESPACHADO(9, "DESPACHADO"),
    CANCELADO(10, "CANCELADO"),
    CERRADO(11, "CERRADO");

    private final Integer id;

    private final String descripcion;

    EstadosDue(Integer id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
