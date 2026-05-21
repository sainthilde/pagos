package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.EntidadModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.OrdenDePagoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.TramiteModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.UpdateOrdenPagoUseCase;

@SpringBootTest
public class OrdenPagoServiceTest {

    @Mock
    private UpdateOrdenPagoUseCase updateOrdenPagoUseCase;

    @InjectMocks
    private OrdenPagoService ordenPagoService;

    @Test
    void testCreateDeclaracionJurada() {

        // Simular comportamiento del caso de uso
        when(updateOrdenPagoUseCase.update(any(OrdenDePagoModel.class))).thenReturn(mockOrdenPagoModel());

        // Llamar al método a probar
        OrdenDePagoModel result = ordenPagoService.udpate(mockOrdenPagoModel());

        // Verificar resultados
        assertNotNull(result);
    }

    public OrdenDePagoModel mockOrdenPagoModel() {
        OrdenDePagoModel ordenPago = new OrdenDePagoModel();

        EntidadModel entidad = new EntidadModel();
        entidad.setId(100);
        // Testing id
        ordenPago.setId(1);
        // Testing entidadId
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

        TramiteModel tramite = new TramiteModel();
        tramite.setTramiteId(400);
        // Testing tramiteId
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
