package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.configuration;

import org.junit.jupiter.api.Test;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.enums.MetaResults;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.ApiResponseMetadata;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.Map;

class ControllerResponseUtilTest {

    static class DummyResponse {
        private ApiResponseMetadata meta;
        private List<String> data;

        public ApiResponseMetadata getMeta() {
            return meta;
        }

        public void setMeta(ApiResponseMetadata meta) {
            this.meta = meta;
        }

        public List<String> getData() {
            return data;
        }

        public void setData(List<String> data) {
            this.data = data;
        }
    }


    @Test
    void buildResponse_shouldSetMetaAndDataCorrectly() {
        // given
        DummyResponse response = new DummyResponse();
        List<String> data = List.of("valor1", "valor2");
        int cantidad = data.size();
        int total = 10;

        // when
        DummyResponse result = ControllerResponseUtil.buildResponse(response, data, cantidad, total);

        // then
        assertThat(result.getData())
                .isNotNull()
                .hasSize(2)
                .containsExactly("valor1", "valor2");

        assertThat(result.getMeta())
                .isNotNull()
                .extracting(ApiResponseMetadata::getCantidadRegistros,
                        ApiResponseMetadata::getCantidadRegistrosTotal,
                        ApiResponseMetadata::getResult,
                        ApiResponseMetadata::getAtributos)
                .containsExactly(cantidad, total, MetaResults.SUCCESS.getValue(), Map.of());
    }

}
