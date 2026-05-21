package pe.gob.vuce.cp.sp.pagos.infrastructure.feign;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.OrdenPagoSunatClient;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.PasarelaEstatusResponse;

class FeignPasarelaAdapterTest {

    private OrdenPagoSunatClient ordenPagoSunatClient;
    private FeignPasarelaAdapter adapter;

    @BeforeEach
    void setUp() {
        ordenPagoSunatClient = mock(OrdenPagoSunatClient.class);
        adapter = new FeignPasarelaAdapter(ordenPagoSunatClient);
    }

    @Test
    void testObtenerEstatusOrdenPago_Success() {
        // Arrange
        Integer ordenPagoId = 1;

        PasarelaEstatusResponse expectedResponse = new PasarelaEstatusResponse();
        // setea campos si tu DTO los tiene
        // expectedResponse.setEstado("OK");

        when(ordenPagoSunatClient.getStatus(ordenPagoId))
                .thenReturn(expectedResponse);

        // Act
        PasarelaEstatusResponse response =
                adapter.obtenerEstatusOrdenPago(ordenPagoId);

        // Assert
        assertEquals(expectedResponse, response);
    }

    @Test
    void testObtenerEstatusOrdenPago_Exception() {
        // Arrange
        Integer ordenPagoId = 1;

        when(ordenPagoSunatClient.getStatus(ordenPagoId))
                .thenThrow(new RuntimeException("Error en Feign"));

        // Act
        PasarelaEstatusResponse response =
                adapter.obtenerEstatusOrdenPago(ordenPagoId);

        // Assert
        assertNull(response);
    }
}