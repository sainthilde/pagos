package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.DocumentoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.DocumentoRepositoryPort;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.mapper.DocumentoMapper;

/**
 * Adaptador JPA que implementa el puerto {@link DocumentoRepositoryPort}
 * para acceder a los datos de documentos desde la base de datos.
 *
 * Este adaptador traduce entre el modelo de dominio y las entidades JPA
 * utilizando {@link DocumentoRepository} y {@link DocumentoMapper}.
 *
 */
@Component
@AllArgsConstructor
public class JpaDocumentoRepositoryAdapter implements DocumentoRepositoryPort {

    private final DocumentoRepository documentoRepository;
    private final DocumentoMapper documentoMapper;

    /**
     * Busca un documento por su ID.
     *
     * @param id El ID del documento a buscar.
     * @return Un {@link Optional} con el modelo del documento si se encuentra,
     *         de lo contrario un {@link Optional} vacío.
     */
    @Override
    public Optional<DocumentoModel> findById(Integer id) {
        return documentoRepository.findById(id).map(documentoMapper::entityToModel);
    }

    /**
     * Busca documentos cuyo acrónimo coincida con alguno en la lista proporcionada.
     *
     * @param acronimos Lista de acrónimos a buscar.
     * @return Lista de modelos de documentos encontrados.
     */
    public List<DocumentoModel> findByDescAcronimoIn(List<String> acronimos) {
        return documentoMapper.entityToModel(documentoRepository.findByDescAcronimoIn(acronimos));
    }
}
