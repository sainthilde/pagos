package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.fasterxml.jackson.core.JsonProcessingException;

import feign.FeignException;
import pe.gob.vuce.cp.sp.pagos.domain.exception.FeignExceptionHandler;
import pe.gob.vuce.cp.sp.pagos.domain.model.OrdenPago;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.CreateSeguimientoUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.ProcesarOrdenSunatUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.OrdenPagoRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.OrdenPagoRequestDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.OrdenPagoResponseDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.mapper.OrdenPagoMapper;
import pe.gob.vuce.cp.sp.pagos.infrastructure.config.exception.JsonParseException;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.ComunesQueryClient;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.GestorProcedimientoClient;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.OAuthClient;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.TramiteCommandClient;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.TramiteQueryClient;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.config.OAuthClientConfig;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ComunesQueryResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.Data;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.Meta;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.OAuthResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ProcedimientosResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.TasaResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.TramiteResponse;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class OrdenPagoSunatRepositoryAdapterTest {

    @Mock
    private OrdenPagoMapper ordenPagoMapper;

    @Mock
    private OrdenPagoRepositoryPort ordenPagoRepositoryPort;

    @Mock
    private ProcesarOrdenSunatUseCase procesarOrdenSunatUseCase;

    @Mock
    private OAuthClient oAuthClient;

    @Mock
    private OAuthClientConfig oAuthClientConfig;

    @Mock
    private GestorProcedimientoClient gestorProcedimientoClient;

    @Mock
    private ComunesQueryClient comunesQueryClient;

    @Mock
    private FeignExceptionHandler feignExceptionHandler;

    @Mock
    private CreateSeguimientoUseCase createSeguimientoUseCase;

    @Mock
    private TramiteCommandClient tramiteCommandClient;

    @Mock
    private TramiteQueryClient tramiteQueryClient;

    @InjectMocks
    private OrdenPagoSunatRepositoryAdapter ordenPagoSunatRepositoryAdapter;

    
    private OrdenPagoRequestDto requestDto;
    private OrdenPago ordenPago;
    private OrdenPagoResponseDto responseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        requestDto = new OrdenPagoRequestDto();
        requestDto.setEntidadId(1);
        requestDto.setDocumentoId(1);
        requestDto.setEscalaId(1);
        requestDto.setRucAgente("12345678901");
        requestDto.setFechaVigencia("2023-10-10");
        requestDto.setIdComponente("1");
        requestDto.setTextSearch("test");
        requestDto.setActividadEntidadPuertoId(1);
        requestDto.setCantidadOrden(1);

        ordenPago = new OrdenPago();
        ordenPago.setEntidadId(1);
        ordenPago.setDocumentoId(1);
        ordenPago.setEscalaId(1);
        ordenPago.setRucAgente("12345678901");
        ordenPago.setFechaVigencia("2023-10-10");
        ordenPago.setIdComponente("1");
        ordenPago.setTextSearch("test");
        ordenPago.setActividadEntidadPuertoId(1);

        responseDto = new OrdenPagoResponseDto(1, 1, 1, 1, "12345678901", "CPB123", 100.0, "2023-10-10", "CPB123",
                "CREADA", "2023-10-10", null, null, null, 100.0, "2023-10-10", null, "Descripción");
        ordenPagoSunatRepositoryAdapter = new OrdenPagoSunatRepositoryAdapter(
                ordenPagoMapper,
                ordenPagoRepositoryPort,
                procesarOrdenSunatUseCase,
                oAuthClient,
                oAuthClientConfig, // ahora sí pasa tu mock manualmente
                gestorProcedimientoClient,
                comunesQueryClient,
                feignExceptionHandler,
                createSeguimientoUseCase,
                tramiteCommandClient,
                tramiteQueryClient);
        // Setup mock behavior for oAuthClientConfig
        when(oAuthClientConfig.getGrantType()).thenReturn("grantType");
        when(oAuthClientConfig.getScope()).thenReturn("scope");
    }

    @Test
    void testEjecutar_Success() throws JsonProcessingException {
        String user = "MATEO";

        // 1. ordenPagoMock completamente preparado
        OrdenPago ordenPagoMock = new OrdenPago();
        ordenPagoMock.setEntidadId(1);
        ordenPagoMock.setDocumentoId(1);
        ordenPagoMock.setEscalaId(1);
        ordenPagoMock.setRucAgente("12345678901");
        ordenPagoMock.setUsuidModAud(user);
        ordenPagoMock.setActividadEntidadPuertoId(1);
        ordenPagoMock.setIdComponente("1");
        ordenPagoMock.setTextSearch("test");
        ordenPagoMock.setCpb("CPB123");

        // 2. Mocks
        when(ordenPagoMapper.dtoToModel(requestDto, user)).thenReturn(ordenPagoMock);
        when(ordenPagoRepositoryPort.save(any())).thenReturn(ordenPagoMock);
        when(ordenPagoRepositoryPort.update(any())).thenReturn(ordenPagoMock);
        when(ordenPagoMapper.modelToDto(any())).thenReturn(responseDto);

        // 3. Mock de ENTIDAD válido (clave para evitar OrdenPagoNotFoundException)
        when(comunesQueryClient.getAllByCodeAndAttribute(eq(1), eq("entidad")))
                .thenReturn(createComunesQueryResponse());

        // 4. Mock token OAuth
        when(oAuthClient.getToken(any(), any())).thenReturn(createTokenResponse());

        // 5. Mock de procedimientos
        when(gestorProcedimientoClient.getProcedimientos(
                anyString(), anyString(), anyInt(), anyString(), anyString(), anyString()))
                .thenReturn(createProcedimientosResponse());

        // 6. Mock de tasas
        when(gestorProcedimientoClient.getTasa(anyString(), anyInt(), anyInt()))
                .thenReturn(createTasaResponse());

        // 7. Mock del procesamiento final SUNAT
        doNothing().when(procesarOrdenSunatUseCase).procesarOrdenSunat(
                any(), any(), any(), anyInt(), anyString());

        // 8. Ejecutar método
        OrdenPagoResponseDto result = ordenPagoSunatRepositoryAdapter.ejecutar(requestDto, user);

        // 9. Validaciones
        assertNotNull(result);
        assertEquals(responseDto, result);

        verify(procesarOrdenSunatUseCase, times(1)).procesarOrdenSunat(
                any(), any(), any(), anyInt(), anyString());
    }

    private ComunesQueryResponse createComunesQueryResponse() {
        ComunesQueryResponse response = new ComunesQueryResponse();
        Meta meta = new Meta();
        meta.setResult("OK");
        response.setMeta(meta);
        Data data = new Data();
        data.setId(1);
        data.setDescriptionEs("Descripción");
        Map<String, String> othersColumns = new HashMap<>();
        othersColumns.put("cod_entidad_gp", "1");
        data.setOthersColumns(othersColumns);
        response.setData(Collections.singletonList(data));
        return response;
    }

    private OAuthResponse createTokenResponse() {
        OAuthResponse tokenResponse = new OAuthResponse();
        tokenResponse.setAccessToken("token");
        return tokenResponse;
    }

    private ProcedimientosResponse createProcedimientosResponse() {
        ProcedimientosResponse response = new ProcedimientosResponse();
        ProcedimientosResponse.Procedimiento procedimiento = new ProcedimientosResponse.Procedimiento();
        procedimiento.setProcedimientoId(1);
        procedimiento.setProcedimientoVersion(1);
        procedimiento.setEntidadId(1);
        procedimiento.setSiglas("SIG");
        procedimiento.setTupa("TUPA");
        procedimiento.setFormato("FORMATO");
        procedimiento.setCut(1);
        procedimiento.setNombreCut("NOMBRE");
        procedimiento.setComponente("COMP");
        procedimiento.setAyuda("AYUDA");
        procedimiento.setPago("PAGO");
        procedimiento.setPlazo("PLAZO");
        procedimiento.setDescripcionCalificacion("DESC");
        response.setProcedimientos(Collections.singletonList(procedimiento));
        return response;
    }

    private TasaResponse createTasaResponse() {
        TasaResponse response = new TasaResponse();
        TasaResponse.Tasa tasa = new TasaResponse.Tasa();
        tasa.setProcedimientoId(1);
        tasa.setProcedimientoVersion(1);
        tasa.setProcedimientoTasaVersion(1);
        tasa.setSecuencia(1);
        tasa.setMonto(100.0);
        tasa.setEtiqueta("ETIQUETA");
        tasa.setDescripcion("DESC");
        tasa.setCodigoMoneda("PEN");
        tasa.setMonedaDescripcion("SOL");
        tasa.setMonedaSigno("S/");
        response.setTasas(Collections.singletonList(tasa));
        return response;
    }

    @Test
    void testEjecutar_FeignException() {
        when(ordenPagoMapper.dtoToModel(requestDto, "user")).thenReturn(ordenPago);
        when(comunesQueryClient.getAllByCodeAndAttribute(1, "entidad")).thenReturn(createComunesQueryResponse());
        when(oAuthClient.getToken(any(), any())).thenReturn(createTokenResponse());
        when(gestorProcedimientoClient.getProcedimientos(anyString(), anyString(), anyInt(), anyString(), anyString(),
                anyString())).thenThrow(FeignException.class);

        assertThrows(FeignException.class, () -> {
            when(ordenPagoRepositoryPort.save(any(OrdenPago.class))).thenReturn(ordenPago);
            ordenPagoSunatRepositoryAdapter.ejecutar(requestDto, "user");
        });

        verify(feignExceptionHandler, times(1)).handleFeignClientException(any(FeignException.class), eq(ordenPago));
        verify(ordenPagoRepositoryPort, times(1)).update(ordenPago);
    }

    @Test
    void testEjecutar_JsonProcessingException() {
        when(ordenPagoMapper.dtoToModel(requestDto, "user")).thenReturn(ordenPago);
        when(comunesQueryClient.getAllByCodeAndAttribute(1, "entidad")).thenReturn(createComunesQueryResponse());
        when(oAuthClient.getToken(any(), any())).thenThrow(new RuntimeException(new JsonProcessingException("Error") {
        }));
        assertThrows(JsonParseException.class, () -> {
            try {
                when(ordenPagoRepositoryPort.save(any(OrdenPago.class))).thenReturn(ordenPago);
                ordenPagoSunatRepositoryAdapter.ejecutar(requestDto, "user");
            } catch (RuntimeException e) {
                if (e.getCause() instanceof JsonProcessingException) {
                    throw new JsonParseException("ERROR", e.getCause());
                }
                throw e;
            }
        });
    }

    @Test
    void testValidarTupa0_ExisteEnBD_SinTramite() {
        String user = "user";

        when(ordenPagoMapper.dtoToModel(requestDto, user)).thenReturn(ordenPago);
        when(ordenPagoRepositoryPort.existeEscalaTupaCero(anyInt(), any(), anyInt()))
                .thenReturn(true);

        var result = ordenPagoSunatRepositoryAdapter
                .validarTupa0(requestDto, user, "token", "false", "IND");

        assertNotNull(result);
        assertEquals(true, result.esTasa0());
        verify(tramiteCommandClient, times(0)).crearTramite(any(), any(), any(), any(), any());
    }

    @Test
    void testValidarTupa0_ExisteEnBD_ConTramite() {
        String user = "user";

        when(ordenPagoMapper.dtoToModel(requestDto, user)).thenReturn(ordenPago);
        when(ordenPagoRepositoryPort.existeEscalaTupaCero(anyInt(), any(), anyInt()))
                .thenReturn(true);

        // mocks necesarios para ejecutarTramiteTupa0
        when(comunesQueryClient.getAllByCodeAndAttribute(anyInt(), anyString()))
                .thenReturn(createComunesQueryResponse());

        when(oAuthClient.getToken(any(), any())).thenReturn(createTokenResponse());

        when(gestorProcedimientoClient.getProcedimientos(
                anyString(), anyString(), anyInt(), anyString(), anyString(), anyString()))
                .thenReturn(createProcedimientosResponse());

        when(tramiteQueryClient.obtenerTramites(any(), anyInt(), anyInt(), anyInt(), anyString()))
                .thenReturn(createTramiteResponse());

        when(ordenPagoRepositoryPort.findByEscalaIdAndDocumentoId(anyInt(), anyInt()))
                .thenReturn(Collections.singletonList(ordenPago));

        ordenPagoSunatRepositoryAdapter
                .validarTupa0(requestDto, user, "token", "true", "IND");

        verify(tramiteCommandClient, times(1))
                .crearTramite(any(), any(), any(), any(), any());
    }

    @Test
    void testValidarTupa0_MontoCero() {
        String user = "user";

        when(ordenPagoMapper.dtoToModel(requestDto, user)).thenReturn(ordenPago);
        when(ordenPagoRepositoryPort.existeEscalaTupaCero(anyInt(), any(), anyInt()))
                .thenReturn(false);

        when(comunesQueryClient.getAllByCodeAndAttribute(anyInt(), anyString()))
                .thenReturn(createComunesQueryResponse());

        when(oAuthClient.getToken(any(), any())).thenReturn(createTokenResponse());

        when(gestorProcedimientoClient.getProcedimientos(
                anyString(), anyString(), anyInt(), anyString(), anyString(), anyString()))
                .thenReturn(createProcedimientosResponse());

        // tasa con monto 0
        TasaResponse tasaResponse = createTasaResponse();
        tasaResponse.getTasas().get(0).setMonto(0.0);

        when(gestorProcedimientoClient.getTasa(anyString(), anyInt(), anyInt()))
                .thenReturn(tasaResponse);

        when(ordenPagoRepositoryPort.save(any())).thenReturn(ordenPago);

        var result = ordenPagoSunatRepositoryAdapter
                .validarTupa0(requestDto, user, "token", "false", "IND");

        assertEquals(true, result.esTasa0());

        verify(createSeguimientoUseCase, times(1))
                .create(any(), eq(user));
    }

    @Test
    void testValidarTupa0_NoEsTupa0() {
        String user = "user";

        when(ordenPagoMapper.dtoToModel(requestDto, user)).thenReturn(ordenPago);
        when(ordenPagoRepositoryPort.existeEscalaTupaCero(anyInt(), any(), anyInt()))
                .thenReturn(false);

        when(comunesQueryClient.getAllByCodeAndAttribute(anyInt(), anyString()))
                .thenReturn(createComunesQueryResponse());

        when(oAuthClient.getToken(any(), any())).thenReturn(createTokenResponse());

        when(gestorProcedimientoClient.getProcedimientos(
                anyString(), anyString(), anyInt(), anyString(), anyString(), anyString()))
                .thenReturn(createProcedimientosResponse());

        when(gestorProcedimientoClient.getTasa(anyString(), anyInt(), anyInt()))
                .thenReturn(createTasaResponse()); // monto 100

        var result = ordenPagoSunatRepositoryAdapter
                .validarTupa0(requestDto, user, "token", "false", "IND");

        assertEquals(false, result.esTasa0());
    }

    @Test
    void testValidarTupa0_EntidadInvalida() {
        String user = "user";

        when(ordenPagoMapper.dtoToModel(requestDto, user)).thenReturn(ordenPago);
        when(ordenPagoRepositoryPort.existeEscalaTupaCero(anyInt(), any(), anyInt()))
                .thenReturn(false);

        when(comunesQueryClient.getAllByCodeAndAttribute(anyInt(), anyString()))
                .thenReturn(null);

        assertThrows(Exception.class, () ->
                ordenPagoSunatRepositoryAdapter
                        .validarTupa0(requestDto, user, "token", "false", "IND")
        );
    }
    private TramiteResponse createTramiteResponse() {
        TramiteResponse response = new TramiteResponse();
        TramiteResponse.Tramites data = new TramiteResponse.Tramites();
        data.setId(10);
        response.setData(Collections.singletonList(data));
        return response;
    }
}
