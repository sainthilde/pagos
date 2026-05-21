package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.DocumentoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.mapper.DocumentoMapper;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity.mae.Documento;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JpaDocumentoRepositoryAdapterTest {

    @InjectMocks
    private JpaDocumentoRepositoryAdapter jpaDocumentoRepositoryAdapter;

    @Mock
    private DocumentoRepository documentoRepository;

    @Mock
    private DocumentoMapper documentoMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jpaDocumentoRepositoryAdapter = new JpaDocumentoRepositoryAdapter(documentoRepository, documentoMapper);
    }

    @Test
    void testFindById() {
        Integer id = 1;
        Documento documento = new Documento();
        DocumentoModel documentoModel = new DocumentoModel();
        when(documentoRepository.findById(id)).thenReturn(Optional.of(documento));
        when(documentoMapper.entityToModel(documento)).thenReturn(documentoModel);

        Optional<DocumentoModel> result = jpaDocumentoRepositoryAdapter.findById(id);

        assertTrue(result.isPresent());
        assertEquals(documentoModel, result.get());
    }

    @Test
    void testFindByDescAcronimoIn() {
        List<String> acronimos = List.of("ACR1", "ACR2");
        List<Documento> documentoList = new ArrayList<>();
        List<DocumentoModel> documentoModelList = new ArrayList<>();
        when(documentoRepository.findByDescAcronimoIn(acronimos)).thenReturn(documentoList);
        when(documentoMapper.entityToModel(documentoList)).thenReturn(documentoModelList);

        List<DocumentoModel> result = jpaDocumentoRepositoryAdapter.findByDescAcronimoIn(acronimos);

        assertEquals(documentoModelList, result);
    }

}
