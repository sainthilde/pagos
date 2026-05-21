package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.ComunesQueryClient;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.GestorProcedimientoClient;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.OAuthClient;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.config.OAuthClientConfig;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ComunesQueryResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.Data;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.Meta;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ProcedimientosResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.OAuthResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.TasaResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class TasaSunatRepositoryAdapterTest {

    @Mock
    private OAuthClient oAuthClient;

    @Mock
    private OAuthClientConfig oAuthClientConfig;

    @Mock
    private GestorProcedimientoClient gestorProcedimientoClient;

    @Mock
    private ComunesQueryClient comunesQueryClient;

    @InjectMocks
    private TasaSunatRepositoryAdapter adapter;

    private static final Integer ENTIDAD_ID = 100;
    private static final String COMPONENTE_ID = "comp-01";
    private static final String TEXT_SEARCH = "ejemplo";

    @BeforeEach
    void setUp() {
        // Config OAuthClientConfig
        when(oAuthClientConfig.getGrantType()).thenReturn("client_credentials");
        when(oAuthClientConfig.getScope()).thenReturn("scope-test");
    }

    @Test
    void obtenerTasa_debeRetornarTasaExitosa() {
        // Mock comunesQueryClient
        Data data = new Data();
        Map<String, String> columns = new HashMap<>();
        columns.put("cod_entidad_gp", "200");
        data.setOthersColumns(columns);
        ComunesQueryResponse comunesResponse = new ComunesQueryResponse(new Meta(), List.of(data));

        when(comunesQueryClient.getAllByCodeAndAttribute(ENTIDAD_ID, "entidad")).thenReturn(comunesResponse);

        // Mock OAuth token
        OAuthResponse oAuthResponse = new OAuthResponse();
        oAuthResponse.setAccessToken("token123");
        when(oAuthClient.getToken("client_credentials", "scope-test")).thenReturn(oAuthResponse);

        // Mock procedimientos
        ProcedimientosResponse.Procedimiento procedimiento = new ProcedimientosResponse.Procedimiento();
        procedimiento.setProcedimientoId(1);
        ProcedimientosResponse procedimientosResponse = new ProcedimientosResponse();
        procedimientosResponse.setProcedimientos(List.of(procedimiento));
        when(gestorProcedimientoClient.getProcedimientos(
                eq("Bearer token123"), eq(COMPONENTE_ID), eq(200), anyString(), anyString(), eq(TEXT_SEARCH))
        ).thenReturn(procedimientosResponse);

        // Mock tasas
        TasaResponse.Tasa tasa = new TasaResponse.Tasa();
        tasa.setMonto(50.0);
        TasaResponse tasaResponse = new TasaResponse();
        tasaResponse.setTasas(List.of(tasa));
        when(gestorProcedimientoClient.getTasa("Bearer token123", 1, 1)).thenReturn(tasaResponse);

        // Act
        TasaResponse.Tasa result = adapter.obtenerTasa(ENTIDAD_ID, COMPONENTE_ID, TEXT_SEARCH);

        // Assert
        assertNotNull(result);
        assertEquals(50.0, result.getMonto());
    }


}

