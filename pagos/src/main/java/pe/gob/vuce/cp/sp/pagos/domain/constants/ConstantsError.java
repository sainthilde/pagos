package pe.gob.vuce.cp.sp.pagos.domain.constants;
/**
 * La clase ConstantsError es una clase de utilidad que define constantes relacionadas
 * con mensajes de error comunes en el servidor. Estas constantes representan diferentes
 * códigos de error y descripciones de errores que pueden ser reutilizadas en varias
 * partes de la aplicación, facilitando la consistencia en el manejo de mensajes de error.
 *
 * <p>Características de la clase:
 * <ul>
 *   <li>Es una clase de solo constantes, diseñada para no ser instanciada.</li>
 *   <li>El constructor privado lanza una excepción para prevenir la creación de
 *       instancias de la clase.</li>
 * </ul>
 *
 * <p>Constantes principales:
 * <ul>
 *   <li>{@code ERROR_503}: Mensaje de error HTTP para el estado 503, indicando que el
 *       servicio no está disponible.</li>
 *   <li>{@code ERROR_504}: Mensaje de error HTTP para el estado 504, indicando que el
 *       tiempo de espera de la puerta de enlace ha expirado.</li>
 *   <li>{@code ERROR_INTERNO} y {@code ERROR_500}: Mensajes de error genéricos para
 *       errores internos del servidor (estado 500).</li>
 *   <li>{@code ERROR_DESCONOCIDO} y {@code ERROR_PROCESS}: Mensajes para errores
 *       desconocidos o para fallos en el procesamiento sin una descripción específica.</li>
 *   <li>{@code ERROR_HTML}: Mensaje utilizado cuando no se encuentra un mensaje de
 *       error en una respuesta HTML.</li>
 *   <li>{@code ERROR_DESC}: Mensaje de error por defecto, indicando un error
 *       desconocido relacionado con el estado 503.</li>
 * </ul>
 * @author Mateo Huancho
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
public class ConstantsError {
    /**
     * Constructor privado para evitar la creación de instancias de la clase.
     * Lanza una excepción si se intenta instanciar, ya que esta clase solo contiene constantes.
     */
    private ConstantsError() {
        throw new IllegalStateException("Constants Error class");
    }
    public static final String ERROR_502 = "Error 502 Bad Gateway";
    public static final String ERROR_404 = "HTTP 404 Not Found";
    public static final String ERROR_400 = "Solicitud incorrecta";
    public static final String ERROR_412 = "Precondition Failed";
    public static final String ERROR_422 = "Unprocessable Entity";
    public static final String ERROR_503 = "503 Service Unavailable";
    public static final String ERROR_504 = "504 Gateway Timeout";
    public static final String ERROR_INTERNO = "Ocurrió un error interno en el servidor. Por favor, inténtelo más tarde.";
    public static final String ERROR_500 = "500 Internal Server Error";
    public static final String ERROR_DESCONOCIDO = "Error desconocido en el servidor.";
    public static final String ERROR_HTML = "No se encontró mensaje de error en el HTML";
    public static final String ERROR_DESC = "Error desconocido (503)";
    public static final String ERROR_JSON = "No se encontró mensaje de error en el JSON";


}
