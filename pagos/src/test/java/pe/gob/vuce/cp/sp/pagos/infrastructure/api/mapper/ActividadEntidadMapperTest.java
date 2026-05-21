package pe.gob.vuce.cp.sp.pagos.infrastructure.api.mapper;

import org.junit.jupiter.api.Test;

import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import pe.gob.vuce.cp.sp.pagos.domain.model.ActividadEntidad;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.ActividadEntidadResponseDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.entity.ActividadEntidadEntity;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ActividadEntidadMapperTest {

   private final ActividadEntidadMapper mapper = Mappers.getMapper(ActividadEntidadMapper.class);

   @Test
   void testActividadEntityToModel() {
      ActividadEntidadEntity entity = new ActividadEntidadEntity();
      entity.setActividadEntidadId(1);
      entity.setEntidadId(100);
      entity.setActividadId(200);
      entity.setCodPuertoNacional("PECLL");
      entity.setCodReglaNegocio("R001");
      entity.setEstado("ACTIVO");

      ActividadEntidad model = mapper.actividadEntityToModel(entity);

      assertNotNull(model);
      assertEquals(1, model.getActividadEntidadId());
      assertEquals(100, model.getEntidadId());
      assertEquals(200, model.getActividadId());
      assertEquals("PECLL", model.getCodPuertoNacional());
      assertEquals("R001", model.getCodReglaNegocio());
      assertEquals("ACTIVO", model.getEstado());
   }

   @Test
   void testActividadEntidadToResponseDto() {
      ActividadEntidad model = new ActividadEntidad(
              2,
              101,
              201,
              "PECAL",
              "R002",
              "INACTIVO"
      );

      ActividadEntidadResponseDto dto = mapper.actividadEntidadToResponseDto(model);

      assertNotNull(dto);
      assertEquals(2, dto.actividadEntidadId());
      assertEquals(101, dto.entidadId());
      assertEquals(201, dto.actividadId());
      assertEquals("PECAL", dto.codPuertoNacional());
      assertEquals("R002", dto.codReglaNegocio());
      assertEquals("INACTIVO", dto.estado());
   }
}
