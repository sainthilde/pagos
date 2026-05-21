package pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.response;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

 class OrdenPagoResponseDTOTest {

    @Test
    void testGettersAndSetters() {
        OrdenPagoResponseDTO dto = new OrdenPagoResponseDTO();

        dto.setOrdenPagoId(1);
        dto.setCodigoOrdenPago("OP-12345");
        dto.setMonto(1000.50);
        dto.setFechaGeneracion("2025-05-18");
        dto.setCpb("CPB-001");
        dto.setEstado("PENDIENTE");
        dto.setFecha("2025-05-17");
        dto.setTipoDocumentoUsuario("DNI");
        dto.setNumeroDocumentoUsuario("12345678");
        dto.setTipOper("Venta");
        dto.setRucOper("20123456789");

        assertEquals(1, dto.getOrdenPagoId());
        assertEquals("OP-12345", dto.getCodigoOrdenPago());
        assertEquals(1000.50, dto.getMonto());
        assertEquals("2025-05-18", dto.getFechaGeneracion());
        assertEquals("CPB-001", dto.getCpb());
        assertEquals("PENDIENTE", dto.getEstado());
        assertEquals("2025-05-17", dto.getFecha());
        assertEquals("DNI", dto.getTipoDocumentoUsuario());
        assertEquals("12345678", dto.getNumeroDocumentoUsuario());
        assertEquals("Venta", dto.getTipOper());
        assertEquals("20123456789", dto.getRucOper());
    }

    @Test
    void testNullValues() {
        OrdenPagoResponseDTO dto = new OrdenPagoResponseDTO();

        assertNull(dto.getOrdenPagoId());
        assertNull(dto.getCodigoOrdenPago());
        assertNull(dto.getMonto());
        assertNull(dto.getFechaGeneracion());
        assertNull(dto.getCpb());
        assertNull(dto.getEstado());
        assertNull(dto.getFecha());
        assertNull(dto.getTipoDocumentoUsuario());
        assertNull(dto.getNumeroDocumentoUsuario());
        assertNull(dto.getTipOper());
        assertNull(dto.getRucOper());
    }
}
