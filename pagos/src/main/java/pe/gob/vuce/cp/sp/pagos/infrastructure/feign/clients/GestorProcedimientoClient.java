package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import pe.gob.vuce.cp.framework.globallogger.annotation.Loggable;
import pe.gob.vuce.cp.framework.globallogger.constants.LogTypes;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ProcedimientosResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.TasaResponse;

/**
 * Cliente Feign para interactuar con la API de gestión de procedimientos.
 * 
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@FeignClient(name = "procedimientoClient", url = "${feign.client.gestor-procedimiento-api.base-url}")
public interface GestorProcedimientoClient {

        /**
         * Obtiene procedimientos basados en un componente y otros parámetros.
         *
         * @param authorization Token de autorización.
         * @param componente    ID del componente.
         * @param entidadId     ID de la entidad.
         * @param tipoDoc       Tipo de documento.
         * @param operacionId   ID de la operación.
         * @param textSearch    Texto para búsqueda.
         * @return Respuesta con los procedimientos.
         */
        @Loggable(category = LogTypes.FEIGN)
        @GetMapping("/procedimientos-componente")
        ProcedimientosResponse getProcedimientos(
                        @RequestHeader("Authorization") String authorization,
                        @RequestParam("componente") String componente,
                        @RequestParam("entidadId") Integer entidadId,
                        @RequestParam("tipoDoc") String tipoDoc,
                        @RequestParam("operacionId") String operacionId,
                        @RequestParam("textSearch") String textSearch);

        /**
         * Obtiene la tasa de un procedimiento por su ID y secuencia.
         *
         * @param authorization   Token de autorización.
         * @param procedimientoId ID del procedimiento.
         * @param secuencia       Número de secuencia.
         * @return Respuesta con la tasa.
         */
        @GetMapping("/procedimiento-tasa")
        @Loggable(category = LogTypes.FEIGN)

        TasaResponse getTasa(
                        @RequestHeader("Authorization") String authorization,
                        @RequestParam("procedimiento") Integer procedimientoId,
                        @RequestParam("secuencia") Integer secuencia);
}
