package pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.request;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

 class NotificarAnulacionDTOTest {

    @Test
    void testSettersAndGetters() {
        NotificarAnulacionDTO dto = new NotificarAnulacionDTO();

        dto.setOrdenPagoId(123);
        dto.setFechaAnulacion("20250518 12:00:00");
        dto.setFechaProcesamiento("20250518 12:10:00");
        dto.setCanalId(8);
        dto.setCanalDescripcion("App móvil");
        dto.setEstado("Anulado");

        assertEquals(123, dto.getOrdenPagoId());
        assertEquals("20250518 12:00:00", dto.getFechaAnulacion());
        assertEquals("20250518 12:10:00", dto.getFechaProcesamiento());
        assertEquals(8, dto.getCanalId());
        assertEquals("App móvil", dto.getCanalDescripcion());
        assertEquals("Anulado", dto.getEstado());
    }

    @Test
    void testEmptyConstructorDefaults() {
        NotificarAnulacionDTO dto = new NotificarAnulacionDTO();

        assertNull(dto.getOrdenPagoId());
        assertNull(dto.getFechaAnulacion());
        assertNull(dto.getFechaProcesamiento());
        assertNull(dto.getCanalId());
        assertNull(dto.getCanalDescripcion());
        assertNull(dto.getEstado());
    }
}
