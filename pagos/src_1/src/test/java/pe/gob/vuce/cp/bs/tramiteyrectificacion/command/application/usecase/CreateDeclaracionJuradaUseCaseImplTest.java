package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants.Constants;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants.ErrorCodes;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.DeclaracionJuradaRequestDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.DocumentoDDJJRequestDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.SeguimientoRequestDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.enums.DeclaracionJuradaEstados;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.enums.EstadosDue;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.enums.TipoSeguimiento;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.exceptions.BusinessError;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.ActividadEntidadPuertoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.DeclaracionJuradaModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.DocumentoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.CreateSeguimientoUseCase;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.ActividadEntidadPuertoPort;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.DeclaracionJuradaRepositoryPort;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.DocumentoRepositoryPort;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.EscalaRepositoryPort;

@ExtendWith(MockitoExtension.class)
public class CreateDeclaracionJuradaUseCaseImplTest {

        @Mock
        private DeclaracionJuradaRepositoryPort declaracionJuradaRepositoryPort;

        @Mock
        private CreateSeguimientoUseCase createSeguimientoUseCase;

        @Mock
        private EscalaRepositoryPort escalaRepository;

        @Mock
        private DocumentoRepositoryPort documentoRepository;

        @Mock
        private ActividadEntidadPuertoPort actividadEntidadPuertoPort;

        @InjectMocks
        private CreateDeclaracionJuradaUseCaseImpl createDeclaracionJuradaUseCase;

        // Captor to capture the DeclaracionJuradaModel passed to save()
        private ArgumentCaptor<DeclaracionJuradaModel> ddjjCaptor;

        @BeforeEach
        void setUp() {
                ddjjCaptor = ArgumentCaptor.forClass(DeclaracionJuradaModel.class);
        }

        @Test
        void testSave_Aceptada() {
                String user = "USER";
                DeclaracionJuradaRequestDto dto = new DeclaracionJuradaRequestDto();
                dto.setId(1);
                dto.setUsuario("testUser");
                dto.setEstadoDdjjPago(DeclaracionJuradaEstados.ACEPTADA.getCodigo());

                DeclaracionJuradaModel initialModel = new DeclaracionJuradaModel();
                // Provide additional required fields used by the use case:
                initialModel.setEscalaId(1);
                initialModel.setRucAgente("123456789");
                DocumentoModel documento = new DocumentoModel();
                documento.setDocumentoId(1);
                documento.setDescAcronimo("DMS");
                initialModel.setDocumento(documento);

                // Stub repository and dependencies
                when(declaracionJuradaRepositoryPort.findById(dto.getId()))
                                .thenReturn(Optional.of(initialModel));
                // Simulate that there is already one declaration in the current year.
                when(declaracionJuradaRepositoryPort.countByFechaSolicitudDdjjBetween(any(LocalDateTime.class),
                                any(LocalDateTime.class))).thenReturn(1);
                // Simulate save returning the same model after modifications.
                when(declaracionJuradaRepositoryPort.save(any(DeclaracionJuradaModel.class)))
                                .thenAnswer(invocation -> invocation.getArgument(0));
                // For update, we use a value (e.g., 999) not in the "entrada" list.
                when(escalaRepository.getEstadoDueId(eq(1))).thenReturn(999);

                // Act: Call the use case
                DeclaracionJuradaModel result = createDeclaracionJuradaUseCase.save(dto,user);

                // Assert: Verify that save was called and capture the argument.
                verify(declaracionJuradaRepositoryPort, times(1)).save(ddjjCaptor.capture());
                DeclaracionJuradaModel captured = ddjjCaptor.getValue();

                // Calculate expected values based on the captured fechaSolicitudDdjj.
                int currentYear = captured.getFechaSolicitudDdjj().getYear();
                String expectedNumero = String.format("%s%s%0" + Constants.TAMANIO_SECUENCIA_DJ + "d",
                                Constants.DECLARACION_JURADA, currentYear, 1);
                assertEquals(expectedNumero, captured.getNumeroDdjj(), "The generated NumeroDdjj is incorrect.");

                // The user from the DTO should be set in the model.
                assertEquals("testUser", captured.getUsuidModAud(), "User was not correctly propagated.");

                // Verify that the seguimiento use case was called with a proper
                // SeguimientoRequestDto.
                ArgumentCaptor<SeguimientoRequestDto> segCaptor = ArgumentCaptor.forClass(SeguimientoRequestDto.class);
                verify(createSeguimientoUseCase, times(1)).create(segCaptor.capture(), eq("USER"));
                SeguimientoRequestDto segDto = segCaptor.getValue();

                // For accepted declarations, the seguimiento id should be that for
                // ACEPTAR_DDJJ.
                assertEquals(TipoSeguimiento.ACEPTAR_DDJJ.getValue(), segDto.getTipoSegId(),
                                "TipoSeguimiento is not as expected.");
                // The comentario is built as: "Solicitud de ddjj aprobada " + numeroDdjj + "
                // Documento: DMS"
                assertEquals("Solicitud de ddjj aprobada " + captured.getNumeroDdjj() + " Documento: DMS",
                                segDto.getComentario(), "Comentario in seguimiento is not as expected.");
                // And the indicador is expected to be the constant for salida.
                assertEquals(Constants.SALIDA_NAVE, segDto.getIndicadorEs(), "Indicador de entrada no coincide.");
        }

        @Test
        void testSave_Denegada() {
                String user = "USER";
                // Arrange: Create a request DTO for a denied declaration
                DeclaracionJuradaRequestDto dto = new DeclaracionJuradaRequestDto();
                dto.setId(2);
                dto.setUsuario("testUser");
                dto.setEstadoDdjjPago(DeclaracionJuradaEstados.DENEGADA.getCodigo());

                DeclaracionJuradaModel initialModel = new DeclaracionJuradaModel();
                initialModel.setEscalaId(2);
                initialModel.setRucAgente("987654321");
                DocumentoModel documento = new DocumentoModel();
                documento.setDocumentoId(2);
                documento.setDescAcronimo("XYZ");
                initialModel.setDocumento(documento);

                when(declaracionJuradaRepositoryPort.findById(dto.getId()))
                                .thenReturn(Optional.of(initialModel));
                when(declaracionJuradaRepositoryPort.countByFechaSolicitudDdjjBetween(any(LocalDateTime.class),
                                any(LocalDateTime.class))).thenReturn(2);
                when(declaracionJuradaRepositoryPort.save(any(DeclaracionJuradaModel.class)))
                                .thenAnswer(invocation -> invocation.getArgument(0));
                when(escalaRepository.getEstadoDueId(eq(2))).thenReturn(999);

                // Act
                DeclaracionJuradaModel result = createDeclaracionJuradaUseCase.save(dto,user);

                // Assert: Capture and verify the saved declaration.
                verify(declaracionJuradaRepositoryPort, times(1)).save(ddjjCaptor.capture());
                DeclaracionJuradaModel captured = ddjjCaptor.getValue();

                int currentYear = captured.getFechaSolicitudDdjj().getYear();
                String expectedNumero = String.format("%s%s%0" + Constants.TAMANIO_SECUENCIA_DJ + "d",
                                Constants.DECLARACION_JURADA, currentYear, 2);
                assertEquals(expectedNumero, captured.getNumeroDdjj(), "The generated NumeroDdjj is incorrect.");

                assertEquals("testUser", captured.getUsuidModAud(), "User was not correctly propagated.");

                // Verify seguimiento use case was invoked with the expected values.
                ArgumentCaptor<SeguimientoRequestDto> segCaptor = ArgumentCaptor.forClass(SeguimientoRequestDto.class);
                verify(createSeguimientoUseCase, times(1)).create(segCaptor.capture(), eq("USER"));
                SeguimientoRequestDto segDto = segCaptor.getValue();

                // For denied declarations, the seguimiento id should be that for DENEGAR_DDJJ.
                assertEquals(TipoSeguimiento.DENEGAR_DDJJ.getValue(), segDto.getTipoSegId(),
                                "TipoSeguimiento is not as expected.");
                // The comentario should reflect the denial.
                assertEquals("Solicitud de ddjj denegada " + captured.getNumeroDdjj() + " Documento: XYZ",
                                segDto.getComentario(), "Comentario in seguimiento is not as expected.");
                assertEquals(Constants.SALIDA_NAVE, segDto.getIndicadorEs(), "Indicador de entrada no coincide.");
        }

        @Test
        void testSave_DeclaracionJuradaNotFound() {
                String user = "USER";
                // Arrange: A request DTO with an ID that is not found.
                DeclaracionJuradaRequestDto dto = new DeclaracionJuradaRequestDto();
                dto.setId(99);
                dto.setUsuario("testUser");

                when(declaracionJuradaRepositoryPort.findById(dto.getId()))
                                .thenReturn(Optional.empty());

                // Act & Assert: Expect a BusinessError with NOT_FOUND.
                BusinessError error = assertThrows(BusinessError.class, () -> {
                        createDeclaracionJuradaUseCase.save(dto,user);
                });
                assertEquals(HttpStatus.NOT_FOUND, error.getHttpStatus());
                assertEquals("DECLARACION_JURADA_NOT_FOUND", error.getErrorCode());
        }

        @Test
        void testSave_InternalServerError() {
                String user = "USER";
                DeclaracionJuradaRequestDto dto = new DeclaracionJuradaRequestDto();
                dto.setId(3);
                dto.setUsuario("testUser");
                dto.setEstadoDdjjPago(DeclaracionJuradaEstados.ACEPTADA.getCodigo());

                DeclaracionJuradaModel initialModel = new DeclaracionJuradaModel();
                initialModel.setEscalaId(3);
                initialModel.setRucAgente("111111111");
                DocumentoModel documento = new DocumentoModel();
                documento.setDocumentoId(3);
                documento.setDescAcronimo("ABC");
                initialModel.setDocumento(documento);

                when(declaracionJuradaRepositoryPort.findById(dto.getId()))
                                .thenReturn(Optional.of(initialModel));
                when(declaracionJuradaRepositoryPort.countByFechaSolicitudDdjjBetween(any(LocalDateTime.class),
                                any(LocalDateTime.class))).thenReturn(3);

                // Simulate an error during save
                when(declaracionJuradaRepositoryPort.save(any(DeclaracionJuradaModel.class)))
                                .thenThrow(new RuntimeException("Database error"));

                // Act & Assert: Expect a BusinessError wrapping the internal exception.
                BusinessError error = assertThrows(BusinessError.class, () -> {
                        createDeclaracionJuradaUseCase.save(dto,user);
                });
                assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, error.getHttpStatus());
                assertEquals(ErrorCodes.INTERNAL_SERVER_ERROR, error.getErrorCode());
                assertEquals("Database error", error.getMessage());
        }

        @Test
        void testCreate_Success() {
                // Updated test for creation scenario (ID is null) and the new GENERAR_DDJJ
                String user = "USER";
                // seguimiento.
                DeclaracionJuradaRequestDto dto = new DeclaracionJuradaRequestDto();
                dto.setRucAgente("20100148049");
                dto.setEstado("1");
                dto.setUsuario("testUser");
                dto.setActivityId(1);
                dto.setCodPuerto("123");

                DocumentoDDJJRequestDto documentoDto = new DocumentoDDJJRequestDto();
                documentoDto.setDocumentoId(1);
                documentoDto.setDescAcronimo("DMS");
                dto.setDocumento(documentoDto);
                dto.setEscalaId(1);

                DocumentoModel documento = new DocumentoModel();
                documento.setDocumentoId(1);
                documento.setDescAcronimo("DMS");

                ActividadEntidadPuertoModel actividadModel = new ActividadEntidadPuertoModel();
                actividadModel.setEntidadId(100);

                // Stub dependencies for creation:
                when(documentoRepository.findById(1)).thenReturn(Optional.of(documento));
                when(actividadEntidadPuertoPort.findByActividadIdAndCodPuertoNacionalAndEstado(
                                eq(1), eq("123"), eq(Constants.VALOR_POR_DEFECTO_ESTADO)))
                                .thenReturn(actividadModel);
                // For creation, choose an estadoId that leads to an "entrada" indicator.
                when(escalaRepository.getEstadoDueId(eq(1)))
                                .thenReturn(EstadosDue.ARRIBO_ANUNCIADO.getId());
                when(declaracionJuradaRepositoryPort.countByFechaSolicitudDdjjBetween(any(LocalDateTime.class),
                                any(LocalDateTime.class))).thenReturn(1);
                when(declaracionJuradaRepositoryPort.save(any(DeclaracionJuradaModel.class)))
                                .thenAnswer(invocation -> {
                                        DeclaracionJuradaModel dj = invocation.getArgument(0);
                                        // Simulate setting fechaSolicitudDdjj during save.
                                        dj.setFechaSolicitudDdjj(LocalDateTime.now());
                                        return dj;
                                });

                // Act
                DeclaracionJuradaModel result = createDeclaracionJuradaUseCase.save(dto,user);

                // Assert: Verify that the declaration is saved with correct fields.
                verify(declaracionJuradaRepositoryPort).save(ddjjCaptor.capture());
                DeclaracionJuradaModel captured = ddjjCaptor.getValue();

                assertEquals(dto.getRucAgente(), captured.getRucAgente());
                assertEquals(dto.getEstado(), captured.getEstado());
                assertEquals(dto.getUsuario(), captured.getUsuidRegAud());
                assertEquals(documento, captured.getDocumento());
                assertEquals(actividadModel.getEntidadId(), captured.getEntidadId());

                int currentYear = captured.getFechaSolicitudDdjj().getYear();
                String expectedNumero = String.format("%s%s%0" + Constants.TAMANIO_SECUENCIA_DJ + "d",
                                Constants.DECLARACION_JURADA, currentYear, 1);
                assertEquals(expectedNumero, captured.getNumeroDdjj(), "NumeroDdjj is not as expected.");

                // Verify that createSeguimientoUseCase is called with GENERAR_DDJJ type.
                ArgumentCaptor<SeguimientoRequestDto> segCaptor = ArgumentCaptor.forClass(SeguimientoRequestDto.class);
                verify(createSeguimientoUseCase, times(1)).create(segCaptor.capture(), eq(user));
                SeguimientoRequestDto segDto = segCaptor.getValue();

                assertEquals(TipoSeguimiento.GENERAR_DDJJ.getValue(), segDto.getTipoSegId(),
                                "TipoSeguimiento is not GENERAR_DDJJ.");
                assertEquals("Solicitud de ddjj generada " + captured.getNumeroDdjj() + " Documento: DMS",
                                segDto.getComentario(), "Comentario in seguimiento is not as expected.");
                // Because the stubbed estado leads to an "entrada" indicator:
                assertEquals(Constants.ENTRADA_NAVE, segDto.getIndicadorEs(),
                                "Indicador de entrada no coincide for creation.");
        }
}
