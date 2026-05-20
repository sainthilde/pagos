package pe.gob.vuce.cp2.bs.infrastructure.in.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;


@RestController
@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    /*
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<MetaDto> handleConstraintViolationException(
            ConstraintViolationException exception) {
        return new ResponseEntity<>(
                InfrastructureUtil.buildMetaDto(CODE_0005), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseEntity<MetaDto> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException exception){
        return new ResponseEntity<>(
                InfrastructureUtil.buildMetaDto(CODE_0004), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(value = DataAccessResourceFailureException.class)
    public ResponseEntity<MetaDto> handleDataAccessResourceFailureException(
            DataAccessResourceFailureException exception) {
        return new ResponseEntity<>(
                InfrastructureUtil.buildMetaDto(CODE_0002), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<MetaDto> handleThrowable(
            Throwable exception) {
        return new ResponseEntity<>(
                InfrastructureUtil.buildMetaDto(CODE_9999), HttpStatus.SERVICE_UNAVAILABLE);
    }
                 */
}
