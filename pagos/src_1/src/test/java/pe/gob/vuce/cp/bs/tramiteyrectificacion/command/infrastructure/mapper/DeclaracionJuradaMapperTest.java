package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.DeclaracionJuradaRequestDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.DeclaracionJuradaResponseDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity.DeclaracionJurada;

import static org.assertj.core.api.Assertions.assertThat;

public class DeclaracionJuradaMapperTest {

    private DeclaracionJuradaMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(DeclaracionJuradaMapper.class);
    }

    @Test
    void testToEntity() {
        DeclaracionJuradaRequestDto dto = new DeclaracionJuradaRequestDto();
        dto.setTramiteId(1);
        String user = "testUser";

        DeclaracionJurada entity = mapper.toEntity(dto, user);

        assertThat(entity).isNotNull();
        assertThat(entity.getUsuidRegAud()).isEqualTo(user);
        assertThat(entity.getTramite().getTramiteId()).isEqualTo(dto.getTramiteId());
    }

    @Test
    void testToDto() {
        DeclaracionJurada entity = new DeclaracionJurada();

        DeclaracionJuradaResponseDto dto = mapper.toDto(entity);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(entity.getDeclaracionJuradaId());
        assertThat(dto.getNumeroDdjj()).isEqualTo(entity.getNumeroDdjj());
    }

}
