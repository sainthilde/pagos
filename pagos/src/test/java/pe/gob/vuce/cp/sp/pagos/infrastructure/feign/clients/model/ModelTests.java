package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

class ModelTests {

    @Test
     void testArchivoResponse() {
        ArchivoResponse archivoResponse = new ArchivoResponse();
        archivoResponse.setNombre("test.pdf");
        archivoResponse.setContenido("contenido del archivo");

        assertEquals("test.pdf", archivoResponse.getNombre());
        assertEquals("contenido del archivo", archivoResponse.getContenido());
    }

    @Test
     void testComunesQueryResponse() {
        // Crear una instancia de Data
        Data data = new Data();
        data.setId(1);
        data.setDescriptionEs("Descripción en español");
        data.setDescriptionEn("Description in English");

        // Crear y establecer othersColumns
        Map<String, String> othersColumns = new HashMap<>();
        othersColumns.put("extra1", "value1");
        othersColumns.put("extra2", "value2");
        data.setOthersColumns(othersColumns);

        // Crear una instancia de Meta
        Meta meta = new Meta();
        meta.setResult("success");
        meta.setCantidadRegistros(1);
        meta.setCantidadRegistrosTotal(10);

        // Establecer mensajes
        List<String> mensajes = new ArrayList<>();
        mensajes.add("Mensaje 1");
        mensajes.add("Mensaje 2");
        meta.setMensajes(mensajes);

        // Crear y llenar la lista de datos
        List<Data> dataList = new ArrayList<>();
        dataList.add(data);

        // Crear la respuesta
        ComunesQueryResponse response = new ComunesQueryResponse();
        response.setMeta(meta);
        response.setData(dataList);

        // Verificaciones
        assertEquals("success", response.getMeta().getResult());
        assertEquals(1, response.getData().size());
        assertEquals(1, response.getData().get(0).getId());
        assertEquals("Descripción en español", response.getData().get(0).getDescriptionEs());
        assertEquals("Description in English", response.getData().get(0).getDescriptionEn());

        // Verificar othersColumns
        assertNotNull(response.getData().get(0).getOthersColumns());
        assertEquals(2, response.getData().get(0).getOthersColumns().size());
        assertEquals("value1", response.getData().get(0).getOthersColumns().get("extra1"));
        assertEquals("value2", response.getData().get(0).getOthersColumns().get("extra2"));

        // Verificar Meta
        assertEquals(1, response.getMeta().getCantidadRegistros());
        assertEquals(10, response.getMeta().getCantidadRegistrosTotal());
        assertNotNull(response.getMeta().getMensajes());
        assertEquals(2, response.getMeta().getMensajes().size());
        assertEquals("Mensaje 1", response.getMeta().getMensajes().get(0));

        // Verificar lista de datos vacía
        ComunesQueryResponse emptyResponse = new ComunesQueryResponse();
        emptyResponse.setMeta(new Meta());
        emptyResponse.setData(new ArrayList<>());

        assertNotNull(emptyResponse.getMeta());
        assertTrue(emptyResponse.getData().isEmpty());
    }

    @Test
     void testDocumentRequestDTO() {
        DocumentRequestDTO documentRequestDTO = new DocumentRequestDTO();

        // Establecer valores
        documentRequestDTO.setNombre("document.pdf");
        documentRequestDTO.setFile("fileContent");

        // Crear un mapa y establecerlo
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("key1", "value1");
        dataMap.put("key2", 123);
        documentRequestDTO.setData(dataMap);

        // Verificar los valores asignados a los atributos
        assertEquals("document.pdf", documentRequestDTO.getNombre());
        assertEquals("fileContent", documentRequestDTO.getFile());

        // Verificar que el mapa se haya establecido correctamente
        assertNotNull(documentRequestDTO.getData());
        assertEquals(2, documentRequestDTO.getData().size());
        assertEquals("value1", documentRequestDTO.getData().get("key1"));
        assertEquals(123, documentRequestDTO.getData().get("key2"));

        // Verificar que los valores iniciales son correctos con el constructor vacío
        DocumentRequestDTO emptyDTO = new DocumentRequestDTO();
        assertNull(emptyDTO.getNombre());
        assertNull(emptyDTO.getFile());
        assertNull(emptyDTO.getData());

        // Probar con valores nulos
        documentRequestDTO.setNombre(null);
        documentRequestDTO.setFile(null);
        documentRequestDTO.setData(null);

        assertNull(documentRequestDTO.getNombre());
        assertNull(documentRequestDTO.getFile());
        assertNull(documentRequestDTO.getData());
    }

    @Test
     void testOAuthResponse() {
        // Crear una instancia de OAuthResponse usando el constructor
        OAuthResponse oauthResponse = new OAuthResponse();
        oauthResponse.setAccessToken("sampleAccessToken");
        oauthResponse.setScope("read write");
        oauthResponse.setTokenType("Bearer");
        oauthResponse.setExpiresIn(3600);

        // Verificar los valores asignados a los atributos
        assertEquals("sampleAccessToken", oauthResponse.getAccessToken());
        assertEquals("read write", oauthResponse.getScope());
        assertEquals("Bearer", oauthResponse.getTokenType());
        assertEquals(3600, oauthResponse.getExpiresIn());

        // Verificar que los valores iniciales son correctos con el constructor vacío
        OAuthResponse oauthResponseEmpty = new OAuthResponse();
        assertNull(oauthResponseEmpty.getAccessToken());
        assertNull(oauthResponseEmpty.getScope());
        assertNull(oauthResponseEmpty.getTokenType());
        assertEquals(0, oauthResponseEmpty.getExpiresIn()); // Si es un int, el valor por defecto es 0

        // Probar la serialización y deserialización JSON
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(oauthResponse);

            // Comprobar que la serialización fue exitosa
            assertNotNull(jsonString);
            assertTrue(jsonString.contains("sampleAccessToken"));
            assertTrue(jsonString.contains("read write"));
            assertTrue(jsonString.contains("Bearer"));
            assertTrue(jsonString.contains("3600"));

            // Deserializar de nuevo a un objeto
            OAuthResponse deserialized = objectMapper.readValue(jsonString, OAuthResponse.class);

            // Verificar que la deserialización fue exitosa
            assertEquals(oauthResponse.getAccessToken(), deserialized.getAccessToken());
            assertEquals(oauthResponse.getScope(), deserialized.getScope());
            assertEquals(oauthResponse.getTokenType(), deserialized.getTokenType());
            assertEquals(oauthResponse.getExpiresIn(), deserialized.getExpiresIn());
        } catch (Exception e) {
            fail("Exception occurred during JSON serialization/deserialization: " + e.getMessage());
        }
    }

    @Test
    void testOrdenPagoRequestDTOConstructorConValores() {
       OrdenPagoRequestDTO ordenPago = new OrdenPagoRequestDTO(
               1,
               2,
               "formato1",
               "Descripción del formato",
               "TUPA123",
               1000.00,
               "2024-12-31",
               "DOC123",
               "usuario1",
               "12345678",
               "tipo1",
               "tipoRef1",
               "ref1",
               "tipoRef2",
               "ref2",
               3,
               "tipoOperador",
               "RUC12345678"
       );

       assertAll("ordenPago",
               () -> assertEquals(1, ordenPago.getEntidadId()),
               () -> assertEquals(2, ordenPago.getPerfilId()),
               () -> assertEquals("formato1", ordenPago.getFormato()),
               () -> assertEquals("Descripción del formato", ordenPago.getDesFormato()),
               () -> assertEquals("TUPA123", ordenPago.getTupa()),
               () -> assertEquals(1000.00, ordenPago.getMontoExacto()),
               () -> assertEquals("2024-12-31", ordenPago.getFechaVigencia()),
               () -> assertEquals("DOC123", ordenPago.getCodDocumento()),
               () -> assertEquals("usuario1", ordenPago.getNombreUsuario()),
               () -> assertEquals("12345678", ordenPago.getNroDocumento()),
               () -> assertEquals("tipo1", ordenPago.getTipoCodigoReferencia()),
               () -> assertEquals("tipoRef1", ordenPago.getTipoReferencia1()),
               () -> assertEquals("ref1", ordenPago.getCodReferencia1()),
               () -> assertEquals("tipoRef2", ordenPago.getTipoReferencia2()),
               () -> assertEquals("ref2", ordenPago.getCodReferencia2()),
               () -> assertEquals(3, ordenPago.getComponenteId()),
               () -> assertEquals("tipoOperador", ordenPago.getTipoOperador()),
               () -> assertEquals("RUC12345678", ordenPago.getRucOperador())
       );
    }

    @Test
    void testOrdenPagoRequestDTOConstructorVacio() {
       OrdenPagoRequestDTO ordenPagoEmpty = new OrdenPagoRequestDTO();

       assertAll("ordenPagoEmpty",
               () -> assertNull(ordenPagoEmpty.getEntidadId()),
               () -> assertNull(ordenPagoEmpty.getPerfilId()),
               () -> assertNull(ordenPagoEmpty.getFormato()),
               () -> assertNull(ordenPagoEmpty.getDesFormato()),
               () -> assertNull(ordenPagoEmpty.getTupa()),
               () -> assertNull(ordenPagoEmpty.getMontoExacto()),
               () -> assertNull(ordenPagoEmpty.getFechaVigencia()),
               () -> assertNull(ordenPagoEmpty.getCodDocumento()),
               () -> assertNull(ordenPagoEmpty.getNombreUsuario()),
               () -> assertNull(ordenPagoEmpty.getNroDocumento()),
               () -> assertNull(ordenPagoEmpty.getTipoCodigoReferencia()),
               () -> assertNull(ordenPagoEmpty.getTipoReferencia1()),
               () -> assertNull(ordenPagoEmpty.getCodReferencia1()),
               () -> assertNull(ordenPagoEmpty.getTipoReferencia2()),
               () -> assertNull(ordenPagoEmpty.getCodReferencia2()),
               () -> assertNull(ordenPagoEmpty.getComponenteId()),
               () -> assertNull(ordenPagoEmpty.getTipoOperador()),
               () -> assertNull(ordenPagoEmpty.getRucOperador())
       );
    }

    @Test
     void testPaymentMethodResponse() {
        // Crear instancia de PaymentMethodResponse
        PaymentMethodResponse paymentMethodResponse = new PaymentMethodResponse();
        paymentMethodResponse.setCanalId(1);
        paymentMethodResponse.setEntidadId(1);
        paymentMethodResponse.setTitulo("Payment Title");
        paymentMethodResponse.setIconoTitulo("icono.png");
        paymentMethodResponse.setOrden(1);

        // Probar Instruccion
        List<PaymentMethodResponse.Instruccion> instrucciones = new ArrayList<>();
        PaymentMethodResponse.Instruccion instruccion = new PaymentMethodResponse.Instruccion();
        instruccion.setDescripcion("Instrucción 1");
        instruccion.setOrden(1);
        instrucciones.add(instruccion);
        paymentMethodResponse.setListaInstruccion(instrucciones);

        // Aserciones para instrucciones
        assertEquals("Payment Title", paymentMethodResponse.getTitulo());
        assertEquals(1, paymentMethodResponse.getListaInstruccion().size());
        assertEquals("Instrucción 1", paymentMethodResponse.getListaInstruccion().get(0).getDescripcion());
        assertEquals(1, paymentMethodResponse.getListaInstruccion().get(0).getOrden());

        // Probar Nota
        List<PaymentMethodResponse.Nota> notas = new ArrayList<>();
        PaymentMethodResponse.Nota nota = new PaymentMethodResponse.Nota();
        nota.setDescripcion("Nota 1");
        nota.setOrden(1);
        notas.add(nota);
        paymentMethodResponse.setListaNota(notas);

        // Aserciones para notas
        assertEquals(1, paymentMethodResponse.getListaNota().size());
        assertEquals("Nota 1", paymentMethodResponse.getListaNota().get(0).getDescripcion());
        assertEquals(1, paymentMethodResponse.getListaNota().get(0).getOrden());

        // Probar Cuenta
        List<PaymentMethodResponse.Cuenta> cuentas = new ArrayList<>();
        PaymentMethodResponse.Cuenta cuenta = new PaymentMethodResponse.Cuenta();
        cuenta.setCuenta("12345678");
        cuenta.setBanco("Banco Test");
        cuentas.add(cuenta);
        paymentMethodResponse.setListaCuenta(cuentas);

        // Aserciones para cuentas
        assertEquals(1, paymentMethodResponse.getListaCuenta().size());
        assertEquals("12345678", paymentMethodResponse.getListaCuenta().get(0).getCuenta());
        assertEquals("Banco Test", paymentMethodResponse.getListaCuenta().get(0).getBanco());

        // Probar Banco
        List<PaymentMethodResponse.Banco> bancos = new ArrayList<>();
        PaymentMethodResponse.Banco banco = new PaymentMethodResponse.Banco();
        banco.setNombre("Banco de Ejemplo");
        banco.setTooltip("Tooltip Test");
        banco.setUrlImg("http://testurl.com/image.png");
        banco.setOrden(1);
        bancos.add(banco);
        paymentMethodResponse.setListaBanco(bancos);

        // Aserciones para bancos
        assertEquals(1, paymentMethodResponse.getListaBanco().size());
        assertEquals("Banco de Ejemplo", paymentMethodResponse.getListaBanco().get(0).getNombre());
        assertEquals("Tooltip Test", paymentMethodResponse.getListaBanco().get(0).getTooltip());
        assertEquals("http://testurl.com/image.png", paymentMethodResponse.getListaBanco().get(0).getUrlImg());
        assertEquals(1, paymentMethodResponse.getListaBanco().get(0).getOrden());

        // Aserciones para el PaymentMethodResponse completo
        assertEquals(1, paymentMethodResponse.getCanalId());
        assertEquals(1, paymentMethodResponse.getEntidadId());
        assertEquals("icono.png", paymentMethodResponse.getIconoTitulo());
        assertEquals(1, paymentMethodResponse.getOrden());
    }


    @Test
     void testProcedimientosResponse() {
        // Crear una instancia de Procedimiento
        ProcedimientosResponse.Procedimiento procedimiento = new ProcedimientosResponse.Procedimiento();
        procedimiento.setProcedimientoId(1);
        procedimiento.setEntidadId(2);
        procedimiento.setTupa("TUPA123");
        procedimiento.setProcedimientoVersion(1);
        procedimiento.setSiglas("SIGLA");
        procedimiento.setFormato("Formato A");
        procedimiento.setCut(123);
        procedimiento.setNombreCut("Nombre del CUT");
        procedimiento.setComponente("Componente A");
        procedimiento.setAyuda("Ayuda A");
        procedimiento.setPago("Pago A");
        procedimiento.setPlazo("30 días");
        procedimiento.setDescripcionCalificacion("Calificación A");

        // Crear una instancia de ProcedimientosResponse y añadir el procedimiento
        ProcedimientosResponse response = new ProcedimientosResponse();
        List<ProcedimientosResponse.Procedimiento> procedimientosList = new ArrayList<>();
        procedimientosList.add(procedimiento);
        response.setProcedimientos(procedimientosList);

        // Verificar que los valores sean los esperados
        assertEquals(1, response.getProcedimientos().size());
        assertEquals(1, response.getProcedimientos().get(0).getProcedimientoId());
        assertEquals(2, response.getProcedimientos().get(0).getEntidadId());
        assertEquals("TUPA123", response.getProcedimientos().get(0).getTupa());
        assertEquals(1, response.getProcedimientos().get(0).getProcedimientoVersion());
        assertEquals("SIGLA", response.getProcedimientos().get(0).getSiglas());
        assertEquals("Formato A", response.getProcedimientos().get(0).getFormato());
        assertEquals(123, response.getProcedimientos().get(0).getCut());
        assertEquals("Nombre del CUT", response.getProcedimientos().get(0).getNombreCut());
        assertEquals("Componente A", response.getProcedimientos().get(0).getComponente());
        assertEquals("Ayuda A", response.getProcedimientos().get(0).getAyuda());
        assertEquals("Pago A", response.getProcedimientos().get(0).getPago());
        assertEquals("30 días", response.getProcedimientos().get(0).getPlazo());
        assertEquals("Calificación A", response.getProcedimientos().get(0).getDescripcionCalificacion());
    }

    @Test
     void testTasaResponse() {
        // Crear una instancia de Tasa
        TasaResponse.Tasa tasa = new TasaResponse.Tasa();
        tasa.setProcedimientoId(1);
        tasa.setProcedimientoVersion(1);
        tasa.setProcedimientoTasaVersion(1);
        tasa.setSecuencia(1);
        tasa.setMonto(50.0);
        tasa.setEtiqueta("Test Tasa");
        tasa.setDescripcion("Descripción de la Tasa");
        tasa.setCodigoMoneda("USD");
        tasa.setMonedaDescripcion("Dólar Estadounidense");
        tasa.setMonedaSigno("$");

        // Crear una instancia de TasaResponse y añadir la tasa
        TasaResponse response = new TasaResponse();
        List<TasaResponse.Tasa> tasasList = new ArrayList<>();
        tasasList.add(tasa);
        response.setTasas(tasasList);

        // Verificar que los valores sean los esperados
        assertEquals(50.0, response.getTasas().get(0).getMonto());
        assertEquals("Test Tasa", response.getTasas().get(0).getEtiqueta());
        assertEquals(1, response.getTasas().get(0).getProcedimientoId());
        assertEquals(1, response.getTasas().get(0).getProcedimientoVersion());
        assertEquals(1, response.getTasas().get(0).getProcedimientoTasaVersion());
        assertEquals(1, response.getTasas().get(0).getSecuencia());
        assertEquals("Descripción de la Tasa", response.getTasas().get(0).getDescripcion());
        assertEquals("USD", response.getTasas().get(0).getCodigoMoneda());
        assertEquals("Dólar Estadounidense", response.getTasas().get(0).getMonedaDescripcion());
        assertEquals("$", response.getTasas().get(0).getMonedaSigno());
    }

}

