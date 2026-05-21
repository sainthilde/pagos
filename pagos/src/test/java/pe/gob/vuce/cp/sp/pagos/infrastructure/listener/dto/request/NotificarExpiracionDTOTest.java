package pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotificarExpiracionDTOTest {

    @Test
    public void testGettersAndSetters() {
        NotificarExpiracionDTO dto = new NotificarExpiracionDTO();

        dto.setOrdenPagoId(123);
        dto.setFechaExtorno("20240609 15:30:00");
        dto.setFechaProcesamiento("20240609 16:00:00");
        dto.setCanalId(10);
        dto.setCanalDescripcion("Canal Prueba");
        dto.setEstado("EXPIRED");

        assertEquals(123, dto.getOrdenPagoId());
        assertEquals("20240609 15:30:00", dto.getFechaExtorno());
        assertEquals("20240609 16:00:00", dto.getFechaProcesamiento());
        assertEquals(10, dto.getCanalId());
        assertEquals("Canal Prueba", dto.getCanalDescripcion());
        assertEquals("EXPIRED", dto.getEstado());
    }

}
