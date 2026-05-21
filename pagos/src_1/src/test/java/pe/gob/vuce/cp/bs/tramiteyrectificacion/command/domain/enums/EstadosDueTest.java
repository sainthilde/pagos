package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class EstadosDueTest {

    @Test
    public void testEstadosDueConstants() {
        // ARRIBO_ANUNCIADO
        EstadosDue estado = EstadosDue.ARRIBO_ANUNCIADO;
        assertEquals(1, estado.getId());
        assertEquals("ARRIBO ANUNCIADO", estado.getDescripcion());

        // ARRIBO_CONFIRMADO
        estado = EstadosDue.ARRIBO_CONFIRMADO;
        assertEquals(2, estado.getId());
        assertEquals("ARRIBO CONFIRMADO", estado.getDescripcion());

        // ARRIBO_AUTORIZADO
        estado = EstadosDue.ARRIBO_AUTORIZADO;
        assertEquals(3, estado.getId());
        assertEquals("ARRIBO AUTORIZADO", estado.getDescripcion());

        // ARRIBADO
        estado = EstadosDue.ARRIBADO;
        assertEquals(4, estado.getId());
        assertEquals("ARRIBADO", estado.getDescripcion());

        // RECEPCIONADO
        estado = EstadosDue.RECEPCIONADO;
        assertEquals(5, estado.getId());
        assertEquals("RECEPCIONADO", estado.getDescripcion());

        // ZARPE_ANUNCIADO
        estado = EstadosDue.ZARPE_ANUNCIADO;
        assertEquals(6, estado.getId());
        assertEquals("ZARPE ANUNCIADO", estado.getDescripcion());

        // ZARPE_CONFIRMADO
        estado = EstadosDue.ZARPE_CONFIRMADO;
        assertEquals(7, estado.getId());
        assertEquals("ZARPE CONFIRMADO", estado.getDescripcion());

        // ZARPE_AUTORIZADO
        estado = EstadosDue.ZARPE_AUTORIZADO;
        assertEquals(8, estado.getId());
        assertEquals("ZARPE AUTORIZADO", estado.getDescripcion());

        // DESPACHADO
        estado = EstadosDue.DESPACHADO;
        assertEquals(9, estado.getId());
        assertEquals("DESPACHADO", estado.getDescripcion());

        // CANCELADO
        estado = EstadosDue.CANCELADO;
        assertEquals(10, estado.getId());
        assertEquals("CANCELADO", estado.getDescripcion());

        // CERRADO
        estado = EstadosDue.CERRADO;
        assertEquals(11, estado.getId());
        assertEquals("CERRADO", estado.getDescripcion());
    }

    @Test
    public void testEnumValuesLength() {
        // Verify that the enum contains 11 constants.
        assertEquals(11, EstadosDue.values().length);
    }
}
