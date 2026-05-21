package pe.gob.vuce.cp.sp.pagos.domain.port.out;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ArchivoResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.DocumentRequestDTO;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.OrdenPagoRequestDTO;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.PaymentMethodResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.response.OrdenPagoResponseDTO;

import java.util.List;

@Component
public interface FeignRepositoryPort {
    OrdenPagoResponseDTO createOrdenPago(OrdenPagoRequestDTO ordenPagoRequestDto);
    ArchivoResponse getArchivo(Integer ordenPagoId);
    String postFile(DocumentRequestDTO documentRequestDTO);
    Resource obtenerFile(String ecmDocumentoId);
    List<PaymentMethodResponse> getPaymentMethods(Integer canalId, Integer entidadId);
}
