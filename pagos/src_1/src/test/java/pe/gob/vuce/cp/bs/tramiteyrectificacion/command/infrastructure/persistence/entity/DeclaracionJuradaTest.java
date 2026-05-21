package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity;

import org.junit.jupiter.api.Test;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity.mae.Documento;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DeclaracionJuradaTest {

    @Test
    void testOrdenDePagoSettersAndGetters() {
        // Create a new OrdenDePago
        DeclaracionJurada declaracionJurada = new DeclaracionJurada();
        declaracionJurada.setDeclaracionJuradaId(123);
        declaracionJurada.setEscalaId(456);
        declaracionJurada.setRucAgente("12345678901");
        declaracionJurada.setNumeroDdjj("12345678901");
        declaracionJurada.setEstadoDdjjPago("AP");
        Documento documento = new Documento();
        documento.setDocumentoId(1);
        declaracionJurada.setDocumento(documento);
        declaracionJurada.setFechaDenegacionDdjj(LocalDateTime.now());
        declaracionJurada.setFechaAprobacionDdjj(LocalDateTime.now().plusDays(10));
        declaracionJurada.setEstado("A");

        // Validate fields using getters
        assertEquals(123, declaracionJurada.getDeclaracionJuradaId(), "Documento ID should match");
        assertEquals(456, declaracionJurada.getEscalaId(), "Escala ID should match");
        assertEquals("12345678901", declaracionJurada.getRucAgente(), "RUC Agente should match");
        assertEquals("AP", declaracionJurada.getEstadoDdjjPago(), "Estado Orden Pago should match");
        assertEquals("12345678901", declaracionJurada.getNumeroDdjj(), "Estado Orden Pago should match");
        assertNotNull( declaracionJurada.getDocumento(), "Cod Autorizador Reasignacion should match");
        assertNotNull( declaracionJurada.getFechaDenegacionDdjj(), "Cod Autorizador Reasignacion should match");
        assertNotNull( declaracionJurada.getFechaAprobacionDdjj(), "Cod Autorizador Reasignacion should match");
        assertEquals("A", declaracionJurada.getEstado(), "Estado should match");
    }

}
