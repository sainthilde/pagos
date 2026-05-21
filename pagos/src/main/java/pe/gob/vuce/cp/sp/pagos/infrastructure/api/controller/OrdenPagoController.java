package pe.gob.vuce.cp.sp.pagos.infrastructure.api.controller;

import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.ERROR_E0047;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.ERROR_E0048;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.ERROR_E0073;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.GP_PREFIX;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.SUNAT_PREFIX;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.APPLICATION_PDF;
import static pe.gob.vuce.cp.sp.pagos.infrastructure.config.ConstantsMetaData.ATTACHMENT_FILENAME;
import static pe.gob.vuce.cp.sp.pagos.infrastructure.config.ConstantsMetaData.ATTACHMENT_PDF;
import static pe.gob.vuce.cp.sp.pagos.infrastructure.config.ConstantsMetaData.ERROR;
import static pe.gob.vuce.cp.sp.pagos.infrastructure.config.ConstantsMetaData.FORMA_PAGO_LISTAR;
import static pe.gob.vuce.cp.sp.pagos.infrastructure.config.ConstantsMetaData.FORMA_PAGO_NOT_FOUND;
import static pe.gob.vuce.cp.sp.pagos.infrastructure.config.ConstantsMetaData.PAGO;
import static pe.gob.vuce.cp.sp.pagos.infrastructure.config.ConstantsMetaData.PDF_NOT_GENERATED;
import static pe.gob.vuce.cp.sp.pagos.infrastructure.config.ConstantsMetaData.SUCCESS;

import java.util.Collections;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import pe.gob.vuce.cp.framework.globallogger.annotation.Loggable;
import pe.gob.vuce.cp.sp.pagos.application.service.AnularOrdenPagoLocalService;
import pe.gob.vuce.cp.sp.pagos.application.service.AnularOrdenPagoService;
import pe.gob.vuce.cp.sp.pagos.application.service.FormaPagoService;
import pe.gob.vuce.cp.sp.pagos.application.service.ObtenerFileService;
import pe.gob.vuce.cp.sp.pagos.application.service.OrdenPagoService;
import pe.gob.vuce.cp.sp.pagos.application.service.PagoSunatService;
import pe.gob.vuce.cp.sp.pagos.application.service.TasaService;
import pe.gob.vuce.cp.sp.pagos.domain.constants.ResponseUtil;
import pe.gob.vuce.cp.sp.pagos.domain.exception.FeignExceptionHandler;
import pe.gob.vuce.cp.sp.pagos.domain.exception.OrdenPagoNotFoundException;
import pe.gob.vuce.cp.sp.pagos.domain.model.OrdenPago;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.GenericResponseDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.OrdenPagoRequestDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.OrdenPagoResponseDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.ResponseUtils;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.Tupa0ResponseDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.response.ApiResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.response.ResponseMetadata;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.mapper.OrdenPagoMapper;
import pe.gob.vuce.cp.sp.pagos.infrastructure.config.exception.PdfGenerationException;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.PaymentMethodResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.TasaResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.response.OrdenPagoResponseDTO;

/**
 * El controlador OrdenPagoController expone endpoints REST para gestionar y
 * consultar órdenes de pago, incluyendo la creación, anulación, consulta de
 * tasas,
 * generación de PDFs, y métodos de pago. Utiliza varios servicios y clientes
 * para realizar operaciones complejas y de integración con sistemas externos.
 *
 * <p>
 * Anotaciones:
 * <ul>
 * <li>{@code @RestController}: Marca esta clase como un controlador REST de
 * Spring,
 * permitiendo manejar solicitudes HTTP y devolver respuestas JSON.</li>
 * <li>{@code @RequestMapping("/")}: Define la ruta base para todos los
 * endpoints
 * dentro de este controlador.</li>
 * <li>{@code @SuppressWarnings("all")}: Suprime advertencias específicas del
 * compilador
 * en esta clase.</li>
 * </ul>
 *
 * <p>
 * Dependencias:
 * <ul>
 * <li>{@code ordenPagoService}: Servicio que gestiona las operaciones
 * relacionadas con
 * la entidad {@code OrdenPago}.</li>
 * <li>{@code ordenPagoMapper}: Mapper que convierte objetos {@code OrdenPago}
 * en DTOs
 * para su exposición en la API.</li>
 * <li>{@code ordenPagoSunatService}: Servicio que gestiona el procesamiento de
 * órdenes
 * de pago en SUNAT de manera asíncrona.</li>
 * <li>{@code oAuthClient}, {@code oAuthClientConfig}: Cliente y configuración
 * para
 * gestionar la autenticación OAuth en solicitudes a sistemas externos.</li>
 * <li>{@code gestorProcedimientoClient}: Cliente que consulta y gestiona
 * procedimientos
 * relacionados con las órdenes de pago.</li>
 * <li>{@code comunesQueryClient}: Cliente para consultas generales de datos de
 * apoyo.</li>
 * </ul>
 *
 * <p>
 * Métodos principales:
 * <ul>
 * <li>{@code createOrdenPago}: Endpoint que permite crear una nueva orden de
 * pago
 * y procesarla en SUNAT.</li>
 * <li>{@code getTasas}: Endpoint para obtener las tasas disponibles para una
 * entidad
 * y componente específicos, utilizando criterios de búsqueda.</li>
 * <li>{@code findByEscalaIdAndDocumentoId}: Endpoint que busca órdenes de pago
 * utilizando el ID de escala y el ID de documento.</li>
 * <li>{@code getPdf}: Endpoint para generar y devolver un archivo PDF para la
 * orden de pago especificada.</li>
 * <li>{@code anular}: Endpoint que permite anular una orden de pago
 * especificada.</li>
 * <li>{@code getPaymentMethods}: Endpoint que obtiene los métodos de pago
 * disponibles
 * para un canal y entidad específicos, utilizando caché para optimizar la
 * respuesta.</li>
 * </ul>
 * 
 * @author Mateo Huancho
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@RestController
@RequestMapping("/")
@SuppressWarnings("all")
@AllArgsConstructor
public class OrdenPagoController {

    private final OrdenPagoService ordenPagoService;
    private final OrdenPagoMapper ordenPagoMapper;
    private final ObtenerFileService obtenerFileService;
    private final PagoSunatService pagoSunatService;
    private final FormaPagoService formaPagoService;
    private final TasaService tasaService;
    private final AnularOrdenPagoService anularOrdenPagoService;
    private final AnularOrdenPagoLocalService anularOrdenPagoLocalService;
    private final FeignExceptionHandler feignExceptionHandler;
    private final HttpServletRequest request;
    /**
     * Crea una nueva orden de pago.
     *
     * @param user                el usuario que realiza la operación (required).
     * @param ordenPagoRequestDto el DTO que contiene los datos de la orden de pago
     *                            a crear (required).
     * @return un ResponseEntity con el DTO de la orden de pago creada y un código
     *         de estado HTTP 201 (CREATED).
     * @throws IllegalStateException si no se encuentra la entidad, o si no se
     *                               pueden encontrar procedimientos o tasas para la
     *                               orden de pago.
     */
    @Loggable
    @PostMapping("/ordenes-pago")
    public ResponseEntity<GenericResponseDto<Object>> createOrdenPago(
            @RequestHeader("user") String user,
            @Valid @RequestBody OrdenPagoRequestDto requestDto) {

        try {
            OrdenPagoResponseDto responseDto = pagoSunatService.ejecutar(requestDto, user);
            GenericResponseDto<Object> response = new GenericResponseDto<>();
            response.setMeta(ResponseUtil.createResponseMetaDataDto());
            response.setData(Collections.singletonList(responseDto));
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (FeignException e) {
            HttpStatus status = switch (e.status()) {
                case 502, 503, 504, 500 -> HttpStatus.INTERNAL_SERVER_ERROR;
                default -> HttpStatus.BAD_REQUEST;
            };

            String errorCode = switch (e.status()) {
                case 502, 503, 504, 500 -> ERROR_E0047;
                default -> ERROR_E0048;
            };

            String source = feignExceptionHandler.getErrorSource(e);

            GenericResponseDto<Object> response = ResponseUtil.createApiResponseVuceCP2Exception(
                    errorCode,
                    e.getMessage(),
                    List.of(source));
            return new ResponseEntity<>(response, status);
        } catch (OrdenPagoNotFoundException e) {
            GenericResponseDto<Object> response = ResponseUtil.createApiResponseVuceCP2Exception(
                    ERROR_E0048,
                    e.getMessage(),
                    List.of(GP_PREFIX));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            GenericResponseDto<Object> response = ResponseUtil.createApiResponseVuceCP2Exception(
                    ERROR_E0073,
                    e.getMessage(),
                    List.of());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Loggable
    @PostMapping("/ordenes-pago/tupa0/validar")
    public ResponseEntity<GenericResponseDto<Object>> validarTupa0(
            @RequestHeader("user") String user,
            @RequestParam("indicador") String indicador,
            @Valid @RequestBody OrdenPagoRequestDto requestDto) {
        try{
            String token = request.getHeader("Authorization");
            String tramite = request.getHeader("X-Tramite");
            Tupa0ResponseDto responseDto = pagoSunatService.validarTupa0(requestDto, user, token,tramite,indicador);
            GenericResponseDto<Object> response = new GenericResponseDto<>();
            response.setMeta(ResponseUtil.createResponseMetaDataDto());
            response.setData(Collections.singletonList(responseDto));
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (Exception e) {
            GenericResponseDto<Object> response = ResponseUtil.createApiResponseVuceCP2Exception(
                    ERROR_E0048,
                    e.getMessage(),
                    List.of());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Obtiene las tasas disponibles para una entidad y componente específicos.
     *
     * @param entidadId    ID de la entidad (required).
     * @param idComponente ID del componente (required).
     * @param textSearch   texto para buscar procedimientos (required).
     * @return un ResponseEntity que contiene la tasa encontrada y un código de
     *         estado HTTP 200 (OK).
     */
    @Loggable
    @GetMapping("/tasas")
    public ResponseEntity<GenericResponseDto<Object>> getTasas(@RequestParam Integer entidadId,
            @RequestParam String idComponente,
            @RequestParam String textSearch) {
        TasaResponse.Tasa tasa = tasaService.obtenerTasa(entidadId, idComponente, textSearch);

        GenericResponseDto<Object> response = new GenericResponseDto<>();
        response.setMeta(ResponseUtil.createResponseMetaDataDto());
        response.setData(tasa != null ? Collections.singletonList(tasa) : Collections.emptyList());

        return ResponseEntity.ok(response);
    }

    /**
     * Busca órdenes de pago utilizando el ID de escala y el ID de documento.
     *
     * @param escalaId    ID de la escala (required).
     * @param documentoId ID del documento (required).
     * @return un ResponseEntity con una lista de DTOs de órdenes de pago y un
     *         código de estado HTTP 200 (OK).
     */
    @Loggable
    @GetMapping(value = "/ordenes-pago/{escalaId}")
    public ResponseEntity<GenericResponseDto<Object>> findByEscalaIdAndDocumentoId(@PathVariable Integer escalaId,
            @RequestParam Integer documentoId) {
        try {
            GenericResponseDto<Object> response = new GenericResponseDto<>();
            response.setMeta(ResponseUtil.createResponseMetaDataDto());
            response.setData(Collections.singletonList(ordenPagoMapper
                    .listModelToListDto(ordenPagoService.findByEscalaIdAndDocumentoId(escalaId, documentoId))));
            return ResponseEntity.ok(response);
        } catch (FeignException e) {
            GenericResponseDto<Object> response = ResponseUtil.createApiResponseVuceCP2Exception(ERROR_E0073,
                    e.getMessage(), List.of());
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(e.status()));
        }

    }

    /**
     * Genera y devuelve un PDF para la orden de pago especificada.
     *
     * @param ordenPagoId ID de la orden de pago (required).
     * @return un ResponseEntity que contiene el recurso del PDF y un código de
     *         estado HTTP 200 (OK).
     * @throws IllegalStateException si no es posible generar el PDF para la orden
     *                               de pago en el estado actual.
     */
    @Loggable
    @GetMapping(value = "/ordenes-pago/{ordenPagoId}/pdf")
    public ResponseEntity<Resource> getPdf(@PathVariable Integer ordenPagoId) {
        OrdenPago ordenPago = ordenPagoService.findById(ordenPagoId);
        if (ordenPago.getFilenetGuid() == null) {
            throw new PdfGenerationException(PDF_NOT_GENERATED);
        }

        Resource resource = obtenerFileService.getDocument(ordenPago.getFilenetGuid());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, APPLICATION_PDF);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, ATTACHMENT_FILENAME + ordenPagoId + ATTACHMENT_PDF);

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    /**
     * Anula la orden de pago especificada.
     *
     * @param ordenPagoId ID de la orden de pago a anular (required).
     * @return un ResponseEntity con el DTO de la respuesta de la anulación y un
     *         código de estado HTTP 200 (OK).
     * @throws IllegalStateException si la orden de pago no existe.
     */
    @Loggable
    @PutMapping("/ordenes-pago/{ordenPagoId}/anular")
    public ResponseEntity<GenericResponseDto<Object>> anular(@RequestHeader("user") String user,@PathVariable Integer ordenPagoId) {
        try {
        OrdenPagoResponseDTO responseDTO = anularOrdenPagoService.anularOrdenPago(ordenPagoId,user);
        GenericResponseDto<Object> response = new GenericResponseDto<>();
        response.setMeta(ResponseUtil.createResponseMetaDataDto());
        response.setData(Collections.singletonList(responseDTO));
        return ResponseEntity.ok(response);
        } catch (FeignException e) {
            HttpStatus status = switch (e.status()) {
                case 502, 503, 504, 500 -> HttpStatus.INTERNAL_SERVER_ERROR;
                default -> HttpStatus.BAD_REQUEST;
            };

            String errorCode = switch (e.status()) {
                case 502, 503, 504, 500 -> ERROR_E0047;
                default -> ERROR_E0048;
            };

            String source = feignExceptionHandler.getErrorSource(e);

            GenericResponseDto<Object> response = ResponseUtil.createApiResponseVuceCP2Exception(
                    errorCode,
                    e.getMessage(),
                    List.of(SUNAT_PREFIX));
            return new ResponseEntity<>(response, status);
        } catch (OrdenPagoNotFoundException e) {
            GenericResponseDto<Object> response = ResponseUtil.createApiResponseVuceCP2Exception(
                    ERROR_E0048,
                    e.getMessage(),
                    List.of(SUNAT_PREFIX));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            GenericResponseDto<Object> response = ResponseUtil.createApiResponseVuceCP2Exception(
                    ERROR_E0073,
                    e.getMessage(),
                    List.of());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Loggable
    @PutMapping("/ordenes-pago/{ordenPagoId}/anularCreado")
    public ResponseEntity<GenericResponseDto<Object>> anularCreado(@RequestHeader("user") String user,@PathVariable Integer ordenPagoId) {
        OrdenPagoResponseDto responseDto = anularOrdenPagoLocalService.anularOrdenPagoLocal(ordenPagoId,user);
        GenericResponseDto<Object> response = new GenericResponseDto<>();
        response.setMeta(ResponseUtil.createResponseMetaDataDto());
        response.setData(Collections.singletonList(responseDto));
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene los métodos de pago disponibles para un canal y entidad específicos.
     *
     * @param canalId   ID del canal (required).
     * @param entidadId ID de la entidad (required).
     * @return una lista de métodos de pago disponibles.
     */

    @Loggable
    @GetMapping("/formas-pago")
    public ResponseEntity<ApiResponse> getPaymentMethods(@RequestParam Integer canalId,
            @RequestParam Integer entidadId) {
        List<PaymentMethodResponse> paymentMethods = formaPagoService.getPaymentMethods(canalId, entidadId);
        boolean hayDatos = paymentMethods != null && !paymentMethods.isEmpty();
        ResponseMetadata metadata = ResponseMetadata.builder()
                .codeInfo(hayDatos ? SUCCESS : ERROR)
                .tipoOperacion(PAGO)
                .mensajeOperacion(hayDatos ? FORMA_PAGO_LISTAR : FORMA_PAGO_NOT_FOUND)
                .esExitoso(hayDatos)
                .httpStatus(hayDatos ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .build();
        return ResponseUtils.buildResponse(paymentMethods, metadata);
    }
}
