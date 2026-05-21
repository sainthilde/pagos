package pe.gob.vuce.cp.sp.pagos.infrastructure.config.exception;

public class JsonParseException extends RuntimeException {
  public JsonParseException(String message, Throwable cause) {
    super(message, cause);
  }
}
