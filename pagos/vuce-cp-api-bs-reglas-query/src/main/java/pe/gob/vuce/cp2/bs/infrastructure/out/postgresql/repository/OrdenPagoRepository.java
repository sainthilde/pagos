package pe.gob.vuce.cp2.bs.infrastructure.out.postgresql.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.gob.vuce.cp2.bs.infrastructure.out.postgresql.entity.OrdenPagoEntity;

@Repository
public interface OrdenPagoRepository extends JpaRepository<OrdenPagoEntity, Integer> {

    Optional<OrdenPagoEntity> 
    findByEntidadIdAndDocumentoIdAndEscalaIdAndEstadoOrdenPago(
            Integer entidadId,
            Integer documentoId,
            Integer escalaId,
            String estadoOrdenPago
    );
}