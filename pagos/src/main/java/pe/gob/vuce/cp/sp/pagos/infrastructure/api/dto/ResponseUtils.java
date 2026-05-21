package pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto;


import org.springframework.http.ResponseEntity;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.response.ApiResponseMeta;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.response.ApiResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.response.ResponseMetadata;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.response.Mensaje;
import static pe.gob.vuce.cp.sp.pagos.infrastructure.config.ConstantsMetaData.SUCCESS;
import static pe.gob.vuce.cp.sp.pagos.infrastructure.config.ConstantsMetaData.ERROR;
import static pe.gob.vuce.cp.sp.pagos.infrastructure.config.ConstantsMetaData.CLASS_CANNOT;

public class ResponseUtils {

    private ResponseUtils() {
        throw new UnsupportedOperationException(CLASS_CANNOT);
    }

    public static ResponseEntity<ApiResponse> buildResponse(Object data, ResponseMetadata metadata) {
        ApiResponse response = new ApiResponse();
        ApiResponseMeta meta = new ApiResponseMeta();

        meta.setResult(metadata.isEsExitoso() ? SUCCESS : ERROR);
        meta.setMensajes(construirMensajes(
                metadata.getCodeInfo(),
                metadata.getTipoOperacion(),
                metadata.getMensajeOperacion(),
                metadata.getErroresAdicionales()));
        meta.setAtributos(construirAtributos(metadata.getAtributos()));
        setCantidadRegistros(meta, data);

        response.setMeta(meta);
        response.setData(data);
        return new ResponseEntity<>(response, metadata.getHttpStatus());
    }

    private static void setCantidadRegistros(ApiResponseMeta meta, Object data) {
        if (data instanceof List<?> dataList) {
            meta.setCantidadRegistros(dataList.size());
            meta.setCantidadRegistrosTotal(dataList.size());
        } else {
            int cantidad = data != null ? 1 : 0;
            meta.setCantidadRegistros(cantidad);
            meta.setCantidadRegistrosTotal(cantidad);
        }
    }

    private static List<Mensaje> construirMensajes(String codeInfo, String tipoOperacion, String mensajeOperacion, List<String> errores) {
        if (errores != null && !errores.isEmpty()) {
            return errores.stream()
                    .map(error -> new Mensaje(codeInfo, tipoOperacion, error))
                    .toList();
        }
        return Collections.singletonList(new Mensaje(codeInfo, tipoOperacion, mensajeOperacion));
    }

    private static Map<String, Object> construirAtributos(Map<String, Object> atributos) {
        return atributos != null ? atributos : Collections.emptyMap();
    }
}
