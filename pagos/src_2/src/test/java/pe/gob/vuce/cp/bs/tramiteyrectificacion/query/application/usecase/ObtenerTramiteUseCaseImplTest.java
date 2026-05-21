package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.application.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.TramiteModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.port.out.TramiteRepositoryPort;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.GetTramiteQueryParamsDto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ObtenerTramiteUseCaseImplTest {

    @Mock
    private TramiteRepositoryPort tramiteRepositoryPort;

    @InjectMocks
    private ObtenerTramiteUseCaseImpl obtenerTramiteUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerTramite() {
        // Arrange
        Integer id = 1;
        TramiteModel tramiteModel = new TramiteModel();
        tramiteModel.setId(id);
        tramiteModel.setDue("DUE-001");
        tramiteModel.setNombreNave("Nave 1");
        tramiteModel.setNumeroSuce("SUCE-001");
        tramiteModel.setNumeroTramite("TRAMITE-001");
        tramiteModel.setEntidadId(100);
        tramiteModel.setEntidadNombre("Entidad 1");
        tramiteModel.setEntidadRuc("12345678901");
        tramiteModel.setTupa("TUPA-001");
        tramiteModel.setEstadoTramite("En Proceso");
        tramiteModel.setMonto(1000.0);
        tramiteModel.setEstadoDePago("Pagado");
        tramiteModel.setAgenciaId(200);
        tramiteModel.setAgenciaNombre("Agencia 1");
        tramiteModel.setAgenciaRuc("98765432109");
        tramiteModel.setFechaDeclaracionJuradaActual(LocalDateTime.now());
        tramiteModel.setDescripcion("Descripción del trámite");
        tramiteModel.setCpb("CPB-001");
        tramiteModel.setFueTramiteManual(true);
        tramiteModel.setDeclaracionesJuradas(Collections.emptyList());

        List<TramiteModel> expectedTramites = Collections.singletonList(tramiteModel);

        when(tramiteRepositoryPort.obtenerTramite(id)).thenReturn(expectedTramites);

        // Act
        List<TramiteModel> actualTramites = obtenerTramiteUseCase.obtenerTramite(id);

        // Assert
        assertEquals(expectedTramites, actualTramites);
        assertEquals("DUE-001", actualTramites.get(0).getDue());
        assertEquals("Nave 1", actualTramites.get(0).getNombreNave());
        assertEquals("SUCE-001", actualTramites.get(0).getNumeroSuce());
        assertEquals("TRAMITE-001", actualTramites.get(0).getNumeroTramite());
        assertEquals(100, actualTramites.get(0).getEntidadId());
        assertEquals("Entidad 1", actualTramites.get(0).getEntidadNombre());
        assertEquals("12345678901", actualTramites.get(0).getEntidadRuc());
        assertEquals("TUPA-001", actualTramites.get(0).getTupa());
        assertEquals("En Proceso", actualTramites.get(0).getEstadoTramite());
        assertEquals(1000.0, actualTramites.get(0).getMonto());
        assertEquals("Pagado", actualTramites.get(0).getEstadoDePago());
        assertEquals(200, actualTramites.get(0).getAgenciaId());
        assertEquals("Agencia 1", actualTramites.get(0).getAgenciaNombre());
        assertEquals("98765432109", actualTramites.get(0).getAgenciaRuc());
        assertEquals("Descripción del trámite", actualTramites.get(0).getDescripcion());
        assertEquals("CPB-001", actualTramites.get(0).getCpb());
        assertEquals(true, actualTramites.get(0).getFueTramiteManual());

        verify(tramiteRepositoryPort).obtenerTramite(id);
    }

    @Test
    void testObtenerTramites() {
        // Arrange
        GetTramiteQueryParamsDto paramsDto = new GetTramiteQueryParamsDto();
        paramsDto.setTupa("TUPA-001");
        paramsDto.setExpedientes(true);
        paramsDto.setDue("DUE-001");
        paramsDto.setNombreNave("Nave 1");
        paramsDto.setEntidad(100);
        paramsDto.setEstadoTramite("En Proceso");
        paramsDto.setNumeroSuce("SUCE-001");
        paramsDto.setNumeroTramite("TRAMITE-001");
        paramsDto.setCpb("CPB-001");
        paramsDto.setAgencia(200);
        paramsDto.setTramiteFechaDesde("2024-08-01");
        paramsDto.setTramiteFechaHasta("2024-08-24");
        paramsDto.setPageNumber(1);
        paramsDto.setPageSize(10);

        Pageable pageable = PageRequest.of(paramsDto.getPageNumber(), paramsDto.getPageSize());
        TramiteModel tramiteModel = new TramiteModel();
        List<TramiteModel> tramiteList = Collections.singletonList(tramiteModel);
        Page<TramiteModel> expectedPage = new PageImpl<>(tramiteList, pageable, tramiteList.size());

        when(tramiteRepositoryPort.obtenerTramites(paramsDto)).thenReturn(expectedPage);

        // Act
        Page<TramiteModel> actualPage = obtenerTramiteUseCase.obtenerTramites(paramsDto);

        // Assert
        assertEquals(expectedPage, actualPage);
        assertEquals("TUPA-001", paramsDto.getTupa());
        assertEquals(true, paramsDto.getExpedientes());
        assertEquals("DUE-001", paramsDto.getDue());
        assertEquals("Nave 1", paramsDto.getNombreNave());
        assertEquals(100, paramsDto.getEntidad());
        assertEquals("En Proceso", paramsDto.getEstadoTramite());
        assertEquals("SUCE-001", paramsDto.getNumeroSuce());
        assertEquals("TRAMITE-001", paramsDto.getNumeroTramite());
        assertEquals("CPB-001", paramsDto.getCpb());
        assertEquals(200, paramsDto.getAgencia());
        assertEquals("2024-08-01", paramsDto.getTramiteFechaDesde());
        assertEquals("2024-08-24", paramsDto.getTramiteFechaHasta());
        assertEquals(1, paramsDto.getPageNumber());
        assertEquals(10, paramsDto.getPageSize());

        verify(tramiteRepositoryPort).obtenerTramites(paramsDto);
    }
}
