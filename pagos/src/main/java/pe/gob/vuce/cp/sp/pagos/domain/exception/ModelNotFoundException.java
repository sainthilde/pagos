package pe.gob.vuce.cp.sp.pagos.domain.exception;
/**
 * Excepción personalizada que indica que un modelo no ha sido encontrado.
 * <p>
 * Esta excepción extiende RuntimeException y se utiliza para manejar casos en los que
 * una entidad o modelo específico no está disponible en el sistema.
 * </p>
 * @author MATEO HUANCHO
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-29
 */
public class ModelNotFoundException extends RuntimeException{
    /**
     * Construye una nueva excepción ModelNotFoundException con el mensaje especificado.
     *
     * @param message Mensaje descriptivo del error.
     */
    public ModelNotFoundException(String message) {
        super(message);
    }
}
