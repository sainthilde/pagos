package pe.gob.vuce.cp.sp.pagos.domain.exception;

public class KafkaListenerException extends RuntimeException {

    public KafkaListenerException(String message, Throwable cause) {
        super(message, cause);
    }
}
