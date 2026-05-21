package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.configuration;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.enums.MetaResults;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.api.advices.exception.ResponseBuildException;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.ApiResponseMetadata;

import java.util.List;
import java.util.Map;

public class ControllerResponseUtil {

    private ControllerResponseUtil() {}

    @SuppressWarnings("unchecked")
    public static <R, D> R buildResponse(
            R response, List<D> data, int cantidad, int total) {

        try {
            // Buscar setMeta
            var setMetaMethod = response.getClass().getMethod("setMeta", ApiResponseMetadata.class);
            var setDataMethod = response.getClass().getMethod("setData", List.class);

            ApiResponseMetadata meta = new ApiResponseMetadata();
            meta.setResult(MetaResults.SUCCESS.getValue());
            meta.setAtributos(Map.of());
            meta.setCantidadRegistros(cantidad);
            meta.setCantidadRegistrosTotal(total);

            setDataMethod.invoke(response, data);
            setMetaMethod.invoke(response, meta);

        } catch (Exception e) {
            throw new ResponseBuildException("Error asignando meta y data", e);
        }
        return response;
    }



}
