package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import pe.gob.vuce.cp.framework.globallogger.annotation.Loggable;
import pe.gob.vuce.cp.framework.globallogger.constants.LogTypes;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ComunesQueryResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ExcepcionesDueResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ExcepcionesResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.MasterResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.entity.ListMaestroDto;

import java.util.List;

/**
 * Cliente Feign para realizar consultas a la API de Comunes Query.
 * 
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@FeignClient(name = "comunesQueryClient", url = "${feign.client.comunes-query-api.base-url}")
public interface ComunesQueryClient {

        /**
         * Obtiene datos según el código y el atributo especificado.
         *
         * @param entidadId ID de la entidad.
         * @param code      Código para la consulta.
         * @return Respuesta de la consulta con los datos.
         */
        @GetMapping("/master/allByCodeAndAttribute")
        @Loggable(category = LogTypes.FEIGN)
        ComunesQueryResponse getAllByCodeAndAttribute(
                        @RequestParam("entidad_id") Integer entidadId,
                        @RequestParam("code") String code);

        /**
         * Obtiene datos según el código y el atributo especificado.
         *
         * @param codigo      Código para la consulta.
         * @return Respuesta de la consulta con los datos.
         */
        @GetMapping("/master/findByCode")
        @Loggable(category = LogTypes.FEIGN)
        ResponseEntity<MasterResponse<List<ListMaestroDto>>> getAllcodeMaster(
                @RequestParam("codigo") String codigo);

        /**
         * Obtiene datos según el id y la entidad especificado.
         *
         * @param escalaId ID de la Escala.
         * @param entidad  Entidad para la consulta.
         * @return Respuesta de la consulta con los datos.
         */
        @GetMapping("/datos_escala/{escalaId}/detalles/{entidad}")
        @Loggable(category = LogTypes.FEIGN)
        ExcepcionesResponse obtenerExcepcion(
                        @PathVariable("escalaId") Integer escalaId,
                        @PathVariable("entidad") Integer entidad);

        /**
         * Obtiene datos según el id y la entidad especificado.
         *
         * @param escalaId ID de la Escala.
         * @param entidad  Entidad para la consulta.
         * @return Respuesta de la consulta con los datos.
         */
        @GetMapping("/datos_escala/{escalaId}/detalles_zarpe/{entidad}")
        @Loggable(category = LogTypes.FEIGN)
        ExcepcionesDueResponse obtenerExcepcionZarpe(
                        @PathVariable("escalaId") Integer escalaId,
                        @PathVariable("entidad") Integer entidad);

        /**
         * Obtiene datos según el id y la entidad especificado.
         *
         * @param escalaId ID de la Escala.
         * @param entidad  Entidad para la consulta.
         * @return Respuesta de la consulta con los datos.
         */
        @GetMapping("/datos_escala/{escalaId}/detalles_declaracion/{entidad}")
        @Loggable(category = LogTypes.FEIGN)
        ExcepcionesResponse obtenerExcepcionDeclaracion(
                        @PathVariable("escalaId") Integer escalaId,
                        @PathVariable("entidad") Integer entidad);

        /**
         * Obtiene datos según el id y la entidad especificado.
         *
         * @param escalaId ID de la Escala.
         * @param entidad  Entidad para la consulta.
         * @return Respuesta de la consulta con los datos.
         */
        @GetMapping("/datos_escala/{escalaId}/detalles_patente/{entidad}")
        @Loggable(category = LogTypes.FEIGN)
        ExcepcionesDueResponse obtenerExcepcionPatente(
                        @PathVariable("escalaId") Integer escalaId,
                        @PathVariable("entidad") Integer entidad);
}
