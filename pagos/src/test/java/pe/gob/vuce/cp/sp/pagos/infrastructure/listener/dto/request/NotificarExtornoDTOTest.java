package pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.request;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

 class NotificarExtornoDTOTest {

    @Test
    void testSettersAndGetters() {
        NotificarExtornoDTO dto = new NotificarExtornoDTO();

        dto.setOrdenPagoId(100);
        dto.setMontoExtornado(750.50);
        dto.setFechaExtorno("20250518 10:30:00");
        dto.setFechaProcesamiento("20250518 10:45:00");
        dto.setCanalId(5);
        dto.setCanalDescripcion("Canal Bancario");
        dto.setEstado("Extornado");

        assertEquals(100, dto.getOrdenPagoId());
        assertEquals(750.50, dto.getMontoExtornado());
        assertEquals("20250518 10:30:00", dto.getFechaExtorno());
        assertEquals("20250518 10:45:00", dto.getFechaProcesamiento());
        assertEquals(5, dto.getCanalId());
        assertEquals("Canal Bancario", dto.getCanalDescripcion());
        assertEquals("Extornado", dto.getEstado());
    }

    @Test
    void testEmptyConstructorDefaults() {
        NotificarExtornoDTO dto = new NotificarExtornoDTO();

        assertNull(dto.getOrdenPagoId());
        assertNull(dto.getMontoExtornado());
        assertNull(dto.getFechaExtorno());
        assertNull(dto.getFechaProcesamiento());
        assertNull(dto.getCanalId());
        assertNull(dto.getCanalDescripcion());
        assertNull(dto.getEstado());
    }
}
