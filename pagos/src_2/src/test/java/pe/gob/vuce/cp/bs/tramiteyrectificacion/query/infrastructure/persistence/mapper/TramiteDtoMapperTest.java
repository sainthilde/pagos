package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.DeclaracionJuradaModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.TramiteModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.DeclaracionJuradaDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.TramiteDetalleDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.TramiteDto;

class TramiteDtoMapperTest {

    private TramiteDtoMapper tramiteDtoMapper;

    @BeforeEach
    void setUp() {
        tramiteDtoMapper = Mappers.getMapper(TramiteDtoMapper.class);
    }

    @Test
    void testToDeclaracionJuradaDto() {
        // Arrange
        DeclaracionJuradaModel declaracionJuradaModel = new DeclaracionJuradaModel();
        declaracionJuradaModel.setId(1);
        declaracionJuradaModel.setNumeroDeclaracionJurada("DJJ-001");
        declaracionJuradaModel.setEstadoDeclaracionJurada("Aprobada");
        declaracionJuradaModel.setRucAgente("12345678901");
        declaracionJuradaModel.setMotivo("Motivo 1");
        declaracionJuradaModel.setError("No errors");
        declaracionJuradaModel.setDocumentoId(101);
        declaracionJuradaModel.setDocumentoNombre("Documento 1");
        declaracionJuradaModel.setFechaDeclaracionJurada(LocalDateTime.now());

        // Act
        DeclaracionJuradaDto declaracionJuradaDto = tramiteDtoMapper.toDeclaracionJuradaDto(declaracionJuradaModel);

        // Assert
        assertEquals(declaracionJuradaModel.getId(), declaracionJuradaDto.getId());
        assertEquals(declaracionJuradaModel.getNumeroDeclaracionJurada(),
                declaracionJuradaDto.getNumeroDeclaracionJurada());
        assertEquals(declaracionJuradaModel.getEstadoDeclaracionJurada(),
                declaracionJuradaDto.getEstadoDeclaracionJurada());
        assertEquals(declaracionJuradaModel.getMotivo(), declaracionJuradaDto.getMotivo());
        assertEquals(declaracionJuradaModel.getError(), declaracionJuradaDto.getError());
        assertEquals(declaracionJuradaModel.getDocumentoNombre(), declaracionJuradaDto.getDocumentoNombre());
    }

    @Test
    void testToTramiteDto() {
        // Arrange
        TramiteModel tramiteModel = new TramiteModel();
        tramiteModel.setId(1);
        tramiteModel.setDue("DUE-001");
        tramiteModel.setNombreNave("Nave 1");
        tramiteModel.setNumeroSuce("SUCE-001");
        tramiteModel.setNumeroTramite("TRAMITE-001");
        tramiteModel.setEntidadId(100);
        tramiteModel.setEntidadNombre("Entidad 1");
        tramiteModel.setEntidadRuc("12345678901");
        tramiteModel.setTupa("TUPA-001");
        tramiteModel.setEstadoTramite("En Proceso");
        tramiteModel.setMonto(1000.0);
        tramiteModel.setEstadoDePago("Pagado");
        tramiteModel.setAgenciaId(200);
        tramiteModel.setAgenciaNombre("Agencia 1");
        tramiteModel.setAgenciaRuc("98765432109");
        tramiteModel.setFechaDeclaracionJuradaActual(LocalDateTime.now());
        tramiteModel.setDescripcion("Descripción del trámite");
        tramiteModel.setCpb("CPB-001");
        tramiteModel.setFueTramiteManual(true);
        tramiteModel.setDeclaracionesJuradas(Collections.emptyList());

        // Act
        TramiteDto tramiteDto = tramiteDtoMapper.toTramiteDto(tramiteModel);

        // Assert
        assertEquals(tramiteModel.getId(), tramiteDto.getId());
        assertEquals(tramiteModel.getDue(), tramiteDto.getDue());
        assertEquals(tramiteModel.getNombreNave(), tramiteDto.getNombreNave());
        assertEquals(tramiteModel.getNumeroSuce(), tramiteDto.getNumeroSuce());
        assertEquals(tramiteModel.getNumeroTramite(), tramiteDto.getNumeroTramite());
        assertEquals(tramiteModel.getEntidadNombre(), tramiteDto.getEntidadNombre());
        assertEquals(tramiteModel.getTupa(), tramiteDto.getTupa());
        assertEquals(tramiteModel.getEstadoTramite(), tramiteDto.getEstadoTramite());
        assertEquals(tramiteModel.getEstadoDePago(), tramiteDto.getEstadoDePago());
        assertEquals(tramiteModel.getAgenciaNombre(), tramiteDto.getAgenciaNombre());
        assertEquals(tramiteModel.getDescripcion(), tramiteDto.getDescripcion());
        assertEquals(tramiteModel.getCpb(), tramiteDto.getCpb());
    }

    @Test
    void testToDeclaracionJuradaModel() {
        // Arrange
        DeclaracionJuradaDto declaracionJuradaDto = new DeclaracionJuradaDto();
        declaracionJuradaDto.setId(1);
        declaracionJuradaDto.setNumeroDeclaracionJurada("DJJ-001");
        declaracionJuradaDto.setEstadoDeclaracionJurada("Aprobada");
        declaracionJuradaDto.setMotivo("Motivo 1");
        declaracionJuradaDto.setError("No errors");
        declaracionJuradaDto.setDocumentoNombre("Documento 1");
        declaracionJuradaDto.setFechaDeclaracionJurada(new Date());

        // Act
        DeclaracionJuradaModel declaracionJuradaModel = tramiteDtoMapper.toDeclaracionJuradaModel(declaracionJuradaDto);

        // Assert
        assertEquals(declaracionJuradaDto.getId(), declaracionJuradaModel.getId());
        assertEquals(declaracionJuradaDto.getNumeroDeclaracionJurada(),
                declaracionJuradaModel.getNumeroDeclaracionJurada());
        assertEquals(declaracionJuradaDto.getEstadoDeclaracionJurada(),
                declaracionJuradaModel.getEstadoDeclaracionJurada());
        assertEquals(declaracionJuradaDto.getMotivo(), declaracionJuradaModel.getMotivo());
        assertEquals(declaracionJuradaDto.getError(), declaracionJuradaModel.getError());
        assertEquals(declaracionJuradaDto.getDocumentoNombre(), declaracionJuradaModel.getDocumentoNombre());
    }

    @Test
    void testToTramiteDtoList() {
        // Arrange
        TramiteModel tramiteModel = new TramiteModel();
        tramiteModel.setId(1);
        List<TramiteModel> tramiteModels = Collections.singletonList(tramiteModel);

        // Act
        List<TramiteDto> tramiteDtos = tramiteDtoMapper.toTramiteDtoList(tramiteModels);

        // Assert
        assertEquals(tramiteModels.size(), tramiteDtos.size());
        assertEquals(tramiteModel.getId(), tramiteDtos.get(0).getId());
    }

    @Test
    void testToTramiteDetalleDtoList() {
        // Arrange
        TramiteModel tramiteModel = new TramiteModel();
        tramiteModel.setId(1);
        List<TramiteModel> tramiteModels = Collections.singletonList(tramiteModel);

        // Act
        List<TramiteDetalleDto> tramiteDetalleDtos = tramiteDtoMapper.toTramiteDetalleDtoList(tramiteModels);

        // Assert
        assertEquals(tramiteModels.size(), tramiteDetalleDtos.size());
        assertEquals(tramiteModel.getId(), tramiteDetalleDtos.get(0).getId());
    }

    @Test
    void testToDeclaracionJuradaDtoList() {
        // Arrange
        DeclaracionJuradaModel declaracionJuradaModel = new DeclaracionJuradaModel();
        declaracionJuradaModel.setId(1);
        List<DeclaracionJuradaModel> declaracionJuradaModels = Collections.singletonList(declaracionJuradaModel);

        // Act
        List<DeclaracionJuradaDto> declaracionJuradaDtos = tramiteDtoMapper
                .toDeclaracionJuradaDtoList(declaracionJuradaModels);

        // Assert
        assertEquals(declaracionJuradaModels.size(), declaracionJuradaDtos.size());
        assertEquals(declaracionJuradaModel.getId(), declaracionJuradaDtos.get(0).getId());
    }

    @Test
    void testToTramiteDetalleDto() {
        // Arrange
        TramiteModel tramiteModel = new TramiteModel();
        tramiteModel.setId(1);
        tramiteModel.setDue("DUE-001");
        tramiteModel.setNombreNave("Nave 1");
        tramiteModel.setNumeroSuce("SUCE-001");
        tramiteModel.setNumeroTramite("TRAMITE-001");
        tramiteModel.setEntidadId(100);
        tramiteModel.setEntidadNombre("Entidad 1");
        tramiteModel.setEntidadRuc("12345678901");
        tramiteModel.setTupa("TUPA-001");
        tramiteModel.setEstadoTramite("En Proceso");
        tramiteModel.setMonto(1000.0);
        tramiteModel.setEstadoDePago("Pagado");
        tramiteModel.setAgenciaId(200);
        tramiteModel.setAgenciaNombre("Agencia 1");
        tramiteModel.setAgenciaRuc("98765432109");
        tramiteModel.setFechaDeclaracionJuradaActual(LocalDateTime.now());
        tramiteModel.setDescripcion("Descripción del trámite");
        tramiteModel.setCpb("CPB-001");
        tramiteModel.setFueTramiteManual(true);
        tramiteModel.setDeclaracionesJuradas(Collections.emptyList());

        // Act
        TramiteDetalleDto tramiteDetalleDto = tramiteDtoMapper.toTramiteDetalleDto(tramiteModel);

        // Assert
        assertEquals(tramiteModel.getId(), tramiteDetalleDto.getId());
        assertEquals(tramiteModel.getDue(), tramiteDetalleDto.getDue());
        assertEquals(tramiteModel.getNombreNave(), tramiteDetalleDto.getNombreNave());
        assertEquals(tramiteModel.getNumeroSuce(), tramiteDetalleDto.getNumeroSuce());
        assertEquals(tramiteModel.getNumeroTramite(), tramiteDetalleDto.getNumeroTramite());
        assertEquals(tramiteModel.getEntidadNombre(), tramiteDetalleDto.getEntidadNombre());
        assertEquals(tramiteModel.getTupa(), tramiteDetalleDto.getTupa());
        assertEquals(tramiteModel.getEstadoTramite(), tramiteDetalleDto.getEstadoTramite());
        assertEquals(tramiteModel.getEstadoDePago(), tramiteDetalleDto.getEstadoDePago());
        assertEquals(tramiteModel.getAgenciaNombre(), tramiteDetalleDto.getAgenciaNombre());
        assertEquals(tramiteModel.getDescripcion(), tramiteDetalleDto.getDescripcion());
    }
}
