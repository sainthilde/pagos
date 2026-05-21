package pe.gob.vuce.cp.sp.pagos.application.usecase;

import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.domain.model.OrdenPago;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.UpdateArchivoPDFUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.FeignRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.ComunesQueryClient;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.*;
import pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.entity.ListMaestroDto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.*;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagosSunat.DATA;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagosSunat.ECM_DOC;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagosSunat.ATZONE;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagosSunat.CP2;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagosSunat.COMPONENT;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagosSunat.ESCALA;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagosSunat.OPTTION;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagosSunat.FOLDER_EXTRAS;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagosSunat.ENTRADA2;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagosSunat.ADJUNTO_ID;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagosSunat.ADJUNTO_TIPO;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagosSunat.PROPIEDADES;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagosSunat.APP_PDF;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagosSunat.VOUCHER;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagosSunat.PDF;

/**
 * Implementación del caso de uso para crear órdenes de pago.
 * Esta clase se encarga de la lógica necesaria para crear
 * una nueva OrdenPago y persistirla en el repositorio correspondiente.
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Component
@AllArgsConstructor
public class UpdateArchivoPDFUseCaseImpl implements UpdateArchivoPDFUseCase {

    private final FeignRepositoryPort feignRepositoryPort;
    private final ComunesQueryClient comunesQueryClient;

    @Override
    public OrdenPago updateArchivoPDF(Integer ordenPagoVuce, OrdenPago ordenPago) {
        ArchivoResponse archivoResponse = feignRepositoryPort.getArchivo(ordenPagoVuce);
        ResponseEntity<MasterResponse<List<ListMaestroDto>>> comunesQueryResponse = comunesQueryClient.getAllcodeMaster(PARAMETROS_GENERALES);
        List<ListMaestroDto> lista = comunesQueryResponse.getBody() != null ? comunesQueryResponse.getBody().getData() : null;
        String descripcionComponente = lista.stream()
                .filter(data -> COMPONENTE_FILENET.equals(data.getCodigo()))
                .findFirst()
                .map(ListMaestroDto::getDescripcion)
                .orElse(null); // o un valor por defecto si no se encuentra

        DocumentRequestDTO documentRequestDTO = getDocumentRequestDTO(ordenPago.getOrdenPagoId(), archivoResponse,descripcionComponente);
        String resultEcmStorage = feignRepositoryPort.postFile(documentRequestDTO);
        JSONObject jsonObject = new JSONObject(resultEcmStorage);
        if(jsonObject.has(DATA) && ((JSONObject) jsonObject.get(DATA)).has(ECM_DOC)) {
            String filenetGui = ((JSONObject) jsonObject.get(DATA)).get(ECM_DOC).toString();
            ordenPago.setFilenetGuid(filenetGui);
            ordenPago.setFechaGuardadoPdfCpb(LocalDateTime.now().atZone(ZoneId.of(ATZONE)).toInstant());
        }
        return ordenPago;
    }

    /**
     * Crea un {@code DocumentRequestDTO} para almacenar un archivo con los datos proporcionados.
     *
     * @param ordenPagoId     Identificador de la orden de pago.
     * @param archivoResponse Contenido del archivo a almacenar.
     * @return El objeto {@code DocumentRequestDTO} configurado.
     */
    public static DocumentRequestDTO getDocumentRequestDTO(Integer ordenPagoId, ArchivoResponse archivoResponse,String componenteFilenet) {
        Map<String,Object> mapaRequest = new HashMap<>();
        mapaRequest.put(COMPONENT,componenteFilenet);
        mapaRequest.put(OPTTION,ESCALA);
        mapaRequest.put(FOLDER_EXTRAS,ENTRADA2);

        Map<String,String> propiedades = new HashMap<>();
        propiedades.put(ADJUNTO_ID, String.valueOf(ordenPagoId));
        propiedades.put(ADJUNTO_TIPO,APP_PDF);
        mapaRequest.put(PROPIEDADES,propiedades);

        DocumentRequestDTO documentRequestDTO = new DocumentRequestDTO();
        documentRequestDTO.setNombre(VOUCHER + String.valueOf(ordenPagoId) + PDF);
        documentRequestDTO.setFile(archivoResponse.getContenido());
        documentRequestDTO.setData(mapaRequest);
        return documentRequestDTO;
    }
}
