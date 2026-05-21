package pe.gob.vuce.cp.sp.pagos.application.usecase;

import com.fasterxml.jackson.core.JsonProcessingException;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.domain.exception.FeignExceptionHandler;
import pe.gob.vuce.cp.sp.pagos.domain.model.EscalaModel;
import pe.gob.vuce.cp.sp.pagos.domain.model.FichaTecnicaDetModel;
import pe.gob.vuce.cp.sp.pagos.domain.model.OrdenPago;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.ProcesarOrdenSunatUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.UpdateOrdenPagoUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.UpdateArchivoPDFUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.EscalaRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.FeignRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.FichaTecnicaDetRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.OrdenPagoRequestDTO;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ProcedimientosResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.TasaResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.response.OrdenPagoResponseDTO;
import java.time.LocalDateTime;
import java.time.ZoneId;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.PENDIENTE_PAGO;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.PP;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.CR;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.EP;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.EN_PROCESO;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.DUE;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.MT;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.EX;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagosSunat.ATZONE;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagosSunat.NAVE;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagosSunat.RUC;

/**
 * Implementación del caso de uso para crear órdenes de pago.
 * Esta clase se encarga de la lógica necesaria para crear
 * una nueva OrdenPago y persistirla en el repositorio correspondiente.
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Component
@AllArgsConstructor
public class ProcesarOrdenSunatUseCaseImpl implements ProcesarOrdenSunatUseCase {

    private final FeignRepositoryPort feignRepositoryPort;
    private final UpdateOrdenPagoUseCase updateOrdenPagoUseCase;
    private final UpdateArchivoPDFUseCase updateArchivoPDFUseCase;
    private final FeignExceptionHandler feignExceptionHandler;
    private final EscalaRepositoryPort escalaRepository;
    private final FichaTecnicaDetRepositoryPort fichaTecnicaDetRepositoryPort;



    @Override
    public void procesarOrdenSunat(ProcedimientosResponse.Procedimiento procedimiento, TasaResponse.Tasa tasa, OrdenPago ordenPago, Integer cantidadOrden, String user) throws JsonProcessingException {
        EscalaModel escala = escalaRepository.findById(ordenPago.escalaId);
        FichaTecnicaDetModel fichaTecnicaDet = fichaTecnicaDetRepositoryPort.findByFichaTecnicaId(escala.getFichaTecnicaDetIn().getFichaTecnicaId());
        OrdenPagoRequestDTO ordenPagoRequestDto = getOrdenPagoRequestDto(procedimiento, ordenPago,escala,fichaTecnicaDet, user);

        try {
            // Crea la orden de pago en SUNAT a través del cliente.
            OrdenPagoResponseDTO ordenPagoResponseDto = feignRepositoryPort.createOrdenPago(ordenPagoRequestDto);
            ordenPago.setCpb(ordenPagoResponseDto.getCpb());
            ordenPago.setCodigoOrdenPago(ordenPagoResponseDto.getCodigoOrdenPago());
            ordenPago.setOrdenPagoInternaId(ordenPagoResponseDto.getOrdenPagoId());
            ordenPago.setPpFechaRespuestaCreacionCpb(LocalDateTime.now().atZone(ZoneId.of(ATZONE)).toInstant());
            // Actualiza el estado de la orden de pago basado en la respuesta.
            String estado = ordenPagoResponseDto.getEstado();
            ordenPago.setPpEstadoCpbTexto(estado != null && estado.length() > 30 ? estado.substring(0, 30) : estado);
            ordenPago.setEstado(CR);
            if (estado != null && estado.equalsIgnoreCase(PENDIENTE_PAGO)) {
                ordenPago.setMonto(ordenPagoResponseDto.getMonto());
                ordenPago.setEstado(PP);
                ordenPago.setPpFechaConfGeneracionCpb(LocalDateTime.now().atZone(ZoneId.of(ATZONE)).toInstant());
            } else if (estado != null && estado.equalsIgnoreCase(EN_PROCESO)) {
                ordenPago.setEstado(EP);
            }
            updateOrdenPagoUseCase.updateOrdenPago(ordenPago);

            if (ordenPago.getEstado().equals(PP) || ordenPago.getEstado().equals(EX)) {
                ordenPago = updateArchivoPDFUseCase.updateArchivoPDF(ordenPagoResponseDto.getOrdenPagoId(), ordenPago);
                updateOrdenPagoUseCase.updateOrdenPago(ordenPago);
            }

        } catch (FeignException e) {
            feignExceptionHandler.handleFeignClientException(e, ordenPago);
            updateOrdenPagoUseCase.updateOrdenPago(ordenPago);
            throw e;
        }
    }

    /**
     * Crea y configura un objeto {@code OrdenPagoRequestDTO} con los datos
     * necesarios para la solicitud de orden de pago.
     *
     * @param procedimiento Datos del procedimiento.
     * @param ordenPago     Datos de la orden de pago.
     * @return El objeto {@code OrdenPagoRequestDTO} configurado.
     */
    static OrdenPagoRequestDTO getOrdenPagoRequestDto(ProcedimientosResponse.Procedimiento procedimiento, OrdenPago ordenPago, EscalaModel escala, FichaTecnicaDetModel fichaTecnicaDet, String user) {
        String numeroEscala="";
        try {
            if (escala.getNumeroEscala() != null) {
                String numStr = escala.getNumeroEscala().toString().trim();
                int num = Integer.parseInt(numStr);
                numeroEscala = String.format("%04d", num);
            }
        } catch (NumberFormatException e) {
            numeroEscala = "0000";
        }
        OrdenPagoRequestDTO ordenPagoRequestDto = new OrdenPagoRequestDTO();
        ordenPagoRequestDto.setEntidadId(procedimiento.getEntidadId());
        ordenPagoRequestDto.setPerfilId(456);
        ordenPagoRequestDto.setFormato(procedimiento.getFormato());
        ordenPagoRequestDto.setDesFormato(procedimiento.getNombreCut());
        ordenPagoRequestDto.setTupa(procedimiento.getTupa());

        Double monto = ordenPago.getMonto();
        if (monto != null) {
            monto = Math.ceil(monto);
        }
        ordenPagoRequestDto.setMontoExacto(monto);
        ordenPagoRequestDto.setFechaVigencia(ordenPago.getFechaVigencia());
        ordenPagoRequestDto.setCodDocumento(RUC);
        ordenPagoRequestDto.setNombreUsuario(user);
        ordenPagoRequestDto.setNroDocumento(ordenPago.getRucAgente());
        ordenPagoRequestDto.setTipoCodigoReferencia(MT);
        ordenPagoRequestDto.setTipoReferencia1(DUE);
        ordenPagoRequestDto.setCodReferencia1(escala.getPuertoEscalaId()+"-"+escala.getAnoEscala()+"-"+numeroEscala);
        ordenPagoRequestDto.setTipoReferencia2(NAVE);
        ordenPagoRequestDto.setCodReferencia2(fichaTecnicaDet.getNombreNave());
        ordenPagoRequestDto.setComponenteId(3);
        ordenPagoRequestDto.setTipoOperador("1");
        return ordenPagoRequestDto;
    }


}
