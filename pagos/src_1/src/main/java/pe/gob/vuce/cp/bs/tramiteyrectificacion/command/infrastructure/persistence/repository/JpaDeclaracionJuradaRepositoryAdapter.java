package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.constants.Constants;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.DeclaracionJuradaModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.DeclaracionJuradaRepositoryPort;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.mapper.DeclaracionJuradaMapper;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.entity.DeclaracionJurada;

@Component
@AllArgsConstructor
public class JpaDeclaracionJuradaRepositoryAdapter implements DeclaracionJuradaRepositoryPort {

    private final DeclaracionJuradaRepository declaracionJuradaRepository;
    private final DeclaracionJuradaMapper declaracionJuradaMapper;

    /**
     * Busca una declaración jurada por su ID.
     *
     * @param id ID de la declaración jurada.
     * @return Optional con el modelo de la declaración jurada si existe.
     */
    @Override
    public Optional<DeclaracionJuradaModel> findById(Integer id) {
        return declaracionJuradaRepository.findById(id).map(declaracionJuradaMapper::entityToModel);
    }

    /**
     * Guarda o actualiza una declaración jurada.
     *
     * @param declaracionJurada Modelo de la declaración jurada a guardar.
     * @return Modelo de la declaración jurada persistida.
     */
    @Override
    public DeclaracionJuradaModel save(DeclaracionJuradaModel declaracionJurada) {
        DeclaracionJurada declaracionJuradaToSave = declaracionJuradaMapper.modelToEntity(declaracionJurada);
        DeclaracionJurada declaracionJuradaSaved = declaracionJuradaRepository
                .save(declaracionJuradaToSave);
        return declaracionJuradaMapper.entityToModel(declaracionJuradaSaved);
    }

    /**
     * Obtiene una lista de declaraciones juradas asociadas a un ID de trámite.
     *
     * @param id ID del trámite.
     * @return Lista de modelos de declaraciones juradas.
     */
    @Override
    public List<DeclaracionJuradaModel> findByTramiteTramiteId(Integer id) {
        return declaracionJuradaMapper.entityListToModelList(declaracionJuradaRepository.findByTramiteTramiteId(id));
    }

    /**
     * Obtiene una lista de declaraciones juradas asociadas a un ID de escala.
     *
     * @param id ID de la escala.
     * @return Lista de modelos de declaraciones juradas.
     */
    @Override
    public List<DeclaracionJuradaModel> findByEscalaId(Integer id) {
        return declaracionJuradaMapper.entityListToModelList(declaracionJuradaRepository.findByEscalaId(id));
    }

    /**
     * Obtiene declaraciones juradas filtradas por documento, escala y RUC del agente.
     *
     * @param documentoId ID del documento.
     * @param escalaId ID de la escala.
     * @param rucAgente RUC del agente.
     * @return Lista de modelos de declaraciones juradas filtradas.
     */
    @Override
    public List<DeclaracionJuradaModel> findByDocumentoIdAndEscalaIdAndRucAgente(Integer documentoId, Integer escalaId,
            String rucAgente) {
        return declaracionJuradaMapper.entityListToModelList(declaracionJuradaRepository
                .findByDocumentoDocumentoIdAndEscalaIdAndRucAgenteAndEstado(documentoId, escalaId, rucAgente, Constants.VALOR_POR_DEFECTO_ESTADO));
    }

    /**
     * Cuenta la cantidad de declaraciones juradas registradas entre dos fechas.
     *
     * @param startDate Fecha de inicio.
     * @param endDate Fecha de fin.
     * @return Cantidad de declaraciones registradas en el rango.
     */
    @Override
    public int countByFechaSolicitudDdjjBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return declaracionJuradaRepository.countByFechaSolicitudDdjjBetween(startDate, endDate);
    }
}
