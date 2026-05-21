package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class OrdenPagoModelTest {

    @Test
    void testGettersAndSetters() {
        OrdenDePagoModel ordenPago = new OrdenDePagoModel();

        // Testing id
        ordenPago.setId(1);
        assertEquals(1, ordenPago.getId());

        EntidadModel entidad = new EntidadModel();
        entidad.setId(100);
        // Testing entidadId
        ordenPago.setEntidad(entidad);
        assertEquals(100, ordenPago.getEntidad().getId());

        // Testing documentoId
        ordenPago.setDocumentoId(200);
        assertEquals(200, ordenPago.getDocumentoId());

        // Testing escalaId
        ordenPago.setEscalaId(300);
        assertEquals(300, ordenPago.getEscalaId());

        // Testing rucAgente
        ordenPago.setRucAgente("12345678901");
        assertEquals("12345678901", ordenPago.getRucAgente());

        // Testing estadoOrdenPago
        ordenPago.setEstadoOrdenPago("pagado");
        assertEquals("pagado", ordenPago.getEstadoOrdenPago());

        // Testing fechaOrdenPago
        LocalDateTime now = LocalDateTime.now();
        ordenPago.setFechaCreacionOrdenPago(now);
        assertEquals(now, ordenPago.getFechaCreacionOrdenPago());

        // Testing fechaVencimientoOrdenPago
        LocalDateTime vencimiento = LocalDateTime.now().plusDays(30);
        ordenPago.setFechaVencimientoOrdenPago(vencimiento);
        assertEquals(vencimiento, ordenPago.getFechaVencimientoOrdenPago());

        // Testing fechaPagado
        LocalDateTime fechaPagado = LocalDateTime.now().minusDays(10);
        ordenPago.setFechaPagado(fechaPagado);
        assertEquals(fechaPagado, ordenPago.getFechaPagado());

        // Testing fechaAnulacionCpb
        LocalDateTime anulacion = LocalDateTime.now().minusDays(5);
        ordenPago.setFechaAnulacionCpb(anulacion);
        assertEquals(anulacion, ordenPago.getFechaAnulacionCpb());

        // Testing fechaExtornoOrdenPago
        LocalDateTime extorno = LocalDateTime.now().minusDays(2);
        ordenPago.setFechaExtornoOrdenPago(extorno);
        assertEquals(extorno, ordenPago.getFechaExtornoOrdenPago());

        // Testing fechaReasignacionOrdenPago
        LocalDateTime reasignacion = LocalDateTime.now().minusDays(1);
        ordenPago.setFechaReasignacionOrdenPago(reasignacion);
        assertEquals(reasignacion, ordenPago.getFechaReasignacionOrdenPago());

        // Testing codAutorizadorReasignacion
        ordenPago.setCodAutorizadorReasignacion("auth123");
        assertEquals("auth123", ordenPago.getCodAutorizadorReasignacion());

        // Testing motivoAutorizacionReasignacion
        ordenPago.setMotivoAutorizacionReasignacion("Motivo de reasignación");
        assertEquals("Motivo de reasignación", ordenPago.getMotivoAutorizacionReasignacion());

        // Testing sustentoReasignacionFilenetGuid
        ordenPago.setSustentoReasignacionFilenetGuid("guid123");
        assertEquals("guid123", ordenPago.getSustentoReasignacionFilenetGuid());

        // Testing pdfCpbFilenetGuid
        ordenPago.setPdfCpbFilenetGuid("pdfguid123");
        assertEquals("pdfguid123", ordenPago.getPdfCpbFilenetGuid());

        // Testing fechaGuardadoPdfCpb
        LocalDateTime guardadoPdf = LocalDateTime.now();
        ordenPago.setFechaGuardadoPdfCpb(guardadoPdf);
        assertEquals(guardadoPdf, ordenPago.getFechaGuardadoPdfCpb());

        // Testing tramiteId
        TramiteModel tramite = new TramiteModel();
        tramite.setTramiteId(400);
        // Set the tramite model before asserting its id.
        ordenPago.setTramite(tramite);
        assertEquals(400, ordenPago.getTramite().getTramiteId());

        // Testing gpTupa
        ordenPago.setGpTupa("tupa");
        assertEquals("tupa", ordenPago.getGpTupa());

        // Testing gpFormato
        ordenPago.setGpFormato("formato");
        assertEquals("formato", ordenPago.getGpFormato());

        // Testing gpMonto
        ordenPago.setGpMonto("1000");
        assertEquals("1000", ordenPago.getGpMonto());

        // Testing gpProcedimiento
        ordenPago.setGpProcedimiento("procedimiento");
        assertEquals("procedimiento", ordenPago.getGpProcedimiento());

        // Testing gpMonedaSigno
        ordenPago.setGpMonedaSigno("signo");
        assertEquals("signo", ordenPago.getGpMonedaSigno());

        // Testing gpEtiquetaTasa
        ordenPago.setGpEtiquetaTasa("etiqueta");
        assertEquals("etiqueta", ordenPago.getGpEtiquetaTasa());

        // Testing gpProcedimientoTasaVersion
        ordenPago.setGpProcedimientoTasaVersion("version");
        assertEquals("version", ordenPago.getGpProcedimientoTasaVersion());

        // Testing gpDescProcedimiento
        ordenPago.setGpDescProcedimiento("descripcion");
        assertEquals("descripcion", ordenPago.getGpDescProcedimiento());

        // Testing gpSecuencia
        ordenPago.setGpSecuencia("secuencia");
        assertEquals("secuencia", ordenPago.getGpSecuencia());

        // Testing ppFechaRespuestaCreacionCpb
        LocalDateTime ppFecha = LocalDateTime.now();
        ordenPago.setPpFechaRespuestaCreacionCpb(ppFecha);
        assertEquals(ppFecha, ordenPago.getPpFechaRespuestaCreacionCpb());

        // Testing ppIdOrdenPagoInterna
        ordenPago.setPpIdOrdenPagoInterna(500);
        assertEquals(500, ordenPago.getPpIdOrdenPagoInterna());

        // Testing ppCodOrdenPago
        ordenPago.setPpCodOrdenPago("codigo123");
        assertEquals("codigo123", ordenPago.getPpCodOrdenPago());

        // Testing ppCpb
        ordenPago.setPpCpb("cpb");
        assertEquals("cpb", ordenPago.getPpCpb());

        // Testing ppMonto
        ordenPago.setPpMonto(2000.0);
        assertEquals(2000.0, ordenPago.getPpMonto());

        // Testing ppFechaConfGeneracionCpb
        LocalDateTime ppFechaConf = LocalDateTime.now().minusDays(3);
        ordenPago.setPpFechaConfGeneracionCpb(ppFechaConf);
        assertEquals(ppFechaConf, ordenPago.getPpFechaConfGeneracionCpb());

        // Testing ppEstadoCpbTexto
        ordenPago.setPpEstadoCpbTexto("estado");
        assertEquals("estado", ordenPago.getPpEstadoCpbTexto());

        // Testing ppCodigoRechazoSinConexion
        ordenPago.setPpCodigoRechazoSinConexion("rechazo123");
        assertEquals("rechazo123", ordenPago.getPpCodigoRechazoSinConexion());

        // Testing ppDescCortaError
        ordenPago.setPpDescCortaError("error corto");
        assertEquals("error corto", ordenPago.getPpDescCortaError());

        // Testing ppMensajeRechazoSinConexion
        ordenPago.setPpMensajeRechazoSinConexion("mensaje rechazo");
        assertEquals("mensaje rechazo", ordenPago.getPpMensajeRechazoSinConexion());
    }
}
