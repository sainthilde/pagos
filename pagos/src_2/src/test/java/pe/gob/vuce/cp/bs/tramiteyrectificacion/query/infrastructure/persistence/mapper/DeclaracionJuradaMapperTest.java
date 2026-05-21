package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.DeclaracionJuradaModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.escala.Escala;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.fictec.FichaTecnicaDet;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.mae.Agencia;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.mae.Documento;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.pago.DeclaracionJurada;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.pago.Tramite;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.DeclaracionJuradaDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.model.DeclaracionJuradaListaDto;

class DeclaracionJuradaMapperTest {

    private final DeclaracionJuradaMapper mapper = Mappers.getMapper(DeclaracionJuradaMapper.class);

    @Test
    void toModelTest() {
        DeclaracionJurada declaracionJurada = new DeclaracionJurada();
        declaracionJurada.setId(1);
        declaracionJurada.setNumeroDdjj("12345");
        declaracionJurada.setEstadoDdjjPago("A");
        declaracionJurada.setMotivoDeclaracion("Test Motive");
        declaracionJurada.setMensajeError("Error Message");
        declaracionJurada.setFechaSolicitudDdjj(LocalDateTime.now());
        declaracionJurada.setRucAgente("12345678901");
        declaracionJurada.setEstado("1");

        Documento documento = new Documento();
        documento.setId(10);
        documento.setNombreDocumento("Document Name");
        documento.setDescCorta("Short Description");
        documento.setDescAcronimo("DOC");
        documento.setEstado("S");
        documento.setUsuidRegAud("user123");
        documento.setUsuidModAud("user456");
        declaracionJurada.setDocumento(documento);

        DeclaracionJuradaModel model = mapper.toModel(declaracionJurada);

        assertNotNull(model);
        assertEquals(declaracionJurada.getId(), model.getId());
        assertEquals(declaracionJurada.getNumeroDdjj(), model.getNumeroDeclaracionJurada());
        assertEquals(declaracionJurada.getEstadoDdjjPago(), model.getEstadoDeclaracionJurada());
        assertEquals(declaracionJurada.getMotivoDeclaracion(), model.getMotivo());
        assertEquals(declaracionJurada.getMensajeError(), model.getError());
        assertEquals("Short Description", declaracionJurada.getDocumento().getDescCorta());
        assertEquals("DOC", declaracionJurada.getDocumento().getDescAcronimo());
        assertEquals("S", declaracionJurada.getDocumento().getEstado());
        assertEquals("user123", declaracionJurada.getDocumento().getUsuidRegAud());
        assertEquals("user456", declaracionJurada.getDocumento().getUsuidModAud());
        assertEquals(declaracionJurada.getDocumento().getId(), model.getDocumentoId());
        assertEquals(declaracionJurada.getDocumento().getNombreDocumento(), model.getDocumentoNombre());
        assertEquals(declaracionJurada.getFechaSolicitudDdjj(), model.getFechaDeclaracionJurada());
        assertEquals(declaracionJurada.getRucAgente(), model.getRucAgente());
    }

    @Test
    void toModelListTest() {
        DeclaracionJurada declaracionJurada = new DeclaracionJurada();
        declaracionJurada.setId(1);
        declaracionJurada.setNumeroDdjj("12345");
        declaracionJurada.setEstadoDdjjPago("A");

        Documento documento = new Documento();
        documento.setId(10);
        documento.setNombreDocumento("Document Name");
        declaracionJurada.setDocumento(documento);

        List<DeclaracionJurada> entityList = Collections.singletonList(declaracionJurada);
        List<DeclaracionJuradaModel> modelList = mapper.toModelList(entityList);

        assertNotNull(modelList);
        assertEquals(1, modelList.size());

        DeclaracionJuradaModel model = modelList.get(0);
        assertEquals(declaracionJurada.getId(), model.getId());
        assertEquals(declaracionJurada.getNumeroDdjj(), model.getNumeroDeclaracionJurada());
        assertEquals(declaracionJurada.getEstadoDdjjPago(), model.getEstadoDeclaracionJurada());
        assertEquals(declaracionJurada.getDocumento().getId(), model.getDocumentoId());
        assertEquals(declaracionJurada.getDocumento().getNombreDocumento(), model.getDocumentoNombre());
    }

    @Test
    void toDeclaracionJuradaDtoListTest() {
        DeclaracionJuradaModel model = new DeclaracionJuradaModel();
        model.setId(1);
        model.setNumeroDeclaracionJurada("12345");
        model.setEstadoDeclaracionJurada("A");
        model.setMotivo("Test Motive");
        model.setError("Error Message");
        model.setDocumentoId(10);
        model.setDocumentoNombre("Document Name");
        model.setFechaDeclaracionJurada(LocalDateTime.now());
        model.setRucAgente("12345678901");

        List<DeclaracionJuradaModel> modelList = Collections.singletonList(model);
        List<DeclaracionJuradaDto> dtoList = mapper.toDeclaracionJuradaDtoList(modelList);

        assertNotNull(dtoList);
        assertEquals(1, dtoList.size());

        DeclaracionJuradaDto dto = dtoList.get(0);
        assertEquals(model.getId(), dto.getId());
        assertEquals(model.getNumeroDeclaracionJurada(), dto.getNumeroDeclaracionJurada());
        assertEquals(model.getEstadoDeclaracionJurada(), dto.getEstadoDeclaracionJurada());
        assertEquals(model.getMotivo(), dto.getMotivo());
        assertEquals(model.getError(), dto.getError());
        assertEquals(model.getDocumentoNombre(), dto.getDocumentoNombre());
    }

    /**
     * Updated unit test for mapping a DeclaracionJurada entity to a
     * DeclaracionJuradaListaDto.
     *
     * <p>
     * Note that the mapping for <code>entidadId</code> has been changed so that it
     * is now sourced from the entity’s top-level <code>entidadId</code> property.
     * </p>
     */
    @Test
    void toDeclaracionJuradaListaDtoTest() {
        // Setup test data
        DeclaracionJurada declaracionJurada = new DeclaracionJurada();
        declaracionJurada.setId(1);
        declaracionJurada.setMotivoDeclaracion("Test Motive");
        declaracionJurada.setMensajeError("Error Message");
        declaracionJurada.setNumeroDdjj("12345");
        declaracionJurada.setEstadoDdjjPago("A");
        declaracionJurada.setRucAgente("20123456789");
        declaracionJurada.setEntidadId(300);

        // Set dates
        LocalDateTime fechaRegAud = LocalDateTime.of(2025, 2, 3, 12, 30, 45);
        LocalDateTime fechaAprobacion = LocalDateTime.of(2025, 2, 3, 13, 45, 0);
        declaracionJurada.setFechaRegAud(fechaRegAud);
        declaracionJurada.setFechaAprobacionDdjj(fechaAprobacion);

        // Setup documento
        Documento documento = new Documento();
        documento.setId(10);
        documento.setNombreDocumento("Document Name");
        declaracionJurada.setDocumento(documento);

        // Setup escala and ficha tecnica
        Escala escala = new Escala();
        escala.setPuertoEscalaId("PUERTO1");
        escala.setAnnoEscala(2025);
        escala.setNumeroEscala(1);
        FichaTecnicaDet ficha = new FichaTecnicaDet();
        ficha.setNombreNave("Nave Test");
        escala.setFichaTecnicaDetIn(ficha);
        declaracionJurada.setEscala(escala);

        // Setup tramite
        Tramite tramite = new Tramite();
        tramite.setId(100);
        Agencia agencia = new Agencia();
        agencia.setId(200);
        tramite.setAgencia(agencia);
        declaracionJurada.setTramite(tramite);

        // Perform mapping
        DeclaracionJuradaListaDto dto = mapper.toDeclaracionJuradaListaDto(declaracionJurada);

        // Assertions
        assertNotNull(dto);
        assertEquals("Test Motive", dto.getMotivo());
        assertEquals("Error Message", dto.getError());
        assertEquals(100, dto.getTramiteId());
        assertEquals("PUERTO1-2025-00001", dto.getDue());
        assertEquals("Document Name", dto.getNombreTramite());
        assertEquals("Nave Test", dto.getNombreNave());
        assertEquals("12345", dto.getNumeroDeclaracionJurada());
        assertEquals("A", dto.getEstadoDeclaracionJurada());
        assertEquals("20123456789", dto.getAgenciaRuc());
        assertEquals(300, dto.getEntidadId());

        // Date assertions
        assertNotNull(dto.getFechaDeclaracionJurada());
        assertNotNull(dto.getFechaAceptacionDenegacion());
    }

    @Test
    void toDeclaracionJuradaListaDtoWithNullDatesTest() {
        DeclaracionJurada declaracionJurada = new DeclaracionJurada();
        declaracionJurada.setId(1);
        // Don't set fechaAprobacionDdjj and fechaDenegacionDdjj

        DeclaracionJuradaListaDto dto = mapper.toDeclaracionJuradaListaDto(declaracionJurada);

        assertNotNull(dto);
        assertEquals(null, dto.getFechaAceptacionDenegacion());
    }
}
