package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrdenDePagoTest {

    @Test
    void testOrdenDePagoSettersAndGetters() {
        // Create a new OrdenDePago
        OrdenDePago ordenDePago = new OrdenDePago();
        ordenDePago.setDocumentoId(123);
        ordenDePago.setEscalaId(456);
        ordenDePago.setRucAgente("12345678901");
        ordenDePago.setEstadoOrdenPago("AP");
        ordenDePago.setFechaCreacionOrdenPago(LocalDateTime.now());
        ordenDePago.setFechaVencimientoOrdenPago(LocalDateTime.now().plusDays(10));
        ordenDePago.setFechaPagado(LocalDateTime.now().minusDays(5));
        ordenDePago.setFechaAnulacionCpb(LocalDateTime.now().minusDays(2));
        ordenDePago.setCodAutorizadorReasignacion("AUTH123");
        ordenDePago.setMotivoAutorizacionReasignacion("Motivo de prueba");
        ordenDePago.setSustentoReasignacionFilenetGuid("GUID123");
        ordenDePago.setPdfCpbFilenetGuid("PDFGUID123");
        ordenDePago.setFechaGuardadoPdfCpb(LocalDateTime.now().minusDays(1));
        ordenDePago.setGpTupa("TUPA123");
        ordenDePago.setGpFormato("FORMATO");
        ordenDePago.setGpMonto(BigDecimal.valueOf(1000.00));
        ordenDePago.setGpProcedimientoId("PROC123");
        ordenDePago.setGpMonedaSigno("USD");
        ordenDePago.setGpEtiquetaTasa("MONTO_EXACTO");
        ordenDePago.setGpProcedimientoTasaVersion("V1");
        ordenDePago.setGpProcedimientoVersion("V1");
        ordenDePago.setGpDescProcedimiento("Descripción del procedimiento");
        ordenDePago.setGpSecuencia("01");
        ordenDePago.setPpFechaRespuestaCreacionCpb(LocalDateTime.now());
        ordenDePago.setPpIdOrdenPagoInterna(789);
        ordenDePago.setPpCodOrdenPago("ORD123");
        ordenDePago.setPpCpb("CPB123");
        ordenDePago.setPpMonto(BigDecimal.valueOf(500.00));
        ordenDePago.setPpFechaConfGeneracionCpb(LocalDateTime.now().minusDays(3));
        ordenDePago.setPpEstadoCpbTexto("ESTADO");
        ordenDePago.setPpCodigorechazoSinConexion("COD123");
        ordenDePago.setPpDescCortaError("Error corto");
        ordenDePago.setPpMensajeRechazoSinConexion("Mensaje de rechazo");
        ordenDePago.setEstado("A");

        // Validate fields using getters
        assertEquals(123, ordenDePago.getDocumentoId(), "Documento ID should match");
        assertEquals(456, ordenDePago.getEscalaId(), "Escala ID should match");
        assertEquals("12345678901", ordenDePago.getRucAgente(), "RUC Agente should match");
        assertEquals("AP", ordenDePago.getEstadoOrdenPago(), "Estado Orden Pago should match");
        assertEquals("AUTH123", ordenDePago.getCodAutorizadorReasignacion(), "Cod Autorizador Reasignacion should match");
        assertEquals("Motivo de prueba", ordenDePago.getMotivoAutorizacionReasignacion(), "Motivo Autorizacion Reasignacion should match");
        assertEquals("GUID123", ordenDePago.getSustentoReasignacionFilenetGuid(), "Sustento Reasignacion Filenet GUID should match");
        assertEquals("PDFGUID123", ordenDePago.getPdfCpbFilenetGuid(), "PDF CPB Filenet GUID should match");
        assertEquals("TUPA123", ordenDePago.getGpTupa(), "GP Tupa should match");
        assertEquals("FORMATO", ordenDePago.getGpFormato(), "GP Formato should match");
        assertEquals(BigDecimal.valueOf(1000.00), ordenDePago.getGpMonto(), "GP Monto should match");
        assertEquals("PROC123", ordenDePago.getGpProcedimientoId(), "GP Procedimiento ID should match");
        assertEquals("USD", ordenDePago.getGpMonedaSigno(), "GP Moneda Signo should match");
        assertEquals("MONTO_EXACTO", ordenDePago.getGpEtiquetaTasa(), "GP Etiqueta Tasa should match");
        assertEquals("V1", ordenDePago.getGpProcedimientoTasaVersion(), "GP Procedimiento Tasa Version should match");
        assertEquals("V1", ordenDePago.getGpProcedimientoVersion(), "GP Procedimiento Version should match");
        assertEquals("Descripción del procedimiento", ordenDePago.getGpDescProcedimiento(), "GP Desc Procedimiento should match");
        assertEquals("01", ordenDePago.getGpSecuencia(), "GP Secuencia should match");
        assertEquals(789, ordenDePago.getPpIdOrdenPagoInterna(), "PP ID Orden Pago Interna should match");
        assertEquals("ORD123", ordenDePago.getPpCodOrdenPago(), "PP Cod Orden Pago should match");
        assertEquals("CPB123", ordenDePago.getPpCpb(), "PP CPB should match");
        assertEquals(BigDecimal.valueOf(500.00), ordenDePago.getPpMonto(), "PP Monto should match");
        assertEquals("ESTADO", ordenDePago.getPpEstadoCpbTexto(), "PP Estado CPB Texto should match");
        assertEquals("COD123", ordenDePago.getPpCodigorechazoSinConexion(), "PP Codigo Rechazo Sin Conexion should match");
        assertEquals("Error corto", ordenDePago.getPpDescCortaError(), "PP Desc Corta Error should match");
        assertEquals("Mensaje de rechazo", ordenDePago.getPpMensajeRechazoSinConexion(), "PP Mensaje Rechazo Sin Conexion should match");
        assertEquals("A", ordenDePago.getEstado(), "Estado should match");
    }

}
