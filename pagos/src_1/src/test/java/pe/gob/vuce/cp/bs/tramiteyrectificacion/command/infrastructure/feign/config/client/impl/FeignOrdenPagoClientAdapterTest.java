package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.feign.config.client.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.ExcepcionMensajeResponseDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.OrdenPagoResponseDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.feign.config.FeignOrdenPagoClient;

public class FeignOrdenPagoClientAdapterTest {

    private FeignOrdenPagoClient feignOrdenPagoClient;
    private FeignOrdenPagoClientAdapter adapter;

    @BeforeEach
    void setUp() {
        feignOrdenPagoClient = mock(FeignOrdenPagoClient.class);
        adapter = new FeignOrdenPagoClientAdapter(feignOrdenPagoClient);
    }

    /*
     * @Test
     * void testAnular() {
     * // Arrange
     * Integer ordenPagoId = 1;
     * String user = "testUser";
     * OrdenPagoAnulacionModel expected = new OrdenPagoAnulacionModel();
     * expected.setOrdenPagoId(ordenPagoId);
     * expected.setEstado("ANULADO");
     * 
     * when(feignOrdenPagoClient.anular(ordenPagoId,user)).thenReturn(expected);
     * 
     * // Act
     * OrdenPagoAnulacionModel result = adapter.anular(ordenPagoId,user);
     * 
     * // Assert
     * assertEquals(expected, result);
     * verify(feignOrdenPagoClient).anular(ordenPagoId,user);
     * }
     */

    @Test
    void testObtenerExcepciones() {
        // Arrange
        Integer escalaId = 10;
        Integer entidadId = 20;
        ExcepcionMensajeResponseDto expected = new ExcepcionMensajeResponseDto("Mensaje de prueba", true);

        when(feignOrdenPagoClient.obtenerExcepciones(escalaId, entidadId)).thenReturn(expected);

        // Act
        ExcepcionMensajeResponseDto result = adapter.obtenerExcepciones(escalaId, entidadId);

        // Assert
        assertEquals(expected, result);
        verify(feignOrdenPagoClient).obtenerExcepciones(escalaId, entidadId);
    }

    @Test
    void testFindByEscalaIdAndDocumentoId() {
        // Arrange
        Integer escalaId = 100;
        Integer documentoId = 200;
        OrdenPagoResponseDto dto = new OrdenPagoResponseDto();
        dto.setOrdenPagoId(1);
        dto.setCodigoOrdenPago("OP123");

        when(feignOrdenPagoClient.findByEscalaIdAndDocumentoId(escalaId, documentoId))
                .thenReturn(List.of(dto));

        // Act
        List<OrdenPagoResponseDto> result = adapter.findByEscalaIdAndDocumentoId(escalaId, documentoId);

        // Assert
        assertEquals(1, result.size());
        assertEquals("OP123", result.get(0).getCodigoOrdenPago());
        verify(feignOrdenPagoClient).findByEscalaIdAndDocumentoId(escalaId, documentoId);
    }
}
