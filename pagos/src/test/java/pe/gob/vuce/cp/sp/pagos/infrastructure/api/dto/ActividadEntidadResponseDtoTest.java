package pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

 class ActividadEntidadResponseDtoTest {

    @Test
    void createActividadEntidadResponseDto_checkFieldValues() {
        Integer actividadEntidadId = 1;
        Integer entidadId = 2;
        Integer actividadId = 3;
        String codPuertoNacional = "codPuertoNacional";
        String codReglaNegocio = "codReglaNegocio";
        String estado = "estado";

        ActividadEntidadResponseDto dto = new ActividadEntidadResponseDto(
                actividadEntidadId,
                entidadId,
                actividadId,
                codPuertoNacional,
                codReglaNegocio,
                estado
        );

        assertEquals(actividadEntidadId, dto.actividadEntidadId());
        assertEquals(entidadId, dto.entidadId());
        assertEquals(actividadId, dto.actividadId());
        assertEquals(codPuertoNacional, dto.codPuertoNacional());
        assertEquals(codReglaNegocio, dto.codReglaNegocio());
        assertEquals(estado, dto.estado());
    }
}