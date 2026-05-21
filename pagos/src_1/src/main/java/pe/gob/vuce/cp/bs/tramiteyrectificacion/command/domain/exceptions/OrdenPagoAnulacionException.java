package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.exceptions;

import java.util.List;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.OrdenPagoErrorResponse;

public class OrdenPagoAnulacionException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    // Marked transient to avoid serialization issues if OrdenPagoErrorResponse
    // isn't Serializable
    private final transient List<OrdenPagoErrorResponse> errores;

    public OrdenPagoAnulacionException(String message, List<OrdenPagoErrorResponse> errores) {
        super(message);
        this.errores = errores;
    }

    public List<OrdenPagoErrorResponse> getErrores() {
        return errores;
    }
}