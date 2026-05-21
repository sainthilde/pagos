package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class DeclaracionJuradaModelTest {

    @Test
    void testGettersAndSetters() {
        DeclaracionJuradaModel model = new DeclaracionJuradaModel();

        // Testing id
        model.setDeclaracionJuradaId(1);
        assertEquals(1, model.getDeclaracionJuradaId());

        // Testing estadoDdjjPago
        model.setEstadoDdjjPago("P");
        assertEquals("P", model.getEstadoDdjjPago());

        // Testing numeroDdjj
        model.setNumeroDdjj("12345");
        assertEquals("12345", model.getNumeroDdjj());

        // Testing fechaSolicitudDdjj
        LocalDateTime now = LocalDateTime.now();
        model.setFechaSolicitudDdjj(now);
        assertEquals(now, model.getFechaSolicitudDdjj());

        // Testing rucAgente
        model.setRucAgente("ABC123456");
        assertEquals("ABC123456", model.getRucAgente());

        // Testing DocumentoModel
        DocumentoModel documento = new DocumentoModel();
        documento.setDocumentoId(1);
        model.setDocumento(documento);
        // Instead of comparing to 1001, we compare the documento's ID
        assertEquals(1, model.getDocumento().getDocumentoId());

        // Testing escalaId
        model.setEscalaId(2);
        assertEquals(2, model.getEscalaId());

        // Testing motivoDeclaracion
        model.setMotivoDeclaracion("Reason for declaration");
        assertEquals("Reason for declaration", model.getMotivoDeclaracion());

        // Testing mensajeError
        model.setMensajeError("No error");
        assertEquals("No error", model.getMensajeError());

        // Testing TramiteModel
        TramiteModel tramite = new TramiteModel();
        tramite.setTramiteId(2001);
        model.setTramite(tramite);
        // Instead of comparing the whole object to an int, compare the tramite's ID
        assertEquals(2001, model.getTramite().getTramiteId());
    }
}
