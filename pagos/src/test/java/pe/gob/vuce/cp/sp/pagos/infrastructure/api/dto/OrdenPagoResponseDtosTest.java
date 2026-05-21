package pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

 class OrdenPagoResponseDtosTest {
    @Test
     void shouldCreateOrdenPagoResponseDtoWithAllFields() {
        OrdenPagoResponseDto dto = new OrdenPagoResponseDto(
                1,
                2,
                3,
                4,
                "123456789021",
                "2023-12-31",
                100.0,
                "component1",
                "",
                "search",
                "2023-12-31",
                "2023-12-31",
                "2023-12-31",
                "2023-12-31",
                100.00,
                "2023-12-31",
                "2023-12-31",
                "descProcedimientos");
        assertNotNull(dto);
        assertEquals(1, dto.ordenPagoId());
        assertEquals(2, dto.entidadId());
        assertEquals(3, dto.documentoId());

    }

    @Test
     void shouldCreateOrdenPagoResponseDtoWithMandatoryFieldsOnly() {
        OrdenPagoResponseDto dto = new OrdenPagoResponseDto(
                1,
                2,
                3,
                4,
                "123456789021",
                "2023-12-31",
                100.0,
                "component1",
                "",
                "search",
                "2023-12-31",
                "2023-12-31",
                "2023-12-31",
                "2023-12-31",
                100.00,
                "2023-12-31",
                "2023-12-31",
                "descProcedimientos");
        assertNotNull(dto);
        assertEquals(1, dto.ordenPagoId());
        assertEquals(2, dto.entidadId());
        assertEquals(3, dto.documentoId());
        assertEquals("123456789021", dto.rucAgente());

    }

}