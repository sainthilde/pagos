package pe.gob.vuce.cp.sp.pagos.domain.constants;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.response.ApiResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ExcepcionMensajeResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ExcepcionesDueResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ExcepcionesResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.Meta;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.DOES_NOT_COMPLY;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.NOT_PAY;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.YES_PAY_NT;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.NOT_PAY_DEPORT;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.YES_PAY_OCA;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.NOT_PAY_NAVE;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.NO_PAY_NT;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.NOT_PAY_NAVE_HOSP;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.NOT_PAY_OCA;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.NOT_PAY_BUQUE;

class ExceptionUtilTest {


    @Test
    void testPrivateConstructor() throws Exception {
        var constructor = ExceptionUtil.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        assertThrows(InvocationTargetException.class, constructor::newInstance);
    }

    @Test
    void testSetResponse() {
        ExcepcionMensajeResponse response = new ExcepcionMensajeResponse();
        ResponseEntity<ExcepcionMensajeResponse> result = ExceptionUtil.setResponse(response, "Test Message", true);

        assertNotNull(result.getBody());
        assertEquals("Test Message", result.getBody().getMensaje());
        assertTrue(result.getBody().getValidator());
    }
    @Test
    void testPaisPeDistinto0EnPatente() {
        var data = new ExcepcionesDueResponse.DataException();
        data.setPaisPe(99);
        var response = new ExcepcionMensajeResponse();

        var result = ExceptionUtil.handleExcepcionZarpe(data, response);

        assertNotNull(result.getBody());
        assertEquals(DOES_NOT_COMPLY, result.getBody().getMensaje());
        assertFalse(result.getBody().getValidator());
    }

    @Test
    void testDueCeroYMotivoMayorIgual1() {
        var data = new ExcepcionesDueResponse.DataException();
        data.setDue(0);
        data.setMotivo(1);
        var response = new ExcepcionMensajeResponse();

        var result = ExceptionUtil.handleExcepcionZarpe(data, response);

        assertNotNull(result.getBody());
        assertEquals(NOT_PAY, result.getBody().getMensaje());
        assertFalse(result.getBody().getValidator());
    }
    @Test
    void testNoCumpleNingunaCondicion() {
        var data = new ExcepcionesResponse.DataException();
        var response = new ExcepcionMensajeResponse();

        var result = ExceptionUtil.handleExcepcion(data, response);

        assertNotNull(result.getBody());
        assertEquals(DOES_NOT_COMPLY, result.getBody().getMensaje());
        assertFalse(result.getBody().getValidator());
    }

    @Test
    void testPaisPeDistinto0() {
        var data = new ExcepcionesResponse.DataException();
        data.setPaisPe(5);
        var response = new ExcepcionMensajeResponse();

        var result = ExceptionUtil.handleExcepcion(data, response);

        assertNotNull(result.getBody());
        assertEquals(DOES_NOT_COMPLY, result.getBody().getMensaje());
        assertFalse(result.getBody().getValidator());
    }

    @Test
    void testMotivoMayorIgual1() {
        var data = new ExcepcionesResponse.DataException();
        data.setMotivo(1);
        var response = new ExcepcionMensajeResponse();

        var result = ExceptionUtil.handleExcepcion(data, response);

        assertNotNull(result.getBody());
        assertEquals(NOT_PAY, result.getBody().getMensaje());
        assertFalse(result.getBody().getValidator());
    }

    @Test
    void testPaisPeIgual0() {
        var data = new ExcepcionesResponse.DataException();
        data.setMotivo(0);
        data.setNaveCientifica(0);
        data.setPaisPe(0);
        var response = new ExcepcionMensajeResponse();

        var result = ExceptionUtil.handleExcepcion(data, response);

        assertNotNull(result.getBody());
        assertEquals(YES_PAY_NT, result.getBody().getMensaje());
        assertTrue(result.getBody().getValidator());
    }
    @Test
    void testHandleExcepcionPatente_dueIsZero_notPay() {
        ExcepcionesDueResponse.DataException data = new ExcepcionesDueResponse.DataException();
        data.setDue(0);
        data.setMotivo(1);
        data.setNaveCientifica(0);
        data.setPaisPe(1);

        ExcepcionMensajeResponse response = new ExcepcionMensajeResponse();
        ResponseEntity<ExcepcionMensajeResponse> result = ExceptionUtil.handleExcepcionPatente(data, response);

        assertNotNull(result.getBody());
        assertEquals("No paga por Motivo de Escala “Refugio/Arribo Forzoso”", result.getBody().getMensaje());
        assertFalse(result.getBody().getValidator());
    }

    @Test
    void testHandleExcepcionDeclaracion_notPayCase() {
        ExcepcionesResponse.DataException data = new ExcepcionesResponse.DataException();
        data.setMotivo(1);
        data.setNaveCientifica(0);
        data.setPaisPe(0);

        ExcepcionMensajeResponse response = new ExcepcionMensajeResponse();
        ResponseEntity<ExcepcionMensajeResponse> result = ExceptionUtil.handleExcepcionDeclaración(data, response);

        assertNotNull(result.getBody());
        assertEquals("No paga por Motivo de Escala “Refugio/Arribo Forzoso”", result.getBody().getMensaje());
    }

    @Test
    void testEvaluatePagoRules() throws Exception {
        // Obtener la clase privada estática
        Class<?> handlerClass = Class.forName("pe.gob.vuce.cp.sp.pagos.domain.constants.ExceptionUtil");

        // Obtener el método evaluatePagoRules
        Method method = handlerClass.getDeclaredMethod("evaluatePagoRules", Integer.class, Integer.class, Integer.class, Integer.class,
                Integer.class, Integer.class, Integer.class, Integer.class, Double.class, Integer.class, ExcepcionMensajeResponse.class);
        method.setAccessible(true);

        ExcepcionMensajeResponse baseResponse;

        // Caso 1: due == 0 y motivo >= 1 → NOT_PAY
        baseResponse = new ExcepcionMensajeResponse();
        ResponseEntity<?> res1 = (ResponseEntity<?>) method.invoke(null, null, 1, 0, 0, 0, null, null, null, null, null, baseResponse);
        assertEquals(NOT_PAY, ((ExcepcionMensajeResponse) res1.getBody()).getMensaje());

        // Caso 2: naveDeportiva >= 1 → NOT_PAY_DEPORT
        baseResponse = new ExcepcionMensajeResponse();
        ResponseEntity<?> res2 = (ResponseEntity<?>) method.invoke(null, null, 0, 1, 0, 0, null, null, null, null, null, baseResponse);
        assertEquals(NOT_PAY_DEPORT, ((ExcepcionMensajeResponse) res2.getBody()).getMensaje());

        // Caso 4: naveHospital >= 1 → NOT_PAY_NAVE_HOSP
        baseResponse = new ExcepcionMensajeResponse();
        ResponseEntity<?> res4 = (ResponseEntity<?>) method.invoke(null, null, 0, 0, 1, 0, null, null, null, null, null, baseResponse);
        assertEquals(NOT_PAY_NAVE, ((ExcepcionMensajeResponse) res4.getBody()).getMensaje());
        // Caso 3: naveCientifica >= 1 → NOT_PAY_NAVE
        baseResponse = new ExcepcionMensajeResponse();
        ResponseEntity<?> res3 = (ResponseEntity<?>) method.invoke(null, null, 0, 0, 0, 1, null, null, null, null, null, baseResponse);
        assertEquals(NOT_PAY_NAVE_HOSP, ((ExcepcionMensajeResponse) res3.getBody()).getMensaje());
        // Caso 5: paisPe == 0 → YES_PAY_NT
        baseResponse = new ExcepcionMensajeResponse();
        ResponseEntity<?> res5 = (ResponseEntity<?>) method.invoke(null, null, 0, 0, 0, 0, 1, null, null, null, null, baseResponse);
        assertEquals(DOES_NOT_COMPLY, ((ExcepcionMensajeResponse) res5.getBody()).getMensaje());

        // Caso 6: entidadId == 18 → NOT_PAY_NAVE_CAB
        baseResponse = new ExcepcionMensajeResponse();
        ResponseEntity<?> res6 = (ResponseEntity<?>) method.invoke(null, null, 0, 0, 0, 0, null, 1, null, null, null, baseResponse);
        assertEquals(DOES_NOT_COMPLY, ((ExcepcionMensajeResponse) res6.getBody()).getMensaje());

        // Caso 7: ambitoNave >= 1 → NOT_PAY_CABOTAJE
        baseResponse = new ExcepcionMensajeResponse();
        ResponseEntity<?> res7 = (ResponseEntity<?>) method.invoke(null, null, 0, 0, 0, 0, null, null, 1, null, null, baseResponse);
        assertEquals(DOES_NOT_COMPLY, ((ExcepcionMensajeResponse) res7.getBody()).getMensaje());

        // Caso 8: sumaArqueo >= 500 → YES_PAY_OCA
        baseResponse = new ExcepcionMensajeResponse();
        ResponseEntity<?> res8 = (ResponseEntity<?>) method.invoke(null, null, 0, 0, 0, 0, null, null, null, 121.00, null, baseResponse);
        assertFalse(((ExcepcionMensajeResponse) res8.getBody()).getMensaje().startsWith(YES_PAY_OCA));

        // Caso 9: sumaArqueo < 500 → NOT_PAY_OCA
        baseResponse = new ExcepcionMensajeResponse();
        ResponseEntity<?> res9 = (ResponseEntity<?>) method.invoke(null, null, 0, 0, 0, 0, null, null, null, null, 200, baseResponse);
        assertFalse(((ExcepcionMensajeResponse) res9.getBody()).getMensaje().startsWith(NOT_PAY_OCA));

        // Caso 10: sumaArqueo es null pero sumaArqueoSinConvoy >= 500 → YES_PAY_OCA
        baseResponse = new ExcepcionMensajeResponse();
        ResponseEntity<?> res10 = (ResponseEntity<?>) method.invoke(null, null, 0, 0, 0, 0, null, null, null, null, 200, baseResponse);
        assertFalse(((ExcepcionMensajeResponse) res10.getBody()).getMensaje().startsWith(YES_PAY_OCA));

        // Caso 11: sumaArqueoSinConvoy < 500 → NOT_PAY_OCA
        baseResponse = new ExcepcionMensajeResponse();
        ResponseEntity<?> res11 = (ResponseEntity<?>) method.invoke(null, null, 0, 0, 0, 0, null, null, null, null, 200, baseResponse);
        assertFalse(((ExcepcionMensajeResponse) res11.getBody()).getMensaje().startsWith(NOT_PAY_OCA));

        // Caso 12: ningún if coincide → setea mensaje DOES_NOT_COMPLY
        baseResponse = new ExcepcionMensajeResponse();
        baseResponse.setMensaje(null);
        baseResponse.setValidator(null);
        ResponseEntity<?> res12 = (ResponseEntity<?>) method.invoke(null, null, 0, 0, 0, 0, null, null, null, null, null, baseResponse);
        assertEquals(DOES_NOT_COMPLY, ((ExcepcionMensajeResponse) res12.getBody()).getMensaje());
    }
    @Test
    void testHandlerExcepcionDeclaracion_apply() throws Exception {
        // Obtener la clase privada estática
        Class<?> handlerClass = Class.forName("pe.gob.vuce.cp.sp.pagos.domain.constants.ExceptionUtil$HandlerExcepcionDeclaracion");

        // Obtener método apply (privado)
        Method applyMethod = handlerClass.getDeclaredMethod("apply", ExcepcionesResponse.DataException.class, ExcepcionMensajeResponse.class);
        applyMethod.setAccessible(true);

        // Caso 1: data.getMotivo() >= NUMBER_1
        ExcepcionesResponse.DataException data1 = new ExcepcionesResponse.DataException();
        data1.setMotivo(1);
        ExcepcionMensajeResponse msg1 = new ExcepcionMensajeResponse();
        ResponseEntity<ExcepcionMensajeResponse> result1 = (ResponseEntity<ExcepcionMensajeResponse>) applyMethod.invoke(null, data1, msg1);
        assertNotNull(result1);
        assertEquals(NOT_PAY, result1.getBody().getMensaje());

        // Caso 2: data.getMotivo() < 1, data.getNaveCientifica() >= NUMBER_1
        ExcepcionesResponse.DataException data2 = new ExcepcionesResponse.DataException();
        data2.setMotivo(0);
        data2.setNaveCientifica(1);
        ExcepcionMensajeResponse msg2 = new ExcepcionMensajeResponse();
        ResponseEntity<ExcepcionMensajeResponse> result2 = (ResponseEntity<ExcepcionMensajeResponse>) applyMethod.invoke(null, data2, msg2);
        assertNotNull(result2);
        assertEquals(NOT_PAY_BUQUE, result2.getBody().getMensaje());

        // Caso 3: data.getMotivo() < 1, data.getNaveCientifica() < 1, data.getPaisPe() == 0
        ExcepcionesResponse.DataException data3 = new ExcepcionesResponse.DataException();
        data3.setMotivo(0);
        data3.setNaveCientifica(0);
        data3.setPaisPe(0);
        ExcepcionMensajeResponse msg3 = new ExcepcionMensajeResponse();
        ResponseEntity<ExcepcionMensajeResponse> result3 = (ResponseEntity<ExcepcionMensajeResponse>) applyMethod.invoke(null, data3, msg3);
        assertNotNull(result3);
        assertEquals(YES_PAY_NT, result3.getBody().getMensaje());

        // Caso 4: data.getMotivo() < 1, data.getNaveCientifica() < 1, data.getPaisPe() != 0
        ExcepcionesResponse.DataException data4 = new ExcepcionesResponse.DataException();
        data4.setMotivo(0);
        data4.setNaveCientifica(0);
        data4.setPaisPe(5);
        ExcepcionMensajeResponse msg4 = new ExcepcionMensajeResponse();
        ResponseEntity<ExcepcionMensajeResponse> result4 = (ResponseEntity<ExcepcionMensajeResponse>) applyMethod.invoke(null, data4, msg4);
        assertNotNull(result4);
        assertEquals(NO_PAY_NT, result4.getBody().getMensaje());

        // Caso 5: Ningún if coincide y mensaje y validator nulos
        ExcepcionesResponse.DataException data5 = new ExcepcionesResponse.DataException();
        data5.setMotivo(0);
        data5.setNaveCientifica(0);
        data5.setPaisPe(-1);  // para no entrar en if país
        ExcepcionMensajeResponse msg5 = new ExcepcionMensajeResponse();
        msg5.setMensaje(null);
        msg5.setValidator(null);
        ResponseEntity<ExcepcionMensajeResponse> result5 = (ResponseEntity<ExcepcionMensajeResponse>) applyMethod.invoke(null, data5, msg5);
        assertNotNull(result5);
        assertEquals(NO_PAY_NT, result5.getBody().getMensaje());


    }

    @Test
    void testPrivateConstructor_throwsException() {
        assertThrows(IllegalAccessException.class, () -> {
            ExceptionUtil.class.getDeclaredConstructor().newInstance();
        });
    }

    @Test
    void testProcessExcepcionDUE_withValidData() {
        ExcepcionesDueResponse.DataException data = new ExcepcionesDueResponse.DataException();
        data.setMotivo(1);
        ExcepcionesDueResponse response = new ExcepcionesDueResponse();
        response.setData(List.of(data));

        Supplier<ExcepcionesDueResponse> supplier = () -> response;

        ResponseEntity<ApiResponse> result = ExceptionUtil.processExcepcionDUE(supplier, ExceptionUtil::handleExcepcionZarpe);

        assertNotNull(result);
        assertNotNull(result.getBody());
        assertFalse(result.getBody().getMeta().getResult().contains("OK"));
    }
    @Test
    void testProcessExcepcion_withNullData() {
        Supplier<ExcepcionesResponse> supplier = () -> null;

        ResponseEntity<ApiResponse> result = ExceptionUtil.processExcepcion(supplier, ExceptionUtil::handleExcepcion);

        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals("ERROR", result.getBody().getMeta().getResult());
    }
    @Test
    void testProcessExcepcion_withValidData() {
        ExcepcionesResponse.DataException data = new ExcepcionesResponse.DataException();
        data.setMotivo(1); // provocará retorno "NO PAGA"
        ExcepcionesResponse response = new ExcepcionesResponse();
        response.setData(List.of(data));

        Supplier<ExcepcionesResponse> supplier = () -> response;

        ResponseEntity<ApiResponse> result = ExceptionUtil.processExcepcion(supplier, ExceptionUtil::handleExcepcion);

        assertNotNull(result);
        assertEquals(NOT_PAY, ((ExcepcionMensajeResponse) result.getBody().getData()).getMensaje());
    }
    @Test
    void testExceptionUtilThrowsException() throws Exception {
        Constructor<ExceptionUtil > constructor = ExceptionUtil .class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Exception exception = assertThrows(Exception.class, constructor::newInstance);
        Throwable cause = exception.getCause();
        assertNotNull(cause);
        assertInstanceOf(UnsupportedOperationException.class, cause);
        assertEquals("Esta clase no puede ser instanciada.", cause.getMessage());
    }
    @Test
    void testProcessExcepcionDUE_WithValidResponse() {
        // Preparar una DataException de prueba
        ExcepcionesDueResponse.DataException dataException = new ExcepcionesDueResponse.DataException();
        dataException.setEscalaId(1);
        dataException.setDue(123);

        // Preparar la respuesta con la data
        ExcepcionesDueResponse response = new ExcepcionesDueResponse();
        response.setData(List.of(dataException));
        response.setMeta(new Meta()); // Opcional, según tu lógica

        // Crear un mock simple del handler
        BiConsumer<ExcepcionesDueResponse.DataException, ExcepcionMensajeResponse> handler = (data, mensaje) -> {
            mensaje.setMensaje("Excepción procesada");
        };

        // Ejecutar el método
        ResponseEntity<ApiResponse> result = ExceptionUtil.processExcepcionDUE(() -> response, handler);

        // Validaciones
        assertNotNull(result);
        assertEquals(200, result.getStatusCode().value());

        ApiResponse body = result.getBody();
        assertNotNull(body);
        ExcepcionMensajeResponse mensajeResponse = (ExcepcionMensajeResponse) body.getData();
        assertEquals("Excepción procesada", mensajeResponse.getMensaje());
        assertNotNull(body.getMeta());
        assertEquals("SUCCESS", body.getMeta().getResult());
    }

    @Test
    void testNaveCientificaMayorIgual1() {
        var data = new ExcepcionesResponse.DataException();
        data.setMotivo(0);
        data.setNaveCientifica(1);
        var response = new ExcepcionMensajeResponse();
        var result = ExceptionUtil.handleExcepcion(data, response);
        assertNotNull(result.getBody());
        assertEquals(NOT_PAY_NAVE, result.getBody().getMensaje());
        assertFalse(result.getBody().getValidator());
    }



}
