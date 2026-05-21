package pe.gob.vuce.cp.sp.pagos.domain.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.any;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pe.gob.vuce.cp.sp.pagos.domain.constants.Constants;
import pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsError;
import pe.gob.vuce.cp.sp.pagos.domain.model.OrdenPago;
import java.lang.reflect.Method;

 class FeignExceptionHandlerTest {

    private FeignExceptionHandler handler;
     private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        handler = new FeignExceptionHandler(objectMapper);
    }

     private FeignException mockFeignException(int status, String message, String content) {
         return new TestFeignException(status, message, content);
     }




    @Test
    void testHandleFeignClientException_400_callsHandleJsonError() {
        OrdenPago ordenPago = new OrdenPago();
        String json = "{\"alias\":\"ALIAS_ERROR\",\"description\":\"Description error\"}";
        FeignException ex = mockFeignException(400, "Caller path " + Constants.GESTOR_PATH, json);

        handler.handleFeignClientException(ex, ordenPago);

        assertEquals("GP ALIAS_ERROR", ordenPago.getPpDescCortaError());
        assertEquals("GP Description error", ordenPago.getPpMensajeRechazoSinConexion());
        assertEquals("400", ordenPago.getPpCodigorechazoSinConexion());
    }

    @Test
    void testHandleFeignClientException_404_callsHandleHtmlError() {
        OrdenPago ordenPago = new OrdenPago();
        String json = "{\"alias\":\"ALIAS_ERROR\",\"description\":\"Description error\"}";
        FeignException ex = mockFeignException(404, "Caller path " + Constants.GESTOR_PATH, json);
        handler.handleFeignClientException(ex, ordenPago);

        assertEquals("GP ALIAS_ERROR", ordenPago.getPpDescCortaError());
        assertEquals("GP Description error", ordenPago.getPpMensajeRechazoSinConexion());
        assertEquals("404", ordenPago.getPpCodigorechazoSinConexion());
    }

    @Test
    void testHandleFeignClientException_500_callsHandleInternalServerError() {
        OrdenPago ordenPago = new OrdenPago();
        String messageWithJson = "some text {\"alias\":\"ALIAS_500\",\"description\":\"Internal error description\"} some text";
        FeignException ex = mockFeignException(500, messageWithJson, "");

        handler.handleFeignClientException(ex, ordenPago);

        assertEquals(ConstantsError.ERROR_500, ordenPago.getPpDescCortaError());
        assertFalse(ordenPago.getPpMensajeRechazoSinConexion().contains("Internal error description"));
        assertEquals("500", ordenPago.getPpCodigorechazoSinConexion());
    }

    @Test
    void testHandleFeignClientException_DefaultCase_setsUnknownError() {
        OrdenPago ordenPago = new OrdenPago();
        FeignException ex = mockFeignException(999, "some message", "some content");

        handler.handleFeignClientException(ex, ordenPago);

        assertEquals(ConstantsError.ERROR_DESCONOCIDO, ordenPago.getPpDescCortaError());
        assertEquals(ConstantsError.ERROR_DESC, ordenPago.getPpMensajeRechazoSinConexion());
        assertEquals("999", ordenPago.getPpCodigorechazoSinConexion());
    }

    @Test
    void testHandleJsonError_withInvalidJson_setsDefaultError() {
        OrdenPago ordenPago = new OrdenPago();
        FeignException ex = mockFeignException(400, "Caller path", "invalid json");

        // Forcing invalid JSON
        handler.handleJsonError(ex, ordenPago, "DEFAULT_ERROR");

        assertEquals("DEFAULT_ERROR", ordenPago.getPpDescCortaError());
        assertEquals(ConstantsError.ERROR_JSON, ordenPago.getPpMensajeRechazoSinConexion());
    }

     @Test
     void testHandleHtmlError_withNoKnownPath_setsNoPrefix() throws Exception {
         OrdenPago ordenPago = new OrdenPago();
         String html = "<html><h1>Title</h1><p>Message</p></html>";
         FeignException ex = mockFeignException(404, "Unknown path", html);
         Method method = FeignExceptionHandler.class.getDeclaredMethod("handleHtmlError", FeignException.class, OrdenPago.class, String.class);
         method.setAccessible(true);
         method.invoke(handler, ex, ordenPago, "ERROR_CODE");
         assertEquals("ERROR_CODE", ordenPago.getPpDescCortaError());
         assertEquals("Title:", ordenPago.getPpMensajeRechazoSinConexion().trim());
     }

     @Test
     void testHandleFeignClientException_502_callsHandleHtmlError() {
         OrdenPago ordenPago = new OrdenPago();
         String html = "<html><h1>Error 502</h1><p>Bad Gateway</p></html>";
         FeignException ex = mockFeignException(502, "Caller path " + Constants.SUNAT_PATH, html);

         handler.handleFeignClientException(ex, ordenPago);

         assertEquals("PP Error 502 Bad Gateway", ordenPago.getPpDescCortaError());
         assertEquals("PP Error 502:", ordenPago.getPpMensajeRechazoSinConexion().trim());
         assertEquals("502", ordenPago.getPpCodigorechazoSinConexion());
     }

     @Test
     void testHandleJsonError_412_setsErrorFields() throws Exception {
         OrdenPago ordenPago = new OrdenPago();
         String json = "{\"error\":\"Validation failed\"}";
         FeignException ex = mockFeignException(412, "Caller path " + Constants.SUNAT_PATH, json);

         Method method = FeignExceptionHandler.class.getDeclaredMethod("handleJsonError", FeignException.class, OrdenPago.class, String.class);
         method.setAccessible(true);
         method.invoke(handler, ex, ordenPago, ConstantsError.ERROR_412);

         assertEquals("PP Precondition Failed", ordenPago.getPpDescCortaError());
         assertEquals("PP No se encontró mensaje de error en el JSON", ordenPago.getPpMensajeRechazoSinConexion());
         assertEquals(null, ordenPago.getPpCodigorechazoSinConexion());
     }

     @Test
     void testHandleFeignClientException_503_callsHandleHtmlError()  {
         OrdenPago ordenPago = new OrdenPago();
         String html = "<html><h1>Error 503</h1><p>Bad Gateway</p></html>";
         FeignException ex = mockFeignException(503, "Caller path " + Constants.SUNAT_PATH, html);

         handler.handleFeignClientException(ex, ordenPago);

         assertEquals("PP 503 Service Unavailable", ordenPago.getPpDescCortaError());
         assertEquals("PP Error 503:", ordenPago.getPpMensajeRechazoSinConexion().trim());
         assertEquals("503", ordenPago.getPpCodigorechazoSinConexion());
     }

     @Test
     void testHandleFeignClientException_504_callsHandleHtmlError() {
         OrdenPago ordenPago = new OrdenPago();
         String html = "<html><h1>Error 504</h1><p>Gateway Timeout</p></html>";
         FeignException ex = mockFeignException(504, "Caller path " + Constants.SUNAT_PATH, html);

         handler.handleFeignClientException(ex, ordenPago);

         assertEquals("PP 504 Gateway Timeout", ordenPago.getPpDescCortaError());
         assertEquals("PP Error 504:", ordenPago.getPpMensajeRechazoSinConexion().trim());
         assertEquals("504", ordenPago.getPpCodigorechazoSinConexion());
     }

     @Test
     void testHandleFeignClientException_412_callsHandleHtmlError() {
         OrdenPago ordenPago = new OrdenPago();
         String html = "<html><h1>Error 412</h1><p>Precondition Failed</p></html>";
         FeignException ex = mockFeignException(412, "Caller path " + Constants.SUNAT_PATH, html);

         handler.handleFeignClientException(ex, ordenPago);

         assertEquals("Precondition Failed", ordenPago.getPpDescCortaError());
         assertEquals("No se encontró mensaje de error en el JSON", ordenPago.getPpMensajeRechazoSinConexion().trim());
         assertEquals("412", ordenPago.getPpCodigorechazoSinConexion());
     }

     @Test
     void testHandleInternalServerError_withHtml_setsErrorMessage()  {
         OrdenPago ordenPago = new OrdenPago();
         String html = "<html><h1>Internal Error</h1><p>Server crashed</p></html>";
         FeignException ex = mockFeignException(500, "Error with html", html);

         FeignExceptionHandler spyHandler = Mockito.spy(handler);

         Mockito.doReturn("Internal Error: Server crashed").when(spyHandler).extractHtmlMessage(html);

         spyHandler.handleInternalServerError(ex, ordenPago);

         assertEquals(ConstantsError.ERROR_500, ordenPago.getPpDescCortaError());
         assertEquals("Internal Error: Server crashed", ordenPago.getPpMensajeRechazoSinConexion());
     }

     @Test
     void testHandleInternalServerError_withHtmlEmpty_setsDefaultMessage() {
         OrdenPago ordenPago = new OrdenPago();
         String html = "<html></html>";
         FeignException ex = mockFeignException(500, "Error with empty html", html);

         FeignExceptionHandler spyHandler = Mockito.spy(handler);
         Mockito.doReturn("").when(spyHandler).extractHtmlMessage(Mockito.eq(html));

         spyHandler.handleInternalServerError(ex, ordenPago);

         assertEquals(ConstantsError.ERROR_500, ordenPago.getPpDescCortaError());
         assertEquals(ConstantsError.ERROR_INTERNO, ordenPago.getPpMensajeRechazoSinConexion());
     }

     @Test
     void testHandleInternalServerError_throwsException_setsUnknownError()  {
         OrdenPago ordenPago = new OrdenPago();
         FeignException ex = mockFeignException(500, "Error causing exception", "");

         FeignExceptionHandler spyHandler = Mockito.spy(handler);
         Mockito.doThrow(new RuntimeException("fail")).when(spyHandler).extractJson(any());

         spyHandler.handleInternalServerError(ex, ordenPago);

         assertEquals(ConstantsError.ERROR_500, ordenPago.getPpDescCortaError());
         assertEquals(ConstantsError.ERROR_DESCONOCIDO, ordenPago.getPpMensajeRechazoSinConexion());
     }


     @Test
     void testHandleInternalServerError_withJsonString_callsHandleJsonError() {
         // Arrange
         OrdenPago ordenPago = new OrdenPago();
         String jsonContent = "{\"error\":\"some error\"}";
         FeignException feignException = mockFeignException(500, "Some path", jsonContent);

         ObjectMapper objectMapper1 = new ObjectMapper();
         FeignExceptionHandler realHandler = new FeignExceptionHandler(objectMapper1);
         FeignExceptionHandler spyHandler = Mockito.spy(realHandler);
         Mockito.doReturn(jsonContent).when(spyHandler).extractJson(anyString());
         Mockito.doNothing().when(spyHandler).handleJsonError(eq(feignException), eq(ordenPago), eq(ConstantsError.ERROR_500));
         spyHandler.handleInternalServerError(feignException, ordenPago);
         Mockito.verify(spyHandler).handleJsonError(feignException, ordenPago, ConstantsError.ERROR_500);
         Mockito.verify(spyHandler, never()).extractHtmlMessage(anyString());
     }

     @Test
     void testExtractHtmlMessage_includesErrorElementText() {
         String html = "<html><h1>Error Title</h1><p>Error detail here</p></html>";
         FeignExceptionHandler feignExceptionHandler = new FeignExceptionHandler(new ObjectMapper());
         String result = feignExceptionHandler.extractHtmlMessage(html);
         assertEquals("Error Title:", result.trim());
     }

     @Test
     void testExtractHtmlMessage_whenExceptionThrown_returnsErrorDesc() {
         FeignExceptionHandler feignExceptionHandler = new FeignExceptionHandler(new ObjectMapper());

         String result = feignExceptionHandler.extractHtmlMessage(null);

         assertEquals(ConstantsError.ERROR_DESC, result);
     }


     private static class TestFeignException extends FeignException {
         private final int status;
         private final String message;
         private final String content;

         protected TestFeignException(int status, String message, String content) {
             super(status, message);
             this.status = status;
             this.message = message;
             this.content = content;
         }

         @Override
         public int status() {
             return status;
         }

         @Override
         public String getMessage() {
             return message;
         }

         @Override
         public String contentUTF8() {
             return content;
         }
     }

 }
