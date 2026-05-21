package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.application.service.OrdenPagoService;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.OrdenPagoUpdateRequestDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.CommonResponse;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.EntidadModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.Meta;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.OrdenDePagoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.TramiteModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.mapper.OrdenPagoMapper;

public class OrdenDePagoControllerTest {

        @Mock
        private OrdenPagoService ordenPagoService;

        @Mock
        private OrdenPagoMapper ordenPagoMapper;

        @InjectMocks
        private OrdenDePagoController ordenDePagoController;

        private OrdenPagoUpdateRequestDto ordenPagoUpdateRequestDto;
        private OrdenPagoUpdateRequestDto ordenPagoUpdateResponseDto;
        private CommonResponse commonResponse;

        private static final String USER = "testUser";

        @BeforeEach
        void setUp() {
                MockitoAnnotations.openMocks(this);

                // Inicializar DTOs y respuesta común
                ordenPagoUpdateRequestDto = new OrdenPagoUpdateRequestDto(
                                1, 1, 1, "PG", "REASIGNAR");

                ordenPagoUpdateResponseDto = new OrdenPagoUpdateRequestDto(
                                1, 1, 1, "PG", "REASIGNAR");

                commonResponse = new CommonResponse();
                commonResponse.setMeta(new Meta());
                commonResponse.setData(ordenPagoUpdateResponseDto);
        }

        @Test
        void testUpdateOrdenPago() {
                // Arrange
                when(ordenPagoMapper.dtoToModel(any(OrdenPagoUpdateRequestDto.class), anyString()))
                                .thenReturn(mockOrdenPagoModel());
                when(ordenPagoService.udpate(any(OrdenDePagoModel.class)))
                                .thenReturn(new OrdenDePagoModel());
                when(ordenPagoMapper.modelToDto(any(OrdenDePagoModel.class)))
                                .thenReturn(ordenPagoUpdateRequestDto);

                // Act
                ResponseEntity<CommonResponse> response = ordenDePagoController.update(ordenPagoUpdateRequestDto, USER);

                // Assert
                assertNotNull(response);
                assertEquals(200, response.getStatusCode().value());
                assertNotNull(response.getBody());
        }

        public OrdenDePagoModel mockOrdenPagoModel() {
                OrdenDePagoModel ordenPago = new OrdenDePagoModel();

                // Testing id
                ordenPago.setId(1);
                // Testing entidadId
                EntidadModel entidad = new EntidadModel();
                entidad.setId(100);
                ordenPago.setEntidad(entidad);

                // Testing documentoId
                ordenPago.setDocumentoId(200);
                // Testing escalaId
                ordenPago.setEscalaId(300);

                // Testing rucAgente
                ordenPago.setRucAgente("12345678901");

                // Testing estadoOrdenPago
                ordenPago.setEstadoOrdenPago("pagado");

                // Testing fechaOrdenPago
                LocalDateTime now = LocalDateTime.now();
                ordenPago.setFechaCreacionOrdenPago(now);

                // Testing fechaVencimientoOrdenPago
                LocalDateTime vencimiento = LocalDateTime.now().plusDays(30);
                ordenPago.setFechaVencimientoOrdenPago(vencimiento);

                // Testing fechaPagado
                LocalDateTime fechaPagado = LocalDateTime.now().minusDays(10);
                ordenPago.setFechaPagado(fechaPagado);

                // Testing fechaAnulacionCpb
                LocalDateTime anulacion = LocalDateTime.now().minusDays(5);
                ordenPago.setFechaAnulacionCpb(anulacion);

                // Testing fechaExtornoOrdenPago
                LocalDateTime extorno = LocalDateTime.now().minusDays(2);
                ordenPago.setFechaExtornoOrdenPago(extorno);

                // Testing fechaReasignacionOrdenPago
                LocalDateTime reasignacion = LocalDateTime.now().minusDays(1);
                ordenPago.setFechaReasignacionOrdenPago(reasignacion);

                // Testing codAutorizadorReasignacion
                ordenPago.setCodAutorizadorReasignacion("auth123");

                // Testing motivoAutorizacionReasignacion
                ordenPago.setMotivoAutorizacionReasignacion("Motivo de reasignación");

                // Testing sustentoReasignacionFilenetGuid
                ordenPago.setSustentoReasignacionFilenetGuid("guid123");

                // Testing pdfCpbFilenetGuid
                ordenPago.setPdfCpbFilenetGuid("pdfguid123");

                // Testing fechaGuardadoPdfCpb
                LocalDateTime guardadoPdf = LocalDateTime.now();
                ordenPago.setFechaGuardadoPdfCpb(guardadoPdf);

                // Testing tramiteId
                TramiteModel tramite = new TramiteModel();
                tramite.setTramiteId(400);
                ordenPago.setTramite(tramite);

                // Testing gpTupa
                ordenPago.setGpTupa("tupa");

                // Testing gpFormato
                ordenPago.setGpFormato("formato");

                // Testing gpMonto
                ordenPago.setGpMonto("1000");

                // Testing gpProcedimiento
                ordenPago.setGpProcedimiento("procedimiento");

                // Testing gpMonedaSigno
                ordenPago.setGpMonedaSigno("signo");

                // Testing gpEtiquetaTasa
                ordenPago.setGpEtiquetaTasa("etiqueta");

                // Testing gpProcedimientoTasaVersion
                ordenPago.setGpProcedimientoTasaVersion("version");

                // Testing gpDescProcedimiento
                ordenPago.setGpDescProcedimiento("descripcion");

                // Testing gpSecuencia
                ordenPago.setGpSecuencia("secuencia");

                // Testing ppFechaRespuestaCreacionCpb
                LocalDateTime ppFecha = LocalDateTime.now();
                ordenPago.setPpFechaRespuestaCreacionCpb(ppFecha);

                // Testing ppIdOrdenPagoInterna
                ordenPago.setPpIdOrdenPagoInterna(500);

                // Testing ppCodOrdenPago
                ordenPago.setPpCodOrdenPago("codigo123");
                assertEquals("codigo123", ordenPago.getPpCodOrdenPago());

                // Testing ppCpb
                ordenPago.setPpCpb("cpb");

                // Testing ppMonto
                ordenPago.setPpMonto(2000.0);

                // Testing ppEstadoCpbTexto
                ordenPago.setPpEstadoCpbTexto("estado");

                // Testing ppCodigoRechazoSinConexion
                ordenPago.setPpCodigoRechazoSinConexion("rechazo123");

                // Testing ppDescCortaError
                ordenPago.setPpDescCortaError("error corto");

                // Testing ppMensajeRechazoSinConexion
                ordenPago.setPpMensajeRechazoSinConexion("mensaje rechazo");

                return ordenPago;
        }
}
