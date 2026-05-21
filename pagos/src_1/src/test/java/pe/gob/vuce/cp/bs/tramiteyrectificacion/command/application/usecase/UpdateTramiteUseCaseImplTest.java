package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants.Constants;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.SeguimientoRequestDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.enums.Operaciones;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.enums.OrdenPagoEstados;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.enums.TramiteEstados;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.exceptions.BusinessError;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.DocumentoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.OrdenDePagoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.TramiteModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.CreateSeguimientoUseCase;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.ObtenerDocumentoUseCase;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.DeclaracionJuradaRepositoryPort;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.FeignOrdenPagoClientPort;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.OrdenPagoRepositoryPort;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.TramiteRepositoryPort;

public class UpdateTramiteUseCaseImplTest {

    @InjectMocks
    private UpdateTramiteUseCaseImpl updateTramiteUseCaseImpl;

    @Mock
    private TramiteRepositoryPort tramiteRepositoryPort;

    @Mock
    private CreateSeguimientoUseCase createSeguimientoUseCase;

    @Mock
    private ObtenerDocumentoUseCase obtenerDocumentoUseCase;

    @Mock
    private OrdenPagoRepositoryPort ordenPagoRepositoryPort;

    @Mock
    private DeclaracionJuradaRepositoryPort declaracionJuradaRepositoryPort;

    @Mock
    private FeignOrdenPagoClientPort feignOrdenPagoClientPort;

    private TramiteModel tramiteModel;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        tramiteModel = new TramiteModel();
        tramiteModel.setDocumentoId(1);
    }

    /*@Test
    public void testUpdateTramiteOperacionAsignarManual() {
        // Arrange
        DocumentoModel documento = new DocumentoModel();
        documento.setDescAcronimo("ACR");
        String user = "testUser";
        when(tramiteRepositoryPort.update(any(TramiteModel.class))).thenReturn(tramiteModel);
        when(obtenerDocumentoUseCase.findById(anyInt())).thenReturn(Optional.of(documento));
        tramiteModel.setUsuidModAud("usuarioTest");

        // Act
        TramiteModel result = updateTramiteUseCaseImpl.update(
                tramiteModel,
                "12345678901",
                "User",
                Operaciones.ASIGNAR_MANUAL.getCodigo());

        // Assert
        assertNotNull(result);
        assertEquals(Constants.ES_REGISTRO_EXPEDIENTE_MANUAL, result.getIndAsignacionTramiteManual());
        verify(createSeguimientoUseCase, times(1))
                .create(any(SeguimientoRequestDto.class), eq(user));
        verify(tramiteRepositoryPort, times(1)).update(any(TramiteModel.class));
    }*/

    /*@Test
    public void testUpdateTramiteOperacionAutorizar() {
        // Arrange
        DocumentoModel documento = new DocumentoModel();
        documento.setDescAcronimo("DOC");

        when(tramiteRepositoryPort.update(any(TramiteModel.class))).thenReturn(tramiteModel);
        when(obtenerDocumentoUseCase.findById(anyInt())).thenReturn(Optional.of(documento));
        tramiteModel.setUsuidModAud("usuarioTest");
        String user = "testUser";
        // Act
        TramiteModel result = updateTramiteUseCaseImpl.update(
                tramiteModel,
                "12345678901",
                "User",
                Operaciones.AUTORIZAR.getCodigo());

        // Assert
        assertNotNull(result);
        assertEquals(TramiteEstados.AUTORIZADO.getCodigo(), result.getEstadoTramite());
        verify(createSeguimientoUseCase, times(1))
                .create(any(SeguimientoRequestDto.class), eq(user));
        verify(tramiteRepositoryPort, times(1)).update(any(TramiteModel.class));
    }*/

    @Test
    public void testUpdateTramiteThrowsBusinessError() {
        // Arrange
        when(tramiteRepositoryPort.update(any(TramiteModel.class)))
                .thenThrow(new RuntimeException("Error al actualizar"));

        // Act & Assert
        BusinessError exception = assertThrows(BusinessError.class, () -> updateTramiteUseCaseImpl.update(tramiteModel,
                "12345678901", "User",Operaciones.ASIGNAR_MANUAL.getCodigo()));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getHttpStatus());
        verify(tramiteRepositoryPort, times(1)).update(any(TramiteModel.class));
        verify(createSeguimientoUseCase, never()).create(any(SeguimientoRequestDto.class), anyString());
    }

    @Test
    void testDesist_withEscalaIdAndTramiteId() {
        // Arrange
        String user ="testUser";
        Integer escalaId = 1;
        Integer tramiteId = 1;
        tramiteModel.setTramiteId(tramiteId);
        // Simulate fetchTramitesById: repository findById returns the tramiteModel
        when(tramiteRepositoryPort.findById(tramiteId)).thenReturn(Optional.of(tramiteModel));
        // For processOrdenesDePago and processDeclaracionesJuradas, return empty lists
        when(declaracionJuradaRepositoryPort.findByEscalaId(escalaId)).thenReturn(new ArrayList<>());
        when(ordenPagoRepositoryPort.findAllByEscalaIdAndEstadoOrdenPagoIn(eq(escalaId), anyList()))
                .thenReturn(new ArrayList<>());
        // Stub save to simply return the same model
        when(tramiteRepositoryPort.save(any(TramiteModel.class))).thenReturn(tramiteModel);

        // Act
        List<TramiteModel> result = updateTramiteUseCaseImpl.desist(escalaId, tramiteId,user);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(tramiteRepositoryPort).findById(tramiteId);
    }

    @Test
    void testDesist_withOnlyEscalaId() {
        // Arrange
        String user = "testUser";
        Integer escalaId = 1;
        Integer tramiteId = null;
        // For the "by documentos" branch, stub the call to
        // obtenerDocumentoUseCase.findByDescAcronimoIn
        when(obtenerDocumentoUseCase.findByDescAcronimoIn(anyList())).thenReturn(new ArrayList<>());
        when(tramiteRepositoryPort.findAllByEscalaIdAndDocumentoIdIn(eq(escalaId), anyList()))
                .thenReturn(new ArrayList<>());

        // Act
        List<TramiteModel> result = updateTramiteUseCaseImpl.desist(escalaId, tramiteId,user);

        // Assert
        assertNotNull(result);
        verify(tramiteRepositoryPort).findAllByEscalaIdAndDocumentoIdIn(eq(escalaId), anyList());
    }

    @Test
    void testDesist_whenExceptionThrown() {
        // Arrange
        String user = "testUser";
        Integer escalaId = 1;
        Integer tramiteId = 1;
        when(tramiteRepositoryPort.findById(tramiteId))
                .thenThrow(new RuntimeException("Error"));

        // Act & Assert
        assertThrows(BusinessError.class, () -> updateTramiteUseCaseImpl.desist(escalaId, tramiteId,user));
    }

    /*@Test
    void testGetComentario_withNroExpediente() {
        String nroSuce = "12345";
        String tipoDocumento = "DOC";
        String nroExpediente = "67890";
        Integer tipoSeguimiento = 47;
        String comentario = UpdateTramiteUseCaseImpl.getComentario(nroSuce, tipoDocumento, nroExpediente,tipoSeguimiento);
        Assertions.assertEquals("Tramite: 12345, Documento: DOC, Numero de Expediente Entidad: 67890", comentario);
    }*/

    @Test
    void testGetComentario_withoutNroExpediente() {
        String nroSuce = "12345";
        String tipoDocumento = "DOC";
        String nroExpediente = null;
        Integer tipoSeguimiento = 12;
        String comentario = UpdateTramiteUseCaseImpl.getComentario(nroSuce, tipoDocumento, nroExpediente,tipoSeguimiento);
        assertEquals("Tramite: 12345, Documento: DOC", comentario);
    }

    @Test
    void shouldUpdateTramiteSuccessfullyWithAssignManualOperation() {
        // Arrange
        tramiteModel.setDocumentoId(1);
        when(tramiteRepositoryPort.update(any(TramiteModel.class))).thenReturn(tramiteModel);
        when(obtenerDocumentoUseCase.findById(anyInt())).thenReturn(Optional.of(new DocumentoModel()));
        tramiteModel.setUsuidModAud("usuarioTest");

        // Act
        TramiteModel result = updateTramiteUseCaseImpl.update(
                tramiteModel,
                "123456789",
                "User",
                Operaciones.ASIGNAR_MANUAL.getCodigo());

        // Assert
        assertNotNull(result);
        assertEquals(Constants.ES_REGISTRO_EXPEDIENTE_MANUAL, result.getIndAsignacionTramiteManual());
    }

    @Test
    void shouldThrowBusinessErrorOnUpdateFailure() {
        // Arrange
        when(tramiteRepositoryPort.update(any(TramiteModel.class)))
                .thenThrow(new RuntimeException("Error updating tramite"));

        // Act & Assert
        BusinessError exception = assertThrows(BusinessError.class, () -> updateTramiteUseCaseImpl.update(tramiteModel,
                "123456789","User", Operaciones.ASIGNAR_MANUAL.getCodigo()));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getHttpStatus());
    }

    @Test
    void shouldDesistTramitesSuccessfully() {
        // Arrange for fetchTramitesByDocumentos path
        String user = "testUser";
        Integer escalaId = 1;
        List<TramiteModel> tramiteList = List.of(tramiteModel);
        when(obtenerDocumentoUseCase.findByDescAcronimoIn(anyList()))
                .thenReturn(List.of(new DocumentoModel()));
        when(tramiteRepositoryPort.findAllByEscalaIdAndDocumentoIdIn(eq(escalaId), anyList()))
                .thenReturn(tramiteList);
        when(declaracionJuradaRepositoryPort.findByEscalaId(escalaId))
                .thenReturn(new ArrayList<>());
        when(ordenPagoRepositoryPort.findAllByEscalaIdAndEstadoOrdenPagoIn(eq(escalaId), anyList()))
                .thenReturn(new ArrayList<>());
        when(tramiteRepositoryPort.save(any(TramiteModel.class))).thenReturn(tramiteModel);

        // Act
        List<TramiteModel> result = updateTramiteUseCaseImpl.desist(escalaId, null,user);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void shouldGenerateSeguimientoRequestCorrectly() {
        // Act
        SeguimientoRequestDto dto = UpdateTramiteUseCaseImpl.generarRequestSeguimiento(
                1, 1, "N", "123456789", "12345", "ACRON", "1234");

        // Assert
        assertNotNull(dto);
        assertEquals("N", dto.getIndicadorEs());
        assertEquals("Tramite: 12345, Documento: ACRON, Numero de Expediente Entidad: 1234", dto.getComentario());
    }

    @Test
    public void testDesist_withValidTramiteId_shouldReturnTramiteModelList() {
        // Arrange
        String user = "testUser";
        Integer escalaId = 1;
        Integer tramiteId = 100;
        tramiteModel.setTramiteId(tramiteId);
        when(tramiteRepositoryPort.findById(tramiteId)).thenReturn(Optional.of(tramiteModel));

        // Act
        List<TramiteModel> result = updateTramiteUseCaseImpl.desist(escalaId, tramiteId,user);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(tramiteRepositoryPort, times(1)).findById(tramiteId);
    }

    @Test
    public void testDesist_withNullEscalaId_shouldReturnEmptyList() {
        // Arrange
        String user = "testUser";
        Integer escalaId = null;
        Integer tramiteId = 100;

        // Act
        List<TramiteModel> result = updateTramiteUseCaseImpl.desist(escalaId, tramiteId,user);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    public void testProcessOrdenesDePago() {
        // Arrange
        Integer escalaId = 1;
        String user = "testUser";

        OrdenDePagoModel ordenDePago1 = new OrdenDePagoModel();
        ordenDePago1.setId(1);
        ordenDePago1.setEstadoOrdenPago(OrdenPagoEstados.CREADO.getCodigo());
        ordenDePago1.setPpCpb("PP001");

        OrdenDePagoModel ordenDePago2 = new OrdenDePagoModel();
        ordenDePago2.setId(2);
        ordenDePago2.setEstadoOrdenPago(OrdenPagoEstados.PENDIENTEPAGO.getCodigo());
        ordenDePago2.setPpCpb("PP002");

        List<OrdenDePagoModel> ordenesDePago = List.of(ordenDePago1, ordenDePago2);

        when(ordenPagoRepositoryPort.findAllByEscalaIdAndEstadoOrdenPagoIn(eq(escalaId),
                eq(List.of(OrdenPagoEstados.PAGADO.getCodigo()))))
                .thenReturn(new ArrayList<>());

        when(ordenPagoRepositoryPort.findAllByEscalaIdAndEstadoOrdenPagoIn(eq(escalaId),
                eq(List.of(OrdenPagoEstados.CREADO.getCodigo(), OrdenPagoEstados.PENDIENTEPAGO.getCodigo()))))
                .thenReturn(ordenesDePago);

        when(ordenPagoRepositoryPort.save(any(OrdenDePagoModel.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        List<Object> result = updateTramiteUseCaseImpl.processOrdenesDePago(escalaId, user);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty()); // No debería haber ppCpb de órdenes PAGADAS

        // Verificar que se actualizó el estado a ANULADO
        assertEquals(OrdenPagoEstados.ANULADO.getCodigo(), ordenDePago1.getEstadoOrdenPago());
        assertEquals(OrdenPagoEstados.ANULADO.getCodigo(), ordenDePago2.getEstadoOrdenPago());

        // Verificar que se llamó a anular solo para la orden PENDIENTEPAGO
        verify(feignOrdenPagoClientPort, times(1)).anular(2, user);
        verify(feignOrdenPagoClientPort, never()).anular(1, user);

        // Verificar que se guardaron las órdenes
        verify(ordenPagoRepositoryPort, times(2)).save(any(OrdenDePagoModel.class));
    }

    @Test
    public void testProcessOrdenesDePago2() {
        // Arrange
        Integer escalaId = 1;
        String user = "testUser";

        OrdenDePagoModel ordenDePago1 = new OrdenDePagoModel();
        ordenDePago1.setId(1);
        ordenDePago1.setEstadoOrdenPago(OrdenPagoEstados.PAGADO.getCodigo());
        ordenDePago1.setPpCpb("PP001");

        OrdenDePagoModel ordenDePago2 = new OrdenDePagoModel();
        ordenDePago2.setId(2);
        ordenDePago2.setEstadoOrdenPago(OrdenPagoEstados.PAGADO.getCodigo());
        ordenDePago2.setPpCpb("PP002");

        List<OrdenDePagoModel> ordenesDePago = List.of(ordenDePago1, ordenDePago2);

        when(ordenPagoRepositoryPort.findAllByEscalaIdAndEstadoOrdenPagoIn(eq(escalaId),
                eq(List.of(OrdenPagoEstados.PAGADO.getCodigo()))))
                .thenReturn(ordenesDePago);

        when(ordenPagoRepositoryPort.findAllByEscalaIdAndEstadoOrdenPagoIn(eq(escalaId),
                eq(List.of(OrdenPagoEstados.CREADO.getCodigo(), OrdenPagoEstados.PENDIENTEPAGO.getCodigo()))))
                .thenReturn(new ArrayList<>());

        // Act
        List<Object> result = updateTramiteUseCaseImpl.processOrdenesDePago(escalaId, user);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains("PP001"));
        assertTrue(result.contains("PP002"));

        // Verificar que los estados no cambiaron
        assertEquals(OrdenPagoEstados.PAGADO.getCodigo(), ordenDePago1.getEstadoOrdenPago());
        assertEquals(OrdenPagoEstados.PAGADO.getCodigo(), ordenDePago2.getEstadoOrdenPago());

        // Verificar que no se llamó a anular
        verify(feignOrdenPagoClientPort, never()).anular(anyInt(), anyString());

        // Verificar que no se guardaron cambios
        verify(ordenPagoRepositoryPort, never()).save(any(OrdenDePagoModel.class));
    }
}
