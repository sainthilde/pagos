package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.application.usecase;

import static pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants.Constants.separador;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants.Constants;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants.ErrorCodes;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.OrdenPagoErrorResponse;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.SeguimientoRequestDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.enums.DeclaracionJuradaEstados;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.enums.Documentos;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.enums.Operaciones;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.enums.OrdenPagoEstados;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.enums.TipoSeguimiento;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.enums.TramiteEstados;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.exceptions.BusinessError;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.exceptions.OrdenPagoAnulacionException;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.DeclaracionJuradaModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.DocumentoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.OrdenDePagoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.TramiteModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.CreateSeguimientoUseCase;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.DesistTramiteUseCase;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.ObtenerDocumentoUseCase;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.UpdateTramiteUseCase;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.DeclaracionJuradaRepositoryPort;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.FeignOrdenPagoClientPort;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.OrdenPagoRepositoryPort;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.TramiteRepositoryPort;

/**
 * Implementación del caso de uso para la actualización de tramite.
 * 
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 22/08/2024
 */
@AllArgsConstructor
@Component
public class UpdateTramiteUseCaseImpl implements UpdateTramiteUseCase, DesistTramiteUseCase {

    private final TramiteRepositoryPort tramiteRepositoryPort;
    private final CreateSeguimientoUseCase createSeguimientoUseCase;
    private final ObtenerDocumentoUseCase obtenerDocumentoUseCase;

    private final OrdenPagoRepositoryPort ordenPagoRepositoryPort;
    private final DeclaracionJuradaRepositoryPort declaracionJuradaRepositoryPort;

    private final FeignOrdenPagoClientPort feignOrdenPagoClientPort;

    /**
     * Actualiza un tramite existente en el sistema.
     * 
     * @param tramiteModel Modelo que contiene los datos del tramite a actualizar.
     * @return El modelo del tramite actualizado.
     * @throws BusinessError en caso de que ocurra un error durante la actualización
     *                       del tramite.
     * @project cp-api-bs-tramiteyrectificacion-command
     * @autor Fernando Tanta
     * @date 22/08/2024
     */
    @Override
    @Transactional
    public TramiteModel update(TramiteModel tramiteModel, String ruc, String user, String operacion) {
        try {
            Integer idSeguimiento = null;

            if (Operaciones.ASIGNAR_MANUAL.getCodigo().equals(operacion)) {
                tramiteModel.setIndAsignacionTramiteManual(Constants.ES_REGISTRO_EXPEDIENTE_MANUAL);
                tramiteModel.setFechaActNumTramiteManual(
                        Instant.now().atZone(ZoneId.of(Constants.ZONA_HORARIA_PERU)).toLocalDateTime());
                idSeguimiento = TipoSeguimiento.ASIGNACION_TRAMITE.getValue();
            } else if (Operaciones.AUTORIZAR.getCodigo().equals(operacion)) {
                tramiteModel.setEstadoTramite(TramiteEstados.AUTORIZADO.getCodigo());
                idSeguimiento = TipoSeguimiento.AUTORIZAR_TRAMITE.getValue();
            }
            tramiteModel.setUsuidModAud(separador(user, 1));
            TramiteModel updatedTramite = tramiteRepositoryPort.update(tramiteModel);
            String indicador;
            Optional<DocumentoModel> documento = obtenerDocumentoUseCase.findById(updatedTramite.getDocumentoId());

            if (updatedTramite.getDocumentoId() == 93 || updatedTramite.getDocumentoId() == 64) {
                indicador = Constants.SALIDA_NAVE;
            } else {
                indicador = Constants.ENTRADA_NAVE;
            }
            if (documento.isPresent() && idSeguimiento != null) {
                SeguimientoRequestDto fichaSanitariaSeguimiento = generarRequestSeguimiento(
                        updatedTramite.getEscalaId(),
                        idSeguimiento,
                        indicador,
                        ruc,
                        updatedTramite.getNumeroSuce(),
                        documento.get().getDescAcronimo(),
                        updatedTramite.getNumeroTramiteEntidad());
                createSeguimientoUseCase.create(fichaSanitariaSeguimiento, user);
            }

            return updatedTramite;

        } catch (Exception e) {
            throw new BusinessError(HttpStatus.INTERNAL_SERVER_ERROR,
                    ErrorCodes.INTERNAL_SERVER_ERROR,
                    List.of(),
                    e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<TramiteModel> desist(Integer escalaId, Integer tramiteId, String user) {
        try {
            List<TramiteModel> tramiteList = fetchTramites(escalaId, tramiteId);

            processDeclaracionesJuradas(escalaId, user);
            List<Object> resultadosAnulacion = processOrdenesDePago(escalaId, user);

            processTramites(tramiteList, user);

            // Filtrar errores de anulación
            List<OrdenPagoErrorResponse> errores = resultadosAnulacion.stream()
                    .filter(OrdenPagoErrorResponse.class::isInstance)
                    .map(OrdenPagoErrorResponse.class::cast)
                    .collect(Collectors.toList());

            // Si hay errores, lanzar excepción con la información
            if (!errores.isEmpty()) {
                throw new OrdenPagoAnulacionException("Error al anular órdenes de pago", errores);
            }

            // Guardar resultados exitosos
            tramiteList.forEach(tramite -> tramite.setPpCpbPayments(resultadosAnulacion.stream()
                    .filter(String.class::isInstance)
                    .map(String.class::cast)
                    .collect(Collectors.toList())));

            return tramiteList;

        } catch (OrdenPagoAnulacionException e) {
            throw e; // Relanzar para manejar en el controller
        } catch (Exception e) {
            throw new BusinessError(HttpStatus.INTERNAL_SERVER_ERROR,
                    ErrorCodes.INTERNAL_SERVER_ERROR,
                    List.of(),
                    e.getMessage());
        }
    }

    static SeguimientoRequestDto generarRequestSeguimiento(
            Integer escalaId,
            Integer tipoSeguimiento,
            String indicadorES,
            String ruc,
            String nroSuce,
            String acronimoDocumento,
            String nroExpediente) {

        SeguimientoRequestDto seguimientoRequestDto = new SeguimientoRequestDto();
        seguimientoRequestDto.setTipoSegId(tipoSeguimiento);
        seguimientoRequestDto.setRucUsuario(ruc);
        seguimientoRequestDto.setIndNil(null);
        seguimientoRequestDto.setEscalaId(escalaId);
        seguimientoRequestDto.setAcronimoDocumento(acronimoDocumento);
        seguimientoRequestDto.setIndicadorEs(indicadorES);
        seguimientoRequestDto.setComentario(getComentario(nroSuce, acronimoDocumento, nroExpediente, tipoSeguimiento));
        seguimientoRequestDto.setEstado(Constants.VALOR_POR_DEFECTO_ESTADO);
        return seguimientoRequestDto;
    }

    /**
     * Obtiene el comentario correspondiente según el tipo de seguimiento.
     *
     * @param nroSuce       Tipo de seguimiento.
     * @param tipoDocumento Tipo de seguimiento.
     * @param nroExpediente Tipo de seguimiento.
     * @return El comentario generado.
     */
    static String getComentario(String nroSuce, String tipoDocumento, String nroExpediente, Integer tipoSeguimiento) {
        String comentario = "Tramite: " + nroSuce + ", Documento: " + tipoDocumento;
        if (tipoSeguimiento == 47) {
            comentario = "Solicitud de ddjj denegada " + nroSuce + " Documento: " + tipoDocumento;
        }
        if (nroExpediente != null && !nroExpediente.isEmpty()) {
            comentario += ", Numero de Expediente Entidad: " + nroExpediente;
        }
        return comentario;
    }

    private List<TramiteModel> fetchTramites(Integer escalaId, Integer tramiteId) {
        if (escalaId == null) {
            return new ArrayList<>();
        }
        if (tramiteId != null) {
            return fetchTramitesById(tramiteId);
        } else {
            return fetchTramitesByDocumentos(escalaId);
        }
    }

    private List<TramiteModel> fetchTramitesById(Integer tramiteId) {
        TramiteModel tramite = tramiteRepositoryPort.findById(tramiteId).orElse(null);
        return (tramite != null) ? List.of(tramite) : new ArrayList<>();
    }

    private List<TramiteModel> fetchTramitesByDocumentos(Integer escalaId) {
        List<Integer> documentosIds = fetchDocumentosIds();
        return tramiteRepositoryPort.findAllByEscalaIdAndDocumentoIdIn(escalaId, documentosIds);
    }

    private List<Integer> fetchDocumentosIds() {
        List<DocumentoModel> documentos = obtenerDocumentoUseCase.findByDescAcronimoIn(
                List.of(Documentos.DECLARACION_MARITIMA_SANIDAD.getValue(),
                        Documentos.DECLARACION_GENERAL_ARRIBO.getValue(),
                        Documentos.DECLARACION_GENERAL_ZARPE.getValue(),
                        Documentos.ACUERDO_MERP.getValue(),
                        Documentos.PATENTE_SANITARIA.getValue()));
        List<Integer> documentosIds = new ArrayList<>();
        documentos.forEach(doc -> documentosIds.add(doc.getDocumentoId()));
        return documentosIds;
    }

    private void processTramites(List<TramiteModel> tramiteList, String user) {
        for (TramiteModel tramite : tramiteList) {
            tramite.setEstadoTramite(TramiteEstados.DESISTIDO.getCodigo());
            tramite.setUsuidModAud(separador(user, 1));
            tramiteRepositoryPort.save(tramite);
            SeguimientoRequestDto tramiteSeguimiento = generarRequestSeguimiento(
                    tramite.getEscalaId(),
                    TipoSeguimiento.DESISTIDO_TRAMITE.getValue(),
                    Constants.ENTRADA_NAVE,
                    tramite.getRucAgente(),
                    tramite.getNumeroSuce(),
                    Constants.tipoDocumento(tramite.getDocumentoId()),
                    tramite.getNumeroTramiteEntidad());
            createSeguimientoUseCase.create(tramiteSeguimiento, user);
        }
    }

    public List<Object> processOrdenesDePago(Integer escalaId, String user) {
        // Primero, añadir los IDs de órdenes ya pagadas
        List<String> ordenesPagadasIds = getOrdenesPagadasIds(escalaId);
        List<Object> resultados = new ArrayList<>(ordenesPagadasIds);

        // Procesar órdenes pendientes o creadas y agregar errores si ocurren
        processOrdenesPendientes(escalaId, user, resultados);
        return resultados;
    }

    private List<String> getOrdenesPagadasIds(Integer escalaId) {
        List<OrdenDePagoModel> ordenesPagadas = ordenPagoRepositoryPort.findAllByEscalaIdAndEstadoOrdenPagoIn(
                escalaId, List.of(OrdenPagoEstados.PAGADO.getCodigo()));
        List<String> ids = new ArrayList<>();
        if (ordenesPagadas != null) {
            ordenesPagadas.forEach(op -> ids.add(op.getPpCpb()));
        }
        return ids;
    }

    private void processOrdenesPendientes(Integer escalaId, String user, List<Object> resultados) {
        List<OrdenDePagoModel> pendientes = ordenPagoRepositoryPort.findAllByEscalaIdAndEstadoOrdenPagoIn(
                escalaId, List.of(OrdenPagoEstados.CREADO.getCodigo(), OrdenPagoEstados.PENDIENTEPAGO.getCodigo()));
        if (pendientes == null) {
            return;
        }
        for (OrdenDePagoModel orden : pendientes) {
            if (orden == null) {
                continue;
            }
            if (OrdenPagoEstados.PENDIENTEPAGO.getCodigo().equals(orden.getEstadoOrdenPago())) {
                intentarAnulacion(orden, user, resultados);
            } else { // CREADO
                marcarAnulado(orden);
            }
            actualizarFechaYGuardar(orden);
        }
    }

    private void intentarAnulacion(OrdenDePagoModel orden, String user, List<Object> resultados) {
        Object resultado = feignOrdenPagoClientPort.anular(orden.getId(), user);
        if (resultado instanceof OrdenPagoErrorResponse) {
            orden.setEstadoOrdenPago(OrdenPagoEstados.PENDIENTEPAGO.getCodigo());
            resultados.add(resultado);
        } else {
            marcarAnulado(orden);
        }
    }

    private void marcarAnulado(OrdenDePagoModel orden) {
        orden.setEstadoOrdenPago(OrdenPagoEstados.ANULADO.getCodigo());
    }

    private void actualizarFechaYGuardar(OrdenDePagoModel orden) {
        orden.setFechaAnulacionCpb(Instant.now().atZone(ZoneId.of(Constants.ZONA_HORARIA_PERU)).toLocalDateTime());
        ordenPagoRepositoryPort.save(orden);
    }

    private void processDeclaracionesJuradas(Integer escalaId, String user) {
        List<DeclaracionJuradaModel> declaracionJuradaList = declaracionJuradaRepositoryPort.findByEscalaId(escalaId);
        LocalDateTime dateNow = Instant.now().atZone(ZoneId.of(Constants.ZONA_HORARIA_PERU)).toLocalDateTime();

        if (declaracionJuradaList != null) {
            declaracionJuradaList.stream()
                    .filter(declaracionJurada -> declaracionJurada != null &&
                            declaracionJurada.getEstadoDdjjPago().equals(DeclaracionJuradaEstados.PEDIENTE.getCodigo()))
                    .forEach(declaracionJurada -> {
                        declaracionJurada.setEstadoDdjjPago(DeclaracionJuradaEstados.DENEGADA.getCodigo());
                        declaracionJurada.setFechaDenegacionDdjj(dateNow);
                        declaracionJurada.setUsuidModAud(separador(user, 1));
                        declaracionJurada.setFechaModAud(Instant.now());
                        DeclaracionJuradaModel savedDDJJ = declaracionJuradaRepositoryPort.save(declaracionJurada);
                        SeguimientoRequestDto declaracionSeguimiento = generarRequestSeguimiento(
                                savedDDJJ.getEscalaId(),
                                TipoSeguimiento.DENEGAR_DDJJ.getValue(),
                                Constants.ENTRADA_NAVE,
                                savedDDJJ.getRucAgente(),
                                savedDDJJ.getNumeroDdjj(),
                                savedDDJJ.getDocumento().getDescAcronimo(),
                                "");
                        createSeguimientoUseCase.create(declaracionSeguimiento, savedDDJJ.getUsuidRegAud());
                    });
        }
    }

}
