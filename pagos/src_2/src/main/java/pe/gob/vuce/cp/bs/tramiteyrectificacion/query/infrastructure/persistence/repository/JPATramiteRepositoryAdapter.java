package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.repository;

import static pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.constants.Constants.ESTADO_ACTIVO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.TramiteModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.port.out.TramiteRepositoryPort;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.pago.Tramite;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.mapper.TramiteModelMapper;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.specification.TramiteSpecification;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.GetTramiteQueryParamsDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.TramitePagoDto;

/**
 * Implementación del puerto de repositorio para Tramite utilizando JPA.
 * Actúa como un adaptador que convierte las entidades persistentes en modelos
 * de dominio.
 * 
 * @project cp-api-bs-tramiteyrectificacion-query
 * @autor Luis Francisco Huertas Mostacero
 * @date 24/08/2024
 */
@Repository
public class JPATramiteRepositoryAdapter implements TramiteRepositoryPort {

    private final JPATramiteRepository jpaTramiteRepository;
    private final TramiteModelMapper tramiteModelMapper;
    private final TramiteSpecification tramiteSpecification;

    /**
     * Constructor que inicializa las dependencias del adaptador.
     * 
     * @param jpaTramiteRepository Repositorio JPA para Tramite.
     * @param tramiteModelMapper   Mapper para convertir entre entidades y modelos
     *                             de dominio.
     * @param tramiteSpecification Especificación para construir consultas
     *                             dinámicas.
     */
    public JPATramiteRepositoryAdapter(JPATramiteRepository jpaTramiteRepository,
            TramiteModelMapper tramiteModelMapper,
            TramiteSpecification tramiteSpecification) {
        this.jpaTramiteRepository = jpaTramiteRepository;
        this.tramiteModelMapper = tramiteModelMapper;
        this.tramiteSpecification = tramiteSpecification;
    }

    /**
     * Obtiene un trámite específico basado en su ID y lo convierte a un modelo de
     * dominio.
     * 
     * @param id Identificador del trámite.
     * @return Una lista con un único TramiteModel correspondiente al trámite
     *         encontrado.
     */
    @Override
    public List<TramiteModel> obtenerTramite(Integer id) {
        Tramite tramite = jpaTramiteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tramite not found"));
        return tramiteModelMapper.toTramiteModels(tramite);
    }

    /**
     * Obtiene una lista paginada de trámites basada en los parámetros de consulta y
     * los convierte a modelos de dominio.
     * 
     * @param getTramiteQueryParamsDto Objeto que contiene los parámetros de
     *                                 consulta.
     * @return Una página de TramiteModel que coincide con los parámetros de
     *         consulta.
     */
    @Override
    public Page<TramiteModel> obtenerTramites(GetTramiteQueryParamsDto getTramiteQueryParamsDto) {
        int pageNumber = getTramiteQueryParamsDto.getPageNumber() - 1;
        int pageSize = getTramiteQueryParamsDto.getPageSize();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Specification<Tramite> specification = tramiteSpecification.getTramites(getTramiteQueryParamsDto);
        Page<Tramite> tramitePage = jpaTramiteRepository.findAll(specification, pageable);

        List<TramiteModel> tramiteModels = tramitePage.stream()
                .flatMap(tramite -> tramiteModelMapper.toTramiteModels(tramite).stream())
                .toList();

        return new PageImpl<>(tramiteModels, pageable, tramitePage.getTotalElements());
    }

    @Override
    public Optional<TramiteModel> findByEscalaIdAndDocumentoId(Integer escalaId, Integer documentoId) {
        return jpaTramiteRepository
                .findByEscalaEscalaIdAndDocumentoIdAndEstado(escalaId, documentoId, ESTADO_ACTIVO)
                .map(tramiteModelMapper::entityToTramiteModel);
    }

    @Override
    public TramitePagoDto getIndNoRequierePagoByEscalaIdAndIndicadorEs(Integer escalaId, String indicadorEs,
            Integer documentoId) {
        return jpaTramiteRepository
                .findByEscalaEscalaIdAndIndicadorEsAndDocumentoId(escalaId, indicadorEs, documentoId)
                .map(tramiteModelMapper::entityToTramitePagoDto)
                .orElse(null);
    }
}
