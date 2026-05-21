package pe.gob.vuce.cp.sp.pagos.infrastructure.api.mapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import pe.gob.vuce.cp.sp.pagos.domain.model.OrdenPago;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.OrdenPagoRequestDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.OrdenPagoResponseDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.entity.OrdenPagoEntity;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@ExtendWith(MockitoExtension.class)
 class OrdenPagoMapperTest {
    private final OrdenPagoMapper mapper = Mappers.getMapper(OrdenPagoMapper.class) ;


    @Test
     void shouldMapEntityToModel() {

        OrdenPagoEntity entity = new OrdenPagoEntity();
        entity.setOrdenPagoId(1);
        entity.setEntidadId(100);
        entity.setDocumentoId(200);
        entity.setEscalaId(300);
        entity.setRucAgente("12345678901");
        entity.setEstadoOrdenPago("CR");
        entity.setFechaCreacionOrdenPago(java.time.Instant.now());
        entity.setFechaVencimientoOrdenPago(java.time.Instant.now().plusSeconds(86400)); // 1 día después
        entity.setFechaPagado(java.time.Instant.now());
        entity.setFechaAnulacionCpb(java.time.Instant.now().minusSeconds(86400)); // 1 día antes
        entity.setFechaExtornoOrdenPago(java.time.Instant.now().minusSeconds(172800)); // 2 días antes
        entity.setPpIdOrdenPagoInterna(1);
        entity.setPpCpb("CPB");
        entity.setGpMonto(new BigDecimal("1000.00"));
        entity.setGpProcedimientoId("PROC01");
        entity.setTextSearch("REG1234");

        // Realizar el mapeo
        OrdenPago result = mapper.entityToModel(entity);

        // Verificaciones
        assertNotNull(result);
        assertEquals(1, result.getOrdenPagoId());
        assertEquals(100, result.getEntidadId());
        assertEquals(200, result.getDocumentoId());
        assertEquals(300, result.getEscalaId());
        assertEquals("12345678901", result.getRucAgente());
        assertEquals("CR", result.getEstado());
        assertNotNull(result.getFechaCreacionOrdenPago());
        assertNotNull(result.getFechaVigencia());
        assertEquals(1, result.getOrdenPagoInternaId());
        assertEquals("CPB", result.getCpb());
        assertEquals(new BigDecimal("1000.00"), result.getGpMonto());
        assertEquals("PROC01", result.getGpProcedimientoId());
        assertEquals("REG1234", result.getTextSearch());
        assertNotNull(result.getFechaPagado());
        assertNotNull(result.getFechaAnulacionCpb());
        assertNotNull(result.getFechaExtornoOrdenPago());
    }

    @Test
     void shouldMapModelToEntity() {

        OrdenPago ordenPago = new OrdenPago();
        ordenPago.setOrdenPagoId(1);
        ordenPago.setEntidadId(100);
        ordenPago.setDocumentoId(200);
        ordenPago.setEscalaId(300);
        ordenPago.setRucAgente("12345678901");
        ordenPago.setEstado("CR");
        ordenPago.setFechaGeneracion("20240101111111");
        ordenPago.setFechaVigencia("20240101");
        ordenPago.setCpb("CPB");
        ordenPago.setGpMonto(new BigDecimal("2000.00"));
        ordenPago.setGpProcedimientoId("PROC02");
        ordenPago.setTextSearch("REG5678");

        // Realizar el mapeo
        OrdenPagoEntity result = mapper.modelToEntity(ordenPago);

        // Verificaciones
        assertNotNull(result);
        assertEquals(1, result.getOrdenPagoId());
        assertEquals(100, result.getEntidadId());
        assertEquals(200, result.getDocumentoId());
        assertEquals(300, result.getEscalaId());
        assertEquals("12345678901", result.getRucAgente());
        assertEquals("CR", result.getEstadoOrdenPago());
        assertEquals("CPB", result.getPpCpb());
        assertEquals(new BigDecimal("2000.00"), result.getGpMonto());
        assertEquals("PROC02", result.getGpProcedimientoId());
        assertEquals("REG5678", result.getTextSearch());
    }


    // Test para modelToDto
    @Test
     void shouldMapModelToDto() {
        OrdenPago ordenPago = new OrdenPago();
        ordenPago.setOrdenPagoId(1);
        ordenPago.setEstado("CR");

        OrdenPagoResponseDto result = mapper.modelToDto(ordenPago);

        assertNotNull(result);
        assertEquals(1, result.ordenPagoId());
        assertEquals("CR", result.estado());
    }

    // Test para dtoToModel
    @Test
     void shouldMapDtoToModel() {
        OrdenPagoRequestDto requestDto = new OrdenPagoRequestDto();
        requestDto.setEntidadId(1);
        requestDto.setDocumentoId(2);
        requestDto.setEscalaId(3);
        requestDto.setRucAgente("RUC123");
        requestDto.setFechaVigencia("20240101");

        OrdenPago result = mapper.dtoToModel(requestDto, "user");

        assertNotNull(result);
        assertEquals(1, result.getEntidadId());
        assertEquals(2, result.getDocumentoId());
        assertEquals(3, result.getEscalaId());
        assertEquals("RUC123", result.getRucAgente());
    }

    // Test para dtoToModel con ID
    @Test
     void shouldMapDtoToModelWithId() {
        OrdenPagoRequestDto requestDto = new OrdenPagoRequestDto();
        requestDto.setEntidadId(1);
        requestDto.setDocumentoId(2);
        requestDto.setEscalaId(3);
        requestDto.setRucAgente("RUC123");

        OrdenPago result = mapper.dtoToModel(requestDto, 10, "user");

        assertNotNull(result);
        assertEquals(10, result.getOrdenPagoId());
        assertEquals(1, result.getEntidadId());
    }

    // Test para listEntityToListModel
    @Test
     void shouldMapListEntityToListModel() {
        OrdenPagoEntity entity1 = new OrdenPagoEntity();
        entity1.setOrdenPagoId(1);
        OrdenPagoEntity entity2 = new OrdenPagoEntity();
        entity2.setOrdenPagoId(2);
        List<OrdenPagoEntity> entities = List.of(entity1, entity2);

        List<OrdenPago> result = mapper.listEntityToListModel(entities);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getOrdenPagoId());
        assertEquals(2, result.get(1).getOrdenPagoId());
    }

    // Test para listModelToListDto
    @Test
     void shouldMapListModelToListDto() {
        OrdenPago ordenPago1 = new OrdenPago();
        ordenPago1.setOrdenPagoId(1);
        OrdenPago ordenPago2 = new OrdenPago();
        ordenPago2.setOrdenPagoId(2);
        List<OrdenPago> models = List.of(ordenPago1, ordenPago2);

        List<OrdenPagoResponseDto> result = mapper.listModelToListDto(models);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).ordenPagoId());
        assertEquals(2, result.get(1).ordenPagoId());
    }

   @Test
   void testModelToDto_shouldMapDatesCorrectly() {
      // Arrange
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
              .withZone(ZoneId.of("America/Lima")); // UTC-5

      OrdenPago model = new OrdenPago();
      model.setFechaPagado(Instant.parse("2024-05-19T10:15:30Z"));
      model.setFechaAnulacionCpb(Instant.parse("2024-05-20T11:15:30Z"));
      model.setFechaExtornoOrdenPago(Instant.parse("2024-05-21T12:15:30Z"));
      model.setFechaCreacionOrdenPago(Instant.parse("2024-05-22T13:15:30Z"));
      model.setPpFechaConfGeneracionCpb(Instant.parse("2024-05-23T14:15:30Z"));
      model.setGpMonto(new BigDecimal("123.45"));

      // Act
      OrdenPagoResponseDto dto = mapper.modelToDto(model);

      // Assert
      assertEquals(formatter.format(model.getFechaPagado()), dto.fechaPagado());
      assertEquals(formatter.format(model.getFechaAnulacionCpb()), dto.fechaAnulacionCpb());
      assertEquals(formatter.format(model.getFechaExtornoOrdenPago()), dto.fechaExtornoOrdenPago());
      assertEquals(formatter.format(model.getFechaCreacionOrdenPago()), dto.fechaCreacionOrdenPago());
      assertEquals(formatter.format(model.getPpFechaConfGeneracionCpb()), dto.ppFechaConfGeneracionCpb());
      assertEquals(123.45, dto.gpMonto());
   }

   @Test
   void testEntityToModel_shouldMapFieldsCorrectly() {
      OrdenPagoEntity entity = new OrdenPagoEntity();
      entity.setFechaCreacionOrdenPago(Instant.parse("2024-05-01T00:00:00Z"));
      entity.setFechaVencimientoOrdenPago(Instant.parse("2024-06-01T00:00:00Z"));
      entity.setEstadoOrdenPago("PE");
      entity.setPpCpb("CPB-001");
      entity.setPdfCpbFilenetGuid("123-GUID");
      entity.setPpMonto(new BigDecimal("1000.00"));
      entity.setPpCodOrdenPago("OP-2024-001");
      entity.setPpIdOrdenPagoInterna(456);

      OrdenPago model = mapper.entityToModel(entity);

      assertEquals("20240430", model.getFechaGeneracion());
      assertEquals("20240531", model.getFechaVigencia());
      assertEquals("PE", model.getEstado());
      assertEquals("CPB-001", model.getCpb());
      assertEquals("123-GUID", model.getFilenetGuid());
      assertEquals(1000.0, model.getMonto());
      assertEquals("OP-2024-001", model.getCodigoOrdenPago());
      assertEquals(456, model.getOrdenPagoInternaId());
   }
}

