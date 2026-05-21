package pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import pe.gob.vuce.cp.sp.pagos.domain.exception.OrdenPagoNotFoundException;
import pe.gob.vuce.cp.sp.pagos.domain.model.OrdenPago;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.OrdenPagoRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.api.mapper.OrdenPagoMapper;
import pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.entity.OrdenPagoEntity;

/**
 * Adaptador de repositorio para la entidad OrdenPago.
 * Este adaptador implementa la interfaz OrdenPagoRepositoryPort,
 * proporcionando una capa de acceso a datos utilizando JPA.
 * 
 * @author CPLX
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Component
@AllArgsConstructor
public class JpaOrdenPagoRepositoryAdapter implements OrdenPagoRepositoryPort {
    private final JpaOrdenPagoRepository repository;
    private final OrdenPagoMapper mapper;
    public static final String MESSAGE = " No existe";
    private final Environment env;
    /**
     * Guarda una nueva OrdenPago en el repositorio.
     *
     * @param ordenPago El modelo de dominio que se va a guardar.
     * @return La OrdenPago guardada como modelo de dominio.
     */
    @Override
    public OrdenPago save(OrdenPago ordenPago) {
        OrdenPagoEntity entity = mapper.modelToEntity(ordenPago);
        return mapper.entityToModel(repository.save(entity));
    }

    /**
     * Encuentra una OrdenPago por su ID.
     *
     * @param ordenPagoId El ID de la OrdenPago a buscar.
     * @return La OrdenPago encontrada como modelo de dominio.
     * @throws OrdenPagoNotFoundException Si no se encuentra la OrdenPago.
     */
    @Override
    public OrdenPago findById(Integer ordenPagoId) {
        return repository.findById(ordenPagoId)
                .map(mapper::entityToModel)
                .orElseThrow(() -> new OrdenPagoNotFoundException("El ID " + ordenPagoId + MESSAGE));
    }

    /**
     * Encuentra una OrdenPago por su ID interno.
     *
     * @param ordenPagoInterna El ID interno de la OrdenPago a buscar.
     * @return La OrdenPago encontrada como modelo de dominio.
     * @throws OrdenPagoNotFoundException Si no se encuentra la OrdenPago interna.
     */
    @Override
    public OrdenPago findByPpIdOrdenPagoInterna(Integer ordenPagoInterna) {
        return repository.findByPpIdOrdenPagoInterna(ordenPagoInterna)
                .map(mapper::entityToModel)
                .orElseThrow(
                        () -> new OrdenPagoNotFoundException("La orden de pago interna " + ordenPagoInterna + MESSAGE));
    }

    /**
     * Encuentra una lista de OrdenPago filtradas por ID de escala y documento.
     *
     * @param escalaId   El ID de la escala.
     * @param documentId El ID del documento.
     * @return Una lista de OrdenPago encontradas como modelos de dominio.
     */
    @Override
    public List<OrdenPago> findByEscalaIdAndDocumentoId(Integer escalaId, Integer documentId) {
        List<OrdenPagoEntity> ordenPagoEntities = repository.findByEscalaIdAndDocumentoId(escalaId, documentId);
        return mapper.listEntityToListModel(ordenPagoEntities);
    }

    /**
     * Actualiza una OrdenPago existente con los nuevos datos proporcionados.
     *
     * @param ordenPago El modelo de dominio que contiene los nuevos datos.
     * @return La OrdenPago actualizada como modelo de dominio.
     * @throws OrdenPagoNotFoundException Si no se encuentra la OrdenPago.
     */
    // @Override
    public OrdenPago update(OrdenPago ordenPago) {
        return repository.findById(ordenPago.getOrdenPagoId())
                .map(entity -> mapper.entityToModel(repository.save(modelToEntityUpdate(entity, ordenPago))))
                .orElseThrow(() -> new OrdenPagoNotFoundException("El ID " + ordenPago.getOrdenPagoId() + MESSAGE));
    }

    /**
     * Actualiza los campos de una OrdenPagoEntity con los datos de una OrdenPago.
     *
     * @param entity La entidad de la OrdenPago que se va a actualizar.
     * @param op     El modelo de dominio que contiene los nuevos datos.
     * @return La entidad actualizada.
     */
    public OrdenPagoEntity modelToEntityUpdate(OrdenPagoEntity entity, OrdenPago op) {
        entity.setGpTupa(op.getGpTupa());
        entity.setGpFormato(op.getGpFormato());
        entity.setGpMonto(op.getGpMonto());
        entity.setGpMonedaSigno(op.getGpMonedaSigno());
        entity.setGpEtiquetaTasa(op.getGpEtiquetaTasa());
        entity.setGpProcedimientoTasaVersion(op.getGpProcedimientoTasaVersion());
        entity.setGpProcedimientoVersion(op.getGpProcedimientoVersion());
        entity.setGpDescProcedimiento(op.getGpDescProcedimiento());
        entity.setGpSecuencia(op.getGpSecuencia());
        entity.setGpProcedimientoId(op.getGpProcedimientoId());
        entity.setEstadoOrdenPago(op.getEstado());
        entity.setPpCpb(op.getCpb());
        entity.setPpMonto(op.getMonto() != null ? BigDecimal.valueOf(op.getMonto()) : null);
        entity.setPpCodOrdenPago(op.getCodigoOrdenPago());
        entity.setPpIdOrdenPagoInterna(op.getOrdenPagoInternaId());
        entity.setPdfCpbFilenetGuid(op.getFilenetGuid());
        entity.setFechaGuardadoPdfCpb(op.getFechaGuardadoPdfCpb());
        entity.setPpFechaRespuestaCreacionCpb(op.getPpFechaRespuestaCreacionCpb());
        entity.setPpFechaConfGeneracionCpb(op.getPpFechaConfGeneracionCpb());
        entity.setPpEstadoCpbTexto(op.getPpEstadoCpbTexto());
        entity.setFechaPagado(op.getFechaPagado());
        entity.setFechaAnulacionCpb(op.getFechaAnulacionCpb());
        entity.setFechaExtornoOrdenPago(op.getFechaExtornoOrdenPago());
        entity.setPpCodigorechazoSinConexion(op.getPpCodigorechazoSinConexion());
        entity.setPpDescCortaError(op.getPpDescCortaError());
        entity.setPpMensajeRechazoSinConexion(op.getPpMensajeRechazoSinConexion());
        entity.setPpCpb(op.getCpb());
        entity.setPpCodOrdenPago(op.getCodigoOrdenPago());
        entity.setPpIdOrdenPagoInterna(op.getOrdenPagoInternaId());
        entity.setPpFechaRespuestaCreacionCpb(op.getPpFechaRespuestaCreacionCpb());
        entity.setUsuidModAud(op.getUsuidModAud());
        entity.setFechaModAud(Instant.now());
        entity.setUsubdModAud(env.getProperty("spring.datasource.username"));
        return entity;
    }


    @Override
    public boolean existeEscalaTupaCero(Integer escalaId, BigDecimal gpMonto) {
        return repository.existsByEscalaIdAndGpMonto(escalaId, gpMonto);
    }

    @Override
    public boolean existeEscalaTupaCero(Integer escalaId, BigDecimal gpMonto, Integer documentoId) {
        return repository.existsByEscalaIdAndGpMontoAndDocumentoId(escalaId, gpMonto, documentoId);
    }

}
