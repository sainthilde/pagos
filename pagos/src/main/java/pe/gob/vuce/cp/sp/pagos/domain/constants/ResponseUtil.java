package pe.gob.vuce.cp.sp.pagos.domain.constants;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.GenericResponseDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.ResponseMessageDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.ResponseMetaDataDto;

public class ResponseUtil {
    private ResponseUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static ResponseMetaDataDto createResponseMetaDataDto() {
        return new ResponseMetaDataDto(Constants.RESULTADO_OK, List.of(), 1, 1, null);
    }

    public static GenericResponseDto<Object> createApiResponseVuceCP2Exception(String code, String message,
            List<String> parametros) {
        ResponseMessageDto mensaje = new ResponseMessageDto(code, Constants.RESULTADO_ERROR_CODE, message, parametros);
        ResponseMetaDataDto meta = new ResponseMetaDataDto();
        meta.setResult(Constants.RESULTADO_ERROR);
        meta.setMensajes(List.of(mensaje));
        meta.setCantidadRegistros(0);
        meta.setCantidadRegistrosTotal(0);
        meta.setAtributos(Map.of());

        GenericResponseDto<Object> response = new GenericResponseDto<>();
        response.setMeta(meta);
        response.setData(Collections.emptyList());
        return response;
    }

}
