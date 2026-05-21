package pe.gob.vuce.cp.sp.pagos.domain.constants;

import org.junit.jupiter.api.Test;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.SeguimientoRequestDto;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;


 class SeguimientoUtilsTest {

    @Test
    void constructorShouldThrowException() throws Exception {
        Constructor<SeguimientoUtils> constructor = SeguimientoUtils.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        InvocationTargetException exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
        assertInstanceOf(IllegalStateException.class, exception.getCause());
        assertEquals("SeguimientoUtils class", exception.getCause().getMessage());
    }

    @Test
    void generarRequestSeguimientoShouldPopulateFieldsCorrectly() {
        Integer escalaId = 1001;
        Integer tipoSeguimiento = 41;
        String indicadorES = "S";
        String ruc = "12345678901";
        String acronimo = "TP";
        String comentario = "Observación de prueba";

        SeguimientoRequestDto result = SeguimientoUtils.generarRequestSeguimiento(
                escalaId, tipoSeguimiento, indicadorES, ruc, acronimo, comentario
        );

        assertEquals(escalaId, result.getEscalaId());
        assertEquals(tipoSeguimiento, result.getTipoSegId());
        assertEquals(indicadorES, result.getIndicadorEs());
        assertEquals(ruc, result.getRucUsuario());
        assertEquals(acronimo, result.getAcronimoDocumento());
        assertEquals(comentario, result.getComentario());
        assertEquals("S", result.getEstado());
        assertNull(result.getIndNil());
    }
}
