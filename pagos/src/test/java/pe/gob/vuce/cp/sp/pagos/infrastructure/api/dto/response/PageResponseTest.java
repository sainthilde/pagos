package pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.response;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

 class PageResponseTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        List<String> content = List.of("Item1", "Item2");
        int paginaActual = 1;
        int totalPaginas = 3;
        long totalElementos = 6;
        int size = 2;

        PageResponse<String> response = new PageResponse<>(content, paginaActual, totalPaginas, totalElementos, size);

        assertEquals(content, response.getContent());
        assertEquals(paginaActual, response.getPaginaActual());
        assertEquals(totalPaginas, response.getTotalPaginas());
        assertEquals(totalElementos, response.getTotalElementos());
        assertEquals(size, response.getSize());
    }

    @Test
    void testFromStaticMethod() {
        @SuppressWarnings("unchecked")
        Page<String> mockPage = mock(Page.class);

        List<String> mockContent = List.of("A", "B", "C");

        when(mockPage.getContent()).thenReturn(mockContent);
        when(mockPage.getNumber()).thenReturn(2);
        when(mockPage.getTotalPages()).thenReturn(5);
        when(mockPage.getTotalElements()).thenReturn(15L);
        when(mockPage.getSize()).thenReturn(3);

        PageResponse<String> result = PageResponse.from(mockPage);

        assertEquals(mockContent, result.getContent());
        assertEquals(2, result.getPaginaActual());
        assertEquals(5, result.getTotalPaginas());
        assertEquals(15L, result.getTotalElementos());
        assertEquals(3, result.getSize());
    }
}

