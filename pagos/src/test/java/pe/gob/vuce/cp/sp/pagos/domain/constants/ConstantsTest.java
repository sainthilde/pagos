package pe.gob.vuce.cp.sp.pagos.domain.constants;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

 class ConstantsTest {

    @Test
    void constructorShouldThrowException() throws Exception {
        Constructor<Constants> constructor = Constants.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        InvocationTargetException exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
        assertInstanceOf(IllegalStateException.class, exception.getCause());
        assertEquals("Constantes class", exception.getCause().getMessage());
    }

    @Test
    void indicadorShouldReturnSalidaNaveWhenTipoIs93() {
        assertEquals(Constants.SALIDA_NAVE, Constants.indicador(93));
    }

    @Test
    void indicadorShouldReturnSalidaNaveWhenTipoIs64() {
        assertEquals(Constants.SALIDA_NAVE, Constants.indicador(64));
    }

    @Test
    void indicadorShouldReturnEntradaNaveForOtherTypes() {
        assertEquals(Constants.ENTRADA_NAVE, Constants.indicador(65));
        assertEquals(Constants.ENTRADA_NAVE, Constants.indicador(1));
    }

    @Test
    void tipoDocumentoShouldReturnEnumNameWhenCodeExists() {
        assertEquals("DMS", Constants.tipoDocumento(81));
        assertEquals("SPS", Constants.tipoDocumento(93));
    }

    @Test
    void tipoDocumentoShouldReturnEmptyStringWhenCodeDoesNotExist() {
        assertEquals("", Constants.tipoDocumento(999));
    }

    @Test
    void tipoDocumentoShouldReturnEmptyStringWhenCodeIsNull() {
        assertEquals("", Constants.tipoDocumento(null));
    }

    @Test
    void getDescripcionShouldReturnCorrectEnumName() {
        assertEquals("AE", Constants.TipoDocumento.getDescripcion(90));
        assertEquals("DGA", Constants.TipoDocumento.getDescripcion(63));
    }

    @Test
    void getDescripcionShouldReturnEmptyStringForInvalidCode() {
        assertEquals("", Constants.TipoDocumento.getDescripcion(999));
    }
}
