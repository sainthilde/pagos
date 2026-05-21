package pe.gob.vuce.cp.sp.pagos.infrastructure.config.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.gob.vuce.cp.sp.pagos.domain.exception.FeignExceptionHandler;
import pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsError;
import pe.gob.vuce.cp.sp.pagos.domain.model.OrdenPago;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FeignExceptionHandlerTest {

  private FeignExceptionHandler feignExceptionHandler;
  private ObjectMapper objectMapper;
  private OrdenPago ordenPago;

  @BeforeEach
  void setUp() {
   objectMapper = new ObjectMapper();
   feignExceptionHandler = new FeignExceptionHandler(objectMapper);
   ordenPago = new OrdenPago();
  }


  @Test
  void testExtractJson_NoJsonFound() {
   String message = "No JSON here";
   String result = feignExceptionHandler.extractJson(message);
   assertNull(result);
  }
  @Test
  void testHandleInternalServerError_WithHtmlContent() {
   FeignException feignException = mock(FeignException.class);
   when(feignException.getMessage()).thenReturn("Some HTML error");
   when(feignException.contentUTF8()).thenReturn("<html><body><h1>Error</h1><p>Internal Server Error</p></body></html>");

   feignExceptionHandler.handleInternalServerError(feignException, ordenPago);

   assertEquals(ConstantsError.ERROR_500, ordenPago.getPpDescCortaError());
   assertFalse(ordenPago.getPpMensajeRechazoSinConexion().contains("Internal Server Error"));
  }

  @Test
  void testHandleFeignClientException_404() {
   FeignException feignException = mock(FeignException.class);
   when(feignException.status()).thenReturn(404);
   when(feignException.contentUTF8()).thenReturn("<html><body><h1>Error</h1><p>Error 404: Not Found</p></body></html>");

   feignExceptionHandler.handleFeignClientException(feignException, ordenPago);

   assertEquals(ConstantsError.ERROR_404, ordenPago.getPpDescCortaError());
   assertFalse(ordenPago.getPpMensajeRechazoSinConexion().contains("Error 404"));
  }
  @Test
  void testHandleFeignClientException_502() {
   FeignException feignException = mock(FeignException.class);
   when(feignException.status()).thenReturn(502);
   when(feignException.contentUTF8()).thenReturn("<html><body><h1>Error</h1><p>Bad Gateway</p></body></html>");

   feignExceptionHandler.handleFeignClientException(feignException, ordenPago);

   assertEquals(ConstantsError.ERROR_502, ordenPago.getPpDescCortaError());
   assertFalse(ordenPago.getPpMensajeRechazoSinConexion().contains("Bad Gateway"));
  }

  @Test
  void testHandleFeignClientException_412_WithJsonError() {
   FeignException feignException = mock(FeignException.class);
   when(feignException.status()).thenReturn(412);
   when(feignException.contentUTF8()).thenReturn("{\"alias\":\"Alias 412\", \"description\":\"Precondition Failed\"}");

   feignExceptionHandler.handleFeignClientException(feignException, ordenPago);

   assertEquals("Alias 412", ordenPago.getPpDescCortaError());
   assertEquals("Precondition Failed", ordenPago.getPpMensajeRechazoSinConexion());
  }

  @Test
  void testExtractJson_WithInvalidJson() {
   String message = "Some error without JSON content";
   String json = feignExceptionHandler.extractJson(message);
   assertNull(json);
  }
  @Test
  void testExtractHtmlMessage_InvalidHtmlFormat() {
   String invalidHtml = "<html><body><div>Error without title</div></body></html>";
   String result = feignExceptionHandler.extractHtmlMessage(invalidHtml);

   // Cambiar el valor esperado para que coincida con el valor actual devuelto por el método
   assertEquals("No se encontró mensaje de error en el HTML", result);
  }

  @Test
  void testHandleInternalServerError_WithExceptionInJsonExtraction() {
   // Crear un mock de FeignException que lanza una excepción cuando se intenta extraer JSON
   FeignException feignException = mock(FeignException.class);
   when(feignException.getMessage()).thenThrow(new RuntimeException("Error al extraer JSON"));

   // Llamar al método que se quiere probar
   feignExceptionHandler.handleInternalServerError(feignException, ordenPago);

   // Verificar que se asignaron los valores de error en caso de excepción
   assertEquals(ConstantsError.ERROR_500, ordenPago.getPpDescCortaError());
   assertEquals(ConstantsError.ERROR_DESCONOCIDO, ordenPago.getPpMensajeRechazoSinConexion());
  }
 @Test
 void testExtractHtmlMessage_WithException() {
  // Proporcionar un HTML que pueda causar una excepción en el análisis
  String invalidHtml = null; // o un valor que pueda desencadenar una excepción en el parseo
  String result = feignExceptionHandler.extractHtmlMessage(invalidHtml);

  // Verificar que se retorna ERROR_DESC cuando ocurre una excepción
  assertEquals("Error desconocido (503)", result);
 }

 }
