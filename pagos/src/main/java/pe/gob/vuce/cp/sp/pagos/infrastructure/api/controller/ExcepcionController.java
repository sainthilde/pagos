package pe.gob.vuce.cp.sp.pagos.infrastructure.api.controller;

import lombok.AllArgsConstructor;
import pe.gob.vuce.cp.framework.globallogger.annotation.Loggable;
import pe.gob.vuce.cp.sp.pagos.application.service.ObtenerExcepcionService;
import pe.gob.vuce.cp.sp.pagos.domain.constants.ExceptionUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.response.ApiResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ExcepcionesDueResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ExcepcionesResponse;
import java.util.function.Supplier;

/**
 * El controlador ExcepcionController expone endpoints REST para gestionar y
 * consultar excepciones de pago, evaluando condiciones específicas en función
 * de la entidad, escala, y tipo de nave. Utiliza el servicio
 * {@code ComunesQueryClient}
 * para obtener datos y aplica reglas de negocio para determinar si se deben
 * realizar
 * pagos bajo condiciones excepcionales.
 *
 * <p>
 * Anotaciones:
 * <ul>
 * <li>{@code @RestController}: Marca esta clase como un controlador REST de
 * Spring,
 * permitiendo manejar solicitudes HTTP y devolver respuestas JSON.</li>
 * <li>{@code @SuppressWarnings("all")}: Suprime advertencias específicas del
 * compilador
 * en esta clase.</li>
 * </ul>
 *
 * <p>
 * Dependencias:
 * <ul>
 * <li>{@code comunesQueryClient}: Cliente de servicio que obtiene excepciones
 * de pago
 * desde un servicio externo.</li>
 * </ul>
 *
 * <p>
 * Métodos principales:
 * <ul>
 * <li>{@code obtenerExcepciones}: Método que recibe identificadores de escala y
 * entidad,
 * obtiene datos de excepciones mediante el servicio {@code comunesQueryClient},
 * y aplica reglas de negocio para determinar si el pago es requerido o no,
 * devolviendo una respuesta con el mensaje de validación correspondiente.</li>
 * </ul>
 * 
 * @author Mateo Huancho
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@RestController
@SuppressWarnings({ "common-java:DuplicatedBlocks", "all" })
@AllArgsConstructor
public class ExcepcionController {

    private final ObtenerExcepcionService obtenerExcepcionService;

    /**
     * Método que evalúa las excepciones de pago según la escala y la entidad.
     * Este método consulta el cliente de excepciones para obtener detalles de la
     * entidad
     * y aplica varias reglas para decidir si el pago debe realizarse, generando un
     * mensaje
     * específico para cada condición cumplida.
     */
    private ResponseEntity<ApiResponse> processExcepcion(Supplier<ExcepcionesResponse> exceptionSupplier) {
        return ExceptionUtil.processExcepcion(exceptionSupplier, ExceptionUtil::handleExcepcion);
    }

    /**
     * Método que evalúa las excepciones de pago según la escala y la entidad.
     * Este método consulta el cliente de excepciones para obtener detalles de la
     * entidad
     * y aplica varias reglas para decidir si el pago debe realizarse, generando un
     * mensaje
     * específico para cada condición cumplida.
     */
    private ResponseEntity<ApiResponse> processExcepcionZarpe(Supplier<ExcepcionesDueResponse> exceptionSupplier) {
        return ExceptionUtil.processExcepcionDUE(exceptionSupplier, ExceptionUtil::handleExcepcionZarpe);
    }

    /**
     * Método que evalúa las excepciones de pago según la escala y la entidad.
     * Este método consulta el cliente de excepciones para obtener detalles de la
     * entidad
     * y aplica varias reglas para decidir si el pago debe realizarse, generando un
     * mensaje
     * específico para cada condición cumplida.
     */
    private ResponseEntity<ApiResponse> processExcepcionDeclaracion(Supplier<ExcepcionesResponse> exceptionSupplier) {
        return ExceptionUtil.processExcepcion(exceptionSupplier, ExceptionUtil::handleExcepcionDeclaración);
    }

    /**
     * Método que evalúa las excepciones de pago según la escala y la entidad.
     * Este método consulta el cliente de excepciones para obtener detalles de la
     * entidad
     * y aplica varias reglas para decidir si el pago debe realizarse, generando un
     * mensaje
     * específico para cada condición cumplida.
     */
    private ResponseEntity<ApiResponse> processExcepcionPatente(Supplier<ExcepcionesDueResponse> exceptionSupplier) {
        return ExceptionUtil.processExcepcionDUE(exceptionSupplier, ExceptionUtil::handleExcepcionPatente);
    }

    /**
     * Endpoint que evalúa las excepciones de pago según la escala y la entidad.
     * Este método consulta el cliente de excepciones para obtener detalles de la
     * entidad
     * y aplica varias reglas para decidir si el pago debe realizarse, generando un
     * mensaje
     * específico para cada condición cumplida.
     *
     * @param escalaId ID de la escala que afecta la excepción.
     * @param entidad  ID de la entidad que se evalúa en la excepción.
     * @return {@code ResponseEntity} con un objeto {@code ExcepcionMensajeResponse}
     *         que contiene el mensaje y la validación del pago.
     */
    @Loggable
    @GetMapping("/pagos/escala/{escalaId}/detalles/{entidad}")
    public ResponseEntity<ApiResponse> obtenerExcepciones(@PathVariable Integer escalaId,
            @PathVariable Integer entidad) {
        return processExcepcion(() -> obtenerExcepcionService.obtenerExcepcion(escalaId, entidad));
    }

    /**
     * Endpoint que evalúa las excepciones de pago según la escala y la entidad.
     * Este método consulta el cliente de excepciones para obtener detalles de la
     * entidad
     * y aplica varias reglas para decidir si el pago debe realizarse, generando un
     * mensaje
     * específico para cada condición cumplida.
     *
     * @param escalaId ID de la escala que afecta la excepción.
     * @param entidad  ID de la entidad que se evalúa en la excepción.
     * @return {@code ResponseEntity} con un objeto {@code ExcepcionMensajeResponse}
     *         que contiene el mensaje y la validación del pago.
     */
    @Loggable
    @GetMapping("/pagos/escala/{escalaId}/detalles_zarpe/{entidad}")
    public ResponseEntity<ApiResponse> ObtenerExcepcionesZarpe(@PathVariable Integer escalaId,
            @PathVariable Integer entidad) {
        return processExcepcionZarpe(() -> obtenerExcepcionService.obtenerExcepcionZarpe(escalaId, entidad));
    }

    /**
     * Endpoint que evalúa las excepciones de pago según la escala y la entidad.
     * Este método consulta el cliente de excepciones para obtener detalles de la
     * entidad
     * y aplica varias reglas para decidir si el pago debe realizarse, generando un
     * mensaje
     * específico para cada condición cumplida.
     *
     * @param escalaId ID de la escala que afecta la excepción.
     * @param entidad  ID de la entidad que se evalúa en la excepción.
     * @return {@code ResponseEntity} con un objeto {@code ExcepcionMensajeResponse}
     *         que contiene el mensaje y la validación del pago.
     */
    @Loggable
    @GetMapping("/pagos/escala/{escalaId}/detalles-declaracion/{entidad}")
    public ResponseEntity<ApiResponse> obtenerExcepcionesDeclaración(@PathVariable Integer escalaId,
            @PathVariable Integer entidad) {
        return processExcepcionDeclaracion(
                () -> obtenerExcepcionService.obtenerExcepcionDeclaracion(escalaId, entidad));
    }

    /**
     * Endpoint que evalúa las excepciones de pago según la escala y la entidad.
     * Este método consulta el cliente de excepciones para obtener detalles de la
     * entidad
     * y aplica varias reglas para decidir si el pago debe realizarse, generando un
     * mensaje
     * específico para cada condición cumplida.
     *
     * @param escalaId ID de la escala que afecta la excepción.
     * @param entidad  ID de la entidad que se evalúa en la excepción.
     * @return {@code ResponseEntity} con un objeto {@code ExcepcionMensajeResponse}
     *         que contiene el mensaje y la validación del pago.
     */
    @Loggable
    @GetMapping("/pagos/escala/{escalaId}/detalles-patente/{entidad}")
    public ResponseEntity<ApiResponse> obtenerExcepcionPatente(@PathVariable Integer escalaId,
            @PathVariable Integer entidad) {
        return processExcepcionPatente(() -> obtenerExcepcionService.obtenerExcepcionPatente(escalaId, entidad));
    }
}
