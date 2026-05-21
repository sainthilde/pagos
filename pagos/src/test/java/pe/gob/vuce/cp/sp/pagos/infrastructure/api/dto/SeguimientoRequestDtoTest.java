package pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * Pruebas unitarias para la clase SeguimientoRequestDto.
 *
 * Verifica que los getters y setters funcionen correctamente y que los campos
 * puedan ser establecidos y recuperados apropiadamente.
 */
class SeguimientoRequestDtoTest {

    private SeguimientoRequestDto seguimientoRequestDto;

    @BeforeEach
    void setUp() {
        seguimientoRequestDto = new SeguimientoRequestDto();
    }

    @Test
    void testTipoSegId() {
        // Valor de prueba
        Integer expectedTipoSegId = 1;

        // Establecer y verificar
        seguimientoRequestDto.setTipoSegId(expectedTipoSegId);
        assertEquals(expectedTipoSegId, seguimientoRequestDto.getTipoSegId(),
                "El tipoSegId debe ser igual al valor establecido");
    }

    @Test
    void testRucUsuario() {
        // Valor de prueba
        String expectedRucUsuario = "20567894532";

        // Establecer y verificar
        seguimientoRequestDto.setRucUsuario(expectedRucUsuario);
        assertEquals(expectedRucUsuario, seguimientoRequestDto.getRucUsuario(),
                "El rucUsuario debe ser igual al valor establecido");
    }

    @Test
    void testIndNil() {
        // Valor de prueba
        Boolean expectedIndNil = true;

        // Establecer y verificar
        seguimientoRequestDto.setIndNil(expectedIndNil);
        assertEquals(expectedIndNil, seguimientoRequestDto.getIndNil(),
                "El indNil debe ser igual al valor establecido");
    }

    @Test
    void testEscalaId() {
        // Valor de prueba
        Integer expectedEscalaId = 5;

        // Establecer y verificar
        seguimientoRequestDto.setEscalaId(expectedEscalaId);
        assertEquals(expectedEscalaId, seguimientoRequestDto.getEscalaId(),
                "El escalaId debe ser igual al valor establecido");
    }

    @Test
    void testAcronimoDocumento() {
        // Valor de prueba
        String expectedAcronimoDocumento = "DOC-001";

        // Establecer y verificar
        seguimientoRequestDto.setAcronimoDocumento(expectedAcronimoDocumento);
        assertEquals(expectedAcronimoDocumento, seguimientoRequestDto.getAcronimoDocumento(),
                "El acronimoDocumento debe ser igual al valor establecido");
    }

    @Test
    void testIndicadorEs() {
        // Valor de prueba
        String expectedIndicadorEs = "IND-001";

        // Establecer y verificar
        seguimientoRequestDto.setIndicadorEs(expectedIndicadorEs);
        assertEquals(expectedIndicadorEs, seguimientoRequestDto.getIndicadorEs(),
                "El indicadorEs debe ser igual al valor establecido");
    }

    @Test
    void testComentario() {
        // Valor de prueba
        String expectedComentario = "Este es un comentario de prueba";

        // Establecer y verificar
        seguimientoRequestDto.setComentario(expectedComentario);
        assertEquals(expectedComentario, seguimientoRequestDto.getComentario(),
                "El comentario debe ser igual al valor establecido");
    }

    @Test
    void testEstado() {
        // Valor de prueba
        String expectedEstado = "ACTIVO";

        // Establecer y verificar
        seguimientoRequestDto.setEstado(expectedEstado);
        assertEquals(expectedEstado, seguimientoRequestDto.getEstado(),
                "El estado debe ser igual al valor establecido");
    }

    @Test
    void testAllFields() {
        // Valores de prueba
        Integer expectedTipoSegId = 2;
        String expectedRucUsuario = "20123456789";
        Boolean expectedIndNil = false;
        Integer expectedEscalaId = 3;
        String expectedAcronimoDocumento = "DOC-002";
        String expectedIndicadorEs = "IND-002";
        String expectedComentario = "Comentario completo";
        String expectedEstado = "INACTIVO";

        // Establecer todos los campos
        seguimientoRequestDto.setTipoSegId(expectedTipoSegId);
        seguimientoRequestDto.setRucUsuario(expectedRucUsuario);
        seguimientoRequestDto.setIndNil(expectedIndNil);
        seguimientoRequestDto.setEscalaId(expectedEscalaId);
        seguimientoRequestDto.setAcronimoDocumento(expectedAcronimoDocumento);
        seguimientoRequestDto.setIndicadorEs(expectedIndicadorEs);
        seguimientoRequestDto.setComentario(expectedComentario);
        seguimientoRequestDto.setEstado(expectedEstado);

        // Verificar todos los campos
        assertAll("Verificación de todos los campos",
                () -> assertEquals(expectedTipoSegId, seguimientoRequestDto.getTipoSegId()),
                () -> assertEquals(expectedRucUsuario, seguimientoRequestDto.getRucUsuario()),
                () -> assertEquals(expectedIndNil, seguimientoRequestDto.getIndNil()),
                () -> assertEquals(expectedEscalaId, seguimientoRequestDto.getEscalaId()),
                () -> assertEquals(expectedAcronimoDocumento, seguimientoRequestDto.getAcronimoDocumento()),
                () -> assertEquals(expectedIndicadorEs, seguimientoRequestDto.getIndicadorEs()),
                () -> assertEquals(expectedComentario, seguimientoRequestDto.getComentario()),
                () -> assertEquals(expectedEstado, seguimientoRequestDto.getEstado())
        );
    }
}