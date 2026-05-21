package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.DeclaracionJuradaModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.port.out.DeclaracionJuradaRepositoryPort;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.escala.Escala;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.mae.Documento;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.pago.DeclaracionJurada;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.mapper.DeclaracionJuradaMapper;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.specification.DjjSpecification;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.DeclaracionJuradaListaDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.GetDjjQueryParamsDto;

/**
 * Clase adaptador que implementa el puerto de salida y se comunica con la base
 * de datos para obtener las declaraciones juradas.
 * 
 * @project cp-api-bs-tramiteyrectificacion-query
 * @autor Luis Francisco Huertas Mostacero
 * @date 24/08/2024
 */
@Repository
@AllArgsConstructor
public class JPADeclaracionJuradaRepositoryAdapter implements DeclaracionJuradaRepositoryPort {

    private final JPADeclaracionJuradaRepository jpaDeclaracionJuradaRepository;
    private final DeclaracionJuradaMapper declaracionJuradaMapper;
    private final DjjSpecification djjSpecification;

    @Override
    public List<DeclaracionJuradaModel> findByEscala(Integer escalaId) {
        Escala escala = new Escala();
        escala.setEscalaId(escalaId);
        List<DeclaracionJurada> declaracionJuradaList = jpaDeclaracionJuradaRepository.findByEscala(escala);
        return declaracionJuradaMapper.toModelList(declaracionJuradaList);
    }

    @Override
    public List<DeclaracionJuradaModel> findDeclaracionJurada(Integer escalaId, String estado, Integer documentoId,
            String estadoDdjjPago, String rucAgente) {
        Escala escala = new Escala();
        escala.setEscalaId(escalaId);
        Documento documento = null;
        if (documentoId != null) {
            documento = new Documento();
            documento.setId(documentoId);
        }
        List<DeclaracionJurada> declaracionJuradaList = jpaDeclaracionJuradaRepository
                .findByEscalaAndEstadoAndDocumentoAndEstadoDdjjPagoAndRucAgente(escala, estado, documento,
                        estadoDdjjPago, rucAgente);
        return declaracionJuradaMapper.toModelList(declaracionJuradaList);
    }

    @Override
    public Page<DeclaracionJuradaListaDto> getDjjs(GetDjjQueryParamsDto params) {
        int pageNumber = params.getPageNumber() - 1;
        int pageSize = params.getPageSize();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Specification<DeclaracionJurada> specification = djjSpecification.getDjjs(params);
        Page<DeclaracionJurada> djjPage = jpaDeclaracionJuradaRepository.findAll(specification, pageable);

        return djjPage.map(declaracionJuradaMapper::toDeclaracionJuradaListaDto);

    }
}
