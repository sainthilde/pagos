package pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.request;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

 class NotificarPagoDTOTest {

    @Test
    void testNotificarPagoDTOSettersAndGetters() {
        NotificarPagoDTO dto = new NotificarPagoDTO();

        dto.setOrdenPagoId(1);
        dto.setMontoPagado(1500.0);
        dto.setFechaPago("20250517");
        dto.setFechaProcesamiento("20250517 14:30:00");
        dto.setCanalId(10);
        dto.setCanalDescripcion("Canal Virtual");
        dto.setBancoId(20);
        dto.setBancoDescripcion("Banco de Prueba");
        dto.setTipo(2);
        dto.setTipoDescripcion("Transferencia");
        dto.setEstado("Procesado");

        assertEquals(1, dto.getOrdenPagoId());
        assertEquals(1500.0, dto.getMontoPagado());
        assertEquals("20250517", dto.getFechaPago());
        assertEquals("20250517 14:30:00", dto.getFechaProcesamiento());
        assertEquals(10, dto.getCanalId());
        assertEquals("Canal Virtual", dto.getCanalDescripcion());
        assertEquals(20, dto.getBancoId());
        assertEquals("Banco de Prueba", dto.getBancoDescripcion());
        assertEquals(2, dto.getTipo());
        assertEquals("Transferencia", dto.getTipoDescripcion());
        assertEquals("Procesado", dto.getEstado());
    }

    @Test
    void testEmptyNotificarPagoDTO() {
        NotificarPagoDTO dto = new NotificarPagoDTO();

        assertNull(dto.getOrdenPagoId());
        assertNull(dto.getMontoPagado());
        assertNull(dto.getFechaPago());
        assertNull(dto.getFechaProcesamiento());
        assertNull(dto.getCanalId());
        assertNull(dto.getCanalDescripcion());
        assertNull(dto.getBancoId());
        assertNull(dto.getBancoDescripcion());
        assertNull(dto.getTipo());
        assertNull(dto.getTipoDescripcion());
        assertNull(dto.getEstado());
    }
}
