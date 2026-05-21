package pe.gob.vuce.cp.sp.pagos.infrastructure.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pe.gob.vuce.cp.sp.pagos.application.service.ActividadEntidadService;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.CreateSeguimientoUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.ActividadEntidadRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.OrdenPagoRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.repository.JpaActividadEntidadRepositoryAdapter;
import pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.repository.JpaOrdenPagoRepositoryAdapter;

 class ApplicationConfigTest {

    @InjectMocks
    private ApplicationConfig applicationConfig;

    @Mock
    private JpaOrdenPagoRepositoryAdapter jpaOrdenPagoRepositoryAdapter;

    @Mock
    private JpaActividadEntidadRepositoryAdapter jpaActividadEntidadRepositoryAdapter;

    @Mock
    private OrdenPagoRepositoryPort ordenPagoRepositoryPort;
    @Mock
    private CreateSeguimientoUseCase createSeguimientoUseCase;

    @Mock
    private ActividadEntidadRepositoryPort actividadEntidadRepositoryPort;

    private ActividadEntidadService actividadEntidadService;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testActividadEntidadService() {
        // Act
        actividadEntidadService = applicationConfig.actividadEntidadService(actividadEntidadRepositoryPort);

        // Assert
        assertNotNull(actividadEntidadService);
    }

    @Test
     void testOrdenPagoRepositoryPort() {
        // Act
        OrdenPagoRepositoryPort repository = applicationConfig.ordenPagoRepositoryPort(jpaOrdenPagoRepositoryAdapter);

        // Assert
        assertSame(jpaOrdenPagoRepositoryAdapter, repository);
    }

    @Test
     void testActividadEntidadRepositoryPort() {
        // Act
        ActividadEntidadRepositoryPort repository = applicationConfig.actividadEntidadRepositoryPort(jpaActividadEntidadRepositoryAdapter);

        // Assert
        assertSame(jpaActividadEntidadRepositoryAdapter, repository);
    }
}
