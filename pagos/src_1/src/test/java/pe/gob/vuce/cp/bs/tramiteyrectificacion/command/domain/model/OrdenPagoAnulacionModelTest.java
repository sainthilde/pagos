package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class OrdenPagoAnulacionModelTest {

    @Test
    void testGettersAndSetters() {
        OrdenPagoAnulacionModel ordenPago = new OrdenPagoAnulacionModel();

        // Testing id
        ordenPago.setOrdenPagoId(1);
        assertEquals(1, ordenPago.getOrdenPagoId());

        // Testing entidadId
        ordenPago.setCodigoOrdenPago("CO");
        assertEquals("CO", ordenPago.getCodigoOrdenPago());

        // Testing documentoId
        ordenPago.setMonto(200.0);
        assertEquals(200.0, ordenPago.getMonto());

        // Testing escalaId
        ordenPago.setFechaGeneracion("10/10/2024");
        assertEquals("10/10/2024", ordenPago.getFechaGeneracion());

        // Testing rucAgente
        ordenPago.setCpb("12345678901");
        assertEquals("12345678901", ordenPago.getCpb());

        // Testing estadoOrdenPago
        ordenPago.setEstado("S");
        assertEquals("S", ordenPago.getEstado());

        // Testing estadoOrdenPago
        ordenPago.setFecha("10/10/2024");
        assertEquals("10/10/2024", ordenPago.getFecha());

        // Testing estadoOrdenPago
        ordenPago.setTipoDocumentoUsuario("DNI");
        assertEquals("DNI", ordenPago.getTipoDocumentoUsuario());

        ordenPago.setNumeroDocumentoUsuario("44556666");
        assertEquals("44556666", ordenPago.getNumeroDocumentoUsuario());

        // Testing motivoAutorizacionReasignacion
        ordenPago.setTipOper("Motivo de reasignación");
        assertEquals("Motivo de reasignación", ordenPago.getTipOper());

        // Testing sustentoReasignacionFilenetGuid
        ordenPago.setRucOper("12345678");
        assertEquals("12345678", ordenPago.getRucOper());

    }

}
