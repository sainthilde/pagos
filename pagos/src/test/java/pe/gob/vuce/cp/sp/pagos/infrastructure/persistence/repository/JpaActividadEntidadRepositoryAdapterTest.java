package pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pe.gob.vuce.cp.sp.pagos.domain.model.ActividadEntidad;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.mapper.ActividadEntidadMapper;
import pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.entity.ActividadEntidadEntity;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;

 class JpaActividadEntidadRepositoryAdapterTest {

    @Mock
    private JpaActividadEntidadRepository repository;

    @Mock
    private ActividadEntidadMapper mapper;

    @InjectMocks
    private JpaActividadEntidadRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByEntidadIdAndDocumentoIdAndPuertoDue_Success() {
        Integer entidadId = 1;
        Integer actividadId = 2;
        String codPuertoNacional = "PUERTO123";

        // Mockeamos la entidad devuelta por el repositorio
        ActividadEntidadEntity actividadEntidadEntity = new ActividadEntidadEntity();
        when(repository.findByEntidadIdAndActividadIdAndCodPuertoNacional(entidadId, actividadId, codPuertoNacional))
                .thenReturn(Optional.of(actividadEntidadEntity));

        // Mockeamos el mapper para convertir la entidad a modelo
        ActividadEntidad actividadEntidad = new ActividadEntidad();
        when(mapper.actividadEntityToModel(actividadEntidadEntity)).thenReturn(actividadEntidad);

        // Llamamos al método que estamos probando
        Optional<ActividadEntidad> result = adapter.findByEntidadIdAndDocumentoIdAndPuertoDue(entidadId, actividadId, codPuertoNacional);

        // Verificamos el resultado
        assertTrue(result.isPresent());
        assertEquals(actividadEntidad, result.get());

        // Verificamos que los mocks fueron llamados correctamente
        verify(repository, times(1)).findByEntidadIdAndActividadIdAndCodPuertoNacional(entidadId, actividadId, codPuertoNacional);
        verify(mapper, times(1)).actividadEntityToModel(actividadEntidadEntity);
    }

    @Test
    void testFindByEntidadIdAndDocumentoIdAndPuertoDue_NotFound() {
        Integer entidadId = 1;
        Integer actividadId = 2;
        String codPuertoNacional = "PUERTO123";

        // Simulamos que el repositorio no encuentra nada
        when(repository.findByEntidadIdAndActividadIdAndCodPuertoNacional(entidadId, actividadId, codPuertoNacional))
                .thenReturn(Optional.empty());

        // Llamamos al método que estamos probando
        Optional<ActividadEntidad> result = adapter.findByEntidadIdAndDocumentoIdAndPuertoDue(entidadId, actividadId, codPuertoNacional);

        // Verificamos que el resultado es vacío
        assertFalse(result.isPresent());

        // Verificamos que los mocks fueron llamados correctamente
        verify(repository, times(1)).findByEntidadIdAndActividadIdAndCodPuertoNacional(entidadId, actividadId, codPuertoNacional);
        verify(mapper, times(0)).actividadEntityToModel(any());
    }

     @Test
     void testFindByActividadIdAndCodPuertoNacional_WhenEntidadIdIsZero() {
         Integer entidadId = 0; // <- menor o igual a 0
         Integer actividadId = 2;
         String codPuertoNacional = "CLL";

         ActividadEntidadEntity actividadEntidadEntity = new ActividadEntidadEntity();
         when(repository.findByActividadIdAndCodPuertoNacional(actividadId, codPuertoNacional))
                 .thenReturn(Optional.of(actividadEntidadEntity));

         ActividadEntidad actividadEntidad = new ActividadEntidad();
         when(mapper.actividadEntityToModel(actividadEntidadEntity)).thenReturn(actividadEntidad);

         Optional<ActividadEntidad> result = adapter.findByEntidadIdAndDocumentoIdAndPuertoDue(entidadId, actividadId, codPuertoNacional);

         assertTrue(result.isPresent());
         assertEquals(actividadEntidad, result.get());

         verify(repository, times(1)).findByActividadIdAndCodPuertoNacional(actividadId, codPuertoNacional);
         verify(mapper, times(1)).actividadEntityToModel(actividadEntidadEntity);
     }

     @Test
     void testFindByActividadIdAndCodPuertoNacional_NotFound_WhenEntidadIdIsNegative() {
         Integer entidadId = -1; // <- también válido para probar <= 0
         Integer actividadId = 3;
         String codPuertoNacional = "CLL";

         when(repository.findByActividadIdAndCodPuertoNacional(actividadId, codPuertoNacional))
                 .thenReturn(Optional.empty());

         Optional<ActividadEntidad> result = adapter.findByEntidadIdAndDocumentoIdAndPuertoDue(entidadId, actividadId, codPuertoNacional);

         assertFalse(result.isPresent());

         verify(repository, times(1)).findByActividadIdAndCodPuertoNacional(actividadId, codPuertoNacional);
         verify(mapper, times(0)).actividadEntityToModel(any());
     }

 }

