package pe.gob.vuce.cp.sp.pagos.domain.port.in;

import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.domain.model.OrdenPago;

@Component
public interface UpdateArchivoPDFUseCase {
    OrdenPago updateArchivoPDF(Integer ordenPagoVuce, OrdenPago ordenPago);
}
