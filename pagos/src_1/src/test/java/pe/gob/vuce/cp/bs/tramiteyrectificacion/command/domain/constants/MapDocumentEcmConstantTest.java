package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MapDocumentEcmConstantTest {

    @Test
    void testConstantValues() {
        assertEquals("componente", MapDocumentEcmConstant.KEY_COMPONENTE);
        assertEquals("CP2", MapDocumentEcmConstant.VALUE_COMPONENTE);
        assertEquals("opcion", MapDocumentEcmConstant.KEY_OPCION);
        assertEquals("ficha_tecnica", MapDocumentEcmConstant.VALUE_OPCION);
        assertEquals("foldersExtras", MapDocumentEcmConstant.KEY_FOLDER_EXTRAS);
        assertEquals("adjunto_id", MapDocumentEcmConstant.KEY_ADJUNTO_ID);
        assertEquals("123", MapDocumentEcmConstant.VALUE_ADJUNTO_ID);
        assertEquals("adjunto_tipo", MapDocumentEcmConstant.KEY_ADJUNTO_TIPO);
        assertEquals("propiedades", MapDocumentEcmConstant.KEY_PROPIEDADES);
        assertEquals("ecmDocumentoId", MapDocumentEcmConstant.KEY_ECM_DOCUMENTO_ID);
        assertEquals("TRAMITE", MapDocumentEcmConstant.PATH_TRAMITE);
    }

    @Test
    void testConstructor() {
        // Verifica que no se pueda instanciar la clase
        assertThrows(InvocationTargetException.class, () -> {
            // Llamamos al constructor privado a través de reflexión
            java.lang.reflect.Constructor<MapDocumentEcmConstant> constructor = MapDocumentEcmConstant.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }

}
