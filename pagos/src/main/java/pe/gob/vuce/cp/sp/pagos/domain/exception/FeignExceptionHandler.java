package pe.gob.vuce.cp.sp.pagos.domain.exception;

import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsError.ERROR_DESC;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsError.ERROR_HTML;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.REGEX;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagosSunat.ALIAS_FIELD;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagosSunat.DESCRIPTION_FIELD;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagosSunat.H1;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagosSunat.P_ERROR;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.FeignException;
import pe.gob.vuce.cp.sp.pagos.domain.constants.Constants;
import pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsError;
import pe.gob.vuce.cp.sp.pagos.domain.model.OrdenPago;

/**
 * Clase de configuración para manejar excepciones de FeignClient en la entidad
 * OrdenPago.
 * <p>
 * Esta clase captura y maneja diferentes códigos de error HTTP en las
 * respuestas de Feign,
 * utilizando `ObjectMapper` para procesar errores JSON y métodos específicos
 * para errores HTML.
 * </p>
 */
@Configuration
public class FeignExceptionHandler {

    private final ObjectMapper objectMapper;

    /**
     * Constructor de la clase FeignExceptionHandler.
     *
     * @param objectMapper Mapper para procesar respuestas JSON.
     */
    public FeignExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Maneja excepciones de tipo FeignClientException para la entidad OrdenPago.
     * <p>
     * Establece el código de error y maneja diferentes casos según el código de
     * estado HTTP.
     * </p>
     *
     * @param e         Excepción de Feign con el error HTTP.
     * @param ordenPago Instancia de OrdenPago en la que se registrará el error.
     */
    public void handleFeignClientException(FeignException e, OrdenPago ordenPago) {
        ordenPago.setPpCodigorechazoSinConexion(String.valueOf(e.status()));
        switch (e.status()) {
            case 400 -> handleJsonError(e, ordenPago, ConstantsError.ERROR_400); // E0048 DEFECTO
            case 404 -> handleJsonError(e, ordenPago, ConstantsError.ERROR_404); // E0048 DEFECTO
            case 502 -> handleHtmlError(e, ordenPago, ConstantsError.ERROR_502); // E0047
            case 503 -> handleHtmlError(e, ordenPago, ConstantsError.ERROR_503); // E0047
            case 504 -> handleHtmlError(e, ordenPago, ConstantsError.ERROR_504); // E0047
            case 412 -> handleJsonError(e, ordenPago, ConstantsError.ERROR_412); // E0048 DEFECTO
            case 422 -> handleJsonError(e, ordenPago, ConstantsError.ERROR_422); // E0048 DEFECTO
            case 500 -> handleInternalServerError(e, ordenPago); // E0047
            default -> {
                ordenPago.setPpDescCortaError(ConstantsError.ERROR_DESCONOCIDO);
                ordenPago.setPpMensajeRechazoSinConexion(ConstantsError.ERROR_DESC);
            }
        }
    }

    /**
     * Maneja errores en respuestas HTML para la entidad OrdenPago.
     *
     * @param e         Excepción de Feign que contiene el contenido HTML.
     * @param ordenPago Instancia de OrdenPago en la que se registrará el error.
     * @param errorCode Código de error específico a establecer.
     */
    private void handleHtmlError(FeignException e, OrdenPago ordenPago, String errorCode) {
        String htmlResponse = e.contentUTF8();
        String errorMessage = extractHtmlMessage(htmlResponse);
        String requester = e.getMessage();

        // Validación segura contra nulos
        String safeRequester = requester != null ? requester : "";

        // Determinar el tipo de error
        String prefix = "";
        if (safeRequester.contains(Constants.GESTOR_PATH)) {
            prefix = Constants.GP_PREFIX;
        } else if (safeRequester.contains(Constants.SUNAT_PATH)) {
            prefix = Constants.SUNAT_PREFIX;
        }
        ordenPago.setPpDescCortaError(prefix + errorCode);
        ordenPago.setPpMensajeRechazoSinConexion(prefix + errorMessage);
    }

    /**
     * Maneja errores en formato JSON y los registra en la entidad OrdenPago.
     *
     * @param e            Excepción de Feign que contiene el contenido json.
     * @param ordenPago    Instancia de OrdenPago en la que se registrará el error.
     * @param defaultError Código de error por defecto.
     */
    public void handleJsonError(FeignException e, OrdenPago ordenPago, String defaultError) {
        try {
            JsonNode jsonNode = objectMapper.readTree(e.contentUTF8());
            String alias = jsonNode.path(ALIAS_FIELD).asText();
            String description = jsonNode.path(DESCRIPTION_FIELD).asText();
            String requester = e.getMessage();

            // Validación segura contra nulos
            String safeRequester = requester != null ? requester : "";

            // Determinar el tipo de error
            String prefix = "";
            String usumod = "";
            if (safeRequester.contains(Constants.GESTOR_PATH)) {
                prefix = Constants.GP_PREFIX;
                usumod = Constants.GESTOR_DESC;
            } else if (safeRequester.contains(Constants.SUNAT_PATH)) {
                prefix = Constants.SUNAT_PREFIX;
                usumod = Constants.PASERELA_DESC;
            }

            // Manejar los mensajes de error con seguridad contra nulos
            String safeAlias = alias != null && !alias.isEmpty() ? alias : defaultError;
            String safeDescription = description != null && !description.isEmpty() ? description
                    : ConstantsError.ERROR_JSON;

            ordenPago.setPpDescCortaError(prefix + safeAlias);
            ordenPago.setPpMensajeRechazoSinConexion(prefix + safeDescription);
            ordenPago.setUsuidModAud(usumod);

        } catch (IOException ex) {
            ordenPago.setPpDescCortaError(defaultError);
            ordenPago.setPpMensajeRechazoSinConexion(ConstantsError.ERROR_JSON);

        }
    }

    /**
     * Maneja errores internos del servidor y los registra en la entidad OrdenPago.
     *
     * @param e         Excepción de Feign que contiene el error.
     * @param ordenPago Instancia de OrdenPago en la que se registrará el error.
     */
    public void handleInternalServerError(FeignException e, OrdenPago ordenPago) {
        try {
            String jsonString = extractJson(e.getMessage());
            if (jsonString != null) {
                handleJsonError(e, ordenPago, ConstantsError.ERROR_500);
            } else {
                String htmlResponse = e.contentUTF8();
                String errorMessage = extractHtmlMessage(htmlResponse);
                ordenPago.setPpDescCortaError(ConstantsError.ERROR_500);
                ordenPago.setPpMensajeRechazoSinConexion(
                        errorMessage.isEmpty() ? ConstantsError.ERROR_INTERNO : errorMessage);
            }
        } catch (Exception ex) {
            ordenPago.setPpDescCortaError(ConstantsError.ERROR_500);
            ordenPago.setPpMensajeRechazoSinConexion(ConstantsError.ERROR_DESCONOCIDO);
        }
    }

    /**
     * Extrae y retorna mensajes de error desde una respuesta en formato HTML.
     *
     * @param htmlResponse Respuesta HTML del error.
     * @return Mensaje de error extraído o mensaje por defecto si no se encuentra.
     */
    public String extractHtmlMessage(String htmlResponse) {
        try {
            Document document = Jsoup.parse(htmlResponse);
            // Extraer el título y el mensaje de error en caso de estar presentes
            Element h1Element = document.selectFirst(H1);
            Element errorElement = document.selectFirst(P_ERROR);

            // Construir un mensaje combinando ambos si están presentes
            StringBuilder messageBuilder = new StringBuilder();
            if (h1Element != null) {
                messageBuilder.append(h1Element.text()).append(": ");
            }
            if (errorElement != null) {
                messageBuilder.append(errorElement.text());
            }

            return !messageBuilder.isEmpty() ? messageBuilder.toString() : ERROR_HTML;
        } catch (Exception e) {
            return ERROR_DESC;
        }
    }

    /**
     * Extrae cadenas JSON específicas de un mensaje, en función de una expresión
     * regular.
     *
     * @param message Mensaje con contenido JSON.
     * @return Cadena JSON extraída o {@code null} si no se encuentra.
     */
    public String extractJson(String message) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(REGEX);
        java.util.regex.Matcher matcher = pattern.matcher(message);
        return matcher.find() ? matcher.group() : null;
    }

    public String getErrorSource(FeignException e) {
        String requester = e.getMessage();
        if (requester != null) {
            if (requester.contains(Constants.GESTOR_PATH)) {
                return Constants.GP_PREFIX;
            } else if (requester.contains(Constants.SUNAT_PATH)) {
                return Constants.SUNAT_PREFIX;
            } else if (requester.contains(Constants.ARCHIVO_PATH)) {
                return Constants.SUNAT_PREFIX;
            }else if(requester.contains(Constants.DOCUMENTOS_PATH)){
                return Constants.FL_PREFIX;
            }
        }
        return Constants.UNKNOWN;
    }
}
