package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.adapter;

import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.DOCUMENT_SEGUI;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.separador;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.SUNAT_PREFIX;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsHelpers.ORDEN_PAGO_ANULADA_CPD;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsHelpers.ORDEN_PAGO_NOT_FOUND;

import java.time.Instant;

import feign.FeignException;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import pe.gob.vuce.cp.sp.pagos.domain.constants.Constants;
import pe.gob.vuce.cp.sp.pagos.domain.constants.SeguimientoUtils;
import pe.gob.vuce.cp.sp.pagos.domain.exception.FeignExceptionHandler;
import pe.gob.vuce.cp.sp.pagos.domain.exception.OrdenPagoNotFoundException;
import pe.gob.vuce.cp.sp.pagos.domain.model.OrdenPago;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.CancelarOrdenPagoUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.CreateSeguimientoUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.AnularOrdenPagoRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.OrdenPagoRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.OrdenPagoResponseDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.SeguimientoRequestDto;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.mapper.OrdenPagoMapper;
import pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.response.OrdenPagoResponseDTO;

@AllArgsConstructor
@Component
public class AnularOrdenPagoRepositoryAdapter implements AnularOrdenPagoRepositoryPort {

    private final OrdenPagoRepositoryPort ordenPagoRepositoryPort;
    private final CancelarOrdenPagoUseCase cancelarOrdenPagoUseCase;
    private final CreateSeguimientoUseCase createSeguimientoUseCase;
    private final OrdenPagoMapper ordenPagoMapper;
    private final FeignExceptionHandler feignExceptionHandler;
    @Override
    public OrdenPagoResponseDTO anularOrdenPago(Integer ordenPagoId,String user) {

        OrdenPago ordenPago = ordenPagoRepositoryPort.findById(ordenPagoId);
        if (ordenPago == null) {
            throw new OrdenPagoNotFoundException(ORDEN_PAGO_NOT_FOUND);
        }
        try{
        OrdenPagoResponseDTO ordenPagoResponseDTO = cancelarOrdenPagoUseCase
                .cancelarOrdenPago(ordenPago.getOrdenPagoInternaId());

        ordenPago.setFechaAnulacionCpb(Instant.now());
        ordenPago.setEstado(Constants.AN);
        ordenPago.setPpEstadoCpbTexto(Constants.ANULADO);
        ordenPago.setUsuidModAud(separador(user,1));
        ordenPagoRepositoryPort.update(ordenPago);

        String cpb = ORDEN_PAGO_ANULADA_CPD;
        if (ordenPagoResponseDTO.getCpb() != null &&
                !ordenPagoResponseDTO.getCpb().trim().isEmpty() &&
                !"null".equalsIgnoreCase(ordenPagoResponseDTO.getCpb())) {
            cpb += ordenPagoResponseDTO.getCpb();
        }

        cpb += DOCUMENT_SEGUI + Constants.tipoDocumento(ordenPago.getDocumentoId());

        SeguimientoRequestDto seguimientoDto = SeguimientoUtils.generarRequestSeguimiento(
                ordenPago.getEscalaId(),
                SeguimientoUtils.ANULADO,
                Constants.indicador(ordenPago.getDocumentoId()),
                ordenPago.getRucAgente(),
                Constants.tipoDocumento(ordenPago.getDocumentoId()),
                cpb);

        createSeguimientoUseCase.create(seguimientoDto, user);

        return ordenPagoResponseDTO;
        } catch (
        FeignException e) {
            feignExceptionHandler.handleFeignClientException(e, ordenPago);
            ordenPagoRepositoryPort.update(ordenPago);
            throw e;
        } catch (OrdenPagoNotFoundException e) {
            // Optionally log to OrdenPago
            ordenPago.setPpCodigorechazoSinConexion("400");
            ordenPago.setPpDescCortaError(SUNAT_PREFIX + "Error funcional");
            ordenPago.setPpMensajeRechazoSinConexion(SUNAT_PREFIX + e.getMessage());
            ordenPagoRepositoryPort.update(ordenPago);
            throw e;
        }
    }

    @Override
    public OrdenPagoResponseDto anularOrdenPagoLocal(Integer ordenPagoId,String user) {
        OrdenPago ordenPago = ordenPagoRepositoryPort.findById(ordenPagoId);
        if (ordenPago == null) {
            throw new OrdenPagoNotFoundException(ORDEN_PAGO_NOT_FOUND);
        }

        ordenPago.setEstado(Constants.AN);
        ordenPago.setFechaAnulacionCpb(Instant.now());
        ordenPago.setUsuidModAud(separador(user,1));
        ordenPagoRepositoryPort.update(ordenPago);
        OrdenPago ordenPagoActualizado = ordenPagoRepositoryPort.findById(ordenPagoId);
        OrdenPagoResponseDto responseDto = ordenPagoMapper.modelToDto(ordenPagoActualizado);

        String cpb = ORDEN_PAGO_ANULADA_CPD;
        if (ordenPagoActualizado.getCpb() != null && !ordenPagoActualizado.getCpb().trim().isEmpty()
                && !"null".equals(ordenPagoActualizado.getCpb())) {
            cpb += ordenPagoActualizado.getCpb();
        }
        cpb += DOCUMENT_SEGUI + Constants.tipoDocumento(ordenPagoActualizado.documentoId);

        SeguimientoRequestDto seguimiento = SeguimientoUtils.generarRequestSeguimiento(
                ordenPagoActualizado.getEscalaId(),
                SeguimientoUtils.ANULADO,
                Constants.indicador(ordenPagoActualizado.documentoId),
                ordenPagoActualizado.getRucAgente(),
                Constants.tipoDocumento(ordenPagoActualizado.documentoId),
                cpb);
        createSeguimientoUseCase.create(seguimiento, user);

        return responseDto;
    }

}
