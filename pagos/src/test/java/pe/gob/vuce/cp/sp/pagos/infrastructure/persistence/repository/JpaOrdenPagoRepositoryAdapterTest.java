package pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import pe.gob.vuce.cp.sp.pagos.domain.model.OrdenPago;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.mapper.OrdenPagoMapper;
import pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.entity.OrdenPagoEntity;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class JpaOrdenPagoRepositoryAdapterTest {

    @Mock
    private JpaOrdenPagoRepository repository;

    @Mock
    private OrdenPagoMapper mapper;

    @Mock
    private Environment env;

    @InjectMocks
    private JpaOrdenPagoRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(env.getProperty("spring.datasource.username")).thenReturn("test_db_user");
    }

    @Test
    void testSave() {
        OrdenPago ordenPago = new OrdenPago();
        OrdenPagoEntity ordenPagoEntity = new OrdenPagoEntity();

        when(mapper.modelToEntity(ordenPago)).thenReturn(ordenPagoEntity);
        when(repository.save(ordenPagoEntity)).thenReturn(ordenPagoEntity);
        when(mapper.entityToModel(ordenPagoEntity)).thenReturn(ordenPago);

        OrdenPago result = adapter.save(ordenPago);

        assertNotNull(result);
        verify(mapper).modelToEntity(ordenPago);
        verify(repository).save(ordenPagoEntity);
        verify(mapper).entityToModel(ordenPagoEntity);
    }

    @Test
    void testFindById_Success() {
        Integer ordenPagoId = 1;
        OrdenPagoEntity ordenPagoEntity = new OrdenPagoEntity();
        OrdenPago ordenPago = new OrdenPago();

        when(repository.findById(ordenPagoId)).thenReturn(Optional.of(ordenPagoEntity));
        when(mapper.entityToModel(ordenPagoEntity)).thenReturn(ordenPago);

        OrdenPago result = adapter.findById(ordenPagoId);

        assertNotNull(result);
        verify(repository).findById(ordenPagoId);
        verify(mapper).entityToModel(ordenPagoEntity);
    }

    @Test
    void testFindById_NotFound() {
        Integer ordenPagoId = 1;
        when(repository.findById(ordenPagoId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> adapter.findById(ordenPagoId));
        assertEquals("El ID " + ordenPagoId + " No existe", exception.getMessage());
        verify(repository).findById(ordenPagoId);
    }

    @Test
    void testFindByPpIdOrdenPagoInterna_Success() {
        Integer ordenPagoInterna = 123;
        OrdenPagoEntity ordenPagoEntity = new OrdenPagoEntity();
        OrdenPago ordenPago = new OrdenPago();

        when(repository.findByPpIdOrdenPagoInterna(ordenPagoInterna)).thenReturn(Optional.of(ordenPagoEntity));
        when(mapper.entityToModel(ordenPagoEntity)).thenReturn(ordenPago);

        OrdenPago result = adapter.findByPpIdOrdenPagoInterna(ordenPagoInterna);

        assertNotNull(result);
        verify(repository).findByPpIdOrdenPagoInterna(ordenPagoInterna);
        verify(mapper).entityToModel(ordenPagoEntity);
    }

    @Test
    void testModelToEntityUpdate() {
        OrdenPago ordenPago = new OrdenPago();
        ordenPago.setOrdenPagoId(1);
        ordenPago.setEstado("PP");
        ordenPago.setCpb("CPB001");
        ordenPago.setMonto(100.00);
        ordenPago.setCodigoOrdenPago("COD001");
        ordenPago.setOrdenPagoInternaId(999);
        ordenPago.setUsuidRegAud("test_user");


        OrdenPagoEntity existingEntity = new OrdenPagoEntity();
        existingEntity.setOrdenPagoId(1);

        OrdenPagoEntity updatedEntity = adapter.modelToEntityUpdate(existingEntity, ordenPago);

        assertEquals(ordenPago.getEstado(), updatedEntity.getEstadoOrdenPago());
        assertEquals(ordenPago.getCpb(), updatedEntity.getPpCpb());
        assertEquals(ordenPago.getMonto(), updatedEntity.getPpMonto().doubleValue());
        assertEquals("test_db_user", updatedEntity.getUsubdModAud());
        assertEquals(ordenPago.getUsuidModAud(), updatedEntity.getUsuidModAud());
        // ... otras verificaciones ...
    }

    @Test
    void testModelToEntityUpdate_WithNullMonto() {
        OrdenPago ordenPago = new OrdenPago();
        ordenPago.setMonto(null);
        OrdenPagoEntity entity = new OrdenPagoEntity();

        OrdenPagoEntity updated = adapter.modelToEntityUpdate(entity, ordenPago);

        assertNull(updated.getPpMonto());
    }

    @Test
    void testModelToEntityUpdate_UsubdModAud() {
        String expectedUsername = "custom_db_user";
        when(env.getProperty("spring.datasource.username")).thenReturn(expectedUsername);

        OrdenPago ordenPago = new OrdenPago();
        ordenPago.setUsuidRegAud("test_user");

        OrdenPagoEntity entity = new OrdenPagoEntity();
        OrdenPagoEntity updated = adapter.modelToEntityUpdate(entity, ordenPago);

        assertEquals(expectedUsername, updated.getUsubdModAud());
    }

    @Test
    void testModelToEntityUpdate_WhenUsernameIsNull() {
        when(env.getProperty("spring.datasource.username")).thenReturn(null);

        OrdenPago ordenPago = new OrdenPago();
        ordenPago.setUsuidRegAud("test_user");

        OrdenPagoEntity entity = new OrdenPagoEntity();
        OrdenPagoEntity updated = adapter.modelToEntityUpdate(entity, ordenPago);

        assertNull(updated.getUsubdModAud());
    }
}