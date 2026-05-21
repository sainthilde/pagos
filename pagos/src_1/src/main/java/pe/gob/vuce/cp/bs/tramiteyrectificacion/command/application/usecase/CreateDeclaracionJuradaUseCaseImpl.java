package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.application.usecase;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants.Constants;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants.ErrorCodes;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.DeclaracionJuradaRequestDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.SeguimientoRequestDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.enums.DeclaracionJuradaEstados;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.enums.EstadosDue;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.enums.TipoSeguimiento;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.exceptions.BusinessError;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.ActividadEntidadPuertoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.DeclaracionJuradaModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.DocumentoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.CreateDeclaracionJuradaUseCase;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.CreateSeguimientoUseCase;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.ActividadEntidadPuertoPort;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.DeclaracionJuradaRepositoryPort;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.DocumentoRepositoryPort;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.EscalaRepositoryPort;

import static pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants.Constants.DOCUMENT_VALUE;

@Component
@AllArgsConstructor
public class CreateDeclaracionJuradaUseCaseImpl implements CreateDeclaracionJuradaUseCase {

    private final DeclaracionJuradaRepositoryPort declaracionJuradaRepositoryPort;
    private final CreateSeguimientoUseCase createSeguimientoUseCase;
    private final EscalaRepositoryPort escalaRepository;
    private final DocumentoRepositoryPort documentoRepository;
    private final ActividadEntidadPuertoPort actividadEntidadPuertoPort;

    @Override
    @Transactional
    public DeclaracionJuradaModel save(DeclaracionJuradaRequestDto declaracionJuradaDto,String user) {
        DeclaracionJuradaModel declaracionJurada = new DeclaracionJuradaModel();
        if (declaracionJuradaDto.getId() != null) {
            declaracionJurada = declaracionJuradaRepositoryPort
                    .findById(declaracionJuradaDto.getId())
                    .orElseThrow(() -> new BusinessError(
                            HttpStatus.NOT_FOUND,
                            "DECLARACION_JURADA_NOT_FOUND",
                            List.of(declaracionJuradaDto.getId().toString()),
                            "Declaración Jurada no encontrada"));
        }

        try {
            Instant now = Instant.now();
            LocalDateTime dateNow = now.atZone(ZoneId.of(Constants.ZONA_HORARIA_PERU)).toLocalDateTime();
            Integer anioSuce = dateNow.getYear();
            LocalDateTime startDate = LocalDateTime.of(anioSuce, 1, 1, 0, 0);
            LocalDateTime endDate = LocalDateTime.of(anioSuce, 12, 31, 23, 59, 59);
            Integer anioSecSuce = declaracionJuradaRepositoryPort.countByFechaSolicitudDdjjBetween(startDate, endDate);

            int tamanioSecuenciaDJ = Constants.TAMANIO_SECUENCIA_DJ;
            String formato = String.format("%%s%%s%%0%dd", tamanioSecuenciaDJ);
            String numSuceCreated = String.format(formato, Constants.DECLARACION_JURADA, anioSuce, anioSecSuce);

            declaracionJurada.setNumeroDdjj(numSuceCreated);
            declaracionJurada.setFechaSolicitudDdjj(dateNow);

            declaracionJurada.setEstadoDdjjPago(declaracionJuradaDto.getEstadoDdjjPago());
            declaracionJurada.setMotivoDeclaracion(declaracionJuradaDto.getMotivoDeclaracion());
            declaracionJurada.setMensajeError(declaracionJuradaDto.getMensajeError());

            if (declaracionJuradaDto.getId() == null) {
                return crearDeclaracionJurada(declaracionJuradaDto, declaracionJurada,user);
            }

            Integer idSeguimiento = null;

            if (!declaracionJurada.getEstadoDdjjPago().isBlank()) {
                if (declaracionJurada.getEstadoDdjjPago()
                        .contentEquals(DeclaracionJuradaEstados.ACEPTADA.getCodigo())) {
                    declaracionJurada.setFechaAprobacionDdjj(dateNow);
                    idSeguimiento = TipoSeguimiento.ACEPTAR_DDJJ.getValue();
                }
                if (declaracionJurada.getEstadoDdjjPago()
                        .contentEquals(DeclaracionJuradaEstados.DENEGADA.getCodigo())) {
                    declaracionJurada.setFechaDenegacionDdjj(dateNow);
                    idSeguimiento = TipoSeguimiento.DENEGAR_DDJJ.getValue();
                }
            }
            declaracionJurada.setUsuidModAud(declaracionJuradaDto.getUsuario());
            DeclaracionJuradaModel savedDDJJ = declaracionJuradaRepositoryPort.save(declaracionJurada);

            SeguimientoRequestDto ddjjSeguimiento = generarRequestSeguimiento(
                    savedDDJJ.getEscalaId(),
                    idSeguimiento,
                    getEntradaSalida(savedDDJJ.getEscalaId()),
                    savedDDJJ.getRucAgente(),
                    savedDDJJ.getNumeroDdjj(),
                    savedDDJJ.getDocumento().getDescAcronimo());

            createSeguimientoUseCase.create(ddjjSeguimiento, user);

            return savedDDJJ;
        } catch (Exception e) {
            throw new BusinessError(HttpStatus.INTERNAL_SERVER_ERROR,
                    ErrorCodes.INTERNAL_SERVER_ERROR,
                    List.of(),
                    e.getMessage());
        }
    }

    private DeclaracionJuradaModel crearDeclaracionJurada(DeclaracionJuradaRequestDto declaracionJuradaDto,
            DeclaracionJuradaModel declaracionJurada,String user) {
        declaracionJurada.setRucAgente(declaracionJuradaDto.getRucAgente());
        declaracionJurada.setEstado(declaracionJuradaDto.getEstado());
        declaracionJurada.setUsuidRegAud(declaracionJuradaDto.getUsuario());
        DocumentoModel documentoModel = documentoRepository
                .findById(declaracionJuradaDto.getDocumento().getDocumentoId()).orElse(null);
        declaracionJurada.setDocumento(documentoModel);
        declaracionJurada.setEscalaId(declaracionJuradaDto.getEscalaId());

        ActividadEntidadPuertoModel actividadEntidadPuertoModel = actividadEntidadPuertoPort
                .findByActividadIdAndCodPuertoNacionalAndEstado(
                        declaracionJuradaDto.getActivityId(),
                        declaracionJuradaDto.getCodPuerto(),
                        Constants.VALOR_POR_DEFECTO_ESTADO);

        declaracionJurada.setEntidadId(actividadEntidadPuertoModel.getEntidadId());

        DeclaracionJuradaModel savedDDJJ = declaracionJuradaRepositoryPort.save(declaracionJurada);

        SeguimientoRequestDto ddjjSeguimiento = generarRequestSeguimiento(
                savedDDJJ.getEscalaId(),
                TipoSeguimiento.GENERAR_DDJJ.getValue(),
                getEntradaSalida(savedDDJJ.getEscalaId()),
                savedDDJJ.getRucAgente(),
                savedDDJJ.getNumeroDdjj(),
                savedDDJJ.getDocumento().getDescAcronimo());

        createSeguimientoUseCase.create(ddjjSeguimiento, user);

        return savedDDJJ;
    }

    public SeguimientoRequestDto generarRequestSeguimiento(
            Integer escalaId,
            Integer tipoSeguimiento,
            String indicadorEntradaSalida,
            String ruc,
            String nroDDJJ,
            String acronimoDocumento) {
        SeguimientoRequestDto seguimientoRequestDto = new SeguimientoRequestDto();
        seguimientoRequestDto.setTipoSegId(tipoSeguimiento);
        seguimientoRequestDto.setRucUsuario(ruc);
        seguimientoRequestDto.setIndNil(Boolean.FALSE);
        seguimientoRequestDto.setEscalaId(escalaId);
        seguimientoRequestDto.setAcronimoDocumento(acronimoDocumento);
        seguimientoRequestDto.setIndicadorEs(indicadorEntradaSalida);
        seguimientoRequestDto.setComentario(getComentario(nroDDJJ, acronimoDocumento, tipoSeguimiento));
        seguimientoRequestDto.setEstado(Constants.VALOR_POR_DEFECTO_ESTADO);
        return seguimientoRequestDto;
    }

    // Comentario: solicitud de ddjj (aprobada/denegada en base a tipoSeguimiento)
    // <num_dec_jurada> Documento: DMS mensaje deberia decir
    public String getComentario(String nroDDJJ, String acronimoDocumento, Integer tipoSeguimiento) {
        if (tipoSeguimiento.equals(TipoSeguimiento.GENERAR_DDJJ.getValue())) {
            return "Solicitud de ddjj generada " + nroDDJJ + DOCUMENT_VALUE + acronimoDocumento;
        }
        if (tipoSeguimiento.equals(TipoSeguimiento.ACEPTAR_DDJJ.getValue())) {
            return "Solicitud de ddjj aprobada " + nroDDJJ + DOCUMENT_VALUE + acronimoDocumento;
        }
        return "Solicitud de ddjj denegada " + nroDDJJ + DOCUMENT_VALUE + acronimoDocumento;
    }

    public String getEntradaSalida(Integer escalaId) {
        Integer estadoId = escalaRepository.getEstadoDueId(escalaId);

        List<Integer> estadosEntrada = List.of(
                EstadosDue.ARRIBO_ANUNCIADO.getId(),
                EstadosDue.ARRIBO_CONFIRMADO.getId(),
                EstadosDue.ARRIBO_AUTORIZADO.getId(),
                EstadosDue.ARRIBADO.getId());

        if (estadosEntrada.contains(estadoId)) {
            return Constants.ENTRADA_NAVE;
        }

        return Constants.SALIDA_NAVE;
    }

}
