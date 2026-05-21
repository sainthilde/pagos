package pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO que representa el mensaje al topico DLQ.
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2025-12-05
 */
@Getter
@Setter
public class ErrorMessageDLQDTO {

    /**
     * Topico original de donde proviene el mensaje con error.
     */
    private String originalTopic;

    /**
     * Partición del topico original de donde proviene el mensaje con error.
     */
    private int partition;

    /**
     * Offset del mensaje original que causó el error.
     */
    private long offset;

    /**
     * Mensaje original que causó el error.
     */
    private Object originalMessage;

    /**
     * Mensaje de error asociado al procesamiento fallido.
     */
    private String errorMessage;

    /**
     * Tipo de excepción que se produjo durante el procesamiento.
     */
    private String exceptionType;

    /**
     * Marca de tiempo cuando ocurrió el error.
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    /**
     * Constructor que inicializa el DTO con el mensaje original y la excepción.
     *
     * @param originalMessage El mensaje original que causó el error.
     * @param ex              La excepción que se produjo durante el procesamiento.
     */
    public ErrorMessageDLQDTO(Object originalMessage, Exception ex, String originalTopic, int partition, long offset) {
        this.originalMessage = originalMessage;
        this.errorMessage = ex.getMessage();
        this.exceptionType = ex.getClass().getSimpleName();
        this.timestamp = LocalDateTime.now();
        this.originalTopic = originalTopic;
        this.partition = partition;
        this.offset = offset;
    }
}
