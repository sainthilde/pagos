package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.application.usecase;

import static pe.gob.vuce.cp.bs.tramiteyrectificacion.command.application.usecase.UpdateTramiteUseCaseImpl.generarRequestSeguimiento;
import static pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants.Constants.separador;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants.Constants;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants.ErrorCodes;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.SeguimientoRequestDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.enums.TipoSeguimiento;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.enums.TramiteEstados;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.exceptions.BusinessError;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.DeclaracionJuradaModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.DocumentoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.OrdenDePagoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.TramiteModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.CreateSeguimientoUseCase;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.CreateTramiteUseCase;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.ObtenerDocumentoUseCase;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.DeclaracionJuradaRepositoryPort;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.OrdenPagoRepositoryPort;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.TramiteRepositoryPort;

/**
 * Implementación del caso de uso para la creación de trámite.
 * 
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 19/08/2024
 */
@AllArgsConstructor
@Component
public class CreateTramiteUseCaseImpl implements CreateTramiteUseCase {

    private final TramiteRepositoryPort tramiteRepositoryPort;

    private final OrdenPagoRepositoryPort ordenPagoRepositoryPort;
    private final DeclaracionJuradaRepositoryPort declaracionJuradaRepositoryPort;
    private final CreateSeguimientoUseCase createSeguimientoUseCase;
    private final ObtenerDocumentoUseCase obtenerDocumentoUseCase;

    /**
     * Crea un nuevo tramite en el sistema.
     * 
     * @param tramiteModel Modelo que contiene los datos del tramite a crear.
     * @return El modelo de tramite creado.
     * @throws BusinessError en caso de que ocurra un error durante la creación del
     *                       tramite.
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Fernando Tanta
     * @date 19/08/2024
     */
    @Override
    @Transactional
    public TramiteModel create(TramiteModel tramiteModel, String ruc, String user) {

        try {
            LocalDateTime dateNow = Instant.now().atZone(ZoneId.of(Constants.ZONA_HORARIA_PERU)).toLocalDateTime();
            String numSuceCreated = getNumSuce(dateNow);
            TramiteModel tramiteDocu = tramiteRepositoryPort.findByEscalaIdAndDocumentoId(tramiteModel.getEscalaId(),
                    tramiteModel.getDocumentoId());
            if (tramiteDocu == null) {
                tramiteModel.setNumeroSuce(numSuceCreated);
                tramiteModel.setEstadoTramite(TramiteEstados.EN_TRAMITE.getCodigo());
                tramiteModel.setFechaTramite(dateNow);
                tramiteModel.setEstado(Constants.VALOR_POR_DEFECTO_ESTADO);

                TramiteModel createdTramite = tramiteRepositoryPort.save(tramiteModel);
                Optional<DocumentoModel> documento = obtenerDocumentoUseCase.findById(createdTramite.getDocumentoId());
                String indicador;
                if (createdTramite.getDocumentoId() == 93 || createdTramite.getDocumentoId() == 64) {
                    indicador = Constants.SALIDA_NAVE;
                } else {
                    indicador = Constants.ENTRADA_NAVE;
                }
                if (documento.isPresent()) {
                    SeguimientoRequestDto fichaSanitariaSeguimiento = generarRequestSeguimiento(
                            createdTramite.getEscalaId(),
                            TipoSeguimiento.GENERAR_TRAMITE.getValue(),
                            indicador,
                            ruc,
                            createdTramite.getNumeroSuce(),
                            documento.get().getDescAcronimo(),
                            createdTramite.getNumeroTramiteEntidad());
                    createSeguimientoUseCase.create(fichaSanitariaSeguimiento, user);
                }
                updateOrdenDePago(createdTramite);
                updateDeclaracionJurada(createdTramite, user);
                return createdTramite;
            } else {
                updateOrdenDePago(tramiteDocu);
                updateDeclaracionJurada(tramiteDocu, user);

                return tramiteDocu;
            }
        } catch (Exception e) {
            throw new BusinessError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCodes.INTERNAL_SERVER_ERROR, List.of(),
                    e.getMessage());
        }
    }

    private String getNumSuce(LocalDateTime dateNow) {
        LocalDateTime startDate = LocalDateTime.of(dateNow.getYear(), 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(dateNow.getYear(), 12, 31, 23, 59, 59);

        Integer anioSecSuce = tramiteRepositoryPort.getNumeroTramitePorAnio(startDate, endDate);

        int tamanioSecuenciaDJ = Constants.TAMANIO_SECUENCIA_SUCE;
        String formato = String.format("%%s%%s%%0%dd", tamanioSecuenciaDJ);
        return String.format(formato, Constants.COMPONENTE_PORTUARIO, dateNow.getYear(), anioSecSuce);

    }

    private void updateOrdenDePago(TramiteModel createdTramite) {
        List<OrdenDePagoModel> ordenDePagoList = ordenPagoRepositoryPort
                .findByDocumentoIdAndEscalaIdAndRucAgente(
                        createdTramite.getDocumentoId(),
                        createdTramite.getEscalaId(),
                        createdTramite.getRucAgente());

        if (ordenDePagoList != null && !ordenDePagoList.isEmpty()) {
            for (OrdenDePagoModel ordenDePago : ordenDePagoList) {
                if (ordenDePago != null && ordenDePago.getTramite() == null) {
                    ordenDePago.setTramite(createdTramite);
                    ordenPagoRepositoryPort.updateV2(ordenDePago);
                }
            }
        }
    }

    private void updateDeclaracionJurada(TramiteModel createdTramite, String user) {
        List<DeclaracionJuradaModel> declaracionJuradaList = declaracionJuradaRepositoryPort
                .findByDocumentoIdAndEscalaIdAndRucAgente(
                        createdTramite.getDocumentoId(),
                        createdTramite.getEscalaId(),
                        createdTramite.getRucAgente());
        if (declaracionJuradaList != null && !declaracionJuradaList.isEmpty()) {
            for (DeclaracionJuradaModel declaracionJurada : declaracionJuradaList) {
                if (declaracionJurada != null && declaracionJurada.getTramite() == null) {
                    declaracionJurada.setTramite(createdTramite);
                    declaracionJurada.setUsuidModAud(separador(user, 1));
                    declaracionJurada.setFechaModAud(Instant.now());
                    declaracionJuradaRepositoryPort.save(declaracionJurada);
                }
            }
        }
    }
}
