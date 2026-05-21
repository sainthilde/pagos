package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DocumentoModelTest {

    @Test
    void testGettersAndSetters() {
        CreateDocumentoModel model = new CreateDocumentoModel();

        // Testing bytes
        model.setBytes("sampleBytes");
        assertEquals("sampleBytes", model.getBytes());

        // Testing nombre
        model.setNombre("documentName");
        assertEquals("documentName", model.getNombre());

        // Testing contentType
        model.setContentType("application/pdf");
        assertEquals("application/pdf", model.getContentType());
    }

}
