package pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PageResponse<T> {

    private List<T> content;
    private int paginaActual;
    private int totalPaginas;
    private long totalElementos;
    private int size;

    public static <T> PageResponse<T> from(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getSize()
        );
    }
}
