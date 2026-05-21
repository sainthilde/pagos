package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.api.advices.exception;

public class ResponseBuildException extends RuntimeException {

    public ResponseBuildException(String message) {
        super(message);
    }

    public ResponseBuildException(String message, Throwable cause) {
        super(message, cause);
    }
}
