package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.DocumentoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity.mae.Documento;

import static org.assertj.core.api.Assertions.assertThat;

public class DocumentoMapperTest {

    private DocumentoMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(DocumentoMapper.class);
    }

    @Test
    void testEntityToModel() {
        Documento documento = new Documento();
        documento.setDocumentoId(1);
        documento.setDescAcronimo("DOC_ACRO");

        DocumentoModel modelo = mapper.entityToModel(documento);

        assertThat(modelo).isNotNull();
        assertThat(modelo.getDocumentoId()).isEqualTo(documento.getDocumentoId());
        assertThat(modelo.getDescAcronimo()).isEqualTo(documento.getDescAcronimo());
    }

}
