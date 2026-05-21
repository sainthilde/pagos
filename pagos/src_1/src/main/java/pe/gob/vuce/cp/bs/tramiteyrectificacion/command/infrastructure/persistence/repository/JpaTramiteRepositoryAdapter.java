package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants.Constants;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.TramiteModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.TramiteRepositoryPort;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.mapper.TramiteMapper;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.mapper.TramiteUpdateMapper;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity.Tramite;

/**
 * Adaptador JPA que implementa el puerto {@link TramiteRepositoryPort} para
 * la gestión de la entidad Tramite.
 * <p>
 * Utiliza {@link TramiteRepository} para acceder a la base de datos y
 * {@link TramiteMapper} y {@link TramiteUpdateMapper} para mapear entre entidad
 * y modelo.
 * </p>
 *
 * Provee operaciones de creación, actualización, búsqueda y conteo de trámites.
 *
 */
@Component
@AllArgsConstructor
public class JpaTramiteRepositoryAdapter implements TramiteRepositoryPort {

    private final TramiteRepository tramiteRepository;
    private final TramiteUpdateMapper tramiteUpdateMapper;
    private final TramiteMapper tramiteMapper;

    /**
     * Guarda un nuevo trámite.
     *
     * @param tramiteyrectificacion modelo de trámite a guardar.
     * @return modelo del trámite guardado.
     */
    @Override
    public TramiteModel save(TramiteModel tramiteyrectificacion) {
        Tramite tramite = tramiteMapper.modelToEntity(tramiteyrectificacion);
        return tramiteMapper.entityToModel(tramiteRepository.save(tramite));
    }

    /**
     * Actualiza un trámite existente o guarda uno nuevo si no existe.
     *
     * @param tramiteModel modelo con los datos a actualizar.
     * @return modelo del trámite actualizado o guardado.
     */
    @Override
    public TramiteModel update(TramiteModel tramiteModel) {
        Optional<Tramite> optionalExistingTramite = tramiteRepository.findById(tramiteModel.getTramiteId());

        Tramite tramiteToUpdate = tramiteMapper.modelToEntity(tramiteModel);

        return optionalExistingTramite.map(existingTramite -> {
            tramiteUpdateMapper.updateTramiteFromDto(tramiteToUpdate, existingTramite);
            return tramiteMapper.entityToModel(tramiteRepository.save(existingTramite));
        }).orElseGet(() -> tramiteMapper.entityToModel(tramiteRepository.save(tramiteToUpdate)));
    }

    /**
     * Busca un trámite por su ID.
     *
     * @param id identificador del trámite.
     * @return {@code Optional} con el trámite si se encuentra.
     */
    @Override
    public Optional<TramiteModel> findById(Integer id) {
        return tramiteRepository.findById(id).map(tramiteMapper::entityToModel);
    }

    @Override
    public Optional<TramiteModel> findByIdAndEscalaId(Integer tramiteId, Integer escalaId) {
        return tramiteRepository.findByTramiteIdAndEscalaId(tramiteId, escalaId).map(tramiteMapper::entityToModel);
    }

    /**
     * Busca los trámites asociados a una escala con estado "En trámite".
     *
     * @param id ID de la escala.
     * @return lista de trámites.
     */
    @Override
    public List<TramiteModel> findByEscalaId(Integer id) {
        return tramiteMapper
                .entityListToModelList(tramiteRepository.findAllByEscalaIdAndEstadoTramite(id, Constants.EN_TRAMITE));
    }

    /**
     * Obtiene el número consecutivo de trámite para un rango de fechas.
     *
     * @param inicio fecha inicial del rango.
     * @param fin    fecha final del rango.
     * @return número de trámite (cantidad de trámites en rango + 1).
     */
    @Override
    public Integer getNumeroTramitePorAnio(LocalDateTime inicio, LocalDateTime fin) {
        return (tramiteRepository.countByFechaTramiteBetween(inicio, fin) + 1);
    }

    /**
     * Busca un trámite activo asociado a una escala y un documento.
     *
     * @param escalaId    ID de la escala.
     * @param documentoId ID del documento.
     * @return trámite encontrado o null si no existe.
     */
    @Override
    public TramiteModel findByEscalaIdAndDocumentoId(Integer escalaId, Integer documentoId) {
        return tramiteMapper
                .entityToModel(tramiteRepository.findByEscalaIdAndDocumentoIdAndEstado(escalaId, documentoId,
                        Constants.VALOR_POR_DEFECTO_ESTADO));
    }

    /**
     * Busca todos los trámites asociados a una escala.
     *
     * @param escalaId ID de la escala.
     * @return lista de trámites.
     */
    @Override
    public List<TramiteModel> findAllByEscalaId(Integer escalaId) {
        return tramiteMapper.entityListToModelList(tramiteRepository.findAllByEscalaId(escalaId));
    }

    /**
     * Busca todos los trámites asociados a una escala y una lista de documentos.
     *
     * @param escalaId     ID de la escala.
     * @param documentoIds lista de IDs de documentos.
     * @return lista de trámites.
     */
    @Override
    public List<TramiteModel> findAllByEscalaIdAndDocumentoIdIn(Integer escalaId, List<Integer> documentoIds) {
        return tramiteMapper
                .entityListToModelList(tramiteRepository.findAllByEscalaIdAndDocumentoIdIn(escalaId, documentoIds));
    }
}
