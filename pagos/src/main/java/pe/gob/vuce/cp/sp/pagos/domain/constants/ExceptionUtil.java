package pe.gob.vuce.cp.sp.pagos.domain.constants;

import org.springframework.http.ResponseEntity;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.ResponseMetadataFactory;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.ResponseUtils;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.response.ApiResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ExcepcionMensajeResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ExcepcionesDueResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ExcepcionesResponse;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.NUMBER_1;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.NOT_PAY_DEPORT;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.NOT_PAY;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.NOT_PAY_NAVE_HOSP;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.NOT_PAY_NAVE;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.YES_PAY_NT;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.NOT_PAY_NAVE_CAB;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.NOT_PAY_CABOTAJE;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.NUMBER_18;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.NUMBER_500;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.YES_PAY_OCA;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.POINT;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.NOT_PAY_OCA;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.DOES_NOT_COMPLY;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.NOT_PAY_BUQUE;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.NO_PAY_NT;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.NO_PAY_PD;
import static pe.gob.vuce.cp.sp.pagos.infrastructure.config.ConstantsMetaData.OK;
import static pe.gob.vuce.cp.sp.pagos.infrastructure.config.ConstantsMetaData.CLASS_CANNOT;

@SuppressWarnings({"common-java:DuplicatedBlocks", "all"})
public class ExceptionUtil {

    private ExceptionUtil() {
        throw new UnsupportedOperationException(CLASS_CANNOT);
    }

    public static ResponseEntity<ExcepcionMensajeResponse> setResponse(
            ExcepcionMensajeResponse excepcionMensajeResponse,
            String message,
            boolean validator) {
        excepcionMensajeResponse.setMensaje(message);
        excepcionMensajeResponse.setValidator(validator);
        return ResponseEntity.ok(excepcionMensajeResponse);
    }
    /**
     * Método que evalúa las excepciones de pago según la escala y la entidad.
     * Este método consulta el cliente de excepciones para obtener detalles de la entidad
     * y aplica varias reglas para decidir si el pago debe realizarse, generando un mensaje
     * específico para cada condición cumplida.
     */
    public static ResponseEntity<ApiResponse> processExcepcion(
            Supplier<ExcepcionesResponse> exceptionSupplier,
            BiConsumer<ExcepcionesResponse.DataException, ExcepcionMensajeResponse> exceptionHandler) {

        ExcepcionMensajeResponse excepcionMensajeResponse = new ExcepcionMensajeResponse();
        ExcepcionesResponse response = exceptionSupplier.get();

        if (response == null || response.getData() == null || response.getData().isEmpty()) {
            return ResponseUtils.buildResponse(null, ResponseMetadataFactory.notFoundListarMetadata());
        }

        ExcepcionesResponse.DataException data = response.getData().get(0);
        exceptionHandler.accept(data, excepcionMensajeResponse);
        return ResponseUtils.buildResponse(
                excepcionMensajeResponse,
                ResponseMetadataFactory.okListarMetadata(OK));
    }
    /**
     * Método que evalúa las excepciones de pago según la escala y la entidad.
     * Este método consulta el cliente de excepciones para obtener detalles de la entidad
     * y aplica varias reglas para decidir si el pago debe realizarse, generando un mensaje
     * específico para cada condición cumplida.
     */
    public static ResponseEntity<ApiResponse> processExcepcionDUE(
            Supplier<ExcepcionesDueResponse> exceptionSupplier,
            BiConsumer<ExcepcionesDueResponse.DataException, ExcepcionMensajeResponse> exceptionHandler) {

        ExcepcionMensajeResponse excepcionMensajeResponse = new ExcepcionMensajeResponse();
        ExcepcionesDueResponse response = exceptionSupplier.get();

        if (response == null || response.getData() == null || response.getData().isEmpty()) {
            return ResponseUtils.buildResponse(null, ResponseMetadataFactory.notFoundListarMetadata());

        }

        ExcepcionesDueResponse.DataException data = response.getData().get(0);
        exceptionHandler.accept(data, excepcionMensajeResponse);

        return ResponseUtils.buildResponse(
                excepcionMensajeResponse,
                ResponseMetadataFactory.okListarMetadata(OK));
    }



    /**
     * Método público que llaman a las clases internas privadas y evalúa las excepciones de pago según la escala y la entidad.
     * Este método consulta el cliente de excepciones para obtener detalles de la entidad
     * y aplica varias reglas para decidir si el pago debe realizarse, generando un mensaje
     * específico para cada condición cumplida.
     *
     * @return {@code ResponseEntity} con un objeto {@code ExcepcionMensajeResponse}
     *         que contiene el mensaje y la validación del pago.
     */
    public static ResponseEntity<ExcepcionMensajeResponse> handleExcepcion(
            ExcepcionesResponse.DataException data,
            ExcepcionMensajeResponse response) {

        return HandlerExcepcion.apply(data, response);
    }
    /**
     * Método que evalúa las excepciones de pago según la escala y la entidad.
     * Este método consulta el cliente de excepciones para obtener detalles de la entidad
     * y aplica varias reglas para decidir si el pago debe realizarse, generando un mensaje
     * específico para cada condición cumplida.
     *
     * @return {@code ResponseEntity} con un objeto {@code ExcepcionMensajeResponse}
     *         que contiene el mensaje y la validación del pago.
     */
    public static ResponseEntity<ExcepcionMensajeResponse> handleExcepcionZarpe(
            ExcepcionesDueResponse.DataException data,
            ExcepcionMensajeResponse response) {
        return HandlerExcepcionZarpe.apply(data, response);

    }
    /**
     * Método que evalúa las excepciones de pago según la escala y la entidad.
     * Este método consulta el cliente de excepciones para obtener detalles de la entidad
     * y aplica varias reglas para decidir si el pago debe realizarse, generando un mensaje
     * específico para cada condición cumplida.
     *
     * @return {@code ResponseEntity} con un objeto {@code ExcepcionMensajeResponse}
     *         que contiene el mensaje y la validación del pago.
     */
    public static ResponseEntity<ExcepcionMensajeResponse> handleExcepcionDeclaración(
            ExcepcionesResponse.DataException data,
            ExcepcionMensajeResponse response) {
        return HandlerExcepcionDeclaracion.apply(data, response);


    }
    /**
     * Método que evalúa las excepciones de pago según la escala y la entidad.
     * Este método consulta el cliente de excepciones para obtener detalles de la entidad
     * y aplica varias reglas para decidir si el pago debe realizarse, generando un mensaje
     * específico para cada condición cumplida.
     *
     * @return {@code ResponseEntity} con un objeto {@code ExcepcionMensajeResponse}
     *         que contiene el mensaje y la validación del pago.
     */
    public static ResponseEntity<ExcepcionMensajeResponse> handleExcepcionPatente(
            ExcepcionesDueResponse.DataException data,
            ExcepcionMensajeResponse response) {
        return HandlerExcepcionPatente.apply(data, response);

    }

    /**
     *==== Clases internas privadas para cada tipo de handler ====
     */
    private static class HandlerExcepcion {
        private static ResponseEntity<ExcepcionMensajeResponse> apply(
                ExcepcionesResponse.DataException data,
                ExcepcionMensajeResponse response) {
            return evaluatePagoRules(
                    null, // no hay campo DUE
                    data.getMotivo(),
                    data.getNaveDeportiva(),
                    data.getNaveCientifica(),
                    data.getNaveHospital(),
                    data.getPaisPe(),
                    data.getEntidadId(),
                    data.getAmbitoNave(),
                    data.getSumaArqueo(),
                    data.getSumaArqueoSinConvoy(),
                    response);
        }
    }

    private static class HandlerExcepcionZarpe {
        private static ResponseEntity<ExcepcionMensajeResponse> apply(
                ExcepcionesDueResponse.DataException data,
                ExcepcionMensajeResponse response) {
            return evaluatePagoRules(
                    data.getDue(),
                    data.getMotivo(),
                    data.getNaveDeportiva(),
                    data.getNaveCientifica(),
                    data.getNaveHospital(),
                    data.getPaisPe(),
                    data.getEntidadId(),
                    data.getAmbitoNave(),
                    data.getSumaArqueo(),
                    data.getSumaArqueoSinConvoy(),
                    response);
        }
    }

    private static class HandlerExcepcionDeclaracion {
        private static ResponseEntity<ExcepcionMensajeResponse> apply(
                ExcepcionesResponse.DataException data,
                ExcepcionMensajeResponse excepcionMensajeResponse) {
            // Evaluar Motivo
            if (data.getMotivo() >= NUMBER_1) {
                return setResponse(excepcionMensajeResponse, NOT_PAY, false);
            }

            // Evaluar Buque de Armada
            if (data.getNaveCientifica() >= NUMBER_1) {
                return setResponse(excepcionMensajeResponse, NOT_PAY_BUQUE, false);
            }

            // Evaluar país (nacional o internacional)
            if (data.getPaisPe() == 0) {
                return setResponse(excepcionMensajeResponse, YES_PAY_NT, true);
            }

            if (data.getPaisPe() != 0) {
                return setResponse(excepcionMensajeResponse, NO_PAY_NT, false);
            }
            if (excepcionMensajeResponse.getMensaje() == null && excepcionMensajeResponse.getValidator() == null) {
                excepcionMensajeResponse.setMensaje(DOES_NOT_COMPLY);
            }
            return ExceptionUtil.setResponse(excepcionMensajeResponse, DOES_NOT_COMPLY, false);
        }
    }

    private static class HandlerExcepcionPatente {
        private static ResponseEntity<ExcepcionMensajeResponse> apply(
                ExcepcionesDueResponse.DataException data,
                ExcepcionMensajeResponse excepcionMensajeResponse) {
            // Evaluar Motivo
            if(data.getDue() == 0){
                // Evaluar Motivo
                if (data.getMotivo() >= NUMBER_1) {
                    return setResponse(excepcionMensajeResponse, NOT_PAY, false);
                }
            }
            // Evaluar Buque de Armada
            if (data.getNaveCientifica() >= NUMBER_1) {
                return setResponse(excepcionMensajeResponse, NOT_PAY_BUQUE, false);
            }

            // Evaluar país (nacional o internacional)
            if (data.getPaisPe() == 0) {
                return setResponse(excepcionMensajeResponse, YES_PAY_NT, true);
            }

            if (data.getPaisPe() != 0) {
                return setResponse(excepcionMensajeResponse, NO_PAY_PD, false);
            }
            if (excepcionMensajeResponse.getMensaje() == null && excepcionMensajeResponse.getValidator() == null) {
                excepcionMensajeResponse.setMensaje(DOES_NOT_COMPLY);
            }
            return ExceptionUtil.setResponse(excepcionMensajeResponse, DOES_NOT_COMPLY, false);
        }
    }


    private static ResponseEntity<ExcepcionMensajeResponse> evaluatePagoRules(
            Integer due,
            Integer motivo,
            Integer naveDeportiva,
            Integer naveCientifica,
            Integer naveHospital,
            Integer paisPe,
            Integer entidadId,
            Integer ambitoNave,
            Double sumaArqueo,
            Integer sumaArqueoSinConvoy,
            ExcepcionMensajeResponse response) {

        if ( motivo != null && motivo >= NUMBER_1) {
            return setResponse(response, NOT_PAY, false);
        }

        if (naveDeportiva != null && naveDeportiva >= NUMBER_1) {
            return setResponse(response, NOT_PAY_DEPORT, false);
        }

        if (naveCientifica != null && naveCientifica >= NUMBER_1) {
            return setResponse(response, NOT_PAY_NAVE, false);
        }

        if (naveHospital != null && naveHospital >= NUMBER_1) {
            return setResponse(response, NOT_PAY_NAVE_HOSP, false);
        }

        if (paisPe != null && paisPe == 0) {
            return setResponse(response, YES_PAY_NT, true);
        } else if (entidadId != null) {
            if (entidadId == NUMBER_18) {
                return setResponse(response, NOT_PAY_NAVE_CAB, false);
            } else if (ambitoNave != null && ambitoNave >= NUMBER_1) {
                return setResponse(response, NOT_PAY_CABOTAJE, false);
            } else if (sumaArqueo != null) {
                return (sumaArqueo >= NUMBER_500)
                        ? setResponse(response, YES_PAY_OCA + sumaArqueo + POINT, true)
                        : setResponse(response, NOT_PAY_OCA + sumaArqueo + POINT, false);
            } else if (sumaArqueoSinConvoy != null) {
                return (sumaArqueoSinConvoy >= NUMBER_500)
                        ? setResponse(response, YES_PAY_OCA + sumaArqueoSinConvoy + POINT, true)
                        : setResponse(response, NOT_PAY_OCA + sumaArqueoSinConvoy + POINT, false);
            }
        }

        if (response.getMensaje() == null && response.getValidator() == null) {
            response.setMensaje(DOES_NOT_COMPLY);
        }
        return setResponse(response, DOES_NOT_COMPLY, false);
    }

}
