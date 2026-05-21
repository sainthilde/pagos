package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.adapter;

import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.DOCUMENT_SEGUI;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.GP_PREFIX;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.separador;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsHelpers.ORDEN_ENTIDAD_NOT_FOUND;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsHelpers.ORDEN_PAGO_CREADA_CPD;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsHelpers.PROCEDURE_NOT_FOUND;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsHelpers.TASA_NOT_FOUND;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.BEARER;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.COD_ENTIDAD_GP;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.ENTIDAD;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.NUMBER_1;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.STRING;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

import feign.FeignException;
import lombok.AllArgsConstructor;
import pe.gob.vuce.cp.sp.pagos.domain.constants.Constants;
import pe.gob.vuce.cp.sp.pagos.domain.constants.SeguimientoUtils;
import pe.gob.vuce.cp.sp.pagos.domain.exception.FeignExceptionHandler;
import pe.gob.vuce.cp.sp.pagos.domain.exception.OrdenPagoNotFoundException;
import pe.gob.vuce.cp.sp.pagos.domain.model.OrdenPago;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.CreateSeguimientoUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.ProcesarOrdenSunatUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.OrdenPagoRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.OrdenPagoSunatRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.OrdenPagoRequestDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.OrdenPagoResponseDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.SeguimientoRequestDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.Tupa0ResponseDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.mapper.OrdenPagoMapper;
import pe.gob.vuce.cp.sp.pagos.infrastructure.config.exception.JsonParseException;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.ComunesQueryClient;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.GestorProcedimientoClient;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.OAuthClient;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.TramiteCommandClient;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.TramiteQueryClient;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.config.OAuthClientConfig;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ComunesQueryResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ProcedimientosResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ProcedimientosResponse.Procedimiento;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.TasaResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.TasaResponse.Tasa;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.TramiteCommandRequest;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.TramiteResponse;

@AllArgsConstructor
@Component
public class OrdenPagoSunatRepositoryAdapter implements OrdenPagoSunatRepositoryPort {

    private final OrdenPagoMapper ordenPagoMapper;
    private final OrdenPagoRepositoryPort ordenPagoRepositoryPort;
    private final ProcesarOrdenSunatUseCase procesarOrdenSunatUseCase;
    private final OAuthClient oAuthClient;
    private final OAuthClientConfig oAuthClientConfig;
    private final GestorProcedimientoClient gestorProcedimientoClient;
    private final ComunesQueryClient comunesQueryClient;
    private final FeignExceptionHandler feignExceptionHandler;
    private final CreateSeguimientoUseCase createSeguimientoUseCase;
    private final TramiteCommandClient tramiteCommandClient;
    private final TramiteQueryClient tramiteQueryClient;
    @Override
    public OrdenPagoResponseDto ejecutar(OrdenPagoRequestDto requestDto, String user) {
        OrdenPago ordenPago = ordenPagoMapper.dtoToModel(requestDto, user);
        Integer actividadEntidadPuertoId = requestDto.getActividadEntidadPuertoId();

        try {
            // 1. Crear orden
            ordenPago.setUsuidRegAud(separador(user, 1));
            ordenPago = ordenPagoRepositoryPort.save(ordenPago);
            ordenPago.setActividadEntidadPuertoId(actividadEntidadPuertoId);

            // 2. Validar ENTIDAD
            ComunesQueryResponse comunesQueryResponse = obtenerEntidadValida(ordenPago.getEntidadId());

            // 3. Seguimiento
            String cpb = ORDEN_PAGO_CREADA_CPD
                    + (ordenPago.getCpb() != null && !ordenPago.getCpb().trim().isEmpty() && !"null".equals(ordenPago.getCpb())
                    ? ordenPago.getCpb() : "")
                    + DOCUMENT_SEGUI + Constants.tipoDocumento(ordenPago.getDocumentoId());

            SeguimientoRequestDto seguimientoRequestDto = SeguimientoUtils.generarRequestSeguimiento(
                    ordenPago.getEscalaId(),
                    SeguimientoUtils.GENERADO,
                    Constants.indicador(ordenPago.getDocumentoId()),
                    ordenPago.getRucAgente(),
                    Constants.tipoDocumento(ordenPago.getDocumentoId()),
                    cpb);
            createSeguimientoUseCase.create(seguimientoRequestDto, user);

            // 4. Token
            String authorization = obtenerAuthorization();

            // ⬇️ 5–8: extraído al método separado (elimina el try anidado)
            return procesarConProcedimientoYTasa(authorization, requestDto, comunesQueryResponse, ordenPago, user);

        } catch (FeignException e) {
            feignExceptionHandler.handleFeignClientException(e, ordenPago);
            ordenPagoRepositoryPort.update(ordenPago);
            throw e;
        } catch (OrdenPagoNotFoundException e) {
            ordenPago.setPpCodigorechazoSinConexion("400");
            ordenPago.setPpDescCortaError(GP_PREFIX + "Error funcional");
            ordenPago.setPpMensajeRechazoSinConexion(GP_PREFIX + e.getMessage());
            ordenPago.setUsuidModAud(Constants.GESTOR_DESC);
            ordenPagoRepositoryPort.update(ordenPago);
            throw e;
        } catch (JsonProcessingException e) {
            throw new JsonParseException("ERROR", e);
        }
    }

    private OrdenPagoResponseDto procesarConProcedimientoYTasa(
            String authorization,
            OrdenPagoRequestDto requestDto,
            ComunesQueryResponse comunesQueryResponse,
            OrdenPago ordenPago,
            String user
    ) throws JsonProcessingException {
        try {
            // 5. Obtener procedimiento
            int codEntidadGp = obtenerCodEntidadGp(comunesQueryResponse);
            ProcedimientosResponse procedimientosResponse = obtenerProcedimiento(authorization, requestDto, codEntidadGp, ordenPago);
            Procedimiento procedimiento = procedimientosResponse.getProcedimientos().get(0);
            cagarGPDatos(ordenPago, procedimientosResponse, user);

 
            ordenPagoRepositoryPort.update(ordenPago);

            // 6. Obtener tasa
            TasaResponse tasaResponse = gestorProcedimientoClient.getTasa(
                    authorization, procedimiento.getProcedimientoId(), NUMBER_1);

            if (tasaResponse.getTasas().isEmpty()) {
                throw new OrdenPagoNotFoundException(TASA_NOT_FOUND);
            }
            Tasa tasa = tasaResponse.getTasas().get(0);

            // 7. Enriquecer y actualizar
            enriquecerOrdenPagoConTasa(ordenPago, tasa, user);
            ordenPago = ordenPagoRepositoryPort.update(ordenPago);

            // 8. Procesar SUNAT
            procesarOrdenSunatUseCase.procesarOrdenSunat(procedimiento, tasa, ordenPago,
                    requestDto.getCantidadOrden(), user);

            return ordenPagoMapper.modelToDto(ordenPago);

        } catch (NumberFormatException e) {
            throw new OrdenPagoNotFoundException(ORDEN_ENTIDAD_NOT_FOUND);
        }
    }

    @Override
    public Tupa0ResponseDto validarTupa0(OrdenPagoRequestDto requestDto, String user, String token, String tramite,String indicador) {
        
        OrdenPago ordenPago = ordenPagoMapper.dtoToModel(requestDto, user);
        Integer actividadEntidadPuertoId = requestDto.getActividadEntidadPuertoId();

        if(ordenPagoRepositoryPort.existeEscalaTupaCero(ordenPago.getEscalaId(), BigDecimal.ZERO, ordenPago.getDocumentoId())){
            if("true".equalsIgnoreCase(tramite)){
                ejecutarTramiteTupa0(requestDto, ordenPago, user, token, indicador);
            }
            return new Tupa0ResponseDto(true, "El trámite es gratuito");
        }

        ComunesQueryResponse comunesQueryResponse = obtenerEntidadValida(ordenPago.getEntidadId());
        String authorization = obtenerAuthorization();
        int codEntidadGp = obtenerCodEntidadGp(comunesQueryResponse);
        ProcedimientosResponse procedimientosResponse = obtenerProcedimiento(authorization, requestDto, codEntidadGp, ordenPago);

        Procedimiento procedimiento = procedimientosResponse.getProcedimientos().get(0);
        cagarGPDatos(ordenPago, procedimientosResponse, user);

        
        TasaResponse tasaResponse = gestorProcedimientoClient.getTasa(
                    authorization, procedimiento.getProcedimientoId(), NUMBER_1);

        if (tasaResponse.getTasas().isEmpty()) {
            throw new OrdenPagoNotFoundException(TASA_NOT_FOUND);
        }
        Tasa tasa = tasaResponse.getTasas().get(0);
        Double monto = tasa.getMonto();
        if ( monto.doubleValue() == 0.0) {
            ordenPago.setUsuidRegAud(separador(user, 1));
            ordenPago = ordenPagoRepositoryPort.save(ordenPago);
            ordenPago.setActividadEntidadPuertoId(actividadEntidadPuertoId);
            
            enriquecerOrdenPagoConTasa(ordenPago, tasa, user);
            ordenPago.setEstado("");
            ordenPago.setPpEstadoCpbTexto("");  
            ordenPagoRepositoryPort.update(ordenPago);

            SeguimientoRequestDto seguimientoRequestDto = SeguimientoUtils.generarRequestSeguimiento(
                    ordenPago.getEscalaId(),
                    56,
                    Constants.indicador(ordenPago.getDocumentoId()),
                    ordenPago.getRucAgente(),
                    Constants.tipoDocumento(ordenPago.getDocumentoId()),
                    "Orden de pago con tasa 0");
            createSeguimientoUseCase.create(seguimientoRequestDto, user);

            if("true".equalsIgnoreCase(tramite)){
                ejecutarTramiteTupa0(requestDto, ordenPago, user, token,  indicador);
            }    
            return new Tupa0ResponseDto(true, "El trámite es gratuito");
        }
            
        return new Tupa0ResponseDto(false, "");
    }

    private void ejecutarTramiteTupa0(OrdenPagoRequestDto requestDto, OrdenPago ordenPago,String user, String token, String indicador) {
        Integer actividadEntidadPuertoId = requestDto.getActividadEntidadPuertoId();
        ComunesQueryResponse comunesQueryResponse = obtenerEntidadValida(ordenPago.getEntidadId());
        String authorization = obtenerAuthorization();
        int codEntidadGp = obtenerCodEntidadGp(comunesQueryResponse);
        ProcedimientosResponse procedimientosResponse = obtenerProcedimiento(authorization, requestDto, codEntidadGp, ordenPago);

        Procedimiento procedimiento = procedimientosResponse.getProcedimientos().get(0);
        cagarGPDatos(ordenPago, procedimientosResponse, user);

        TramiteCommandRequest tramiteRequest = new TramiteCommandRequest();
        tramiteRequest.setEscalaId(ordenPago.getEscalaId());
        tramiteRequest.setDocumentoId(ordenPago.getDocumentoId());
        tramiteRequest.setIndicadorEs(indicador);
        tramiteRequest.setRucAgente(ordenPago.getRucAgente());
        tramiteRequest.setActividadEntidadPuertoId(actividadEntidadPuertoId);
        tramiteRequest.setIndNoRequierePago(true);
        tramiteRequest.setTupa(procedimiento.getTupa());
        tramiteRequest.setReglaPagoExencionAplicada("NO PAGA POR TUPA 0");
        tramiteRequest.setDescripcionTramite(procedimiento.getNombreCut());
        tramiteCommandClient.crearTramite(token,separador(user, 1),user,ordenPago.getRucAgente(),tramiteRequest);

        TramiteResponse response = tramiteQueryClient.obtenerTramites(token, 100, ordenPago.getEscalaId(), ordenPago.getDocumentoId(), indicador);
        Integer tramiteId = response.getData().get(0).getId();  
        List<OrdenPago> ordenesPago = ordenPagoRepositoryPort.findByEscalaIdAndDocumentoId(ordenPago.getEscalaId(), ordenPago.getDocumentoId());
        OrdenPago ordenPagoExistente = ordenesPago.get(0);
        ordenPagoExistente.setTramiteId(tramiteId);
        ordenPagoExistente.setGpProcedimientoId(String.valueOf(procedimiento.getProcedimientoId()));
        ordenPagoRepositoryPort.update(ordenPagoExistente);
    }     

    private ComunesQueryResponse obtenerEntidadValida(Integer entidadId) {
        ComunesQueryResponse response =
                comunesQueryClient.getAllByCodeAndAttribute(entidadId, ENTIDAD);

        if (response == null || response.getData().isEmpty()) {
            throw new OrdenPagoNotFoundException(ORDEN_ENTIDAD_NOT_FOUND);
        }

        return response;
    }

    private String obtenerAuthorization() {
        return BEARER + oAuthClient.getToken(
                oAuthClientConfig.getGrantType(),
                oAuthClientConfig.getScope()
        ).getAccessToken();
    }

    private int obtenerCodEntidadGp(ComunesQueryResponse response) {
        try {
            return Integer.parseInt(
                    response.getData().get(0).getOthersColumns().get(COD_ENTIDAD_GP));
        } catch (Exception e) {
            throw new OrdenPagoNotFoundException(ORDEN_ENTIDAD_NOT_FOUND);
        }
    }

    private ProcedimientosResponse obtenerProcedimiento(
                String authorization,
                OrdenPagoRequestDto requestDto,
                int codEntidadGp,
                OrdenPago ordenPago) {
        ProcedimientosResponse response =
                gestorProcedimientoClient.getProcedimientos(
                        authorization,
                        requestDto.getIdComponente(),
                        codEntidadGp,
                        STRING, STRING, ordenPago.getTextSearch());

        if (response.getProcedimientos().isEmpty()) {
            throw new OrdenPagoNotFoundException(PROCEDURE_NOT_FOUND);
        }

        return response;
    }

    private void cagarGPDatos(OrdenPago ordenPago, ProcedimientosResponse procedimientosResponse, String user) {
        Procedimiento procedimiento = procedimientosResponse.getProcedimientos().get(0);
        ordenPago.setGpTupa(procedimiento.getTupa());
        ordenPago.setGpFormato(procedimiento.getFormato());
        ordenPago.setGpProcedimientoId(String.valueOf(procedimiento.getProcedimientoId()));
        ordenPago.setGpDescProcedimiento(procedimiento.getNombreCut());
        ordenPago.setUsuidModAud(separador(user, 1));
    }

    private void enriquecerOrdenPagoConTasa(OrdenPago ordenPago, Tasa tasa, String user) {
        ordenPago.setMonto(tasa.getMonto());
        ordenPago.setGpMonto(BigDecimal.valueOf(tasa.getMonto()));
        ordenPago.setGpMonedaSigno(tasa.getMonedaSigno());
        ordenPago.setGpEtiquetaTasa(tasa.getEtiqueta());
        ordenPago.setGpProcedimientoTasaVersion(String.valueOf(tasa.getProcedimientoTasaVersion()));
        ordenPago.setGpProcedimientoVersion(String.valueOf(tasa.getProcedimientoVersion()));
        ordenPago.setGpSecuencia(String.valueOf(tasa.getSecuencia()));
        ordenPago.setUsuidModAud(separador(user, 1));
    }
}    