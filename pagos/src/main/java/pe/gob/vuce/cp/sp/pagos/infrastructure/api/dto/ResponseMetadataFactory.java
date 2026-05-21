package pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto;

import org.springframework.http.HttpStatus;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.response.ResponseMetadata;
import static pe.gob.vuce.cp.sp.pagos.infrastructure.config.ConstantsMetaData.CLASS_CANNOT;
import static pe.gob.vuce.cp.sp.pagos.infrastructure.config.ConstantsMetaData.NOT_FOUND;
import static pe.gob.vuce.cp.sp.pagos.infrastructure.config.ConstantsMetaData.LISTAR;
import static pe.gob.vuce.cp.sp.pagos.infrastructure.config.ConstantsMetaData.LIST_NOT_FOUND;
import static pe.gob.vuce.cp.sp.pagos.infrastructure.config.ConstantsMetaData.SUCCESS_200;

public class ResponseMetadataFactory {

    private ResponseMetadataFactory() {
        throw new UnsupportedOperationException(CLASS_CANNOT);
    }

    public static ResponseMetadata notFoundListarMetadata() {
        return ResponseMetadata.builder()
                .codeInfo(NOT_FOUND)
                .tipoOperacion(LISTAR)
                .mensajeOperacion(LIST_NOT_FOUND)
                .esExitoso(false)
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
    }

    public static ResponseMetadata okListarMetadata(String mensaje) {
        return ResponseMetadata.builder()
                .codeInfo(SUCCESS_200)
                .tipoOperacion(LISTAR)
                .mensajeOperacion(mensaje)
                .esExitoso(true)
                .httpStatus(HttpStatus.OK)
                .build();
    }
}
