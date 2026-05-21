package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity.Tramite;

import static org.assertj.core.api.Assertions.assertThat;

public class TramiteUpdateMapperTest {

    private TramiteUpdateMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(TramiteUpdateMapper.class);
    }

    @Test
    void testUpdateTramiteFromDto() {
        Tramite existingTramite = new Tramite();
        existingTramite.setTramiteId(1);

        Tramite updatedTramite = new Tramite();

        mapper.updateTramiteFromDto(updatedTramite, existingTramite);

        assertThat(existingTramite).isNotNull();
        assertThat(existingTramite.getTramiteId()).isEqualTo(1); // ID should not change
    }

    @Test
    void testUpdateTramiteFromDtoWithNullProperties() {
        Tramite existingTramite = new Tramite();
        existingTramite.setTramiteId(1);

        Tramite updatedTramite = new Tramite(); // No fields set

        mapper.updateTramiteFromDto(updatedTramite, existingTramite);

        assertThat(existingTramite).isNotNull();
        assertThat(existingTramite.getTramiteId()).isEqualTo(1); // ID should not change
    }

}
