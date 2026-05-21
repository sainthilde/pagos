package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants.Constants;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.SeguimientoRequestDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.enums.TramiteEstados;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.exceptions.BusinessError;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.DocumentoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.OrdenDePagoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.TramiteModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.CreateSeguimientoUseCase;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.ObtenerDocumentoUseCase;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.DeclaracionJuradaRepositoryPort;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.OrdenPagoRepositoryPort;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.TramiteRepositoryPort;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity.mae.Documento;

public class CreateTramiteUseCaseImplTest {

    @Mock
    private TramiteRepositoryPort tramiteRepositoryPort;

    @Mock
    private OrdenPagoRepositoryPort ordenPagoRepositoryPort;

    @Mock
    private DeclaracionJuradaRepositoryPort declaracionJuradaRepositoryPort;

    @InjectMocks
    private CreateTramiteUseCaseImpl createTramiteUseCase;
    @Mock
    private ObtenerDocumentoUseCase obtenerDocumentoUseCase;
    @Mock
    private CreateSeguimientoUseCase createSeguimientoUseCase;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate_Success() {
        // Arrange: Create an input TramiteModel with the necessary properties.
        TramiteModel inputTramite = new TramiteModel();
        inputTramite.setIndNoRequierePago(false);
        inputTramite.setTipoTramite(Constants.TRAMITE_PAGO);
        inputTramite.setDocumentoId(3);
        inputTramite.setEscalaId(4);
        inputTramite.setRucAgente("12345678901");
        inputTramite.setUsuidRegAud("user");
        String ruc = "ruc";
        String user = "user";

        DocumentoModel documento = new DocumentoModel();
        documento.setDocumentoId(81);


        // Stub the repository call for generating the sequential number.
        when(tramiteRepositoryPort.getNumeroTramitePorAnio(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(1);

        // Stub the save method to simply return the model that is passed (with the
        // extra fields set by the use case).
        when(tramiteRepositoryPort.save(any(TramiteModel.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Stub the OrdenPagoRepositoryPort to simulate a found order payment record.
        OrdenDePagoModel dummyOrden = new OrdenDePagoModel();
        dummyOrden.setTramite(null);
        when(ordenPagoRepositoryPort.findByDocumentoIdAndEscalaIdAndRucAgente(3, 4, "12345678901"))
                .thenReturn(List.of(dummyOrden));

        // Stub the DeclaracionJuradaRepositoryPort to return an empty list (i.e.
        // nothing to update).
        when(declaracionJuradaRepositoryPort.findByDocumentoIdAndEscalaIdAndRucAgente(3, 4, "12345678901"))
                .thenReturn(List.of());

        when(obtenerDocumentoUseCase.findById(anyInt()))
                .thenReturn(Optional.of(documento));
        doNothing().when(createSeguimientoUseCase).create(any(SeguimientoRequestDto.class), anyString());


        // Act: Call the use case.
        TramiteModel result = createTramiteUseCase.create(inputTramite,ruc,user);

        // Assert: Verify that the result has the expected values.
        assertNotNull(result);
        assertNotNull(result.getNumeroSuce());
        assertNotNull(result.getFechaTramite());
        assertEquals(TramiteEstados.EN_TRAMITE.getCodigo(), result.getEstadoTramite());
        assertEquals(Constants.VALOR_POR_DEFECTO_ESTADO, result.getEstado());

        // Verify that the repository methods were called as expected.
        verify(tramiteRepositoryPort, times(1)).getNumeroTramitePorAnio(any(LocalDateTime.class),
                any(LocalDateTime.class));
        verify(tramiteRepositoryPort, times(1)).save(any(TramiteModel.class));
        verify(ordenPagoRepositoryPort, times(1))
                .findByDocumentoIdAndEscalaIdAndRucAgente(3, 4, "12345678901");
        verify(ordenPagoRepositoryPort, times(1)).updateV2(dummyOrden);
        verify(declaracionJuradaRepositoryPort, times(1))
                .findByDocumentoIdAndEscalaIdAndRucAgente(3, 4, "12345678901");
    }

    @Test
    void testCreate_ThrowsException() {
        // Arrange: Create an input TramiteModel.
        TramiteModel inputTramite = new TramiteModel();
        String ruc = "ruc";
        String user="user";

        // Stub the save method to throw a RuntimeException.
        when(tramiteRepositoryPort.save(any(TramiteModel.class)))
                .thenThrow(new RuntimeException("Error inesperado"));

        // Act & Assert: Verify that a BusinessError is thrown with
        // INTERNAL_SERVER_ERROR status.
        BusinessError exception = assertThrows(BusinessError.class, () -> {
            createTramiteUseCase.create(inputTramite,ruc,user);
        });
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getHttpStatus());
    }
}
