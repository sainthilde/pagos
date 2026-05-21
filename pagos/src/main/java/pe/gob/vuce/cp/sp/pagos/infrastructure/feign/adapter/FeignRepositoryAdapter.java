package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.adapter;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.FeignRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.DocumentClient;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.OrdenPagoSunatClient;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.OrdenPagoRequestDTO;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ArchivoResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.DocumentRequestDTO;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.PaymentMethodResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.response.OrdenPagoResponseDTO;

import java.util.List;

@AllArgsConstructor
@Component
public class FeignRepositoryAdapter implements FeignRepositoryPort {

    private final DocumentClient documentClient;
    private final OrdenPagoSunatClient ordenPagoSunatClient;

    @Override
    public OrdenPagoResponseDTO createOrdenPago(OrdenPagoRequestDTO ordenPagoRequestDto) {
        return ordenPagoSunatClient.createOrdenPago(ordenPagoRequestDto);
    }

    @Override
    public ArchivoResponse getArchivo(Integer ordenPagoId) {
        return ordenPagoSunatClient.getArchivo(ordenPagoId);
    }

    @Override
    public String postFile(DocumentRequestDTO documentRequestDTO) {
        return documentClient.postFile(documentRequestDTO);
    }

    @Override
    public Resource obtenerFile(String ecmDocumentoId) {
        return documentClient.obtenerFile(ecmDocumentoId);
    }

    @Override
    @Cacheable(value = "FormasPagoCache", key = "#canalId + '-' + #entidadId")
    public List<PaymentMethodResponse> getPaymentMethods(Integer canalId, Integer entidadId) {
        return ordenPagoSunatClient.getPaymentMethods(canalId, entidadId);
    }
}
